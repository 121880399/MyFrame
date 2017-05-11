package com.viking.myframe.http;

/**
 * 项目名称: MyFrame-master
 * 创建人: 周正一
 * 创建时间：2017/5/11
 * 返回信息头，根据服务器端返回的数据进行调整
 */

public class ResponseHeader {
    private String device;
    private int encrpy;
    private String platform;
    private String version;
    private int zip;

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public int getEncrpy() {
        return encrpy;
    }

    public void setEncrpy(int encrpy) {
        this.encrpy = encrpy;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public int getZip() {
        return zip;
    }

    public void setZip(int zip) {
        this.zip = zip;
    }
}
