package com.xiaowei.springbootdemo.pro.controller;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.xiaowei.springbootdemo.pro.entity.User;
import com.xiaowei.springbootdemo.pro.interf.ICacheService;
import com.xiaowei.springbootdemo.pro.pojo.LoginInfo;
import com.xiaowei.springbootdemo.pro.result.Result;
import com.xiaowei.springbootdemo.pro.result.ResultFactory;
import com.xiaowei.springbootdemo.pro.service.CaptchaService;
import com.xiaowei.springbootdemo.pro.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;

@Controller
public class LoginController {
    private final static Logger logger = LoggerFactory.getLogger(LoginController.class);
    @Autowired
    UserService userService;
    @Autowired
    private DefaultKaptcha producer;
    @Autowired
    private CaptchaService captchaService;
    @Autowired
    private ICacheService redisCacheServiceImpl;


    @CrossOrigin
    @PostMapping(value = "/api/login")
    @ResponseBody
    public Result login(@RequestBody LoginInfo loginInfo) {
        logger.info("用户["+loginInfo.getUsername()+"]进行登录认证！");
        String cacheCaptcha = redisCacheServiceImpl.getCache(loginInfo.getcToken());
        if (null == cacheCaptcha){
            return ResultFactory.buildFailResult("请刷新验证码后重新输入验证码!");
        }else{
            if (cacheCaptcha.equals(loginInfo.getCaptcha())){
                //删除缓存中的验证码
                redisCacheServiceImpl.removeCache(loginInfo.getcToken());
                String username = loginInfo.getUsername();
                Subject subject = SecurityUtils.getSubject();
//              subject.getSession().setTimeout(10000);
                UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username, loginInfo.getPassword());
                usernamePasswordToken.setRememberMe(true);
                try {
                    subject.login(usernamePasswordToken);
                    User user = (User) subject.getPrincipal();
                    logger.info("用户["+loginInfo.getUsername()+"]登录认证成功！");
                    return ResultFactory.buildSuccessResult(user);
                } catch (AuthenticationException e) {
                    logger.info("用户["+loginInfo.getUsername()+"]登录认证失败！");
                    String message = "账号密码错误";
                    return ResultFactory.buildResult(100001,message,null);
                }
            }else{
                logger.info("用户["+loginInfo.getUsername()+"]登录验证码输入错误！");
                return ResultFactory.buildFailResult("请输入正确验证码!");
            }
        }

    }

    @CrossOrigin
    @PostMapping("api/register")
    @ResponseBody
    public Result register(@RequestBody User user) {
        String username = user.getUsername();
        String password = user.getPassword();
        username = HtmlUtils.htmlEscape(username);
        user.setUsername(username);

        boolean exist = userService.isExist(username);
        if (exist) {
            String message = "用户名已被使用";
            return ResultFactory.buildFailResult(message);
        }

        // 生成盐,默认长度 16 位
        String salt = new SecureRandomNumberGenerator().nextBytes().toString();
        // 设置 hash 算法迭代次数
        int times = 2;
        // 得到 hash 后的密码
        String encodedPassword = new SimpleHash("md5", password, salt, times).toString();
        // 存储用户信息，包括 salt 与 hash 后的密码
        user.setSalt(salt);
        user.setPassword(encodedPassword);
        userService.add(user);

        return ResultFactory.buildSuccessResult(user);
    }
    @CrossOrigin
    @ResponseBody
    @GetMapping("api/logout")
    public Result logout() {
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        subject.logout();
        String message = "成功登出";
        logger.info("用户["+user.getUsername()+"]登出成功！");
        return ResultFactory.buildSuccessResult(message);
    }
    @CrossOrigin
    @ResponseBody
    @GetMapping(value = "api/authentication")
    public String authentication(){
        return "身份认证成功";
    }

    /**
     * 获取验证码
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    @ResponseBody
    @PostMapping("/api/captcha")
    public Map<String, Object> captcha(HttpServletResponse response) throws ServletException, IOException {

        // 生成文字验证码
        String text = producer.createText();
        // 生成图片验证码
        ByteArrayOutputStream outputStream = null;
        BufferedImage image = producer.createImage(text);

        outputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "png", outputStream);

        // 对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();

        // 生成captcha的token
        Map<String, Object> map = captchaService.createToken(text);
        map.put("img", encoder.encode(outputStream.toByteArray()));
        map.put("code", 200);
        logger.info("刷新验证码成功！");
        return map;
    }

}
