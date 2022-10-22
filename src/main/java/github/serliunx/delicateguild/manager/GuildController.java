package github.serliunx.delicateguild.manager;

import github.serliunx.delicateguild.entity.Guild;
import github.serliunx.delicateguild.entity.Member;
import github.serliunx.delicateguild.util.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class GuildController {

    private GuildController(){}

    /**
     * 判断一个成员是否属于公会的管理人员
     * @param member 指定成员
     * @param guild 指定公会
     * @return 属于管理人员返回真, 否则返回假
     */
    public static boolean isAdministrator(Member member, Guild guild){
        return guild.isAnAdministrator(member);
    }

    /**
     * 判断一个成员是否属于公会的管理人员
     * @param member 指定成员
     * @param guild 指定公会
     * @param message 是否向该成员发送文本提示
     * @return 属于管理人员返回真, 否则返回假
     */
    public static boolean isAdministrator(Member member, Guild guild, boolean message){
        boolean is = isAdministrator(member, guild);
        if(!is && message){
            Player player = Bukkit.getPlayer(member.getUuid());
            if(player == null) return false;
            player.sendMessage(StringUtils.Color("&cyou do not have permission to do that!"));
            return false;
        }
        return is;
    }

    /**
     * 判断一个用户是否属于指定公会的成员
     * @param member 用户
     * @param guild 指定公会
     * @return 用户属于该工会则返回真, 否则返回假
     */
    public static boolean isInTheGuild(Member member, Guild guild){
        if(member == null || member.getGuildBelong() == null) return false;
        return guild.getMembers().contains(member);
    }

    /**
     * 判断一个用户是否属于指定公会的成员
     * @param member 用户
     * @param guild 指定公会
     * @param message 是否向该用户发送文本提示
     * @return 用户属于该工会则返回真, 否则返回假
     */
    public static boolean isInTheGuild(Member member, Guild guild, boolean message){
        boolean is = isInTheGuild(member, guild);
        if(!is && message){
            Player player = Bukkit.getPlayer(member.getUuid());
            if(player == null) return false;
            player.sendMessage(StringUtils.Color("&cYou do not join a guild!"));
            return false;
        }
        return is;
    }

    /**
     * 获取指定公会的申请列表, 返回的是玩家名称:
     * {@link Player#getName()}
     * @param member 操作成员
     * @param guild 指定公会
     * @return 申请列表(玩家名称)
     */
    public static List<String> getApplicationList(@Nullable Member member,@Nullable Guild guild){
        if(guild == null) return Collections.emptyList();
        if(!isAdministrator(member, guild)) return Collections.emptyList();
        return getApplicationList(guild);
    }

    /**
     * 获取指定公会的申请列表, 返回的是玩家名称:
     * {@link Player#getName()}
     * @param guild 指定公会
     * @return 申请列表(玩家名称)
     */
    public static List<String> getApplicationList(@NotNull Guild guild){
        List<String> names = new ArrayList<>();
        for(Player p:guild.getPlayerApplications()){
            names.add(p.getName());
        }
        return names;
    }

    public static void changeGuildName(@NotNull Guild targetGuild, @NotNull Player operator, @NotNull String newName){
        targetGuild.setAlias(newName);
        for(Member m:targetGuild.getMembers()){
            Player p = Bukkit.getPlayer(m.getUuid());
            if(p != null && p.isOnline()){
                p.sendMessage(StringUtils.Color("&ename changed to:&r " + newName + " &r&eby &b" +
                        operator.getName()));
            }
        }
    }

}
