package github.serliunx.delicateguild.api.event.menu;

import github.serliunx.delicateguild.api.event.DGPlayerEvent;
import github.serliunx.delicateguild.menu.Menu;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public abstract class MenuEvent extends DGPlayerEvent {

    protected Menu menu;

    public MenuEvent(@NotNull Player who, @NotNull Menu menu) {
        super(who);
        this.menu = menu;
    }

    /**
     * 获取触发该事件时所涉及的菜单
     * @return 菜单
     */
    public Menu getMenu() {
        return menu;
    }

    /**
     * 设置触发该事件时所涉及的菜单
     * @param menu 菜单
     */
    public void setMenu(Menu menu) {
        this.menu = menu;
    }
}
