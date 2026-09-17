/*
 * SPDX-FileCopyrightText: The LineageOS Project
 * SPDX-License-Identifier: Apache-2.0
 */
package org.lineageos.updater.misc;

import android.graphics.Typeface;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.StyleSpan;

/**
 * Minimal, fail-soft rendering of the per-build changelog carried in the OTA
 * feed ("changelog" key). The text is a markdown-ish list produced by the
 * release scripts: "### section" headers and "- item" bullets. Anything the
 * prettifier does not recognise is shown verbatim, and any failure falls back
 * to the raw string, so a malformed feed can never crash the app.
 */
public final class Changelog {

    private Changelog() {
    }

    public static CharSequence prettify(String raw) {
        if (raw == null) {
            return "";
        }
        try {
            SpannableStringBuilder out = new SpannableStringBuilder();
            boolean first = true;
            for (String line : raw.split("\n")) {
                line = line.replace("\r", "");
                String trimmed = line.trim();
                if (trimmed.isEmpty()) {
                    continue;
                }
                if (!first) {
                    out.append('\n');
                }
                if (trimmed.startsWith("#")) {
                    String title = trimmed.replaceFirst("^#+\\s*", "");
                    if (!first) {
                        out.append('\n');
                    }
                    int start = out.length();
                    out.append(title);
                    out.setSpan(new StyleSpan(Typeface.BOLD), start, out.length(),
                            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                } else if (trimmed.startsWith("- ") || trimmed.startsWith("* ")) {
                    out.append("• ").append(trimmed.substring(2).trim());
                } else {
                    out.append(trimmed);
                }
                first = false;
            }
            return out.length() > 0 ? out : raw;
        } catch (RuntimeException e) {
            return raw;
        }
    }
}
