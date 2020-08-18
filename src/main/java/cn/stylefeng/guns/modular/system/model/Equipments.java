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
@TableName("equipments")
public class Equipments extends Model<Equipments> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 设备编号
     */
    private String equipmentid;
    /**
     * 设备名称
     */
    private String equipmentname;
    /**
     * 所属单位（会议室名称）
     */
    private String belongto;
    /**
     * 入库时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date storetime;
    /**
     * 设备类型
     */
    private Integer equiptype;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEquipmentid() {
        return equipmentid;
    }

    public void setEquipmentid(String equipmentid) {
        this.equipmentid = equipmentid;
    }

    public String getEquipmentname() {
        return equipmentname;
    }

    public void setEquipmentname(String equipmentname) {
        this.equipmentname = equipmentname;
    }

    public String getBelongto() {
        return belongto;
    }

    public void setBelongto(String belongto) {
        this.belongto = belongto;
    }

    public Date getStoretime() {
        return storetime;
    }

    public void setStoretime(Date storetime) {
        this.storetime = storetime;
    }

    public Integer getEquiptype() {
        return equiptype;
    }

    public void setEquiptype(Integer equiptype) {
        this.equiptype = equiptype;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Equipments{" +
        ", id=" + id +
        ", equipmentid=" + equipmentid +
        ", equipmentname=" + equipmentname +
        ", belongto=" + belongto +
        ", storetime=" + storetime +
        ", equiptype=" + equiptype +
        "}";
    }
}
