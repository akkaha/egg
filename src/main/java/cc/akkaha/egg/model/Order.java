package cc.akkaha.egg.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

@TableName("order")
public class Order extends Model<Order> {
    private Integer id;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
