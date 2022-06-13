package cn.raccoon.team.boot.controller;

import cn.raccoon.team.boot.entity.RegisterInfo;
import cn.raccoon.team.boot.exception.response.R;
import cn.raccoon.team.boot.service.IUserService;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * @Author Qian
 * @Date 2021/12/20 16:33
 */
@RestController
@Api(tags = "用户")
@RequestMapping("/user")
@Validated
public class UserController {

    @Autowired
    private IUserService userService;

    @PostMapping("/userLogin")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "account", value = "账号", dataTypeClass = String.class),
            @ApiImplicitParam(name = "passwd", value = "密码", dataTypeClass = String.class)
    })
    @ApiOperationSupport(order = 101)
    @ApiOperation(value = "用户登录")
    public R userLogin(@RequestParam("account") String account,
                       @RequestParam("passwd") String passwd) {
        return R.ok(userService.userLogin(account, passwd));
    }

    @PostMapping("/changePassword")
    @ApiImplicitParam(name = "password", value = "密码", dataTypeClass = String.class)
    @RequiresPermissions("/user/changePassword")
    @ApiOperationSupport(order = 102)
    @ApiOperation(value = "修改密码")
    public R changePassword(@RequestParam("password") String password, HttpServletResponse response){
        return R.ok(userService.changePassword(password, response));
    }

    @PostMapping("/register")
    @ApiOperation(value = "注册")
    @ApiOperationSupport(order = 103)
    public R register(@Valid @RequestBody RegisterInfo registerInfo) {
        return R.ok(userService.register(registerInfo));
    }

    @GetMapping("/checkUsername")
    @ApiOperation("校验用户名是否重复")
    @ApiOperationSupport(order = 104)
    @ApiImplicitParam(name = "username", value = "用户名", dataTypeClass = String.class)
    public R checkUsername(@NotEmpty(message = "请输入用户名！") @RequestParam("username") String username) {
        return R.ok(userService.checkUserName(username));
    }

    @PostMapping("/logout")
    @ApiOperation("退出登录")
    @ApiOperationSupport(order = 105)
    public R logout() {
        return R.ok(userService.logout());
    }
}
