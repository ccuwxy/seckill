package cn.ccuwxy.miaosha.service.impl;

import cn.ccuwxy.miaosha.dao.UserDao;
import cn.ccuwxy.miaosha.domain.User;
import cn.ccuwxy.miaosha.exception.GlobleException;
import cn.ccuwxy.miaosha.redis.RedisService;
import cn.ccuwxy.miaosha.redis.UserKey;
import cn.ccuwxy.miaosha.result.CodeMsg;
import cn.ccuwxy.miaosha.service.UserService;
import cn.ccuwxy.miaosha.util.MD5Util;
import cn.ccuwxy.miaosha.util.UUIDUtil;
import cn.ccuwxy.miaosha.vo.LoginVo;
import com.mysql.jdbc.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * (User)表服务实现类
 *
 * @author makejava
 * @since 2020-03-20 23:42:38
 */
@Service("userService")
public class UserServiceImpl implements UserService {


    @Autowired
    private UserDao userDao;

    @Autowired
    private RedisService redisService;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public User queryById(Long id) {
        //取缓存
        User user = redisService.get(UserKey.getById, "" + id, User.class);
        if(user!=null){
            return user;
        }

        //查数据库
        user = userDao.queryById(id);
        if(user !=null){
            redisService.set(UserKey.getById, "" + id, user);
        }
        return user;
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<User> queryAllByLimit(int offset, int limit) {
        return this.userDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param user 实例对象
     * @return 实例对象
     */
    @Override
    public User insert(User user) {
        System.out.println(user.getId());
        this.userDao.insert(user);
        return user;
    }

    /**
     * 修改数据
     *
     * @param user 实例对象
     * @return 实例对象
     */
    @Override
    public User updateLastLoginDate(User user) {

        this.userDao.updateLastLoginDate(user);
        return this.queryById(user.getId());
    }

    @Override
    public boolean updatePassword(String token,long id,String formPass) {
        //取user
        User u = queryById(id);
        if(u==null){
            throw new GlobleException(CodeMsg.MOBILE_NOT_EXIST);
        }
        //
        User toBeUpdate = new User();
        toBeUpdate.setId(id);
        toBeUpdate.setPassword(MD5Util.formPassToDbPass(formPass,u.getSalt()));
        userDao.updatePassword(toBeUpdate);
        //处理缓存
        redisService.del(UserKey.getById,""+id);
        u.setPassword(toBeUpdate.getPassword());
        redisService.set(UserKey.token,token,u);
        return true;
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.userDao.deleteById(id) > 0;
    }

    @Override
    public String login(HttpServletResponse response, LoginVo loginVo) {
        if (loginVo == null) {
            throw new GlobleException(CodeMsg.SERVER_ERROR);
        }
        String mobile = loginVo.getMobile();
        String formPass = loginVo.getPassword();
        User user = queryById(Long.parseLong(mobile));
        if (user == null) {
            throw new GlobleException(CodeMsg.MOBILE_NOT_EXIST);
        }
        String dbPass = user.getPassword();
        String dbSalt = user.getSalt();
        String calcPass = MD5Util.formPassToDbPass(formPass, dbSalt);
        if (!calcPass.equals(dbPass)) {
            throw new GlobleException(CodeMsg.PASSWORD_ERROR);
        }

        //生成cookie
        String token = UUIDUtil.uuid();
//        token = MD5Util.tokenToRedis(user.getId().toString());
        addCookie(user,token, response);
        Date date = new Date();
        user.setLastLoginDate(date);
        userDao.updateLastLoginDate(user);
        return token;
    }

    @Override
    public User getByToken(String token, HttpServletResponse response) {
        if (StringUtils.isNullOrEmpty(token))
            return null;
        User user = redisService.get(UserKey.token, token, User.class);
        //延长有效期
        if (user != null)
            addCookie(user,token, response);
        return user;
    }

    private void addCookie(User user,String token, HttpServletResponse response) {

        //生成cookie
//        String token = UUIDUtil.uuid();
//        String flag = UUIDUtil.uuid();

        redisService.set(UserKey.token, token, user);
        Cookie cookie = new Cookie(COOKIE_NAME_TOKEN, token);
        cookie.setMaxAge(UserKey.token.expireSeconds());
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}