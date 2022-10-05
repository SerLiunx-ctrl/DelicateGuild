package github.serliunx.delicateguild.api.event.menu;

import github.serliunx.delicateguild.menu.Menu;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class MenuOpenEvent extends MenuEvent implements Cancellable {

    private static final HandlerList handlers = new HandlerList();
    private boolean cancelled = false;
    private boolean keepPage = false;

    public MenuOpenEvent(@NotNull Player who, @NotNull Menu menu, boolean keepPage) {
        super(who, menu);
        this.keepPage = keepPage;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancelled =cancel;
    }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public boolean isKeepPage() {
        return keepPage;
    }
}
