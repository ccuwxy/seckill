package cn.ccuwxy.miaosha.domain;

import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.io.Serializable;

/**
 * (User)实体类
 *
 * @author makejava
 * @since 2020-03-20 21:49:51
 */

public class User implements Serializable {
    private static final long serialVersionUID = -10486422908266753L;
    /**
    * 用户ID，手机号码
    */

    private Long id;
    
    private String nickname;
    /**
    * MD5(MD5(pass明文+固定salt)+salt)
    */

    private String password;
    
    private String salt;
    /**
    * 头像，云存储ID
    */
    private String head;
    /**
    * 注册时间
    */
    private Date registerData;
    /**
    * 上次登录时间
    */
    private Date lastLoginDate;
    
    private Integer loginCount;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public Date getRegisterData() {
        return registerData;
    }

    public void setRegisterData(Date registerData) {
        this.registerData = registerData;
    }

    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public Integer getLoginCount() {
        return loginCount;
    }

    public void setLoginCount(Integer loginCount) {
        this.loginCount = loginCount;
    }
}