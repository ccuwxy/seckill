package cn.ccuwxy.miaosha.controller;

import cn.ccuwxy.miaosha.domain.User;
import cn.ccuwxy.miaosha.redis.RedisService;
import cn.ccuwxy.miaosha.result.Result;
import cn.ccuwxy.miaosha.service.UserService;
import cn.ccuwxy.miaosha.vo.LoginVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    RedisService redisService;


    @RequestMapping("/info")
    @ResponseBody
    public Result<User> info(User user) {
        return Result.success(user);
    }


}
