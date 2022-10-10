package github.serliunx.delicateguild.api.event.guild;

import github.serliunx.delicateguild.api.event.DGPlayerEvent;
import github.serliunx.delicateguild.entity.Guild;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public abstract class GuildEvent extends DGPlayerEvent {

    private Guild guild;

    public GuildEvent(@NotNull Player who, @NotNull Guild guild) {
        super(who);
        this.guild = guild;
    }

    /**
     * 获取触发该事件时所涉及的公会
     * @return 公会
     */
    public Guild getGuild() {
        return guild;
    }

    /**
     * 设置触发该事件时所涉及的公会
     * @param guild 公会
     */
    public void setGuild(Guild guild) {
        this.guild = guild;
    }
}
