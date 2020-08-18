package cn.stylefeng.guns.modular.homes.controller;

import cn.stylefeng.guns.modular.homestypes.service.IHomestypesService;
import cn.stylefeng.guns.modular.system.model.Homestypes;
import cn.stylefeng.guns.modular.system.model.User;
import cn.stylefeng.guns.modular.system.service.IUserService;
import cn.stylefeng.roses.core.base.controller.BaseController;
import com.alibaba.fastjson.JSONArray;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import cn.stylefeng.guns.core.log.LogObjectHolder;
import org.springframework.web.bind.annotation.RequestParam;
import cn.stylefeng.guns.modular.system.model.Homes;
import cn.stylefeng.guns.modular.homes.service.IHomesService;
import top.glma.plus.util.BeanToMap;

import java.util.List;
import java.util.Map;

/**
 * 会议室资源管理控制器
 *
 * @author fengshuonan
 * @Date 2020-08-15 21:52:23
 */
@Controller
@RequestMapping("/homes")
public class HomesController extends BaseController {

    private String PREFIX = "/homes/homes/";

    @Autowired
    private IHomesService homesService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IHomestypesService homestypesService;

    /**
     * 跳转到会议室资源管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "homes.html";
    }

    /**
     * 跳转到添加会议室资源管理
     */
    @RequestMapping("/homes_add")
    public String homesAdd() {
        return PREFIX + "homes_add.html";
    }

    /**
     * 跳转到修改会议室资源管理
     */
    @RequestMapping("/homes_update/{homesId}")
    public String homesUpdate(@PathVariable Integer homesId, Model model) {
        Homes homes = homesService.selectById(homesId);
        model.addAttribute("item",homes);
        LogObjectHolder.me().set(homes);
        return PREFIX + "homes_edit.html";
    }

    /**
     * 获取会议室资源管理列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        List<Homes> homes = homesService.selectList(null);
        JSONArray array = new JSONArray();
        for (Homes home : homes) {
            Map<String, Object> convert = BeanToMap.convert(home);
            User user = userService.selectById(home.getHosterid());
            Homestypes homestypes = homestypesService.selectById(home.getHometype());

            convert.replace("hosterid",user.getAccount());
            convert.replace("hometype",homestypes.getTypename());

            array.add(convert);
        }
        return array;
    }

    /**
     * 新增会议室资源管理
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(Homes homes) {
        homesService.insert(homes);
        return SUCCESS_TIP;
    }

    /**
     * 删除会议室资源管理
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer homesId) {
        homesService.deleteById(homesId);
        return SUCCESS_TIP;
    }

    /**
     * 修改会议室资源管理
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(Homes homes) {
        homesService.updateById(homes);
        return SUCCESS_TIP;
    }

    /**
     * 会议室资源管理详情
     */
    @RequestMapping(value = "/detail/{homesId}")
    @ResponseBody
    public Object detail(@PathVariable("homesId") Integer homesId) {
        return homesService.selectById(homesId);
    }
}
