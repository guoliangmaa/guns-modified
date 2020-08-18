package top.glma.plus.controller;

import cn.stylefeng.guns.core.shiro.ShiroKit;
import cn.stylefeng.guns.core.shiro.ShiroUser;
import cn.stylefeng.guns.modular.homes.service.IHomesService;
import cn.stylefeng.guns.modular.schedule.service.IScheduleService;
import cn.stylefeng.guns.modular.system.model.Homes;
import cn.stylefeng.guns.modular.system.model.Schedule;
import cn.stylefeng.guns.modular.system.model.User;
import cn.stylefeng.guns.modular.system.service.IUserService;
import top.glma.plus.annotation.UserLoginToken;
import top.glma.plus.service.TokenService;
import top.glma.plus.util.TokenUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    IUserService userService;
    @Autowired
    TokenService tokenService;
    @Autowired
    private IHomesService homesService;
    @Autowired
    private IScheduleService scheduleService;

    // 登录
    @PostMapping("/logins")
    public Object login(@RequestBody User user, HttpServletResponse response) {
        JSONObject jsonObject = new JSONObject();
//        EntityWrapper<User> wrapper = new EntityWrapper<>();
//        wrapper.eq("account", user.getAccount());
//        wrapper.eq("password", user.getPassword());
//        User one = userService.selectOne(wrapper);
//        System.out.println(user.getPassword());

        Subject currentUser = ShiroKit.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(user.getAccount(), user.getPassword().toCharArray());

        try {
            currentUser.login(token);
            ShiroUser shiroUser = ShiroKit.getUser();
            System.out.println(shiroUser.getId());
            User selectById = userService.selectById(shiroUser.getId());

            String userToken = tokenService.getToken(selectById);
            jsonObject.put("token", userToken);

            Cookie cookie = new Cookie("token", userToken);
            cookie.setPath("/");
            response.addCookie(cookie);
        }catch (Exception e){
            e.printStackTrace();
            jsonObject.put("message", "登录失败,密码错误");
        }

        return jsonObject;
    }

    /***
     * 这个请求需要验证token才能访问
     *
     * @author: MRC
     * @date 2019年5月27日 下午5:45:19
     * @return String 返回类型
     */
    @UserLoginToken
    @GetMapping("/getMessage")
    public String getMessage() {

        // 取出token中带的用户id 进行操作
        System.out.println(TokenUtil.getTokenUserId());

        return "你已通过验证";
    }

    @GetMapping("/checkUsername")
    public Object checkUser(@RequestParam("username") String username) {
        User user = userService.selectOne(new EntityWrapper<User>().eq("u_username", username));
        Map<String, Object> map = new HashMap<>();
        if (user != null) {
            map.put("error_code", 1);
        }else {
            map.put("error_code", 0);
        }

        return map;
    }

    @GetMapping("/roomList")
    @UserLoginToken
    public Object roomList(){
//        User user = userService.selectOne(new EntityWrapper<User>().eq("id", TokenUtil.getTokenUserId()));
        List<Homes> homes = homesService.selectList(new EntityWrapper<Homes>());
        JSONArray array = new JSONArray();
        for (Homes home : homes) {
            JSONObject object = new JSONObject();
            object.put("home",home);
            List<Schedule> schedules = scheduleService.selectList(new EntityWrapper<Schedule>().eq("homeid", home.getId()));
            object.put("schedules",schedules);

            array.add(object);
        }

        return array;
    }
}
