package github.serliunx.delicateguild.database;

import github.serliunx.delicateguild.allenum.Role;
import github.serliunx.delicateguild.entity.Guild;
import github.serliunx.delicateguild.entity.Member;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.util.Map;
import java.util.UUID;

public interface IDriver {
    String getDatabaseUrl();
    String getHost();
    int getPort();
    String getDatabaseName();
    String getUsername();
    String getPassword();
    String getPlayerTable();
    String getGuildTable();
    String getRelationTable();
    String getLogTable();

    void init();
    Connection getConnection();
    boolean createAnMember(Member member);
    boolean createAnGuild(Guild guild);
    void createRelation(Guild guild, Player player);
    boolean createRelation(Guild guild, Player player, Role role);
    Map<UUID, Member> loadAllMembers();
    Map<String, Guild> loadAllGuilds();
    int loadRelation();
    boolean exist(String table, String field, String value);
    void deleteRecord(String table, String field, String value);
    void disConnect();
}
