package cc.akkaha.egg.db.service.impl;

import cc.akkaha.egg.db.model.Order;
import cc.akkaha.egg.db.client.OrderMapper;
import cc.akkaha.egg.db.service.OrderService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {
    
}
