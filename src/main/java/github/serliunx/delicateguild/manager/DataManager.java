package github.serliunx.delicateguild.manager;

import github.serliunx.delicateguild.DelicateGuild;
import github.serliunx.delicateguild.allenum.Role;
import github.serliunx.delicateguild.database.IDriver;
import github.serliunx.delicateguild.entity.Guild;
import github.serliunx.delicateguild.entity.Member;

import org.bukkit.entity.Player;
import java.util.*;

public final class DataManager {
    private final DelicateGuild instance;
    private final IDriver driver;

    public DataManager() {
        this.instance = DelicateGuild.getInstance();
        driver = instance.getDriverManager().getDriver();
    }

    public boolean createAnMember(Member member){
        return driver.createAnMember(member);
    }

    public boolean createAnGuild(Guild guild){
        return driver.createAnGuild(guild);
    }

    public void createRelation(Guild guild, Player player){
       if(createRelation(guild, player, Role.MEMBER)){
          instance.getLogger().info("a relation has been updated!");
       }
    }

    public boolean createRelation(Guild guild, Player player, Role role){
        return driver.createRelation(guild, player, role);
    }

    public Map<UUID, Member> loadAllMembers(){
        return driver.loadAllMembers();
    }

    public Map<String, Guild> loadAllGuilds(){
        return driver.loadAllGuilds();
    }

    public int loadRelation(){
        return driver.loadRelation();
    }

    public void updateMember(Member member){

    }

    public void updateGuild(Guild guild){

    }

    public void updateRelation(Guild guild, Player player){

    }

    public void playerLeaveGuild(Player player){
        driver.deleteRecord(driver.getRelationTable(), "PLAYER_UUID", player.getUniqueId().toString());
    }
}
