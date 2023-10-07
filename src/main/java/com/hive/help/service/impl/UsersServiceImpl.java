package com.hive.help.service.impl;

import com.hive.help.bean.Users;
import com.hive.help.service.UsersService;
import com.hive.help.mapper.UsersMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Luolaigang
 * @since 2022-10-20
 */
@Service
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users> implements UsersService {

}
