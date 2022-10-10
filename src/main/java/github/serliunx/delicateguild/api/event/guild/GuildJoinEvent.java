package github.serliunx.delicateguild.api.event.guild;

import github.serliunx.delicateguild.entity.Guild;
import github.serliunx.delicateguild.entity.Member;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class GuildJoinEvent extends GuildEvent implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    private boolean cancelled = false;

    private final Member memberAccept;

    public GuildJoinEvent(@NotNull Player who, @NotNull Guild guild, @NotNull Member memberAccept) {
        super(who, guild);
        this.memberAccept = memberAccept;
    }

    /**
     * 获取触发该事件时同意该申请的公会成员
     * @return 操作该申请的成员
     */
    public Member getMemberAccept() {
        return memberAccept;
    }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }
}
