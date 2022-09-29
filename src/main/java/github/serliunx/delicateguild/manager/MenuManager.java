package github.serliunx.delicateguild.manager;

import github.serliunx.delicateguild.DelicateGuild;
import github.serliunx.delicateguild.allenum.GUISize;
import github.serliunx.delicateguild.menu.Button;
import github.serliunx.delicateguild.menu.ButtonBuilder;
import github.serliunx.delicateguild.menu.Menu;
import github.serliunx.delicateguild.menu.inventory.InventoryButton;
import github.serliunx.delicateguild.menu.inventory.InventoryMenu;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.Inventory;

import java.util.*;

public class MenuManager {

    private final Map<String, Menu> menus;
    private final List<String> menuNames = Arrays.asList("main_menu");

    public MenuManager() {
        menus = new HashMap<>();
        loadMenus();
    }

    /**
     * 针对菜单的初始化操作
     */
    private void loadMenus(){
        for(String name:menuNames){
            FileConfiguration menuFile = DelicateGuild.getInstance().getConfigManager().getByConfigName(name)
                    .getConfiguration();
            Menu menu = new InventoryMenu(
                    menuFile.getString("title"),
                    menuFile.getString("id"),
                    GUISize.valueOf(menuFile.getString("size"))
            );
            ButtonBuilder buttonBuilder = new ButtonBuilder(menuFile);
            Map<Integer, Button> buttonMap = buttonBuilder.build();
            for(Integer pos: buttonMap.keySet()){
                menu.addButton(pos, buttonMap.get(pos));
            }

            menus.put(menu.getId(), menu);
        }

    }

    public Menu getById(String id){
        return menus.get(id);
    }

    public Menu getByInventory(Inventory inventory){
        for(Menu menu:menus.values()){
            if(menu.getInventory() == null) break;
            if(menu.getInventory().equals(inventory)) return menu;
        }
        return null;
    }

    public Map<String, Menu> getMenus() {
        return menus;
    }

    /**
     * 检查指定容器是否属于本插件中内置的菜单
     * @param inventory 容器
     * @return 如果是则返回真, 反之则返回假
     */
    public boolean contains(Inventory inventory){
        for(Menu menu: menus.values()){
            if(menu.getInventory().equals(inventory)) return true;
        }
        return false;
    }
}
