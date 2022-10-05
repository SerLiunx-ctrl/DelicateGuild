package github.serliunx.delicateguild.listener;

import github.serliunx.delicateguild.DelicateGuild;
import org.bukkit.event.Listener;

public class GuildListener implements Listener {
    private final DelicateGuild instance;

    public GuildListener(DelicateGuild instance) {
        this.instance = instance;
    }
}
