package com.hive.help.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hive.help.bean.TestBean;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface TestMapper extends BaseMapper<TestBean> {
    int getCount();
}
