package github.serliunx.delicateguild.listener;

import github.serliunx.delicateguild.DelicateGuild;
import github.serliunx.delicateguild.api.event.menu.MenuClickEvent;
import github.serliunx.delicateguild.manager.MemberManager;
import github.serliunx.delicateguild.manager.MenuManager;
import github.serliunx.delicateguild.menu.Menu;
import org.bukkit.Bukkit;
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
        MemberManager memberManager = instance.getMemberManager();
        memberManager.createMember(event.getPlayer());
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event){
        Menu menu = menuManager.getByInventory(event.getInventory());
        if(menu == null) return;
        if(event.getCurrentItem() == null || event.getCurrentItem().getItemMeta() == null) return;

        Player player = (Player) event.getWhoClicked();
        int pos = event.getSlot();

        for(Integer index:menu.getButtons().keySet()){
            if(index == pos){
                MenuClickEvent menuClickEvent = new MenuClickEvent(player,menu,
                        menu.getButtons().get(index), pos);
                Bukkit.getPluginManager().callEvent(menuClickEvent);

                if(!menuClickEvent.isCancelled()){
                    if(menuClickEvent.getMenu().getButtons().get(menuClickEvent.getSlotClicked()) != null){
                        menuClickEvent.getMenu().getButtons().get(menuClickEvent.getSlotClicked())
                                .onClick(menuClickEvent.getPlayer());
                    }
                }
                break;
            }
        }
        event.setCancelled(true);
    }
}
