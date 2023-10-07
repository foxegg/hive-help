package com.hive.help.service.impl;

import com.hive.help.bean.MyOrder;
import com.hive.help.service.MyOrderService;
import com.hive.help.mapper.MyOrderMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 我的订单 服务实现类
 * </p>
 *
 * @author Luolaigang
 * @since 2022-10-20
 */
@Service
public class MyOrderServiceImpl extends ServiceImpl<MyOrderMapper, MyOrder> implements MyOrderService {

}
