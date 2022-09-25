package github.serliunx.delicateguild.menu;

import github.serliunx.delicateguild.allenum.GUISize;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public interface Menu{
    void Show(Player player);
    String getTitle();
    void setTitle(String title);
    String getId();
    int getSize();
    void setSize(GUISize size);
    Map<Integer, Button> getButtons();
    void addButton(int index, Button button);
    void create();
    Inventory getInventory();
    void setInventory(Inventory inventory);
    InventoryHolder getInventoryHolder();
    void setInventoryHolder(@Nullable InventoryHolder inventoryHolder);
}
