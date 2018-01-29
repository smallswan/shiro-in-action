package com.zhiluniao.shiro;

import org.apache.shiro.crypto.hash.Sha512Hash;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 
 *
 * @author Administrator<br>
 *         2018年1月29日  下午4:47:05
 */
@RunWith(SpringRunner.class)
public class TestSha512Hash {
    private Logger log = LoggerFactory.getLogger(TestSha512Hash.class);
    
    @Test
    public void demo1(){
        //盐
        String salt = "c77edd96ace29c665c274708bfa72a3c";
        String hex = new Sha512Hash("123456",salt).toHex();
        
        String pwd = "5832fc5e47a551cbe6f8c4352264ddb5b5f9fe7f0c256c6ba000e74a9b6b641e895ad15d71be27eef8b7225c90ed229abde1f0446c14868b3366361295e194b9";
    
    
        if(pwd.equals(hex)){
            log.info("密码正确");
        }
    }

    @Test
    public void demo2(){
        //盐
        String salt = "c77edd96ace29c665c274708bfa72a3c";
        String hex = new Sha512Hash("123456",salt,1024).toHex();
        
        String pwd = "5832fc5e47a551cbe6f8c4352264ddb5b5f9fe7f0c256c6ba000e74a9b6b641e895ad15d71be27eef8b7225c90ed229abde1f0446c14868b3366361295e194b9";
    
    
        log.info("hex : {}",hex);
        if(pwd.equals(hex)){
            log.info("密码正确");
        }else{
            log.error("密码错误");
        }
    }
}
