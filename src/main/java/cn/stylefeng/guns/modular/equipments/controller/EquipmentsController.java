package cn.stylefeng.guns.modular.equipments.controller;

import cn.stylefeng.guns.modular.equiptypes.service.IEquiptypesService;
import cn.stylefeng.guns.modular.homes.service.IHomesService;
import cn.stylefeng.guns.modular.system.model.Equiptypes;
import cn.stylefeng.guns.modular.system.model.Homes;
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
import cn.stylefeng.guns.modular.system.model.Equipments;
import cn.stylefeng.guns.modular.equipments.service.IEquipmentsService;
import top.glma.plus.util.BeanToMap;

import java.util.List;
import java.util.Map;

/**
 * 会议室设备管理控制器
 *
 * @author fengshuonan
 * @Date 2020-08-14 14:48:27
 */
@Controller
@RequestMapping("/equipments")
public class EquipmentsController extends BaseController {

    private String PREFIX = "/equipments/equipments/";

    @Autowired
    private IEquipmentsService equipmentsService;

    @Autowired
    private IHomesService homesService;

    @Autowired
    private IEquiptypesService equiptypesService;

    /**
     * 跳转到会议室设备管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "equipments.html";
    }

    /**
     * 跳转到添加会议室设备管理
     */
    @RequestMapping("/equipments_add")
    public String equipmentsAdd() {
        return PREFIX + "equipments_add.html";
    }

    /**
     * 跳转到修改会议室设备管理
     */
    @RequestMapping("/equipments_update/{equipmentsId}")
    public String equipmentsUpdate(@PathVariable Integer equipmentsId, Model model) {
        Equipments equipments = equipmentsService.selectById(equipmentsId);
        model.addAttribute("item",equipments);
        LogObjectHolder.me().set(equipments);
        return PREFIX + "equipments_edit.html";
    }

    /**
     * 获取会议室设备管理列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        List<Equipments> equipments = equipmentsService.selectList(null);
        JSONArray array = new JSONArray();
        for (Equipments equipment : equipments) {
            Map<String, Object> convert = BeanToMap.convert(equipment);
            Homes homes = homesService.selectById(equipment.getBelongto());
            Equiptypes equiptypes = equiptypesService.selectById(equipment.getEquiptype());

            convert.replace("belongto",homes.getHomeno());
            convert.replace("equiptype",equiptypes.getTypename());

            array.add(convert);
        }
        return array;
    }

    /**
     * 新增会议室设备管理
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(Equipments equipments) {
        equipmentsService.insert(equipments);
        return SUCCESS_TIP;
    }

    /**
     * 删除会议室设备管理
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer equipmentsId) {
        equipmentsService.deleteById(equipmentsId);
        return SUCCESS_TIP;
    }

    /**
     * 修改会议室设备管理
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(Equipments equipments) {
        equipmentsService.updateById(equipments);
        return SUCCESS_TIP;
    }

    /**
     * 会议室设备管理详情
     */
    @RequestMapping(value = "/detail/{equipmentsId}")
    @ResponseBody
    public Object detail(@PathVariable("equipmentsId") Integer equipmentsId) {
        return equipmentsService.selectById(equipmentsId);
    }
}
