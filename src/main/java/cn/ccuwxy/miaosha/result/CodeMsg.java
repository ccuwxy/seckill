package cn.ccuwxy.miaosha.result;

import java.util.Objects;

public class CodeMsg {
    private int code;
    private String msg;

    private CodeMsg() {
    }

    private CodeMsg(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    //通用模块500100
    public static CodeMsg SUCCESS = new CodeMsg(0, "success");
    public static CodeMsg SERVER_ERROR = new CodeMsg(500100, "服务端异常");
    public static CodeMsg BIND_ERROR = new CodeMsg(500101, "参数检验异常：%s");
    public static CodeMsg DB_SALT_ERROR = new CodeMsg(500102, "数据库salt异常");
    //登录模块500200
    public static CodeMsg SESSION_ERROR = new CodeMsg(500210, "session不存在或已失效");
    public static CodeMsg USER_NOT_LOGIN = new CodeMsg(500216, "用户未登录");
    public static CodeMsg PASSWORD_EMPTY = new CodeMsg(500211, "登录密码不能为空");
    public static CodeMsg MOBILE_EMPTY = new CodeMsg(500212, "手机号不能为空");
    public static CodeMsg MOBILE_ERROR = new CodeMsg(500213, "手机号格式不正确");
    public static CodeMsg PASSWORD_ERROR = new CodeMsg(500214, "密码错误");
    public static CodeMsg MOBILE_NOT_EXIST = new CodeMsg(500215, "手机号不存在");
    //商品模块500300

    //订单模块500400
    public static CodeMsg ORDER_NOT_EXIST = new CodeMsg(500401, "订单不存在");

    //秒杀模块500500
    public static CodeMsg MIAO_SHA_OVER = new CodeMsg(500500, "库存不足");
    public static CodeMsg REQUEST_ILLEGAL = new CodeMsg(500502, "请求非法");
    public static CodeMsg REQUEST_SPEED = new CodeMsg(500504, "请求太频繁");
    public static CodeMsg REPEAT_MIAOSHA = new CodeMsg(500501, "重复秒杀");
    public static CodeMsg FAIL_MIAOSHA = new CodeMsg(500503, "秒杀失败");

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public CodeMsg fillArgs(Object... args) {
        int code = this.code;
        String message = String.format(this.msg, args);
        return new CodeMsg(code, message);
    }

    @Override
    public String toString() {
        return "CodeMsg{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }
}
