package github.serliunx.delicateguild.manager;

import github.serliunx.delicateguild.DelicateGuild;
import github.serliunx.delicateguild.allenum.Role;
import github.serliunx.delicateguild.entity.Guild;
import github.serliunx.delicateguild.entity.guild.SimpleGuild;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;
import java.util.*;
import java.util.stream.Collectors;

public class GuildManager {

    private Map<String, Guild> guilds;
    private final DelicateGuild instance;
    private List<Guild> guildTopList;
    private final List<List<Guild>> guildPages;

    public GuildManager(){
        guilds = new HashMap<>();
        instance = DelicateGuild.getInstance();
        guildPages = new ArrayList<>();
        loadAllGuilds();
        guildReorder();
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
        guildReorder();
        return true;
    }

    /**
     * 获取所有已加载的公会
     * @return 所有公会
     */
    public Map<String, Guild> getAllGuilds() {
        return guilds;
    }

    /**
     * 根据公会等级重排序
     */
    private void guildReorder(){
        guildTopList = new ArrayList<>(guilds.values());
        guildTopList = guildTopList.stream().sorted(Comparator.comparing(Guild::getLevel).reversed())
                .collect(Collectors.toList());
    }

    /**
     * 公会列表分页
     * @param size 页面尺寸
     */
    private void guildPagination(int size){
        guildPages.clear();
        int page = 1, addition;
        if(guildTopList.size() > size){
            addition = (guildTopList.size() % size != 0) ? 1 : 0;
            page = (guildTopList.size() / size) + addition;
        }

        int startIndex = 0, endIndex = size;
        for(int i = 1; i <= page; i++){
            if(page > 1){
                if(i == page){
                    guildPages.add(guildTopList.subList(startIndex, guildTopList.size()));
                    break;
                }
                guildPages.add(guildTopList.subList(startIndex, endIndex));
                startIndex += size;
                endIndex += size;
                continue;
            }
            guildPages.add(guildTopList);
        }
    }

    /**
     * 根据特定尺寸分割公会列表, 同时获取分页后的指定页码
     * <li> 注意: 页码从 0 开始为第一页
     * @param pageSize 页面尺寸 (如现有公会数量小于或等于页面尺寸会返回整个列表)
     * @param page 需要返回的页码 (如页码不存在则会返回第一页)
     * @return 指定页码的所有公会
     */
    public List<Guild> getReorderedPage(int pageSize, int page){
        if(pageSize >= guildTopList.size()) return guildTopList;
        guildPagination(pageSize);
        if(!checkPageRange(page)) return guildPages.get(0);
        return guildPages.get(page);
    }

    /**
     * 检查指定页码的公会列表是否存在.
     * <li> 注意: 页码从 0 开始为第一页
     * @param page 页码
     * @return 存在返回真, 否则返回假
     */
    public boolean checkPageRange(int page){
        return page < (guildPages.size() + 1);
    }
}
