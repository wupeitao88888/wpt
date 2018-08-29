package com.iloomo.bean;

/**
 * Created by wupeitao on 2017/7/12.
 */

public class Update extends BaseModel {


    /**
     * data : {"date":"2017-07-13 09:15:54","is_force":0,"size":"44.7M","id":3,"version":1,"versionname":"xxx","url":"http://img.ryit.co/appversion/2017/07/13/qaUFVr.apk","content":"更新内容"}
     * errorMessage :
     * errorCode : 0
     * timestamp : 1500344826
     */
    private DataEntity data;

    public void setData(DataEntity data) {
        this.data = data;
    }



    public DataEntity getData() {
        return data;
    }



    public class DataEntity {
        /**
         * date : 2017-07-13 09:15:54
         * is_force : 0
         * size : 44.7M
         * id : 3
         * version : 1
         * versionname : xxx
         * url : http://img.ryit.co/appversion/2017/07/13/qaUFVr.apk
         * content : 更新内容
         * type 1:版本更新 2：热更新
         */
        private String date;
        private String is_force;
        private String size;
        private int id;
        private int version;
        private String versionname;
        private String url;
        private String content;
        private String type;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public void setIs_force(String is_force) {
            this.is_force = is_force;
        }

        public void setSize(String size) {
            this.size = size;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setVersion(int version) {
            this.version = version;
        }

        public void setVersionname(String versionname) {
            this.versionname = versionname;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getDate() {
            return date;
        }

        public String getIs_force() {
            return is_force;
        }

        public String getSize() {
            return size;
        }

        public int getId() {
            return id;
        }

        public int getVersion() {
            return version;
        }

        public String getVersionname() {
            return versionname;
        }

        public String getUrl() {
            return url;
        }

        public String getContent() {
            return content;
        }
    }
}
