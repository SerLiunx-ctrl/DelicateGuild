package github.serliunx.delicateguild.api.event.guild;

import github.serliunx.delicateguild.entity.Guild;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class GuildLevelUpEvent extends GuildEvent{

    public GuildLevelUpEvent(@NotNull Player who, @NotNull Guild guild) {
        super(who, guild);
    }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return null;
    }
}
