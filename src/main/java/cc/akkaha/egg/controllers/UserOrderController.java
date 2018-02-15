package cc.akkaha.egg.controllers;

import cc.akkaha.egg.constants.OrderStatus;
import cc.akkaha.egg.db.model.UserOrder;
import cc.akkaha.egg.model.ApiRes;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/egg/user-order")
public class UserOrderController {

    @PostMapping("/query")
    public Object query(@RequestBody QueryUserOrder query) {
        ApiRes res = new ApiRes();
        UserOrder order = new UserOrder();
        Wrapper wrapper = new EntityWrapper<UserOrder>();
        if (StringUtils.isNotEmpty(query.getSeller())) {
            wrapper.eq(UserOrder.SELLER, query.getSeller());
        }
        if (StringUtils.isNotEmpty(query.getPhone())) {
            wrapper.eq(UserOrder.PHONE, query.getPhone());
        }
        if (StringUtils.isNotEmpty(query.getStatus())) {
            wrapper.eq(UserOrder.STATUS, query.getStatus());
        }
        Page page = order.selectPage(new Page<UserOrder>(query.getCurrent(), query.getSize(),
                        UserOrder.CREATED_AT, false),
                wrapper);
        res.setData(page);
        return res;
    }

    @PostMapping("/insert")
    public Object insert(@RequestBody UserOrder order) {
        ApiRes res = new ApiRes();
        order.setStatus(OrderStatus.STATUS_NEW);
        boolean ret = order.insert();
        if (ret) {
            res.setData(order);
        } else {
            res.setMsg("创建订单失败!");
        }
        return res;
    }

    @PostMapping("/update")
    public Object update(@RequestBody UserOrder order) {
        ApiRes res = new ApiRes();
        boolean ret = order.updateById();
        if (ret) {
            res.setData(order);
        } else {
            res.setMsg("创建订单失败!");
        }
        return res;
    }
}

class QueryUserOrder extends UserOrder {

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