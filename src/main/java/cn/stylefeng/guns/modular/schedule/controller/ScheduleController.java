package cn.stylefeng.guns.modular.schedule.controller;

import cn.stylefeng.guns.modular.homes.service.IHomesService;
import cn.stylefeng.guns.modular.homestypes.service.IHomestypesService;
import cn.stylefeng.guns.modular.system.model.Homes;
import cn.stylefeng.guns.modular.system.model.Homestypes;
import cn.stylefeng.roses.core.base.controller.BaseController;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import cn.stylefeng.guns.core.log.LogObjectHolder;
import org.springframework.web.bind.annotation.RequestParam;
import cn.stylefeng.guns.modular.system.model.Schedule;
import cn.stylefeng.guns.modular.schedule.service.IScheduleService;
import top.glma.plus.util.BeanToMap;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 会议室日程查询控制器
 *
 * @author fengshuonan
 * @Date 2020-08-14 14:41:02
 */
@Controller
@RequestMapping("/schedule")
public class ScheduleController extends BaseController {

    private String PREFIX = "/schedule/schedule/";

    @Autowired
    private IScheduleService scheduleService;

    @Autowired
    private IHomestypesService homestypesService;

    @Autowired
    private IHomesService homesService;

    /**
     * 跳转到会议室日程查询首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "schedule.html";
    }

    /**
     * 跳转到添加会议室日程查询
     */
    @RequestMapping("/schedule_add")
    public String scheduleAdd() {
        return PREFIX + "schedule_add.html";
    }

    /**
     * 跳转到修改会议室日程查询
     */
    @RequestMapping("/schedule_update/{scheduleId}")
    public String scheduleUpdate(@PathVariable Integer scheduleId, Model model) {
        Schedule schedule = scheduleService.selectById(scheduleId);
        model.addAttribute("item",schedule);
        LogObjectHolder.me().set(schedule);
        return PREFIX + "schedule_edit.html";
    }

    /**
     * 获取会议室日程查询列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        Date date = new Date();
        List<Schedule> schedules = scheduleService.selectList(null);
        JSONArray array = new JSONArray();
        for (Schedule schedule : schedules) {
            Map<String, Object> convert = BeanToMap.convert(schedule);
            Date beginTime = schedule.getBegintime();
            Date endTime = schedule.getEndtime();
            if (date.before(beginTime)){
                convert.put("status",0);
            }else if (date.after(beginTime) && date.before(endTime)){
                convert.put("status",1);
            }else {
                convert.put("status",2);
            }

//            Homestypes homestypes = homestypesService.selectById(schedule.getHomeid());
            Homes homes = homesService.selectById(schedule.getHomeid());
            convert.replace("homeid",homes.getHomeno());

            //如果加了条件，在这里筛选，如果房间号为该房间号则添加进数组
            if (condition != null && !condition.equals("")){
                if (condition.equals(homes.getHomeno())){
                    array.add(convert);
                }
            }else {
                array.add(convert);
            }
        }
        return array;
    }

    /**
     * 新增会议室日程查询
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(Schedule schedule) {
        scheduleService.insert(schedule);
        return SUCCESS_TIP;
    }

    /**
     * 删除会议室日程查询
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer scheduleId) {
        scheduleService.deleteById(scheduleId);
        return SUCCESS_TIP;
    }

    /**
     * 修改会议室日程查询
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(Schedule schedule) {
        scheduleService.updateById(schedule);
        return SUCCESS_TIP;
    }

    /**
     * 会议室日程查询详情
     */
    @RequestMapping(value = "/detail/{scheduleId}")
    @ResponseBody
    public Object detail(@PathVariable("scheduleId") Integer scheduleId) {
        return scheduleService.selectById(scheduleId);
    }
}
