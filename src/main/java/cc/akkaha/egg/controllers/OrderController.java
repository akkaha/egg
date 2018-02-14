package cc.akkaha.egg.controllers;

import cc.akkaha.egg.db.model.Order;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/order")
public class OrderController {

    @PostMapping("/list")
    public Object getList(@RequestBody Map<String, Object> query) {
        Order order = new Order();
        return order.selectAll();
    }
}
