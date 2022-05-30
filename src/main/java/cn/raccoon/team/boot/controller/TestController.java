package cn.raccoon.team.boot.controller;

import cn.raccoon.team.boot.exception.response.R;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "测试模块")
@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/helloWorld")
    @ApiOperation("测试接口")
    @ApiOperationSupport(order = 1)
    @ApiImplicitParam(name = "name", value = "名称", dataTypeClass = String.class)
    public R helloWorld(@RequestParam("name") String name) {
        return R.ok("hello, world："+ name);
    }
}
