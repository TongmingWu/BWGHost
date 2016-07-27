package com.tongming.bwghost.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Tongming on 2016/7/17.
 */
public class ServiceInfo implements Parcelable {

    /**
     * status : running
     * hostname : tongming.local
     * load_average : 0.00/0.01/0.05
     * nproc : 46
     * nproc_b : 200
     * kmemsize : 8340404
     * kmemsize_b : 134217728
     * privvmpages : 87388
     * privvmpages_b : 262144
     * oomguarpages : 19146
     * oomguarpages_b : 65536
     * numtcpsock : 25
     * numtcpsock_b : 1670
     * numfile : 593
     * numfile_b : 10240
     * swappages : 3587
     * swappages_b : 32768
     * physpages : 38038
     * physpages_l : 65536
     */

    private VzStatusBean vz_status;
    /**
     * occupied_kb : 2925776
     * softlimit_kb : 12582912
     * hardlimit_kb : 13211648
     * occupied_inodes : 116748
     * softlimit_inodes : 12288000
     * hardlimit_inodes : 12902400
     */

    private VzQuotaBean vz_quota;
    /**
     * vz_status : {"status":"running","hostname":"tongming.local","load_average":"0.00/0.01/0.05","nproc":"46","nproc_b":"200","kmemsize":"8340404","kmemsize_b":"134217728","privvmpages":"87388","privvmpages_b":"262144","oomguarpages":"19146","oomguarpages_b":"65536","numtcpsock":"25","numtcpsock_b":"1670","numfile":"593","numfile_b":"10240","swappages":"3587","swappages_b":"32768","physpages":"38038","physpages_l":"65536"}
     * vz_quota : {"occupied_kb":"2925776","softlimit_kb":"12582912","hardlimit_kb":"13211648","occupied_inodes":"116748","softlimit_inodes":"12288000","hardlimit_inodes":"12902400"}
     * is_cpu_throttled :
     * ssh_port : 27373
     * hostname : tongming.local
     * node_ip : 45.78.0.203
     * node_alias : v418
     * node_location : US, California
     * location_ipv6_ready : true
     * plan : wagonv2-10g
     * plan_monthly_data : 590558003200
     * plan_disk : 12884901888
     * plan_ram : 268435456
     * plan_swap : 134217728
     * plan_max_ipv6s : 5
     * os : centos-7-x86_64
     * email : wtm1069505260@gmail.com
     * data_counter : 10031402289
     * data_next_reset : 1470110400
     * ip_addresses : ["45.78.25.201"]
     * rdns_api_available : true
     * ptr : {"45.78.25.201":"tongming.local"}
     * suspended : false
     * error : 0
     */

    private String is_cpu_throttled;
    private int ssh_port;
    private String hostname;
    private String node_ip;
    private String node_alias;
    private String node_location;
    private boolean location_ipv6_ready;
    private String plan;
    private long plan_monthly_data;
    private long plan_disk;
    private int plan_ram;
    private int plan_swap;
    private int plan_max_ipv6s;
    private String os;
    private String email;
    private long data_counter;
    private int data_next_reset;
    private boolean rdns_api_available;
    private boolean suspended;
    private int error;
    private List<String> ip_addresses;

    public VzStatusBean getVz_status() {
        return vz_status;
    }

    public void setVz_status(VzStatusBean vz_status) {
        this.vz_status = vz_status;
    }

    public VzQuotaBean getVz_quota() {
        return vz_quota;
    }

    public void setVz_quota(VzQuotaBean vz_quota) {
        this.vz_quota = vz_quota;
    }

    public String getIs_cpu_throttled() {
        return is_cpu_throttled;
    }

    public void setIs_cpu_throttled(String is_cpu_throttled) {
        this.is_cpu_throttled = is_cpu_throttled;
    }

    public int getSsh_port() {
        return ssh_port;
    }

    public void setSsh_port(int ssh_port) {
        this.ssh_port = ssh_port;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getNode_ip() {
        return node_ip;
    }

    public void setNode_ip(String node_ip) {
        this.node_ip = node_ip;
    }

    public String getNode_alias() {
        return node_alias;
    }

    public void setNode_alias(String node_alias) {
        this.node_alias = node_alias;
    }

    public String getNode_location() {
        return node_location;
    }

    public void setNode_location(String node_location) {
        this.node_location = node_location;
    }

    public boolean isLocation_ipv6_ready() {
        return location_ipv6_ready;
    }

    public void setLocation_ipv6_ready(boolean location_ipv6_ready) {
        this.location_ipv6_ready = location_ipv6_ready;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public long getPlan_monthly_data() {
        return plan_monthly_data;
    }

    public void setPlan_monthly_data(long plan_monthly_data) {
        this.plan_monthly_data = plan_monthly_data;
    }

    public long getPlan_disk() {
        return plan_disk;
    }

    public void setPlan_disk(long plan_disk) {
        this.plan_disk = plan_disk;
    }

    public int getPlan_ram() {
        return plan_ram;
    }

    public void setPlan_ram(int plan_ram) {
        this.plan_ram = plan_ram;
    }

    public int getPlan_swap() {
        return plan_swap;
    }

    public void setPlan_swap(int plan_swap) {
        this.plan_swap = plan_swap;
    }

    public int getPlan_max_ipv6s() {
        return plan_max_ipv6s;
    }

    public void setPlan_max_ipv6s(int plan_max_ipv6s) {
        this.plan_max_ipv6s = plan_max_ipv6s;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getData_counter() {
        return data_counter;
    }

    public void setData_counter(long data_counter) {
        this.data_counter = data_counter;
    }

    public int getData_next_reset() {
        return data_next_reset;
    }

    public void setData_next_reset(int data_next_reset) {
        this.data_next_reset = data_next_reset;
    }

    public boolean isRdns_api_available() {
        return rdns_api_available;
    }

    public void setRdns_api_available(boolean rdns_api_available) {
        this.rdns_api_available = rdns_api_available;
    }

    public boolean isSuspended() {
        return suspended;
    }

    public void setSuspended(boolean suspended) {
        this.suspended = suspended;
    }

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public List<String> getIp_addresses() {
        return ip_addresses;
    }

    public void setIp_addresses(List<String> ip_addresses) {
        this.ip_addresses = ip_addresses;
    }

    public static class VzStatusBean implements Parcelable {

        private String status;
        private String hostname;
        private String load_average;
        private String nproc;
        private String nproc_b;
        private String kmemsize;
        private String kmemsize_b;
        private String privvmpages;
        private String privvmpages_b;
        private String oomguarpages;
        private String oomguarpages_b;
        private String numtcpsock;
        private String numtcpsock_b;
        private String numfile;
        private String numfile_b;
        private String swappages;
        private String swappages_b;
        private String physpages;
        private String physpages_l;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getHostname() {
            return hostname;
        }

        public void setHostname(String hostname) {
            this.hostname = hostname;
        }

        public String getLoad_average() {
            return load_average;
        }

        public void setLoad_average(String load_average) {
            this.load_average = load_average;
        }

        public String getNproc() {
            return nproc;
        }

        public void setNproc(String nproc) {
            this.nproc = nproc;
        }

        public String getNproc_b() {
            return nproc_b;
        }

        public void setNproc_b(String nproc_b) {
            this.nproc_b = nproc_b;
        }

        public String getKmemsize() {
            return kmemsize;
        }

        public void setKmemsize(String kmemsize) {
            this.kmemsize = kmemsize;
        }

        public String getKmemsize_b() {
            return kmemsize_b;
        }

        public void setKmemsize_b(String kmemsize_b) {
            this.kmemsize_b = kmemsize_b;
        }

        public String getPrivvmpages() {
            return privvmpages;
        }

        public void setPrivvmpages(String privvmpages) {
            this.privvmpages = privvmpages;
        }

        public String getPrivvmpages_b() {
            return privvmpages_b;
        }

        public void setPrivvmpages_b(String privvmpages_b) {
            this.privvmpages_b = privvmpages_b;
        }

        public String getOomguarpages() {
            return oomguarpages;
        }

        public void setOomguarpages(String oomguarpages) {
            this.oomguarpages = oomguarpages;
        }

        public String getOomguarpages_b() {
            return oomguarpages_b;
        }

        public void setOomguarpages_b(String oomguarpages_b) {
            this.oomguarpages_b = oomguarpages_b;
        }

        public String getNumtcpsock() {
            return numtcpsock;
        }

        public void setNumtcpsock(String numtcpsock) {
            this.numtcpsock = numtcpsock;
        }

        public String getNumtcpsock_b() {
            return numtcpsock_b;
        }

        public void setNumtcpsock_b(String numtcpsock_b) {
            this.numtcpsock_b = numtcpsock_b;
        }

        public String getNumfile() {
            return numfile;
        }

        public void setNumfile(String numfile) {
            this.numfile = numfile;
        }

        public String getNumfile_b() {
            return numfile_b;
        }

        public void setNumfile_b(String numfile_b) {
            this.numfile_b = numfile_b;
        }

        public String getSwappages() {
            return swappages;
        }

        public void setSwappages(String swappages) {
            this.swappages = swappages;
        }

        public String getSwappages_b() {
            return swappages_b;
        }

        public void setSwappages_b(String swappages_b) {
            this.swappages_b = swappages_b;
        }

        public String getPhyspages() {
            return physpages;
        }

        public void setPhyspages(String physpages) {
            this.physpages = physpages;
        }

        public String getPhyspages_l() {
            return physpages_l;
        }

        public void setPhyspages_l(String physpages_l) {
            this.physpages_l = physpages_l;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.status);
            dest.writeString(this.hostname);
            dest.writeString(this.load_average);
            dest.writeString(this.nproc);
            dest.writeString(this.nproc_b);
            dest.writeString(this.kmemsize);
            dest.writeString(this.kmemsize_b);
            dest.writeString(this.privvmpages);
            dest.writeString(this.privvmpages_b);
            dest.writeString(this.oomguarpages);
            dest.writeString(this.oomguarpages_b);
            dest.writeString(this.numtcpsock);
            dest.writeString(this.numtcpsock_b);
            dest.writeString(this.numfile);
            dest.writeString(this.numfile_b);
            dest.writeString(this.swappages);
            dest.writeString(this.swappages_b);
            dest.writeString(this.physpages);
            dest.writeString(this.physpages_l);
        }

        public VzStatusBean() {
        }

        protected VzStatusBean(Parcel in) {
            this.status = in.readString();
            this.hostname = in.readString();
            this.load_average = in.readString();
            this.nproc = in.readString();
            this.nproc_b = in.readString();
            this.kmemsize = in.readString();
            this.kmemsize_b = in.readString();
            this.privvmpages = in.readString();
            this.privvmpages_b = in.readString();
            this.oomguarpages = in.readString();
            this.oomguarpages_b = in.readString();
            this.numtcpsock = in.readString();
            this.numtcpsock_b = in.readString();
            this.numfile = in.readString();
            this.numfile_b = in.readString();
            this.swappages = in.readString();
            this.swappages_b = in.readString();
            this.physpages = in.readString();
            this.physpages_l = in.readString();
        }

        public static final Creator<VzStatusBean> CREATOR = new Creator<VzStatusBean>() {
            @Override
            public VzStatusBean createFromParcel(Parcel source) {
                return new VzStatusBean(source);
            }

            @Override
            public VzStatusBean[] newArray(int size) {
                return new VzStatusBean[size];
            }
        };
    }

    public static class VzQuotaBean implements Parcelable {
        private String occupied_kb;
        private String softlimit_kb;
        private String hardlimit_kb;
        private String occupied_inodes;
        private String softlimit_inodes;
        private String hardlimit_inodes;

        public String getOccupied_kb() {
            return occupied_kb;
        }

        public void setOccupied_kb(String occupied_kb) {
            this.occupied_kb = occupied_kb;
        }

        public String getSoftlimit_kb() {
            return softlimit_kb;
        }

        public void setSoftlimit_kb(String softlimit_kb) {
            this.softlimit_kb = softlimit_kb;
        }

        public String getHardlimit_kb() {
            return hardlimit_kb;
        }

        public void setHardlimit_kb(String hardlimit_kb) {
            this.hardlimit_kb = hardlimit_kb;
        }

        public String getOccupied_inodes() {
            return occupied_inodes;
        }

        public void setOccupied_inodes(String occupied_inodes) {
            this.occupied_inodes = occupied_inodes;
        }

        public String getSoftlimit_inodes() {
            return softlimit_inodes;
        }

        public void setSoftlimit_inodes(String softlimit_inodes) {
            this.softlimit_inodes = softlimit_inodes;
        }

        public String getHardlimit_inodes() {
            return hardlimit_inodes;
        }

        public void setHardlimit_inodes(String hardlimit_inodes) {
            this.hardlimit_inodes = hardlimit_inodes;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.occupied_kb);
            dest.writeString(this.softlimit_kb);
            dest.writeString(this.hardlimit_kb);
            dest.writeString(this.occupied_inodes);
            dest.writeString(this.softlimit_inodes);
            dest.writeString(this.hardlimit_inodes);
        }

        public VzQuotaBean() {
        }

        protected VzQuotaBean(Parcel in) {
            this.occupied_kb = in.readString();
            this.softlimit_kb = in.readString();
            this.hardlimit_kb = in.readString();
            this.occupied_inodes = in.readString();
            this.softlimit_inodes = in.readString();
            this.hardlimit_inodes = in.readString();
        }

        public static final Creator<VzQuotaBean> CREATOR = new Creator<VzQuotaBean>() {
            @Override
            public VzQuotaBean createFromParcel(Parcel source) {
                return new VzQuotaBean(source);
            }

            @Override
            public VzQuotaBean[] newArray(int size) {
                return new VzQuotaBean[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.vz_status, flags);
        dest.writeParcelable(this.vz_quota, flags);
        dest.writeString(this.is_cpu_throttled);
        dest.writeInt(this.ssh_port);
        dest.writeString(this.hostname);
        dest.writeString(this.node_ip);
        dest.writeString(this.node_alias);
        dest.writeString(this.node_location);
        dest.writeByte(this.location_ipv6_ready ? (byte) 1 : (byte) 0);
        dest.writeString(this.plan);
        dest.writeLong(this.plan_monthly_data);
        dest.writeLong(this.plan_disk);
        dest.writeInt(this.plan_ram);
        dest.writeInt(this.plan_swap);
        dest.writeInt(this.plan_max_ipv6s);
        dest.writeString(this.os);
        dest.writeString(this.email);
        dest.writeLong(this.data_counter);
        dest.writeInt(this.data_next_reset);
        dest.writeByte(this.rdns_api_available ? (byte) 1 : (byte) 0);
        dest.writeByte(this.suspended ? (byte) 1 : (byte) 0);
        dest.writeInt(this.error);
        dest.writeStringList(this.ip_addresses);
    }

    public ServiceInfo() {
    }

    protected ServiceInfo(Parcel in) {
        this.vz_status = in.readParcelable(VzStatusBean.class.getClassLoader());
        this.vz_quota = in.readParcelable(VzQuotaBean.class.getClassLoader());
        this.is_cpu_throttled = in.readString();
        this.ssh_port = in.readInt();
        this.hostname = in.readString();
        this.node_ip = in.readString();
        this.node_alias = in.readString();
        this.node_location = in.readString();
        this.location_ipv6_ready = in.readByte() != 0;
        this.plan = in.readString();
        this.plan_monthly_data = in.readLong();
        this.plan_disk = in.readLong();
        this.plan_ram = in.readInt();
        this.plan_swap = in.readInt();
        this.plan_max_ipv6s = in.readInt();
        this.os = in.readString();
        this.email = in.readString();
        this.data_counter = in.readLong();
        this.data_next_reset = in.readInt();
        this.rdns_api_available = in.readByte() != 0;
        this.suspended = in.readByte() != 0;
        this.error = in.readInt();
        this.ip_addresses = in.createStringArrayList();
    }

    public static final Parcelable.Creator<ServiceInfo> CREATOR = new Parcelable.Creator<ServiceInfo>() {
        @Override
        public ServiceInfo createFromParcel(Parcel source) {
            return new ServiceInfo(source);
        }

        @Override
        public ServiceInfo[] newArray(int size) {
            return new ServiceInfo[size];
        }
    };
}
