package cc.akkaha.egg.db.model;

import com.baomidou.mybatisplus.enums.IdType;
import java.math.BigDecimal;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

@TableName("egg_price_extra")
public class PriceExtra extends Model<PriceExtra> {

    private static final long serialVersionUID = 1L;

    /**
     * 自增主键
     */
    @TableId(value="id", type= IdType.AUTO)
    private Integer id;
    /**
     * 日期: yyyy-MM-dd
     */
    private String day;
    /**
     * 箱重
     */
    @TableField("weight_adjust")
    private BigDecimal weightAdjust;
    /**
     * 创建时间
     */
    @TableField("created_at")
    private Date createdAt;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public BigDecimal getWeightAdjust() {
        return weightAdjust;
    }

    public void setWeightAdjust(BigDecimal weightAdjust) {
        this.weightAdjust = weightAdjust;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public static final String ID = "id";

    public static final String DAY = "day";

    public static final String WEIGHT_ADJUST = "weight_adjust";

    public static final String CREATED_AT = "created_at";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
