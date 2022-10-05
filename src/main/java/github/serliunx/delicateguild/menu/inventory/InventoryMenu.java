package github.serliunx.delicateguild.menu.inventory;

import github.serliunx.delicateguild.allenum.GUISize;
import github.serliunx.delicateguild.api.event.menu.MenuOpenEvent;
import github.serliunx.delicateguild.menu.AbstractMenu;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class InventoryMenu extends AbstractMenu {

    public InventoryMenu(String title, String id, GUISize size) {
        super(title, id, size);
    }

    @Override
    public void show(Player player) {
        show(player, false);
    }

    @Override
    public void show(Player player, boolean keepPage){
        MenuOpenEvent menuOpenEvent = new MenuOpenEvent(player, this, keepPage);
        Bukkit.getPluginManager().callEvent(menuOpenEvent);
        if(menuOpenEvent.isCancelled()) return;
        create(menuOpenEvent.getPlayer());
        player.openInventory(menuOpenEvent.getMenu().getInventory());
    }
}
