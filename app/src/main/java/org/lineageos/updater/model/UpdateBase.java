/*
 * SPDX-FileCopyrightText: The LineageOS Project
 * SPDX-License-Identifier: Apache-2.0
 */
package org.lineageos.updater.model;

public class UpdateBase implements UpdateBaseInfo {

    private String mName;
    private String mDownloadUrl;
    private String mDownloadId;
    private long mTimestamp;
    private String mType;
    private String mVersion;
    private long mFileSize;
    private String mChangelog;
    private String mChangelogUrl;

    public UpdateBase() {
    }

    public UpdateBase(UpdateBaseInfo update) {
        mName = update.getName();
        mDownloadUrl = update.getDownloadUrl();
        mDownloadId = update.getDownloadId();
        mTimestamp = update.getTimestamp();
        mType = update.getType();
        mVersion = update.getVersion();
        mFileSize = update.getFileSize();
        mChangelog = update.getChangelog();
        mChangelogUrl = update.getChangelogUrl();
    }

    @Override
    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    @Override
    public String getDownloadId() {
        return mDownloadId;
    }

    public void setDownloadId(String downloadId) {
        mDownloadId = downloadId;
    }

    @Override
    public long getTimestamp() {
        return mTimestamp;
    }

    public void setTimestamp(long timestamp) {
        mTimestamp = timestamp;
    }

    @Override
    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
    }

    @Override
    public String getVersion() {
        return mVersion;
    }

    public void setVersion(String version) {
        mVersion = version;
    }

    @Override
    public String getDownloadUrl() {
        return mDownloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        mDownloadUrl = downloadUrl;
    }

    @Override
    public long getFileSize() {
        return mFileSize;
    }

    public void setFileSize(long fileSize) {
        mFileSize = fileSize;
    }

    /**
     * Optional release notes for this build, or null when the feed carries none.
     * Never persisted: it is reconstituted from the cached JSON on every parse.
     */
    @Override
    public String getChangelog() {
        return mChangelog;
    }

    public void setChangelog(String changelog) {
        mChangelog = changelog;
    }

    @Override
    public String getChangelogUrl() {
        return mChangelogUrl;
    }

    public void setChangelogUrl(String changelogUrl) {
        mChangelogUrl = changelogUrl;
    }
}
