package cn.ccuwxy.miaosha.controller;

import cn.ccuwxy.miaosha.access.AccessLimit;
import cn.ccuwxy.miaosha.domain.User;
import cn.ccuwxy.miaosha.redis.GoodsKey;
import cn.ccuwxy.miaosha.redis.RedisService;
import cn.ccuwxy.miaosha.result.CodeMsg;
import cn.ccuwxy.miaosha.result.Result;
import cn.ccuwxy.miaosha.service.GoodsService;
import cn.ccuwxy.miaosha.service.UserService;
import cn.ccuwxy.miaosha.vo.GoodsDetailVo;
import cn.ccuwxy.miaosha.vo.GoodsVo;
import cn.ccuwxy.miaosha.vo.LoginVo;
import com.mysql.jdbc.StringUtils;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.context.webflux.SpringWebFluxContext;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/goods")
public class GoodsController {
    @Autowired
    UserService userService;

    @Autowired
    RedisService redisService;

    @Autowired
    GoodsService goodsService;

    @Autowired
    ThymeleafViewResolver thymeleafViewResolver;

    @Autowired
    ApplicationContext applicationContext;

    /**
     * QPS 822.9
     * 5000*10
     *
     * @param model
     * @param user
     * @return
     */
    @RequestMapping(value = "/to_list", produces = "text/html")
    @ResponseBody
    public String list(HttpServletRequest request, HttpServletResponse response, Model model, User user) {

//        if (user == null)
//            return "login";
//        System.out.println("user = " + user);
        model.addAttribute("user", user);

        //取缓存
        String html = redisService.get(GoodsKey.getGoodsList, "", String.class);
        if (!StringUtils.isNullOrEmpty(html)) {
            return html;
        }

        //
        List<GoodsVo> goodsVos = goodsService.queryGoodsVoList();
        model.addAttribute("goodsVos", goodsVos);


//        return "goods_list";


        //手动渲染
        WebContext ctx = new WebContext(request, response,
                request.getServletContext(), request.getLocale(), model.asMap());
        html = thymeleafViewResolver.getTemplateEngine().process("goods_list", ctx);
        if (!StringUtils.isNullOrEmpty(html)) {
            redisService.set(GoodsKey.getGoodsList, "", html);
        }
        return html;

    }

//    @RequestMapping(value = "/to_detail2/{goodsId}", produces = "text/html")
//    @ResponseBody
//    public String detail2(HttpServletRequest request, HttpServletResponse response, Model model, User user, @PathVariable("goodsId") long goodsId) {
//        if (user == null)
//            return "login";
//        //snowflake
//        model.addAttribute("user", user);
//
//        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
//        model.addAttribute("goods", goods);
//
//        //
//        long startTime = goods.getStartDate().getTime();
//        long endTime = goods.getEndDate().getTime();
//        long now = System.currentTimeMillis();
//
//        int miaoshaStatue = 0;
//        int remainSeconds = 0;
//
//        if (now < startTime) { //秒杀未开始
//            miaoshaStatue = 0;
//            remainSeconds = (int) (startTime - now) / 1000;
//        } else if (now > endTime) {//秒杀结束
//            miaoshaStatue = 2;
//            remainSeconds = -1;
//        } else {
//            miaoshaStatue = 1;
//            remainSeconds = 0;
//        }
//        model.addAttribute("miaoshaStatue", miaoshaStatue);
//        model.addAttribute("remainSeconds", remainSeconds);
//        List<GoodsVo> goodsVos = goodsService.queryGoodsVoList();
//        model.addAttribute("goodsVos", goodsVos);
//
//
////        return "goods_detail";
//
//        //取缓存
//        String html = redisService.get(GoodsKey.getGoodsDetail, goodsId + "", String.class);
//        if (!StringUtils.isNullOrEmpty(html)) {
//            return html;
//        }
//        //手动渲染
//        WebContext ctx = new WebContext(request, response,
//                request.getServletContext(), request.getLocale(), model.asMap());
//        html = thymeleafViewResolver.getTemplateEngine().process("goods_detail", ctx);
//        if (!StringUtils.isNullOrEmpty(html)) {
//            redisService.set(GoodsKey.getGoodsDetail, goodsId + "", html);
//        }
//        return html;
//    }

    @AccessLimit(seconds = 5,maxCount = 5,needLogin = true)
    @RequestMapping(value = "/detail/{goodsId}")
    @ResponseBody
    public Result<GoodsDetailVo> detail(User user, @PathVariable("goodsId") long goodsId) {
//        if (user == null)
//            return Result.error(CodeMsg.USER_NOT_LOGIN);

        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);

        long startTime = goods.getStartDate().getTime();
        long endTime = goods.getEndDate().getTime();
        long now = System.currentTimeMillis();

        int miaoshaStatue = 0;
        int remainSeconds = 0;

        if (now < startTime) { //秒杀未开始
            miaoshaStatue = 0;
            remainSeconds = (int) (startTime - now) / 1000;
        } else if (now > endTime) {//秒杀结束
            miaoshaStatue = 2;
            remainSeconds = -1;
        } else {
            miaoshaStatue = 1;
            remainSeconds = 0;
        }
        GoodsDetailVo vo = new GoodsDetailVo();
        vo.setGoodsVo(goods);
        vo.setUser(user);
        vo.setMiaoshaStatus(miaoshaStatue);
        vo.setRemainSeconds(remainSeconds);

        return Result.success(vo);
    }



}
