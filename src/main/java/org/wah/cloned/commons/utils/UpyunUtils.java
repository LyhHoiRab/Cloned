package org.wah.cloned.commons.utils;

import main.java.com.UpYun;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.wah.doraemon.consts.base.EnumType;
import org.wah.doraemon.security.exception.UnknownEnumTypeException;
import org.wah.doraemon.security.exception.UtilsException;

import java.util.Map;

public class UpyunUtils{

    //600秒
    private static final int DEFAULT_TIME_OUT = 600;

    public UpyunUtils(){

    }

    public static boolean upload(Upyun upyun, String uploadPath, Integer timeOut, CommonsMultipartFile file){
        if(upyun == null){
            throw new UtilsException("upyun参数不能为空");
        }
        if(StringUtils.isBlank(uploadPath)){
            throw new UtilsException("上传路径不能为空");
        }
        if(file == null || file.getSize() == 0){
            throw new UtilsException("上传文件不能为空");
        }

        UpYun client = new UpYun(upyun.getBucket(), upyun.getOperator(), upyun.getPassword());
        client.setDebug(false);
        client.setTimeout(timeOut == null ? DEFAULT_TIME_OUT : timeOut);

        return client.writeFile(uploadPath, file.getBytes());
    }

    public static boolean upload(String bucket, String operator, String password, String uploadPath, Integer timeOut, CommonsMultipartFile file){
        if(StringUtils.isBlank(bucket)){
            throw new UtilsException("上传空间名不能为空");
        }
        if(StringUtils.isBlank(operator)){
            throw new UtilsException("upyun登录名不能为空");
        }
        if(StringUtils.isBlank(password)){
            throw new UtilsException("upyun登录密码不能为空");
        }
        if(StringUtils.isBlank(uploadPath)){
            throw new UtilsException("上传路径不能为空");
        }
        if(file == null || file.getSize() == 0){
            throw new UtilsException("上传文件不能为空");
        }

        UpYun client = new UpYun(bucket, operator, password);
        client.setDebug(false);
        client.setTimeout(600);

        return client.writeFile(uploadPath, file.getBytes());
    }

    public static boolean delete(Upyun upyun, String uploadPath, Integer timeOut){
        if(upyun == null){
            throw new UtilsException("upyun参数不能为空");
        }
        if(StringUtils.isBlank(uploadPath)){
            throw new UtilsException("上传路径不能为空");
        }

        UpYun client = new UpYun(upyun.getBucket(), upyun.getOperator(), upyun.getPassword());
        client.setDebug(false);
        client.setTimeout(timeOut == null ? DEFAULT_TIME_OUT : timeOut);

        return client.deleteFile(uploadPath);
    }

    public static boolean delete(String bucket, String operator, String password, String uploadPath, Integer timeOut){
        if(StringUtils.isBlank(bucket)){
            throw new UtilsException("上传空间名不能为空");
        }
        if(StringUtils.isBlank(operator)){
            throw new UtilsException("upyun登录名不能为空");
        }
        if(StringUtils.isBlank(password)){
            throw new UtilsException("upyun登录密码不能为空");
        }
        if(StringUtils.isBlank(uploadPath)){
            throw new UtilsException("上传路径不能为空");
        }

        UpYun client = new UpYun(bucket, operator, password);
        client.setDebug(false);
        client.setTimeout(timeOut == null ? DEFAULT_TIME_OUT : timeOut);

        return client.deleteFile(uploadPath);
    }

    public static Map<String, String> getFileInfo(Upyun upyun, String uploadPath, Integer timeOut){
        if(upyun == null){
            throw new UtilsException("upyun参数不能为空");
        }
        if(StringUtils.isBlank(uploadPath)){
            throw new UtilsException("上传路径不能为空");
        }

        UpYun client = new UpYun(upyun.getBucket(), upyun.getOperator(), upyun.getPassword());
        client.setDebug(false);
        client.setTimeout(timeOut == null ? DEFAULT_TIME_OUT : timeOut);

        return client.getFileInfo(uploadPath);
    }

    public static Map<String, String> getFileInfo(String bucket, String operator, String password, String uploadPath, Integer timeOut){
        if(StringUtils.isBlank(bucket)){
            throw new UtilsException("上传空间名不能为空");
        }
        if(StringUtils.isBlank(operator)){
            throw new UtilsException("upyun登录名不能为空");
        }
        if(StringUtils.isBlank(password)){
            throw new UtilsException("upyun登录密码不能为空");
        }
        if(StringUtils.isBlank(uploadPath)){
            throw new UtilsException("上传路径不能为空");
        }


        UpYun client = new UpYun(bucket, operator, password);
        client.setDebug(false);
        client.setTimeout(timeOut == null ? DEFAULT_TIME_OUT : timeOut);

        return client.getFileInfo(uploadPath);
    }

    public enum Upyun implements EnumType{

        NINE_LAB(0, "九研", "ninelab", "unesmall", "unesmall123456", "http://ninelab.b0.upaiyun.com"),
        MIKU_MINE(1, "米酷", "mikumine", "unesmall", "unesmall123456", "http://mikumine.b0.upaiyun.com"),
        UNES_MALL(2, "优理氏", "unesmall", "unesmall", "unesmall123456", "http://unesmall.b0.upaiyun.com"),
        KULIAO(3, "酷撩", "kuliao", "unesmall", "unesmall123456", "http://kuliao.b0.upaiyun.com");

        private int id;
        private String description;
        private String bucket;
        private String operator;
        private String password;
        private String url;

        private Upyun(int id, String description, String bucket, String operator, String password, String url){
            this.id = id;
            this.description = description;
            this.bucket = bucket;
            this.operator = operator;
            this.password = password;
            this.url = url;
        }

        public int getId(){
            return id;
        }

        public String getDescription(){
            return description;
        }

        public String getBucket(){
            return bucket;
        }

        public String getOperator(){
            return operator;
        }

        public String getPassword(){
            return password;
        }

        public String getUrl(){
            return url;
        }

        public static Upyun getById(int id){
            for(Upyun upyun : Upyun.values()){
                if(upyun.getId() == id){
                    return upyun;
                }
            }

            throw new UnknownEnumTypeException("未知的常量ID[{0}:{1}]", Upyun.class, id);
        }
    }
}
