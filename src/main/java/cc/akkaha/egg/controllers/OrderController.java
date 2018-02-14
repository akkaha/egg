package cc.akkaha.egg.controllers;

import cc.akkaha.egg.constants.OrderStatus;
import cc.akkaha.egg.db.model.UserOrder;
import cc.akkaha.egg.model.ApiRes;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/user-order")
public class OrderController {

    @PostMapping("/list")
    public Object getList(@RequestBody Map<String, Object> query) {
        UserOrder order = new UserOrder();
        return order.selectAll();
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
