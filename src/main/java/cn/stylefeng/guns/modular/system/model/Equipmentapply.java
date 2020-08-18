package cn.stylefeng.guns.modular.system.model;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author stylefeng
 * @since 2020-08-14
 */
@TableName("equipmentapply")
public class Equipmentapply extends Model<Equipmentapply> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 会议编号
     */
    private Integer mettingid;
    /**
     * 设备主键
     * 做了些修改
     */
    private String equipmentid;

    /**
     * 审核状态
     * mgl添加
     */
    private Integer status;

    @Override
    public String toString() {
        return "Equipmentapply{" +
                "id=" + id +
                ", mettingid=" + mettingid +
                ", equipmentid=" + equipmentid +
                ", status=" + status +
                '}';
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMettingid() {
        return mettingid;
    }

    public void setMettingid(Integer mettingid) {
        this.mettingid = mettingid;
    }

    public String getEquipmentid() {
        return equipmentid;
    }

    public void setEquipmentid(String equipmentid) {
        this.equipmentid = equipmentid;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
