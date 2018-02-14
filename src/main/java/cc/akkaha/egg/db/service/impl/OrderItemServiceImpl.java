package cc.akkaha.egg.db.service.impl;

import cc.akkaha.egg.db.model.OrderItem;
import cc.akkaha.egg.db.client.OrderItemMapper;
import cc.akkaha.egg.db.service.OrderItemService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class OrderItemServiceImpl extends ServiceImpl<OrderItemMapper, OrderItem> implements OrderItemService {
    
}
