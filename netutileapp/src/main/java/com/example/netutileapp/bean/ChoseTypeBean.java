package com.example.netutileapp.bean;

import java.io.Serializable;
import java.util.List;

public class ChoseTypeBean implements Serializable {

    /**
     * code : 200
     * count : 0.0
     * data : [{"createDate":1.555294802E12,"desc":"寻人启事","id":"1a7e9d5028f448c4843bd70ae3823617","isNewRecord":false,"label":"寻人启事","parentId":"0","remarks":"","sort":1,"updateDate":1.555294802E12,"value":"001"},{"createDate":1.555294829E12,"desc":"物品丢失","id":"d4957135ca4c4bc8a34043366d3634e1","isNewRecord":false,"label":"物品丢失","parentId":"0","remarks":"","sort":2,"updateDate":1.555294829E12,"value":"002"},{"createDate":1.555294849E12,"desc":"宠物走失","id":"8c06da5616024982817449cde999bef1","isNewRecord":false,"label":"宠物走失","parentId":"0","remarks":"","sort":3,"updateDate":1.555294849E12,"value":"003"},{"createDate":1.555294862E12,"desc":"失物招领","id":"a8fbdd97dc1e4386bd5208bba5be1717","isNewRecord":false,"label":"失物招领","parentId":"0","remarks":"","sort":4,"updateDate":1.555294862E12,"value":"004"},{"createDate":1.555294877E12,"desc":"房屋租售","id":"4f32c5f8eb3a44c1afe66f7acc3b6748","isNewRecord":false,"label":"房屋租售","parentId":"0","remarks":"","sort":5,"updateDate":1.555294877E12,"value":"005"},{"createDate":1.555294894E12,"desc":"车位租售","id":"c8fe5507d5514854add6bcabe862e410","isNewRecord":false,"label":"车位租售","parentId":"0","remarks":"","sort":6,"updateDate":1.555294894E12,"value":"006"},{"createDate":1.555294909E12,"desc":"邻里拼车","id":"99edf36bd22d4168be9dbc3929d88f7a","isNewRecord":false,"label":"邻里拼车","parentId":"0","remarks":"","sort":7,"updateDate":1.555294909E12,"value":"007"},{"createDate":1.555294926E12,"desc":"其它","id":"933b2f5f04d0448bb87b20e3c347e3f2","isNewRecord":false,"label":"其它","parentId":"0","remarks":"","sort":8,"updateDate":1.555294926E12,"value":"008"}]
     * extra : {}
     * map : {}
     * msg : 操作成功
     */

    private String code;
    private double count;

    private String msg;
    private List<DataBean> data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public double getCount() {
        return count;
    }

    public void setCount(double count) {
        this.count = count;
    }



    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }


    public static class DataBean implements Serializable {
        /**
         * createDate : 1.555294802E12
         * desc : 寻人启事
         * id : 1a7e9d5028f448c4843bd70ae3823617
         * isNewRecord : false
         * label : 寻人启事
         * parentId : 0
         * remarks :
         * sort : 1.0
         * updateDate : 1.555294802E12
         * value : 001
         */

        private double createDate;
        private String desc;
        private String id;
        private boolean isNewRecord;
        private String label;
        private String parentId;
        private String remarks;
        private double sort;
        private double updateDate;
        private String value;

        public double getCreateDate() {
            return createDate;
        }

        public void setCreateDate(double createDate) {
            this.createDate = createDate;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public boolean isIsNewRecord() {
            return isNewRecord;
        }

        public void setIsNewRecord(boolean isNewRecord) {
            this.isNewRecord = isNewRecord;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public String getParentId() {
            return parentId;
        }

        public void setParentId(String parentId) {
            this.parentId = parentId;
        }

        public String getRemarks() {
            return remarks;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }

        public double getSort() {
            return sort;
        }

        public void setSort(double sort) {
            this.sort = sort;
        }

        public double getUpdateDate() {
            return updateDate;
        }

        public void setUpdateDate(double updateDate) {
            this.updateDate = updateDate;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
