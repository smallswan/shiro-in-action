package com.zhiluniao.model.constants;

/**
 * 
 *
 * @author Administrator<br>
 *         2018年1月20日  下午5:25:43
 */
public class Constants {
    /** shiro 哈希算法*/
    public static final String SHIRO_HASH_ALGORITHM_NAME = "SHA-512";
    /** shiro 哈希算法迭代次数 */
    public static final int SHIRO_HASH_ITERATIONS = 1024;
    
    /**手机号码正则表达*/
    public static final String MOBILE_PATTERN = "^((13[0-9])|(14[5|7|9])|(15([0-9]))|(166)|(17[0-9])|(18[0-9])|(19[8,9]))\\d{8}$";
    /**电子邮箱正则表达*/
    public static final String EMAIL_PATTERN = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";
}
