package github.serliunx.delicateguild.menu;

import github.serliunx.delicateguild.allenum.GUISize;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public interface Menu{
    void show(Player player);
    String getTitle();
    void setTitle(String title);
    String getId();
    int getSize();
    void setSize(GUISize size);
    Map<Integer, Button> getButtons();
    void addButton(int index, Button button);

    /**
     * <li>构建一个菜单
     *
     * <p>包括:
     * <p>   添加容器
     * <p>   给容器添加按钮
     * <p>   包括初始化所有占位符, 给玩家打开菜单之前必须执行一个该方法
     */
    void create(Player player);
    Inventory getInventory();
    void setInventory(Inventory inventory);
    InventoryHolder getInventoryHolder();
    void setInventoryHolder(@Nullable InventoryHolder inventoryHolder);
}
