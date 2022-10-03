package github.serliunx.delicateguild.api.event.menu;

import github.serliunx.delicateguild.DelicateGuild;
import github.serliunx.delicateguild.menu.Button;
import github.serliunx.delicateguild.menu.Menu;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class MenuClickEvent extends MenuEvent implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    private boolean cancelled = false;

    private final Button button;

    private final int slotClicked;

    public MenuClickEvent(@NotNull Player who, @NotNull Menu menu, @NotNull Button button,
                          int slotClicked) {
        super(who, menu);
        this.button = button;
        this.slotClicked = slotClicked;

        DelicateGuild.getInstance().getLogger().info("event get called");
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    /**
     * 获取触发该事件时所对应的按钮
     * @return 按钮
     */
    public Button getButton() {
        return button;
    }

    /**
     * 获取点击时的位置
     * @return 位置 (0~53)
     */
    public int getSlotClicked() {
        return slotClicked;
    }
}
