package com.example.lcsrq.bean.resq;

/**
 * Created by 苏毅 on 2017/4/11.
 */

public class ContentGyzRespData {

    /**
     * "id":"18",
     * "title":"湘A88888",
     * "tel":"8888888",
     * "company_id":"20",
     * "company":"测试"
     */
    private String id;
    private String title;
    private String tel;
    private String company_id;
    private String company;
    private String address;
    private String lat;
    private String lng;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getCompany_id() {
        return company_id;
    }

    public void setCompany_id(String company_id) {
        this.company_id = company_id;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    @Override
    public String toString() {
        return "ContentCarRespData{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", tel='" + tel + '\'' +
                ", company_id='" + company_id + '\'' +
                ", company='" + company + '\'' +
                '}';
    }
}
