package github.serliunx.delicateguild.placeholder;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;

public final class PlaceholderAdapter {

    /**
     *
     * 占位符填充
     *
     * @param player 玩家
     * @param text 待填充的文本
     * @return 填充过的文本
     */
    public static String setPlaceholders(Player player, String text){
        return PlaceholderAPI.setPlaceholders(player, text);
    }
}
