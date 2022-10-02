package github.serliunx.delicateguild.manager;

import github.serliunx.delicateguild.DelicateGuild;
import github.serliunx.delicateguild.allenum.Role;
import github.serliunx.delicateguild.entity.Guild;
import github.serliunx.delicateguild.entity.guild.SimpleGuild;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class GuildManager {

    private Map<String, Guild> guilds;
    private final DelicateGuild instance;

    public GuildManager(){
        guilds = new HashMap<>();
        instance = DelicateGuild.getInstance();

        loadAllGuilds();
    }

    /**
     * 从数据库中载入所有公会信息.
     */
    private void loadAllGuilds(){
        guilds = instance.getDataManager().loadAllGuilds();
        instance.getLogger().info(guilds.size() + " guild(s) data has been loaded from database.");
    }

    /**
     * 查看指定Id的公会是否已存在.
     * @param id 公会id
     * @return 已存在返回真， 否则返回假
     */
    public boolean contain(String id){
        return getById(id) != null;
    }

    /**
     * 获取指定id的公会
     * @param id 公会id
     * @return 获取到的公会
     */
    @Nullable
    public Guild getById(String id){
        return guilds.get(id);
    }

    /**
     * 创建一个公会
     * @param id 公会id(唯一性)
     * @return 成功创建返回真, 否则返回假.
     */
    public boolean createGuild(String id){
        return createGuild(id, null);
    }

    /**
     * 创建一个公会
     * @param id 公会id(唯一性)
     * @param player 指定公会会长
     * @return 成功创建返回真, 否则返回假.
     */
    public boolean createGuild(String id, @Nullable Player player){
        if(contain(id)) return false;
        Guild guild;
        if(player == null){
            guild = new SimpleGuild(id, 5);
        }else {
            guild = new SimpleGuild(instance.getMemberManager().getMember(player.getUniqueId()),
                    id, 5);
            guild.setCreateDate(new java.util.Date());

            instance.getDataManager().createRelation(guild, player, Role.OWNER);
        }
        guilds.put(id, guild);
        if(instance.getDataManager().createAnGuild(guild)){
            instance.getLogger().info("New guild data for " + guild.getId() + " has been updated to sql.");
        }else{
            instance.getLogger().warning("new guild data for " + guild.getId() + " failed updated to sql.");
            instance.getLogger().warning("you'd better go check your sql configuration.");
        }

        return true;
    }

    /**
     * 获取所有已加载的公会
     * @return 所有公会
     */
    public Map<String, Guild> getAllGuilds() {
        return guilds;
    }
}
