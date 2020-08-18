package cn.stylefeng.guns.modular.meetingapply.controller;

import cn.stylefeng.guns.core.shiro.ShiroKit;
import cn.stylefeng.guns.modular.homes.service.IHomesService;
import cn.stylefeng.guns.modular.schedule.service.IScheduleService;
import cn.stylefeng.guns.modular.system.model.Homes;
import cn.stylefeng.guns.modular.system.model.Schedule;
import cn.stylefeng.guns.modular.system.model.User;
import cn.stylefeng.guns.modular.system.service.IUserService;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.reqres.response.ResponseData;
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
import cn.stylefeng.guns.modular.system.model.Meetingapply;
import cn.stylefeng.guns.modular.meetingapply.service.IMeetingapplyService;
import top.glma.plus.util.BeanToMap;
import top.glma.plus.util.MergeObject;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 预约会议申请审批控制器
 *
 * @author fengshuonan
 * @Date 2020-08-14 15:20:58
 */
@Controller
@RequestMapping("/meetingapply")
public class MeetingapplyController extends BaseController {

    private String PREFIX = "/meetingapply/meetingapply/";

    @Autowired
    private IMeetingapplyService meetingapplyService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IHomesService homesService;

    @Autowired
    private IScheduleService scheduleService;

    /**
     * 跳转到预约会议申请审批首页
     */
    @RequestMapping("")
    public String index(Model model) {
        //获取用户头像
        Integer id = ShiroKit.getUser().getId();

        model.addAttribute("userId",id);
        return PREFIX + "meetingapply.html";
    }

    /**
     * 跳转到添加预约会议申请审批
     */
    @RequestMapping("/meetingapply_add")
    public String meetingapplyAdd(Model model) {
        //获取用户头像
        Integer id = ShiroKit.getUser().getId();

        model.addAttribute("userId",id);
        return PREFIX + "meetingapply_add.html";
    }

    /**
     * 跳转到修改预约会议申请审批
     */
    @RequestMapping("/meetingapply_update/{meetingapplyId}")
    public String meetingapplyUpdate(@PathVariable Integer meetingapplyId, Model model) {
        Meetingapply meetingapply = meetingapplyService.selectById(meetingapplyId);
        model.addAttribute("item",meetingapply);
        LogObjectHolder.me().set(meetingapply);
        return PREFIX + "meetingapply_edit.html";
    }

    /**
     * 获取预约会议申请审批列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        EntityWrapper<Meetingapply> wrapper = new EntityWrapper<>();
        wrapper.ne("statusid",1);
        List<Meetingapply> list = meetingapplyService.selectList(wrapper);
        JSONArray array = new JSONArray();
        for (Meetingapply meetingapply : list) {
            if (meetingapply.getEndtime().after(new Date())) {
                //convert javabean to map
                Map<String, Object> convert = BeanToMap.convert(meetingapply);
                String font;
                if (meetingapply.getStatusid() == 0) {
                    font = "<p style=\"color: yellowgreen\">审核中</p>";
                } else if (meetingapply.getStatusid() == 1) {
                    font = "<p style=\"color: green\">审核通过</p>";
                } else if (meetingapply.getStatusid() == 2) {
                    font = "<p style=\"color: red\">审核驳回</p>";
                } else if (meetingapply.getStatusid() == 3) {
                    font = "<p style=\"color: #0d8ddb\">使用中</p>";
                } else {
                    font = " <p style=\"color: sienna\">已失效</p>";
                }
                convert.replace("statusid", font);
                System.out.println(convert);

                Object workid = convert.get("workerid");

                // log work id
                int workerId = Integer.parseInt(workid.toString());
                System.out.println("workerid:    " + workerId);

                //find user and put his account to map
                User user = userService.selectById(workerId);
                convert.put("account", user.getAccount());

                //add home no and home id
                Object homeid = convert.get("homeid");
                Integer _homeId = Integer.parseInt(homeid.toString());
                Homes homes = homesService.selectById(_homeId);

                convert.replace("homeid", homes.getHomeno());
                convert.put("_homeid", homes.getId());

                //put a map to array
                array.add(convert);

            }
        }
        return array;
    }

    @RequestMapping(value = "/_list")
    @ResponseBody
    public Object _list(String condition) {
        EntityWrapper<Meetingapply> wrapper = new EntityWrapper<>();
//        wrapper.ne("statusid",1);
        List<Meetingapply> list = meetingapplyService.selectList(wrapper);
        JSONArray array = new JSONArray();
        for (Meetingapply meetingapply : list) {
            if (meetingapply.getEndtime().after(new Date())) {
                //convert javabean to map
                Map<String, Object> convert = BeanToMap.convert(meetingapply);
                String font;
                if (meetingapply.getStatusid() == 0) {
                    font = "<p style=\"color: yellowgreen\">审核中</p>";
                } else if (meetingapply.getStatusid() == 1) {
                    font = "<p style=\"color: green\">审核通过</p>";
                } else if (meetingapply.getStatusid() == 2) {
                    font = "<p style=\"color: red\">审核驳回</p>";
                } else if (meetingapply.getStatusid() == 3) {
                    font = "<p style=\"color: #0d8ddb\">使用中</p>";
                } else {
                    font = " <p style=\"color: sienna\">已失效</p>";
                }
                convert.replace("statusid", font);
                System.out.println(convert);

                Object workid = convert.get("workerid");

                // log work id
                int workerId = Integer.parseInt(workid.toString());
                System.out.println("workerid:    " + workerId);

                //find user and put his account to map
                User user = userService.selectById(workerId);
                convert.put("account", user.getAccount());

                //add home no and home id
                Object homeid = convert.get("homeid");
                Integer _homeId = Integer.parseInt(homeid.toString());
                Homes homes = homesService.selectById(_homeId);

                convert.replace("homeid", homes.getHomeno());
                convert.put("_homeid", homes.getId());

                //put a map to array
                array.add(convert);

            }
        }
        return array;
    }
    /**
     * 新增预约会议申请审批
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(Meetingapply meetingapply) {
        meetingapply.setApplytime(new Date());
        if (meetingapply.getBegintime().after(meetingapply.getEndtime())){
//            Map<String,Object> map = new HashMap<>();
//            map.put("message","结束时间早于开始时间！");
           return new ResponseData(false,500,"结束时间早于开始时间",null);
        }
        if (meetingapply.getBegintime().before(new Date())){
            return new ResponseData(false,500,"会议开始时间不得早于当前时间",null);
        }
        Integer homeId = meetingapply.getHomeid();
        Homes homes = homesService.selectById(homeId);
        if (meetingapply.getAttendance() > homes.getSpace()){
            return new ResponseData(false,500,"人数超过会议室限制( " + homes.getSpace()  +  "  人)",null);
        }
        meetingapplyService.insert(meetingapply);
        return SUCCESS_TIP;
    }

    /**
     * 删除预约会议申请审批
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer meetingapplyId) {
        meetingapplyService.deleteById(meetingapplyId);
        return SUCCESS_TIP;
    }

    /**
     * 修改预约会议申请审批
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(Meetingapply meetingapply) {
        Meetingapply byId = meetingapplyService.selectById(meetingapply.getId());
        if (meetingapply.getStatusid() == 1){
            Schedule schedule = new Schedule();
            schedule.setBegintime(byId.getBegintime());
            schedule.setEndtime(byId.getEndtime());
            schedule.setHomeid(byId.getHomeid());

            //判断时间内房间是否被占用
            EntityWrapper<Schedule> wrapper = new EntityWrapper<>();
            wrapper.eq("homeid",byId.getHomeid());
            List<Schedule> scheduleList = scheduleService.selectList(wrapper);
//            for (Schedule schedule1 : scheduleList) {
//                if ((byId.getBegintime().before(schedule1.getEndtime())
//                        && byId.getBegintime().after(schedule1.getBegintime()))
//                || (byId.getEndtime().before(schedule1.getEndtime())
//                    && byId.getEndtime().after(schedule1.getBegintime()))
//                || (byId.getBegintime().before(schedule1.getBegintime())
//                    && byId.getEndtime().after(schedule1.getEndtime()))
//                || (byId.getBegintime().equals(schedule1.getBegintime())
//                    && byId.getEndtime().equals(schedule1.getEndtime()))
//                ){
//                    return new ResponseData(false,500,"该时间段内会议室已被占用！",null);
//                }
//            }
            for (Schedule schedule1 : scheduleList) {
                if (!(byId.getEndtime().before(schedule1.getBegintime())
                        || byId.getBegintime().after(schedule1.getEndtime()))
                ){
                    return new ResponseData(false,500,"该时间段内会议室已被占用！",null);
                }
            }

            //插入日程并在申请表中保存
            scheduleService.insert(schedule);
            MergeObject.mergeObject(meetingapply,byId);
            meetingapplyService.updateById(byId);
//            meetingapplyService.deleteById(meetingapply.getId());
            return SUCCESS_TIP;
        }
        MergeObject.mergeObject(meetingapply,byId);
        meetingapplyService.updateById(byId);

        //如果是驳回，在日程表中删除
        EntityWrapper<Schedule> wrapper = new EntityWrapper<>();
        wrapper.eq("begintime",byId.getBegintime());
        wrapper.eq("endtime",byId.getEndtime());
        boolean delete = scheduleService.delete(wrapper);
        System.out.println("delete:  " +delete);
        return SUCCESS_TIP;
    }

    /**
     * 预约会议申请审批详情
     */
    @RequestMapping(value = "/detail/{meetingapplyId}")
    @ResponseBody
    public Object detail(@PathVariable("meetingapplyId") Integer meetingapplyId) {
        return meetingapplyService.selectById(meetingapplyId);
    }
}
