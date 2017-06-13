package com.example.lcsrq.bean.resq;

import com.example.lcsrq.bean.respbean.Data_ckloglist;
import com.example.lcsrq.bean.respbean.Data_cklogtimeslist;
import com.example.lcsrq.bean.respbean.Data_fzr;
import com.example.lcsrq.bean.respbean.Data_fzr_company;
import com.example.lcsrq.bean.respbean.Data_jftloglist;
import com.example.lcsrq.bean.respbean.Data_ysg;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by 苏毅 on 2017/4/11.
 *  供应站返回数据详情
 */

public class ContentGyzDetailRespData implements Serializable{

     private double lat;
     private double lng;

     public double getLat() {
          return lat;
     }

     public void setLat(double lat) {
          this.lat = lat;
     }

     public double getLng() {
          return lng;
     }

     public void setLng(double lng) {
          this.lng = lng;
     }


     public String id;
     public String title;
     public String remark;
     public String creat_at;
     public String content;
     public String upload_path;
     public String company_id;
     public String company;
     public String tel;
     public String address;
     public String upload_json;
     public ArrayList<Data_fzr> data_fzr;
     public ArrayList<Data_fzr_company> data_fzr_company;
     public ArrayList<Data_ysg> data_ysg;
     public ArrayList<Data_ckloglist> ckloglist;  //  存在问题
     public ArrayList<Data_cklogtimeslist> cklogtimeslist; //  检查记录
     public String jf_value;
     public String supply_company_mid;
     public ArrayList<Data_jftloglist> jftloglist; //  历史扣分项目

     public ArrayList<Data_fzr_company> getData_fzr_company() {
          return data_fzr_company;
     }

     public void setData_fzr_company(ArrayList<Data_fzr_company> data_fzr_company) {
          this.data_fzr_company = data_fzr_company;
     }

     public String getJf_value() {
          return jf_value;
     }

     public void setJf_value(String jf_value) {
          this.jf_value = jf_value;
     }

     public String getSupply_company_mid() {
          return supply_company_mid;
     }

     public void setSupply_company_mid(String supply_company_mid) {
          this.supply_company_mid = supply_company_mid;
     }

     public ArrayList<Data_jftloglist> getJftloglist() {
          return jftloglist;
     }

     public void setJftloglist(ArrayList<Data_jftloglist> jftloglist) {
          this.jftloglist = jftloglist;
     }

     public ArrayList<Data_cklogtimeslist> getCklogtimeslist() {
          return cklogtimeslist;
     }

     public void setCklogtimeslist(ArrayList<Data_cklogtimeslist> cklogtimeslist) {
          this.cklogtimeslist = cklogtimeslist;
     }

     public ArrayList<Data_ckloglist> getCkloglist() {
          return ckloglist;
     }

     public void setCkloglist(ArrayList<Data_ckloglist> ckloglist) {
          this.ckloglist = ckloglist;
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

     public String getRemark() {
          return remark;
     }

     public void setRemark(String remark) {
          this.remark = remark;
     }

     public String getCreat_at() {
          return creat_at;
     }

     public void setCreat_at(String creat_at) {
          this.creat_at = creat_at;
     }

     public String getContent() {
          return content;
     }

     public void setContent(String content) {
          this.content = content;
     }

     public String getUpload_path() {
          return upload_path;
     }

     public void setUpload_path(String upload_path) {
          this.upload_path = upload_path;
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

     public String getTel() {
          return tel;
     }

     public void setTel(String tel) {
          this.tel = tel;
     }

     public String getAddress() {
          return address;
     }

     public void setAddress(String address) {
          this.address = address;
     }

     public String getUpload_json() {
          return upload_json;
     }

     public void setUpload_json(String upload_json) {
          this.upload_json = upload_json;
     }

     public ArrayList<Data_fzr> getData_fzr() {
          return data_fzr;
     }

     public void setData_fzr(ArrayList<Data_fzr> data_fzr) {
          this.data_fzr = data_fzr;
     }

     public ArrayList<Data_ysg> getData_ysg() {
          return data_ysg;
     }

     public void setData_ysg(ArrayList<Data_ysg> data_ysg) {
          this.data_ysg = data_ysg;
     }
}
