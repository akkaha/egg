package cc.akkaha.egg.controllers;

import cc.akkaha.egg.controllers.model.NewOrderItem;
import cc.akkaha.egg.controllers.model.QueryOrderItem;
import cc.akkaha.egg.db.model.OrderItem;
import cc.akkaha.egg.db.model.UserOrder;
import cc.akkaha.egg.db.service.OrderItemService;
import cc.akkaha.egg.model.ApiRes;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Date;

@RestController
@RequestMapping("/egg/order-item")
public class OrderItemController {

    @Autowired
    private OrderItemService itemService;

    @PostMapping("/query")
    public Object query(@RequestBody QueryOrderItem query) {
        ApiRes res = new ApiRes();
        Wrapper wrapper = new EntityWrapper<OrderItem>();
        if (null != query.getUser()) {
            wrapper.eq(OrderItem.USER, query.getUser());
        }
        if (null != query.getCar()) {
            wrapper.eq(OrderItem.CAR, query.getCar());
        }
        Page page = itemService.selectPage(new Page<OrderItem>(query.getCurrent(), query.getSize(),
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
        // ugly but lazy, no easy function
        OrderItem dbItem = item.selectById();
        dbItem.setUser(item.getUser());
        dbItem.setWeight(item.getWeight());
        dbItem.setCar(item.getCar());
        dbItem.setUpdatedAt(new Date());
        boolean ret = dbItem.updateAllColumnById();
        if (ret) {
            res.setData(dbItem);
        } else {
            res.setMsg("更新失败!");
        }
        return res;
    }
}
