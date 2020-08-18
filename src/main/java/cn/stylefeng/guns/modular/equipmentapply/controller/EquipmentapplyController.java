package cn.stylefeng.guns.modular.equipmentapply.controller;

import cn.stylefeng.guns.modular.equipments.service.IEquipmentsService;
import cn.stylefeng.guns.modular.meetingapply.service.IMeetingapplyService;
import cn.stylefeng.guns.modular.system.model.Equipments;
import cn.stylefeng.guns.modular.system.model.Meetingapply;
import cn.stylefeng.guns.modular.system.model.User;
import cn.stylefeng.guns.modular.system.service.IUserService;
import cn.stylefeng.roses.core.base.controller.BaseController;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import cn.stylefeng.guns.core.log.LogObjectHolder;
import org.springframework.web.bind.annotation.RequestParam;
import cn.stylefeng.guns.modular.system.model.Equipmentapply;
import cn.stylefeng.guns.modular.equipmentapply.service.IEquipmentapplyService;
import top.glma.plus.util.BeanToMap;
import top.glma.plus.util.MergeObject;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 会议设备申请审批控制器
 *
 * @author fengshuonan
 * @Date 2020-08-14 14:35:38
 */
@Controller
@RequestMapping("/equipmentapply")
public class EquipmentapplyController extends BaseController {

    private String PREFIX = "/equipmentapply/equipmentapply/";

    @Autowired
    private IEquipmentapplyService equipmentapplyService;

    @Autowired
    private IEquipmentsService equipmentsService;

    @Autowired
    private IMeetingapplyService meetingapplyService;

    @Autowired
    private IUserService userService;

    /**
     * 跳转到会议设备申请审批首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "equipmentapply.html";
    }

    /**
     * 跳转到添加会议设备申请审批
     */
    @RequestMapping("/equipmentapply_add")
    public String equipmentapplyAdd() {
        return PREFIX + "equipmentapply_add.html";
    }

    /**
     * 跳转到修改会议设备申请审批
     */
    @RequestMapping("/equipmentapply_update/{equipmentapplyId}")
    public String equipmentapplyUpdate(@PathVariable Integer equipmentapplyId, Model model) {
        Equipmentapply equipmentapply = equipmentapplyService.selectById(equipmentapplyId);
        model.addAttribute("item",equipmentapply);
        LogObjectHolder.me().set(equipmentapply);
        return PREFIX + "equipmentapply_edit.html";
    }

    /**
     * 获取会议设备申请审批列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        EntityWrapper<Equipmentapply> wrapper = new EntityWrapper<>();
        JSONArray array = new JSONArray();
        List<Equipmentapply> equipmentapplyList = equipmentapplyService.selectList(null);
        for (Equipmentapply equipmentapply : equipmentapplyList) {
            Meetingapply meetingapply = meetingapplyService.selectById(equipmentapply.getMettingid());
            //会议还没结束的话，才显示，其他的都不显示
            if (meetingapply.getEndtime().after(new Date())){
                //把设备申请放入
                Map<String, Object> convert = BeanToMap.convert(equipmentapply);
                User user = userService.selectById(meetingapply.getWorkerid());
                convert.put("meetDetail",user.getAccount() + "发起的   " + meetingapply.getTopic());

                //处理设备信息,是一个json对象
                JSONArray jsonArray = JSONArray.parseArray(equipmentapply.getEquipmentid());
                String equipments = "";
                for (int i = 0; i < jsonArray.size(); i++) {
                    String o = jsonArray.get(i).toString();
                    Equipments selectById = equipmentsService.selectById(o);
                    equipments += selectById.getEquipmentname() + "    ";
                }

                convert.put("equipments", equipments);

                array.add(convert);

            }
        }
        return array;
    }

    /**
     * 新增会议设备申请审批
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(Equipmentapply equipmentapply) {
        String equipmentid = equipmentapply.getEquipmentid();
        System.out.println(equipmentid);
        equipmentapplyService.insert(equipmentapply);
        return SUCCESS_TIP;
    }

    /**
     * 删除会议设备申请审批
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer equipmentapplyId) {
        equipmentapplyService.deleteById(equipmentapplyId);
        return SUCCESS_TIP;
    }

    /**
     * 修改会议设备申请审批
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(Equipmentapply equipmentapply) {
        Equipmentapply byId = equipmentapplyService.selectById(equipmentapply.getId());
        MergeObject.mergeObject(equipmentapply,byId);
        equipmentapplyService.updateById(byId);
        return SUCCESS_TIP;
    }

    /**
     * 会议设备申请审批详情
     */
    @RequestMapping(value = "/detail/{equipmentapplyId}")
    @ResponseBody
    public Object detail(@PathVariable("equipmentapplyId") Integer equipmentapplyId) {
        return equipmentapplyService.selectById(equipmentapplyId);
    }


    @RequestMapping(value = "/findEquipments")
    @ResponseBody
    public Object findEquipments(@RequestParam("homeno") Integer homeno) {
        EntityWrapper<Equipments> wrapper = new EntityWrapper<>();
        wrapper.eq("belongto",homeno);
        List<Equipments> equipments = equipmentsService.selectList(wrapper);

        return equipments;
    }


}
