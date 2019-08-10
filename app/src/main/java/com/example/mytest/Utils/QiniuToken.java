package com.example.myTest.Utils;

import android.text.TextUtils;
import android.util.Log;

import com.qiniu.android.utils.StringUtils;
import com.qiniu.android.utils.UrlSafeBase64;

import org.apache.commons.codec.binary.Base64;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;


public class QiniuToken {


    //指定时间转Unix时间戳
    public static String Date2TimeStamp(String dateStr, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return String.valueOf(sdf.parse(dateStr).getTime() / 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 取得当前时间戳（精确到秒）
     *
     * @return nowTimeStamp
     */
    public static String getNowTimeStamp() {
        long time = System.currentTimeMillis();
        String nowTimeStamp = String.valueOf(time / 1000);
        return nowTimeStamp;
    }

    /**
     * Java将Unix时间戳转换成指定格式日期字符串
     *
     * @param timestampString 时间戳 如："1473048265";
     * @param formats         要格式化的格式 默认："yyyy-MM-dd HH:mm:ss";
     * @return 返回结果 如："2016-09-05 16:06:42";
     */
    public static String TimeStamp2Date(String timestampString, String formats) {
        if (TextUtils.isEmpty(formats))
            formats = "yyyy-MM-dd HH:mm:ss";
        Long timestamp = Long.parseLong(timestampString) * 1000;
        String date = new SimpleDateFormat(formats, Locale.CHINA).format(new Date(timestamp));
        return date;
    }

    public static byte[] HmacSHA1Encrypt(String encryptText, String encryptKey)
            throws Exception {
        byte[] data = StringUtils.utf8Bytes(encryptKey);
        // 根据给定的字节数组构造一个密钥,第二参数指定一个密钥算法的名称
        SecretKey secretKey = new SecretKeySpec(data, "HmacSHA1");
        // 生成一个指定 Mac 算法 的 Mac 对象
        Mac mac = Mac.getInstance("HmacSHA1");
        // 用给定密钥初始化 Mac 对象
        mac.init(secretKey);
        byte[] text = StringUtils.utf8Bytes(encryptKey);
        // 完成 Mac 操作
        return mac.doFinal(text);
    }

    public static String tokenupload(String fileName, String deadline) throws NoSuchAlgorithmException, InvalidKeyException {
        String ACCESS_KEY = "1v0flI4KTNjPAFlCMl8_JeIgHfJtfIFVzKAGCidC";
        String SECRET_KEY = "YSpnaCpayLY-0Snc28yO_x516Jssi9zuUliEN4Pa";
//      String putpolicy = "{\"scope\":\"javademo\",\"deadline\":1457843866}";
        String putpolicy = "{\"scope\":\"" + fileName + "\",\"deadline\":" + deadline + "}";
        Log.d("activity_main_test", "tokenupload: " + putpolicy);
//        JSONObject object = new JSONObject();
//        JsonHelper.put(object, "scope", fileName);
//        JsonHelper.put(object, "deadline", deadline);
        String t3 = UrlSafeBase64.encodeToString(putpolicy);
        Mac mac = javax.crypto.Mac.getInstance("HmacSHA1");
        mac.init(new SecretKeySpec(StringUtils.utf8Bytes(SECRET_KEY), "HmacSHA1"));
        String t2 = UrlSafeBase64.encodeToString(mac.doFinal(StringUtils.utf8Bytes(t3)));
        System.out.println(ACCESS_KEY + ":" + t2 + ":" + t3);
        return ACCESS_KEY + ":" + t2 + ":" + t3;
    }
//
//    public static void main(String args[]) throws InvalidKeyException, NoSuchAlgorithmException{
//        tokenupload();
//    }


    //HMAC 加密算法
    public static String getHMAC(String data, String key) {
        String HMAC_SHA1_ALGORITHM = "HmacSHA1";
        byte[] result = null;
        try {
            //根据给定的字节数组构造一个密钥,第二参数指定一个密钥算法的名称
            SecretKeySpec signinKey = new SecretKeySpec(key.getBytes(), HMAC_SHA1_ALGORITHM);
            //生成一个指定 Mac 算法 的 Mac 对象
            Mac mac = Mac.getInstance(HMAC_SHA1_ALGORITHM);
            //用给定密钥初始化 Mac 对象
            mac.init(signinKey);
            //完成 Mac 操作
            byte[] rawHmac = mac.doFinal(data.getBytes());
            result = Base64.encodeBase64(rawHmac);

        } catch (NoSuchAlgorithmException e) {
            System.err.println(e.getMessage());
        } catch (InvalidKeyException e) {
            System.err.println(e.getMessage());
        }
        if (null != result) {
            return new String(result);
        } else {
            return null;
        }
    }


}
