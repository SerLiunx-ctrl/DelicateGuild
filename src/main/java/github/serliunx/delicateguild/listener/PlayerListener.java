package github.serliunx.delicateguild.listener;

import github.serliunx.delicateguild.DelicateGuild;
import github.serliunx.delicateguild.manager.MemberManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerListener implements Listener {
    private final DelicateGuild instance;

    public PlayerListener(DelicateGuild instance) {
        this.instance = instance;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        MemberManager memberManager = instance.getMemberManager();
        memberManager.createMember(event.getPlayer());
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event){

    }
}
