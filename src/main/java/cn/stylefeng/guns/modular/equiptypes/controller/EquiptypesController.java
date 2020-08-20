package cn.stylefeng.guns.modular.equiptypes.controller;

import cn.stylefeng.roses.core.base.controller.BaseController;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import cn.stylefeng.guns.core.log.LogObjectHolder;
import org.springframework.web.bind.annotation.RequestParam;
import cn.stylefeng.guns.modular.system.model.Equiptypes;
import cn.stylefeng.guns.modular.equiptypes.service.IEquiptypesService;

/**
 * 设备类型管理控制器
 *
 * @author fengshuonan
 * @Date 2020-08-14 14:58:05
 */
@Controller
@RequestMapping("/equiptypes")
public class EquiptypesController extends BaseController {

    private String PREFIX = "/equiptypes/equiptypes/";

    @Autowired
    private IEquiptypesService equiptypesService;

    /**
     * 跳转到设备类型管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "equiptypes.html";
    }

    /**
     * 跳转到添加设备类型管理
     */
    @RequestMapping("/equiptypes_add")
    public String equiptypesAdd() {
        return PREFIX + "equiptypes_add.html";
    }

    /**
     * 跳转到修改设备类型管理
     */
    @RequestMapping("/equiptypes_update/{equiptypesId}")
    public String equiptypesUpdate(@PathVariable Integer equiptypesId, Model model) {
        Equiptypes equiptypes = equiptypesService.selectById(equiptypesId);
        model.addAttribute("item",equiptypes);
        LogObjectHolder.me().set(equiptypes);
        return PREFIX + "equiptypes_edit.html";
    }

    /**
     * 获取设备类型管理列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        EntityWrapper<Equiptypes> wrapper = new EntityWrapper<>();
        if (condition != null && !condition.equals("")){
                wrapper.like("typename",condition);
        }
        return equiptypesService.selectList(wrapper);
    }

    /**
     * 新增设备类型管理
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(Equiptypes equiptypes) {
        equiptypesService.insert(equiptypes);
        return SUCCESS_TIP;
    }

    /**
     * 删除设备类型管理
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer equiptypesId) {
        equiptypesService.deleteById(equiptypesId);
        return SUCCESS_TIP;
    }

    /**
     * 修改设备类型管理
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(Equiptypes equiptypes) {
        equiptypesService.updateById(equiptypes);
        return SUCCESS_TIP;
    }

    /**
     * 设备类型管理详情
     */
    @RequestMapping(value = "/detail/{equiptypesId}")
    @ResponseBody
    public Object detail(@PathVariable("equiptypesId") Integer equiptypesId) {
        return equiptypesService.selectById(equiptypesId);
    }
}
