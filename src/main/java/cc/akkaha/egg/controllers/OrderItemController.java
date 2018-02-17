package cc.akkaha.egg.controllers;

import cc.akkaha.egg.db.model.OrderItem;
import cc.akkaha.egg.db.model.UserOrder;
import cc.akkaha.egg.model.ApiRes;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/egg/order-item")
public class OrderItemController {

    @PostMapping("/query")
    public Object query(@RequestBody QueryOrderItem query) {
        ApiRes res = new ApiRes();
        OrderItem order = new OrderItem();
        Wrapper wrapper = new EntityWrapper<OrderItem>();
        if (null != query.getUser()) {
            wrapper.eq(OrderItem.USER, query.getUser());
        }
        if (null != query.getCar()) {
            wrapper.eq(OrderItem.CAR, query.getCar());
        }
        Page page = order.selectPage(new Page<OrderItem>(query.getCurrent(), query.getSize(),
                        UserOrder.CREATED_AT, false),
                wrapper);
        res.setData(page);
        return res;
    }

    @PostMapping("/insert")
    public Object insert(@RequestBody NewOrderItem item) {
        ApiRes res = new ApiRes();
        OrderItem order = new OrderItem();
        order.setWeight(new BigDecimal(item.getWeight()));
        order.setUser(item.getUser());
        order.setCar(item.getCar());
        boolean ret = order.insert();
        if (ret) {
            res.setData(order);
        } else {
            res.setMsg("创建失败!");
        }
        return res;
    }

    @PostMapping("/delete")
    public Object delete(@RequestBody OrderItem item) {
        ApiRes res = new ApiRes();
        boolean ret = item.deleteById();
        if (ret) {
            res.setData(item);
        } else {
            res.setMsg("删除失败!");
        }
        return res;
    }

    @PostMapping("/update")
    public Object update(@RequestBody OrderItem item) {
        ApiRes res = new ApiRes();
        Wrapper wrapper = new EntityWrapper<OrderItem>();
        wrapper.setSqlSelect(OrderItem.USER, OrderItem.CAR, OrderItem.WEIGHT).eq(OrderItem.ID,
                item.getId());
        boolean ret = item.update(wrapper);
        if (ret) {
            res.setData(item);
        } else {
            res.setMsg("更新失败!");
        }
        return res;
    }
}

class NewOrderItem {

    private String weight;
    private Integer user;
    private Integer car;

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public Integer getUser() {
        return user;
    }

    public void setUser(Integer user) {
        this.user = user;
    }

    public Integer getCar() {
        return car;
    }

    public void setCar(Integer car) {
        this.car = car;
    }
}

class QueryOrderItem extends OrderItem {

    private Integer current = 1;
    private Integer size = 10;

    public Integer getCurrent() {
        if (null == this.current) {
            return 10;
        } else {
            return this.current;
        }
    }

    public Integer getSize() {
        if (null == this.size) {
            return 10;
        } else {
            return this.size;
        }
    }
}