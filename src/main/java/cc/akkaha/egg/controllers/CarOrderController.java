package cc.akkaha.egg.controllers;

import cc.akkaha.egg.constants.OrderStatus;
import cc.akkaha.egg.db.model.CarOrder;
import cc.akkaha.egg.db.model.OrderItem;
import cc.akkaha.egg.model.ApiRes;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/egg/car-order")
public class CarOrderController {

    @PostMapping("/query")
    public Object query(@RequestBody QueryCarOrder query) {
        ApiRes res = new ApiRes();
        CarOrder order = new CarOrder();
        Wrapper wrapper = new EntityWrapper<CarOrder>();
        if (StringUtils.isNotEmpty(query.getSerial())) {
            wrapper.eq(CarOrder.SERIAL, query.getSerial());
        }
        if (StringUtils.isNotEmpty(query.getDriver())) {
            wrapper.eq(CarOrder.DRIVER, query.getDriver());
        }
        if (StringUtils.isNotEmpty(query.getDriverPhone())) {
            wrapper.eq(CarOrder.DRIVER_PHONE, query.getDriverPhone());
        }
        if (StringUtils.isNotEmpty(query.getStatus())) {
            wrapper.eq(CarOrder.STATUS, query.getStatus());
        }
        Page page = order.selectPage(new Page<CarOrder>(query.getCurrent(), query.getSize(),
                        CarOrder.CREATED_AT, false),
                wrapper);
        res.setData(page);
        return res;
    }

    @PostMapping("/insert")
    public Object insert(@RequestBody CarOrder order) {
        ApiRes res = new ApiRes();
        order.setStatus(OrderStatus.STATUS_NEW);
        boolean ret = order.insert();
        if (ret) {
            res.setData(order);
        } else {
            res.setMsg("创建失败!");
        }
        return res;
    }

    @PostMapping("/update")
    public Object update(@RequestBody CarOrder order) {
        ApiRes res = new ApiRes();
        boolean ret = order.updateById();
        if (ret) {
            res.setData(order);
        } else {
            res.setMsg("更新失败!");
        }
        return res;
    }

    @PostMapping("/delete")
    public Object delete(@RequestBody CarOrder order) {
        ApiRes res = new ApiRes();
        boolean ret = order.deleteById();
        if (ret) {
            res.setData(order);
        } else {
            res.setMsg("操作失败!");
        }
        return res;
    }

    @GetMapping("/detail/{id}")
    public Object detail(@PathVariable("id") String id) {
        ApiRes res = new ApiRes();
        CarOrder carOrder = new CarOrder();
        carOrder.setId(Integer.parseInt(id));
        CarOrder order = carOrder.selectById();
        HashMap<String, Object> data = new HashMap<>();
        data.put("order", order);
        OrderItem orderItem = new OrderItem();
        EntityWrapper<OrderItem> wrapper = new EntityWrapper<>();
        wrapper.eq(OrderItem.CAR, id).orderBy(OrderItem.CREATED_AT, true);
        List items = orderItem.selectList(wrapper);
        data.put("items", items);
        res.setData(data);
        return res;
    }
}

class QueryCarOrder extends CarOrder {

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