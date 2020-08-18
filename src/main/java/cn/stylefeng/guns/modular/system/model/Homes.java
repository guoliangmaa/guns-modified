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
 * @since 2020-08-15
 */
@TableName("homes")
public class Homes extends Model<Homes> {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 房间号（名称）
     */
    private String homeno;
    /**
     * 所在地点
     */
    private String address;
    /**
     * 可容纳人数
     */
    private Integer space;
    /**
     * 负责人id
     */
    private String hosterid;
    /**
     * 会议室类型
     */
    private Integer hometype;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHomeno() {
        return homeno;
    }

    public void setHomeno(String homeno) {
        this.homeno = homeno;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getSpace() {
        return space;
    }

    public void setSpace(Integer space) {
        this.space = space;
    }

    public String getHosterid() {
        return hosterid;
    }

    public void setHosterid(String hosterid) {
        this.hosterid = hosterid;
    }

    public Integer getHometype() {
        return hometype;
    }

    public void setHometype(Integer hometype) {
        this.hometype = hometype;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Homes{" +
        ", id=" + id +
        ", homeno=" + homeno +
        ", address=" + address +
        ", space=" + space +
        ", hosterid=" + hosterid +
        ", hometype=" + hometype +
        "}";
    }
}
