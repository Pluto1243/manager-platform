package cn.raccoon.team.boot.service;

import cn.raccoon.team.boot.entity.Menu;
import cn.raccoon.team.boot.entity.Navigation;

import java.util.List;

/**
 * 菜单接口
 *
 * @author wangjie
 * @date 11:49 2022年05月30日
 **/
public interface IMenuService {
    
    /**
     * @description 菜单列表(不包含导航)
     *
     * @author wangjie
     * @date 14:32 2022年05月30日 
     * @param 
     * @return java.util.List 
     */
    List<Menu> listMenu();
    
    /**
     * @description 菜单详情列表
     *
     * @author wangjie
     * @date 14:30 2022年05月30日 
     * @param 
     * @return java.util.List<cn.raccoon.team.boot.entity.Menu> 
     */
    List<Menu> listMenusInfo();
    
    /**
     * @description 菜单详情
     *
     * @author wangjie
     * @date 14:35 2022年05月30日 
     * @param menuId
     * @return cn.raccoon.team.boot.entity.Menu 
     */
    Menu getMenuById(Integer menuId);
    
    /**
     * @description 新增菜单
     *
     * @author wangjie
     * @date 14:36 2022年05月30日 
     * @param menu
     * @return java.lang.Boolean 
     */
    Boolean insertMenu(Menu menu);
    
    /**
     * @description 更新菜单
     *
     * @author wangjie
     * @date 14:39 2022年05月30日 
     * @param menu
     * @return java.lang.Boolean 
     */
    Boolean updateMenu(Menu menu);
    
    /**
     * @description 删除菜单
     *
     * @author wangjie
     * @date 14:39 2022年05月30日 
     * @param menuId
     * @return java.lang.Boolean 
     */
    Boolean deleteMenu(Integer menuId);
    
    /**
     * @description 菜单下的导航列表
     *
     * @author wangjie
     * @date 14:31 2022年05月30日 
     * @param menuId
     * @return java.util.List<cn.raccoon.team.boot.entity.Navigation> 
     */
    List<Navigation> listNavigationByMenuId(Integer menuId);
    
    /**
     * @description 获得导航详情
     *
     * @author wangjie
     * @date 14:40 2022年05月30日 
     * @param navigationId
     * @return cn.raccoon.team.boot.entity.Navigation 
     */
    Navigation getNavigationById(Integer navigationId);

    /**
     * @description 新增导航
     *
     * @author wangjie
     * @date 14:41 2022年05月30日
     * @param navigation
     * @return java.lang.Boolean
     */
    Boolean insertNavigation(Navigation navigation);

    /**
     * @description 更新导航
     *
     * @author wangjie
     * @date 14:43 2022年05月30日
     * @param navigation
     * @return java.lang.Boolean
     */
    Boolean updateNavigation(Navigation navigation);

    /**
     * @description 删除导航
     *
     * @author wangjie
     * @date 14:43 2022年05月30日
     * @param navigationId
     * @return java.lang.Boolean
     */
    Boolean deleteNavigation(Integer navigationId);
}
