package com.viking.myframe.http;

import org.json.JSONObject;

/**
 * Created by 周正一 on 2017/5/8.
 */

/**
 * {"header":{"device":"xiaomi","encrpy":0,"platform":"android","version":"0.0.0.1","zip":0},
 * "response":{"message":"执行成功",
 * "result":{"currentPage":0,"data":"{\"userInfo\":{\"accountAmount\":0.00,\"accountBaseStatusDesc\":\"已完善\",\"authStatus\":2,\"authStatusDesc\":\"审核通过\",\"businessLicenseCode\":\"2132132132\",\"certificateCode\":\"321321321321\",\"companyEmail\":\"13120315300@163.com\",\"companyFreeze\":0,\"companyId\":848,\"companyName\":\"鸿轩集团\",\"companyPhone\":\"13342289879\",\"companyStatus\":1,\"companyType\":1,\"contacts\":\"鸿轩集团\",\"creditLimit\":30168.10,\"email\":\"13120315300@163.com\",\"firstLogin\":0,\"icon\":\"/group1/M00/00/0D/wKgB3FkAbzuASyTKAAX7dB3EHWI173.jpg\",\"isSign\":1,\"loginName\":\"hxjt\",\"organizationCodeCode\":\"213213213\",\"phone\":\"13342289879\",\"propertyType\":1,\"quaAuthStatus\":2,\"quaAuthStatusDesc\":\"审核通过\",\"registerOrigin\":1,\"taxCertificateCode\":\"13213213\",\"usedCredit\":23769.90,\"userFreeze\":0,\"userId\":13,\"userName\":\"鸿轩集团\",\"userStatus\":1,\"userType\":6}}",
 * "size":0},
 * "status":"10000"}
 * ,"token":""}
 */
public class Response {
    private ResponseHeader responseHeader;
    private ResponseDate responseDate;

    /**
     * Gets response header.
     *
     * @return the response header
     */
    public ResponseHeader getResponseHeader() {
        return responseHeader;
    }

    /**
     * Sets response header.
     *
     * @param responseHeader the response header
     */
    public void setResponseHeader(ResponseHeader responseHeader) {
        this.responseHeader = responseHeader;
    }

    /**
     * Gets response date.
     *
     * @return the response date
     */
    public ResponseDate getResponseDate() {
        return responseDate;
    }

    /**
     * Sets response date.
     *
     * @param responseDate the response date
     */
    public void setResponseDate(ResponseDate responseDate) {
        this.responseDate = responseDate;
    }


    /**
     * Get main data json object.
     *
     * @return the json object
     */
    public JSONObject getMainData() {
        try {

            JSONObject object = new JSONObject(responseDate.getResult().getData());
            return object;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //获取每页显示条数
    public int getSize(){
        return responseDate.getResult().getSize();
    }

    //获取当前页
    public int getcurrentPage(){
        return responseDate.getResult().getCurrentPage();
    }

}
