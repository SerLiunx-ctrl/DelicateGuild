package github.serliunx.delicateguild.manager;

import github.serliunx.delicateguild.DelicateGuild;
import github.serliunx.delicateguild.entity.Member;
import github.serliunx.delicateguild.entity.member.SimpleMember;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class MemberManager {

    private Map<UUID, Member> members;
    private final DelicateGuild instance;

    public MemberManager() {
        members = new HashMap<>();
        instance = DelicateGuild.getInstance();
        loadAllMembers();
    }

    /**
     * 载入所有用户
     */
    private void loadAllMembers(){
        members = instance.getDataManager().loadAllMembers();
        instance.getLogger().info(members.size() + " member(s) data has been loaded from database.");
    }

    /**
     * 新建用户
     */
    public void createMember(Player player){
        if(contain(player)) return;
        Member member = new SimpleMember(player.getUniqueId(), player.getName(), 0);
        members.put(player.getUniqueId(), member);

        //将玩家添加至数据库中
        if(instance.getDataManager().createAnMember(member)){
            instance.getLogger().info("New player data for " + player.getName() + " has been updated to sql.");
        }else{
            instance.getLogger().warning("new player data for " + player.getName() + " failed updated to sql.");
            instance.getLogger().warning("you'd better go check your sql configuration.");
        }
    }

    /**
     * 检查指定玩家{@link Player}是否存在于已加载的所有用户{@link Member}中.
     * @param player 玩家
     * @return 如已存在则返回真, 否则返回假
     */
    public boolean contain(Player player){
        return contain(player.getUniqueId());
    }

    /**
     * 查看指定UUID是否存在于已加载的所有用户中.
     * @param uuid uuid
     * @return 如已存在则返回真, 否则返回假
     */
    public boolean contain(UUID uuid){
        return members.containsKey(uuid);
    }

    /**
     * 获取一个用户
     *
     * @param uuid 用户的UUID
     * @return 用户 <p>可能为空
     */
    @Nullable
    public Member getMember(UUID uuid){
        return members.get(uuid);
    }

    public Map<UUID, Member> getMembers() {
        return members;
    }
}
