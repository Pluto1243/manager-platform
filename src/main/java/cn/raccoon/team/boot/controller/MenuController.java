package cn.raccoon.team.boot.controller;

import cn.raccoon.team.boot.entity.Menu;
import cn.raccoon.team.boot.entity.Navigation;
import cn.raccoon.team.boot.exception.response.R;
import cn.raccoon.team.boot.service.IMenuService;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@Api(tags = "菜单")
@RestController
@RequestMapping("/menu")
@Validated
public class MenuController {

    @Autowired
    private IMenuService menuService;

    /**
     * @description 菜单列表(不包含导航)
     *
     * @author wangjie
     * @date 14:32 2022年05月30日
     * @param
     * @return java.util.List
     */
    @ApiOperation("菜单列表(不包含导航)")
    @ApiOperationSupport(order = 101)
    @GetMapping("/listMenu")
    public R<List<Menu>> listMenu() {
        return R.ok(menuService.listMenu());
    }

    /**
     * @description 菜单详情列表
     *
     * @author wangjie
     * @date 14:30 2022年05月30日
     * @param
     * @return java.util.List<cn.raccoon.team.boot.entity.Menu>
     */
    @ApiOperation("菜单详情列表——首页使用")
    @ApiOperationSupport(order = 102)
    @GetMapping("/listMenusInfo")
    public R<List<Menu>> listMenusInfo() {
        return R.ok(menuService.listMenusInfo());
    }

    /**
     * @description 菜单详情
     *
     * @author wangjie
     * @date 14:35 2022年05月30日
     * @param menuId
     * @return cn.raccoon.team.boot.entity.Menu
     */
    @ApiOperation("菜单详情")
    @ApiOperationSupport(order = 103)
    @GetMapping("/getMenuById")
    @ApiImplicitParam(name = "menuId", value = "菜单ID", dataTypeClass = Integer.class)
    public R<Menu> getMenuById(@NotNull(message = "你怎么能不传ID！")
                               @RequestParam("menuId") Integer menuId) {
        return R.ok(menuService.getMenuById(menuId));
    }

    /**
     * @description 新增菜单
     *
     * @author wangjie
     * @date 14:36 2022年05月30日
     * @param menu
     * @return java.lang.Boolean
     */
    @ApiOperation("新增菜单")
    @PostMapping("/insertMenu")
    @ApiOperationSupport(order = 104)
    public R insertMenu(@RequestBody Menu menu) {
        return R.ok(menuService.insertMenu(menu));
    }

    /**
     * @description 更新菜单
     *
     * @author wangjie
     * @date 14:39 2022年05月30日
     * @param menu
     * @return java.lang.Boolean
     */
    @ApiOperation("更新菜单")
    @PostMapping("/updateMenu")
    @ApiOperationSupport(order = 105)
    public R updateMenu(@RequestBody Menu menu) {
        return R.ok(menuService.updateMenu(menu));
    }

    /**
     * @description 删除菜单
     *
     * @author wangjie
     * @date 14:39 2022年05月30日
     * @param menuId
     * @return java.lang.Boolean
     */
    @ApiOperation("删除菜单")
    @PostMapping("/deleteMenu")
    @ApiOperationSupport(order = 106)
    @ApiImplicitParam(name = "menuId", value = "菜单Id", dataTypeClass = Integer.class)
    public R deleteMenu(@NotNull(message = "你怎么能不传ID！")
                        @RequestParam("menuId") Integer menuId) {
        return R.ok(menuService.deleteMenu(menuId));
    }

    /**
     * @description 菜单下的导航列表
     *
     * @author wangjie
     * @date 14:31 2022年05月30日
     * @param menuId
     * @return java.util.List<cn.raccoon.team.boot.entity.Navigation>
     */
    @ApiOperation("菜单下的导航列表")
    @GetMapping("/listNavigationByMenuId")
    @ApiOperationSupport(order = 107)
    @ApiImplicitParam(name = "menuId", value = "菜单Id", dataTypeClass = Integer.class)
    public R<List<Navigation>> listNavigationByMenuId(@NotNull(message = "你怎么能不传ID！")
                                                      @RequestParam("menuId") Integer menuId) {
        return R.ok(menuService.listNavigationByMenuId(menuId));
    }

    /**
     * @description 获得导航详情
     *
     * @author wangjie
     * @date 14:40 2022年05月30日
     * @param navigationId
     * @return cn.raccoon.team.boot.entity.Navigation
     */
    @ApiOperation("获得导航详情")
    @GetMapping("/getNavigationById")
    @ApiOperationSupport(order = 108)
    @ApiImplicitParam(name = "navigationId", value = "导航Id", dataTypeClass = Integer.class)
    public R<Navigation> getNavigationById(@NotNull(message = "你怎么能不传ID！")
                                           @RequestParam("navigationId") Integer navigationId) {
        return R.ok(menuService.getNavigationById(navigationId));
    }

    /**
     * @description 新增导航
     *
     * @author wangjie
     * @date 14:41 2022年05月30日
     * @param navigation
     * @return java.lang.Boolean
     */
    @ApiOperation("新增导航")
    @PostMapping("/insertNavigation")
    @ApiOperationSupport(order = 109)
    public R insertNavigation(@RequestBody Navigation navigation) {
        return R.ok(menuService.insertNavigation(navigation));
    }

    /**
     * @description 更新导航
     *
     * @author wangjie
     * @date 14:43 2022年05月30日
     * @param navigation
     * @return java.lang.Boolean
     */
    @ApiOperation("更新导航")
    @PostMapping("/updateNavigation")
    @ApiOperationSupport(order = 110)
    public R updateNavigation(@RequestBody Navigation navigation) {
        return R.ok(menuService.updateNavigation(navigation));
    }

    /**
     * @description 删除导航
     *
     * @author wangjie
     * @date 14:43 2022年05月30日
     * @param navigationId
     * @return java.lang.Boolean
     */
    @ApiOperation("删除导航")
    @PostMapping("/deleteNavigation")
    @ApiOperationSupport(order = 111)
    @ApiImplicitParam(name = "navigationId", value = "导航ID", dataTypeClass = Integer.class)
    public R deleteNavigation(@NotNull(message = "你怎么能不传ID！")
                              @RequestParam("navigationId") Integer navigationId) {
        return R.ok(menuService.deleteNavigation(navigationId));
    }
}
