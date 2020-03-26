package cn.ccuwxy.miaosha.redis;

public interface KeyPrefix {

    int expireSeconds();
    String getPrefix();
}
