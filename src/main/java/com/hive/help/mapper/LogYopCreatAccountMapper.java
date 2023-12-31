package com.hive.help.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hive.help.bean.payyop.LogYopCreatAccount;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 易宝开户返回记录表 Mapper 接口
 * </p>
 *
 * @author Luolaigang
 * @since 2022-10-18
 */
@Repository
@Mapper
public interface LogYopCreatAccountMapper extends BaseMapper<LogYopCreatAccount> {
    int getCount();
}
