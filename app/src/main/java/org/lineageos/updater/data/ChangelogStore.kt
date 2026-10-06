/*
 * SPDX-FileCopyrightText: The LineageOS Project
 * SPDX-License-Identifier: Apache-2.0
 */

package org.lineageos.updater.data

import android.content.Context
import android.util.Log
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.io.File

/**
 * Per-build release notes from the OTA feed (the optional "changelog" and
 * "changelog_url" keys of a feed entry), keyed by download id (the file's
 * sha256), shown as "What's new" on an update.
 *
 * Kept beside the Room database instead of in it, so the updates schema stays
 * upstream's: a column of our own would need a migration that collides with
 * upstream's next one. Replaced wholesale on every successful feed fetch and
 * persisted to a small JSON file, so the notes survive an app restart.
 */
object ChangelogStore {
    private const val TAG = "ChangelogStore"
    private const val FILE_NAME = "changelogs.json"

    @Serializable
    data class Entry(
        val text: String,
        val url: String? = null,
    )

    private val json = Json { ignoreUnknownKeys = true }

    @Volatile
    private var entries: Map<String, Entry> = emptyMap()

    @Volatile
    private var file: File? = null

    fun init(context: Context) {
        val f = File(context.filesDir, FILE_NAME)
        file = f
        entries = try {
            if (f.exists()) json.decodeFromString<Map<String, Entry>>(f.readText()) else emptyMap()
        } catch (e: Exception) {
            Log.w(TAG, "Discarding unreadable $FILE_NAME", e)
            emptyMap()
        }
    }

    fun get(downloadId: String): Entry? = entries[downloadId]

    fun replace(newEntries: Map<String, Entry>) {
        if (newEntries == entries) return
        entries = newEntries
        try {
            file?.writeText(json.encodeToString(newEntries))
        } catch (e: Exception) {
            Log.w(TAG, "Could not persist $FILE_NAME", e)
        }
    }
}
