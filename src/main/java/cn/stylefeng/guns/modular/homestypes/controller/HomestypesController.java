package cn.stylefeng.guns.modular.homestypes.controller;

import cn.stylefeng.roses.core.base.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import cn.stylefeng.guns.core.log.LogObjectHolder;
import org.springframework.web.bind.annotation.RequestParam;
import cn.stylefeng.guns.modular.system.model.Homestypes;
import cn.stylefeng.guns.modular.homestypes.service.IHomestypesService;

/**
 * 会议室类型管理控制器
 *
 * @author fengshuonan
 * @Date 2020-08-14 14:46:49
 */
@Controller
@RequestMapping("/homestypes")
public class HomestypesController extends BaseController {

    private String PREFIX = "/homestypes/homestypes/";

    @Autowired
    private IHomestypesService homestypesService;

    /**
     * 跳转到会议室类型管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "homestypes.html";
    }

    /**
     * 跳转到添加会议室类型管理
     */
    @RequestMapping("/homestypes_add")
    public String homestypesAdd() {
        return PREFIX + "homestypes_add.html";
    }

    /**
     * 跳转到修改会议室类型管理
     */
    @RequestMapping("/homestypes_update/{homestypesId}")
    public String homestypesUpdate(@PathVariable Integer homestypesId, Model model) {
        Homestypes homestypes = homestypesService.selectById(homestypesId);
        model.addAttribute("item",homestypes);
        LogObjectHolder.me().set(homestypes);
        return PREFIX + "homestypes_edit.html";
    }

    /**
     * 获取会议室类型管理列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        return homestypesService.selectList(null);
    }

    /**
     * 新增会议室类型管理
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(Homestypes homestypes) {
        homestypesService.insert(homestypes);
        return SUCCESS_TIP;
    }

    /**
     * 删除会议室类型管理
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer homestypesId) {
        homestypesService.deleteById(homestypesId);
        return SUCCESS_TIP;
    }

    /**
     * 修改会议室类型管理
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(Homestypes homestypes) {
        homestypesService.updateById(homestypes);
        return SUCCESS_TIP;
    }

    /**
     * 会议室类型管理详情
     */
    @RequestMapping(value = "/detail/{homestypesId}")
    @ResponseBody
    public Object detail(@PathVariable("homestypesId") Integer homestypesId) {
        return homestypesService.selectById(homestypesId);
    }
}
