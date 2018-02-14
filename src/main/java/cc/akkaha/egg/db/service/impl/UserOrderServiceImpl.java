package cc.akkaha.egg.db.service.impl;

import cc.akkaha.egg.db.model.UserOrder;
import cc.akkaha.egg.db.client.UserOrderMapper;
import cc.akkaha.egg.db.service.UserOrderService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class UserOrderServiceImpl extends ServiceImpl<UserOrderMapper, UserOrder> implements UserOrderService {
    
}
