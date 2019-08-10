package com.example.myTest.bean;

import java.io.Serializable;

public class TokenBean implements Serializable {


    /**
     * code : 200
     * count : 0
     * data : {"tokenInfo":{"tokenKey":"fd6138bf06c05ae0657d36b1dd64e3bc","userKey":"isw48h26u2"}}
     * extra : {}
     * isEnd : 1
     * map : {"$ref":"$.extra"}
     * msg : 操作成功
     * nextStartIndex : 0
     */

    private String code;
    private int count;
    private DataBean data;
    private ExtraBean extra;
    private int isEnd;
    private MapBean map;
    private String msg;
    private int nextStartIndex;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public ExtraBean getExtra() {
        return extra;
    }

    public void setExtra(ExtraBean extra) {
        this.extra = extra;
    }

    public int getIsEnd() {
        return isEnd;
    }

    public void setIsEnd(int isEnd) {
        this.isEnd = isEnd;
    }

    public MapBean getMap() {
        return map;
    }

    public void setMap(MapBean map) {
        this.map = map;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getNextStartIndex() {
        return nextStartIndex;
    }

    public void setNextStartIndex(int nextStartIndex) {
        this.nextStartIndex = nextStartIndex;
    }

    public static class DataBean {
        /**
         * tokenInfo : {"tokenKey":"fd6138bf06c05ae0657d36b1dd64e3bc","userKey":"isw48h26u2"}
         */

        private TokenInfoBean tokenInfo;

        public TokenInfoBean getTokenInfo() {
            return tokenInfo;
        }

        public void setTokenInfo(TokenInfoBean tokenInfo) {
            this.tokenInfo = tokenInfo;
        }

        public static class TokenInfoBean {
            /**
             * tokenKey : fd6138bf06c05ae0657d36b1dd64e3bc
             * userKey : isw48h26u2
             */

            private String tokenKey;
            private String userKey;

            public String getTokenKey() {
                return tokenKey;
            }

            public void setTokenKey(String tokenKey) {
                this.tokenKey = tokenKey;
            }

            public String getUserKey() {
                return userKey;
            }

            public void setUserKey(String userKey) {
                this.userKey = userKey;
            }
        }
    }

    public static class ExtraBean {
    }

    public static class MapBean {
        /**
         * $ref : $.extra
         */

        private String $ref;

        public String get$ref() {
            return $ref;
        }

        public void set$ref(String $ref) {
            this.$ref = $ref;
        }
    }
}
