package cn.raccoon.team.boot.controller;

import cn.raccoon.team.boot.entity.RegisterInfo;
import cn.raccoon.team.boot.entity.UpdateUserInfo;
import cn.raccoon.team.boot.entity.User;
import cn.raccoon.team.boot.exception.response.R;
import cn.raccoon.team.boot.service.IUserService;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
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
    @ApiOperationSupport(order = 101)
    @ApiOperation(value = "用户登录")
    public R userLogin(@RequestBody User user) {
        return R.ok(userService.userLogin(user.getUsername(), user.getPassword()));
    }

    @PostMapping("/changePassword")
    @RequiresPermissions("/user/changePassword")
    @ApiOperationSupport(order = 102)
    @ApiOperation(value = "修改密码")
    public R changePassword(@RequestBody User user, HttpServletResponse response){
        return R.ok(userService.changePassword(user.getPassword(), response));
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

    @PostMapping("/updateUser")
    @ApiOperation("修改用户信息")
    @ApiOperationSupport(order = 106)
    public R updateUser(@RequestBody UpdateUserInfo updateUserInfo) {
        return R.ok(userService.updateUser(updateUserInfo));
    }

    @PostMapping("/logout")
    @ApiOperation("退出登录")
    @ApiOperationSupport(order = 107)
    public R logout() {
        return R.ok(userService.logout());
    }
}
