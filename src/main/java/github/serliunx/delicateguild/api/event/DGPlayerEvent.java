package github.serliunx.delicateguild.api.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

public abstract class DGPlayerEvent extends Event {

    protected final Player player;

    public DGPlayerEvent(@NotNull final Player who){
        player = who;
    }

    /**
     * 获取触发该事件时所涉及的玩家
     * @return 玩家
     */
    @NotNull
    public final Player getPlayer() {
        return player;
    }
}
