package com.tongming.bwghost.bean;

import java.util.List;

/**
 * Created by Tongming on 2016/7/23.
 */
public class SnapshotList {

    /**
     * error : 0
     * snapshots : [{"fileName":"snapshot-310354-1469259446-2016-07-23-3b360c68665a9d117643f5cd5657fb63c7985687.tar.gz","os":"centos-7-x86_64","description":"dGVzdA==","size":"1072506553","md5":"5a5bfd91aab71d9a1d45320ff778b08d","sticky":false,"uncompressed":2820485438,"purgesIn":2591768,"downloadLink":"http://45.78.0.203:8779/310354/snapshot-310354-1469259446-2016-07-23-3b360c68665a9d117643f5cd5657fb63c7985687.tar.gz"}]
     */

    private int error;
    /**
     * fileName : snapshot-310354-1469259446-2016-07-23-3b360c68665a9d117643f5cd5657fb63c7985687.tar.gz
     * os : centos-7-x86_64
     * description : dGVzdA==
     * size : 1072506553
     * md5 : 5a5bfd91aab71d9a1d45320ff778b08d
     * sticky : false
     * uncompressed : 2820485438
     * purgesIn : 2591768
     * downloadLink : http://45.78.0.203:8779/310354/snapshot-310354-1469259446-2016-07-23-3b360c68665a9d117643f5cd5657fb63c7985687.tar.gz
     */

    private List<SnapshotsBean> snapshots;

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public List<SnapshotsBean> getSnapshots() {
        return snapshots;
    }

    public void setSnapshots(List<SnapshotsBean> snapshots) {
        this.snapshots = snapshots;
    }

    public static class SnapshotsBean {
        private String fileName;
        private String os;
        private String description;
        private String size;
        private String md5;
        private boolean sticky;
        private long uncompressed;
        private int purgesIn;
        private String downloadLink;

        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }

        public String getOs() {
            return os;
        }

        public void setOs(String os) {
            this.os = os;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getSize() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
        }

        public String getMd5() {
            return md5;
        }

        public void setMd5(String md5) {
            this.md5 = md5;
        }

        public boolean isSticky() {
            return sticky;
        }

        public void setSticky(boolean sticky) {
            this.sticky = sticky;
        }

        public long getUncompressed() {
            return uncompressed;
        }

        public void setUncompressed(long uncompressed) {
            this.uncompressed = uncompressed;
        }

        public int getPurgesIn() {
            return purgesIn;
        }

        public void setPurgesIn(int purgesIn) {
            this.purgesIn = purgesIn;
        }

        public String getDownloadLink() {
            return downloadLink;
        }

        public void setDownloadLink(String downloadLink) {
            this.downloadLink = downloadLink;
        }
    }
}
