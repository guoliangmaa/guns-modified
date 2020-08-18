package cn.stylefeng.guns.modular.system.model;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author stylefeng
 * @since 2020-08-14
 */
@TableName("meetingapply")
public class Meetingapply extends Model<Meetingapply> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 发起人ID
     */
    private String workerid;
    /**
     * 会议主题
     */
    private String topic;
    /**
     * 会议说明文档地址
     */
    private String documentlink;
    /**
     * 会议室编号
     */
    private Integer homeid;
    /**
     * 参加会议人数
     */
    private Integer attendance;
    /**
     * 会议开始时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date begintime;
    /**
     * 会议结束时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endtime;
    /**
     * 审核状态
     */
    private Integer statusid;
    /**
     * 申请日期
     */

    private Date applytime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getWorkerid() {
        return workerid;
    }

    public void setWorkerid(String workerid) {
        this.workerid = workerid;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getDocumentlink() {
        return documentlink;
    }

    public void setDocumentlink(String documentlink) {
        this.documentlink = documentlink;
    }

    public Integer getHomeid() {
        return homeid;
    }

    public void setHomeid(Integer homeid) {
        this.homeid = homeid;
    }

    public Integer getAttendance() {
        return attendance;
    }

    public void setAttendance(Integer attendance) {
        this.attendance = attendance;
    }

    public Date getBegintime() {
        return begintime;
    }

    public void setBegintime(Date begintime) {
        this.begintime = begintime;
    }

    public Date getEndtime() {
        return endtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

    public Integer getStatusid() {
        return statusid;
    }

    public void setStatusid(Integer statusid) {
        this.statusid = statusid;
    }

    public Date getApplytime() {
        return applytime;
    }

    public void setApplytime(Date applytime) {
        this.applytime = applytime;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Meetingapply{" +
        ", id=" + id +
        ", workerid=" + workerid +
        ", topic=" + topic +
        ", documentlink=" + documentlink +
        ", homeid=" + homeid +
        ", attendance=" + attendance +
        ", begintime=" + begintime +
        ", endtime=" + endtime +
        ", statusid=" + statusid +
        ", applytime=" + applytime +
        "}";
    }
}
