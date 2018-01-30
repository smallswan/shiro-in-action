package com.zhiluniao.shiro;

import java.security.Key;

import org.apache.shiro.codec.Hex;
import org.apache.shiro.crypto.AesCipherService;
import org.junit.Assert;
import org.junit.Test;

/**
 * 
 *
 * @author Administrator<br>
 *         2018年1月30日  下午2:48:34
 */
public class TestAes {

    @Test
    public void demo1(){
        AesCipherService aesCipherService = new AesCipherService();
        aesCipherService.setKeySize(128); //设置 key 长度
        //生成 key
        Key key = aesCipherService.generateNewKey();
        String text = "hello";
        //加密
        String encrptText =
        aesCipherService.encrypt(text.getBytes(), key.getEncoded()).toHex();
        //解密
        String text2 =
        new String(aesCipherService.decrypt(Hex.decode(encrptText), key.getEncoded()).getBytes());
        Assert.assertEquals(text, text2);
    }
}
