package github.serliunx.delicateguild.listener;

import github.serliunx.delicateguild.DelicateGuild;
import github.serliunx.delicateguild.manager.MenuManager;
import github.serliunx.delicateguild.menu.Button;
import github.serliunx.delicateguild.menu.Menu;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerListener implements Listener {

    private final MenuManager menuManager;
    private final DelicateGuild instance;

    public PlayerListener(DelicateGuild instance) {
        this.instance = instance;
        menuManager = instance.getMenuManager();
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){

    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event){
        if(!menuManager.contains(event.getInventory())) return;
        Menu menu = DelicateGuild.getInstance().getMenuManager().getByInventory(event.getInventory());
        if(menu == null || event.getCurrentItem() == null || event.getCurrentItem().getItemMeta() == null) return;

        Player player = (Player) event.getWhoClicked();
        int pos = event.getSlot();

        for(Integer index:menu.getButtons().keySet()){
            if(index == pos){
                menu.getButtons().get(index).onClick(player);
                break;
            }
        }

        event.setCancelled(true);
    }
}
