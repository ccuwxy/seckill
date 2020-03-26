package cn.ccuwxy.miaosha.service;

import cn.ccuwxy.miaosha.domain.User;
import cn.ccuwxy.miaosha.result.CodeMsg;
import cn.ccuwxy.miaosha.vo.LoginVo;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * (User)表服务接口
 *
 * @author makejava
 * @since 2020-03-20 23:42:37
 */
public interface UserService {
    String COOKIE_NAME_TOKEN = "token";

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    User queryById(Long id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<User> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param user 实例对象
     * @return 实例对象
     */
    User insert(User user);

    /**
     * 修改最后登录日期
     *
     * @param user 实例对象
     * @return 实例对象
     */
    User updateLastLoginDate(User user);

    /**
     * 修改密码
     * @param token
     * @param id
     * @param formPass
     * @return
     */
    boolean updatePassword(String token,long id,String formPass);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

    String login(HttpServletResponse response, LoginVo loginVo);


    User getByToken(String token, HttpServletResponse response);
}