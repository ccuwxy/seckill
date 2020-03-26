package cn.ccuwxy.miaosha.access;

import cn.ccuwxy.miaosha.domain.User;
import cn.ccuwxy.miaosha.redis.AccessKey;
import cn.ccuwxy.miaosha.redis.RedisService;
import cn.ccuwxy.miaosha.result.CodeMsg;
import cn.ccuwxy.miaosha.result.Result;
import cn.ccuwxy.miaosha.service.UserService;
import com.alibaba.fastjson.JSON;
import com.mysql.jdbc.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class AccessInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    UserService userService;

    @Autowired
    RedisService redisService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (handler instanceof HandlerMethod) {
            User user = getUser(request, response);
            UserContext.setUser(user);

            HandlerMethod hm = (HandlerMethod) handler;
            AccessLimit accessLimit = hm.getMethodAnnotation(AccessLimit.class);
            if (accessLimit == null) {

                return true;
            }

            int seconds = accessLimit.seconds();
            int maxCount = accessLimit.maxCount();
            boolean needLogin = accessLimit.needLogin();
            String key = request.getRequestURI();
            if (needLogin) {
                if (user == null) {
                    System.out.println("USER_NOT_LOGIN");
                    render(response, CodeMsg.USER_NOT_LOGIN);
                    return false;
                }
                key += "_" + user.getId();
            } else {
                //do_nothing
            }
            //martine flower 重构  改善代码设计
            AccessKey accessKey = AccessKey.withExpire(seconds);
            Integer count = redisService.get(accessKey, key, Integer.class);
            if (count == null) {
                redisService.set(accessKey, key, 1);
            } else if (count < maxCount) {
                redisService.incr(accessKey, key);
            } else {
                render(response, CodeMsg.REQUEST_SPEED);
                return false;
            }
        }
        return true;

    }

    private void render(HttpServletResponse response, CodeMsg cm) throws Exception {
        response.setContentType("application/json;charset=UTF-8");
        ServletOutputStream outputStream = response.getOutputStream();
        String s = JSON.toJSONString(Result.error(cm));
        outputStream.write(s.getBytes("UTF-8"));
        outputStream.flush();
        outputStream.close();
    }

    private User getUser(HttpServletRequest request, HttpServletResponse response) {
        String paramToken = request.getParameter(UserService.COOKIE_NAME_TOKEN);
        String cookieToken = getCookieValue(request, UserService.COOKIE_NAME_TOKEN);

        if (StringUtils.isNullOrEmpty(cookieToken) && StringUtils.isNullOrEmpty(paramToken)) {
            return null;
        }
        String token = StringUtils.isNullOrEmpty(paramToken) ? cookieToken : paramToken;
        return userService.getByToken(token, response);
    }

    private String getCookieValue(HttpServletRequest request, String cookieName) {

        Cookie[] cookies = request.getCookies();
        if (cookies == null || cookies.length <= 0)
            return null;
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(cookieName))
                return cookie.getValue();
        }
        return null;
    }
}
