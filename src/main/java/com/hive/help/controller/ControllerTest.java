package com.hive.help.controller;

import com.hive.help.bean.vo.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pay/test")
@Api(tags = "测试")
@Slf4j
public class ControllerTest {

    @PostMapping("test")
    @ApiOperation("测试")
    public Response<Integer> findFacebookStatus(){
        Response response = new Response<>();
        log.info("测试:{}","is ok!");
        return response;
    }


}
