package com.example.myTest.constant;

public class Constants {

    public static class Flag {


        public static final String POI_ID = "poi_id";
        public static final String URL = "url";
        public static final String SEARCH_LOCATION = "search_location";
        public static final String DATA_BEAN = "data";


        public static final int Message_SUCCESS = 1;
        public static final int Message_ERROR = 2;

    }

    public static class URL {
//        public static final String BASE_MAP_URL = "http://115.29.65.225:8080";

        public static final String WeiHai_OverView_URL = "http://vr.iwhcity.com:8000/tour//ed1479d5f1009871";

        public static final String THREE_DIMENSION_MAP_URL = "http://139.129.90.117:8180/dist/index.html#/mycesium";

        public static final String BASE_MAP_URL = "http://139.129.90.117:8180/dist/index.html#/";//探索

        public static final String DETAIL_MAP_URL = "http://139.129.90.117:8180/dist/appindex.html#/";//详情

        public static final String Dofuntech_URL = "http://139.129.90.117:8180/charm-mmc/";//魅力威海  客户服务器 URL

//        public static final String Dofuntech_URL = "http://116.255.145.242:8801/charm-mmc";//魅力威海 敦锋服务器 URL

        //  http://116.255.145.242:8801/charm-mmc
        public static final String OverView_URL = "http://vr.iwhcity.com:8000/tour//";//全景图URL

        public static final String POI_INTRODUCE_IMAGE = "http://ml.iwhcity.com:8044/Upload/whsq/";//景点介绍图片

    }

    public static class API {

        //兴趣点查询interest
        public static final String INTEREST_BY_ID = "/api/v1/poi/whPoi/get/";
//        public static final String INTEREST_BY_MAP = "/api/v1/poi/whPoi/getByMap";
//        public static final String INTEREST_BY_TYPE = "/api/v1/poi/whPoi/list";
//        public static final String INTEREST_BY_IDS = "/api/v1/poi/whPoi/listByIds";
        public static final String INTEREST_BY_SEARCH = "/api/v1/poi/whPoi/search";

        //登录
        public static final String LOGIN = "/api/v1/app/whAppUser/login";
        public static final String LOGOUT = "/api/v1/app/whAppUser/logout";//登出
        public static final String REGISTER = "/api/v1/app/whAppUser/register";//注册
        public static final String VCODE = "/api/v1/common/sms/vcode/";//验证码{mobile},{type}
        public static final String GET_TOKEN = "/api/v1/app/whAppUser/getEncryptToken?tokenKey=get";

        public static final String FORGET = "/api/v1/app/whAppUser/forgetPassword";//忘记密码

        public static final String SAVE_BH = "/api/v1/marker/whMarkerInf/modify";
        public static final String BU_BY_ID = "/api/v1/marker/whMarkerInf/list";//
        public static final String DELETE_BH = "/api/v1/marker/whMarkerInf/updateStatus/";//

        public static final String collection = "/api/v1/favorite/whFavorite/modify";//收藏添加
        public static final String collection_GET_LIST = "/api/v1/favorite/whFavorite/list";//收藏获取


        public static final String PL_Commit = "/api/v1/comment/whComment/modify";//评论添加
        public static final String PL_GET_LIST = "/api/v1/comment/whComment/list";//评论获取








    }
}

