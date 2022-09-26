package github.serliunx.delicateguild.menu.inventory;

import github.serliunx.delicateguild.allenum.GUISize;
import github.serliunx.delicateguild.menu.AbstractMenu;
import org.bukkit.entity.Player;

public class InventoryMenu extends AbstractMenu {

    public InventoryMenu(String title, String id, GUISize size) {
        super(title, id, size);
    }

    @Override
    public void show(Player player) {
        create(player);
        player.openInventory(getInventory());
    }
}
