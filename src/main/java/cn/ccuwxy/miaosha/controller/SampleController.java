//package cn.ccuwxy.miaosha.controller;
//
//import cn.ccuwxy.miaosha.domain.User;
//import cn.ccuwxy.miaosha.rabbitmq.MQSender;
//import cn.ccuwxy.miaosha.redis.RedisService;
//import cn.ccuwxy.miaosha.redis.UserKey;
//import cn.ccuwxy.miaosha.result.CodeMsg;
//import cn.ccuwxy.miaosha.result.Result;
//import cn.ccuwxy.miaosha.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//@Controller
//@RequestMapping("/demo")
//public class SampleController {
//
//    @Autowired
//    UserService userService;
//
//    @Autowired
//    RedisService redisService;
//
//    @Autowired
//    MQSender mqSender;
//
//    @RequestMapping("/")
//    @ResponseBody
//    public String home() {
//
//        return "hello";
//    }
//
//    @RequestMapping("/mq")
//    @ResponseBody
//    public Result<String> mq() {
//        mqSender.send("hello,wxy");
//        return Result.success("hello,11111");
//    }
//
//    @RequestMapping("/mq/topic")
//    @ResponseBody
//    public Result<String> topic() {
//        mqSender.sendTopic("hello,wxy");
//        return Result.success("hello,11111");
//    }
//
//    //swagger
//    @RequestMapping("/mq/fanout")
//    @ResponseBody
//    public Result<String> fanout() {
//        mqSender.sendFanout("hello,wxy");
//        return Result.success("hello,11111");
//    }
//    @RequestMapping("/mq/header")
//    @ResponseBody
//    public Result<String> headers() {
//        mqSender.sendHeaders("hello,wxy");
//        return Result.success("hello,11111");
//    }
//
//    @RequestMapping("/hello")
//    @ResponseBody
//    public Result<String> hello() {
//        return Result.success("hello,wxy");
//    }
//
//    @RequestMapping("/helloError")
//    @ResponseBody
//    public Result<String> helloError() {
//        return Result.error(CodeMsg.SERVER_ERROR);
//    }
//
//    @RequestMapping("/thymeleaf")
//    public String thymeleaf(Model model) {
//        model.addAttribute("name", "wxy");
//        return "hello";
//    }
//
//    @RequestMapping("/db/get")
//    @ResponseBody
//    public Result<User> dbGet() {
//        User user = userService.queryById((long) 1);
//        return Result.success(user);
//    }
//
//    @RequestMapping("/db/tx")
//    @ResponseBody
//    public Result<Boolean> dbTx() {
////        userService.tx();
//        return Result.success(true);
//    }
//
//    @RequestMapping("/redis/get")
//    @ResponseBody
//    public Result<User> redisGet() {
//        User user = redisService.get(UserKey.getById, 1 + "", User.class);
//        return Result.success(user);
//    }
//
//    @RequestMapping("/redis/set")
//    @ResponseBody
//    public Result<Boolean> redisSet() {
//        User user = new User();
////        user.setId(1);
////        user.setName("111111");
//        boolean ret = redisService.set(UserKey.getById, 1 + "", user);
////        String str = redisService.get(UserKey.getById,"key2",String.class);
//        return Result.success(true);
//    }
//
//}
