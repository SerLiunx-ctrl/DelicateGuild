package github.serliunx.delicateguild.placeholder;

import github.serliunx.delicateguild.DelicateGuild;
import github.serliunx.delicateguild.entity.Guild;
import github.serliunx.delicateguild.entity.Member;
import github.serliunx.delicateguild.util.StringUtils;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class DelicateGuildHolder {

    public static String setPlaceholders(@NotNull Player player, String rawtext){
        if(DelicateGuild.getInstance().isUsePapi()){
            return PlaceholderAPI.setPlaceholders(player, rawtext);
        }
        return rawtext;
    }

    public static String setPlaceholders(@NotNull Guild guild, String rawtext){

        return  rawtext.replace("{guild_alias}", guild.getAlias())
                .replace("{guild_owner_name}", guild.getOwner() == null ? "null": guild.getOwner().getName())
                .replace("{guild_members}", String.valueOf(guild.getMembers().size()))
                .replace("{guild_max_members}", String.valueOf(guild.getMaxMembers()))
                .replace("{guild_level}", String.valueOf(guild.getLevel()))
                .replace("{guild_points}", String.valueOf(guild.getPoints()))
                .replace("{guild_alias}", guild.getAlias())
                .replace("{guild_id}", guild.getId())
                .replace("{guild_money}", String.valueOf(guild.getMoney()))
                .replace("{guild_create_time}", StringUtils.formatDate(guild.getCreateDate()));
    }

    public static String setPlaceholders(@NotNull Member member, String rawtext){
        return rawtext.replace("{player_contributionPoint}", String.valueOf(member.getContributionPoint()))
                .replace("{player_role}", member.getRole().toString())
                .replace("{player_join_date}", StringUtils.formatDate(member.getJoinedDate()));
    }


}
