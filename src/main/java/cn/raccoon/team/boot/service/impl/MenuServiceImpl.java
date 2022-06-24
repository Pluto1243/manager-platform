package cn.raccoon.team.boot.service.impl;

import cn.raccoon.team.boot.entity.Menu;
import cn.raccoon.team.boot.entity.Navigation;
import cn.raccoon.team.boot.mapper.MenuMapper;
import cn.raccoon.team.boot.mapper.NavigationMapper;
import cn.raccoon.team.boot.service.IMenuService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 菜单业务
 *
 * @author wangjie
 * @date 11:50 2022年05月30日
 **/
@Service
public class MenuServiceImpl implements IMenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private NavigationMapper navigationMapper;

    @Override
    public List<Menu> listMenu() {
        return menuMapper.selectList(
            new QueryWrapper<Menu>()
                .isNull("deleteAt")
                .orderByAsc("`order`"));
    }

    @Override
    public List<Menu> listMenusInfo() {
        List<Menu> menus = menuMapper.selectList(
            new QueryWrapper<Menu>()
                .isNull("deleteAt")
                .orderByAsc("`order`"));
        menus.stream().forEach(menu -> {
            menu.setNavigationList(navigationMapper.selectList(
                new QueryWrapper<Navigation>()
                .eq("menuId", menu.getId())
                .isNull("deleteAt")
                .orderByAsc("`order`")));
        });
        return menus;
    }

    @Override
    public Menu getMenuById(Integer menuId) {
        Menu menu = menuMapper.selectById(menuId);
        menu.setNavigationList(navigationMapper.selectList(
                new QueryWrapper<Navigation>()
                    .eq("menuId", menuId)
                    .isNull("deleteAt")
                    .orderByAsc("`order`")));
        return menu;
    }

    @Override
    public Boolean insertMenu(Menu menu) {
        Menu limitMenu = menuMapper.selectOne(
            new QueryWrapper<Menu>()
                .isNull("deleteAt")
                .orderByDesc("`order`")
                .last("limit 1"));
        // 当前菜单在最末
        if (limitMenu != null) {
            menu.setOrder(limitMenu.getOrder() + 1);
        } else {
            menu.setOrder(1);
        }

        return menuMapper.insert(menu) > 0;
    }

    @Override
    public Boolean updateMenu(Menu menu) {
        // 查询当前菜单更新前的排序，调整更新后被影响的所有菜单的排序
        Integer order = menuMapper.selectById(menu.getId()).getOrder();

        if (menu.getOrder() < order) {
            // 菜单排序前移
            // 将所有小于更新前排序的菜单 + 1，大于更新前菜单的不动
            List<Menu> menus = menuMapper.selectList(new QueryWrapper<Menu>()
                .between("`order`", menu.getOrder(), order - 1)
                .isNull("deleteAt"));
            menus.stream().forEach(item -> {
                item.setOrder(item.getOrder() + 1);
                menuMapper.updateById(item);
            });
        } else if (menu.getOrder() > order) {
            // 菜单排序后移
            // 将所有大于更新后排序的菜单 - 1，小于更新前菜单的不动
            List<Menu> menus = menuMapper.selectList(new QueryWrapper<Menu>()
                .between("`order`", order + 1, menu.getOrder())
                .isNull("deleteAt"));
            menus.stream().forEach(item -> {
                item.setOrder(item.getOrder() - 1);
                menuMapper.updateById(item);
            });
        }
        return menuMapper.updateById(menu) > 0;
    }

    @Override
    public Boolean deleteMenu(Integer menuId) {
        // 查询当前菜单的位置
        Integer order = menuMapper.selectById(menuId).getOrder();
        // 查询菜单总数
        Integer count = menuMapper.selectCount(new QueryWrapper<Menu>().isNull(("deleteAt"))).intValue();
        // 位于当前位置之后的菜单全部前移
        List<Menu> menus = menuMapper.selectList(new QueryWrapper<Menu>()
            .between("`order`", order + 1, count)
            .isNull("deleteAt"));

        menus.stream().forEach(item -> {
            item.setOrder(item.getOrder() - 1);
            menuMapper.updateById(item);
        });
        return menuMapper.deleteById(menuId) > 0;
    }

    @Override
    public List<Navigation> listNavigationByMenuId(Integer menuId) {
        return navigationMapper.selectList(
            new QueryWrapper<Navigation>()
                .eq("menuId", menuId)
                .orderByAsc("`order`")
                .isNull("deleteAt"));
    }

    @Override
    public Navigation getNavigationById(Integer navigationId) {
        return navigationMapper.selectById(navigationId);
    }

    @Override
    public Boolean insertNavigation(Navigation navigation) {
        // 查询新增导航的菜单下最大的排序
        Navigation limitNavigation = navigationMapper.selectOne(
            new QueryWrapper<Navigation>()
                .isNull("deleteAt")
                .eq("menuId", navigation.getMenuId())
                .orderByDesc("`order`")
                .last("limit 1"));
        // 当前导航在最末
        if (limitNavigation == null) {
            navigation.setOrder(1);
        } else {
            navigation.setOrder(limitNavigation.getOrder() + 1);
        }
        return navigationMapper.insert(navigation) > 0;
    }

    @Override
    public Boolean updateNavigation(Navigation navigation) {
        // 查询当前导航更新前的排序，调整更新后被影响的所有导航的排序
        Integer order = navigationMapper.selectById(navigation.getId()).getOrder();

        if (navigation.getOrder() < order) {
            // 导航排序前移
            // 将所有小于更新前导航的导航 + 1，大于更新前导航的不动
            List<Navigation> navigationList = navigationMapper.selectList(new QueryWrapper<Navigation>()
                .between("`order`", navigation.getOrder(), order - 1)
                .isNull("deleteAt"));
            navigationList.stream().forEach(item -> {
                item.setOrder(item.getOrder() + 1);
                navigationMapper.updateById(navigation);
            });
        } else if (navigation.getOrder() > order) {
            // 导航排序后移
            // 将所有大于更新后排序的导航 - 1，小于更新前导航的不动
            List<Navigation> navigationList = navigationMapper.selectList(new QueryWrapper<Navigation>()
                .between("`order`", order + 1, navigation.getOrder())
                .isNull("deleteAt"));
            navigationList.stream().forEach(item -> {
                item.setOrder(item.getOrder() - 1);
                navigationMapper.updateById(item);
            });
        }
        return navigationMapper.updateById(navigation) > 0;
    }

    @Override
    public Boolean deleteNavigation(Integer navigationId) {
        // 查询当前导航的位置
        Integer order = navigationMapper.selectById(navigationId).getOrder();
        // 查询父菜单
        Navigation navigation = navigationMapper.selectById(navigationId);
        // 查询菜单下的导航总数
        Integer count = navigationMapper.selectCount(new QueryWrapper<Navigation>()
            .isNull(("deleteAt"))
            .eq("menuId", navigation.getMenuId())).intValue();
        // 位于当前位置之后的菜单全部前移
        List<Navigation> navigationList = navigationMapper.selectList(new QueryWrapper<Navigation>()
            .between("`order`", order + 1, count)
            .eq("menuId", navigation.getMenuId())
            .isNull("deleteAt"));

        navigationList.stream().forEach(item -> {
            item.setOrder(item.getOrder() - 1);
            navigationMapper.updateById(item);
        });
        return navigationMapper.deleteById(navigationId) > 0;
    }
}
