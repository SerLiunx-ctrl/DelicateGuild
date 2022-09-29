package github.serliunx.delicateguild.manager;

import github.serliunx.delicateguild.DelicateGuild;
import github.serliunx.delicateguild.allenum.DriverType;
import github.serliunx.delicateguild.database.MySQL;
import github.serliunx.delicateguild.database.SQL;
import github.serliunx.delicateguild.database.SQLite;
import github.serliunx.delicateguild.entity.Member;
import github.serliunx.delicateguild.entity.member.SimpleMember;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public final class DataManager {
    private final DriverType driverType;
    private final String host;
    private final int port;
    private final String database;
    private final String username;
    private final String password;
    private final String playerTable, guildTable,relationTable,logTable;
    private final DelicateGuild instance;
    private SQL sql;

    public DataManager() {
        this.instance = DelicateGuild.getInstance();

        this.driverType = DriverType.valueOf(instance.getConfigManager().getByConfigName("config").getConfiguration()
                .getString("database.driver", "SQLITE"));
        this.host = instance.getConfigManager().getByConfigName("config").getConfiguration()
                .getString("database.host", "localhost");
        this.port = instance.getConfigManager().getByConfigName("config").getConfiguration()
                .getInt("database.port", 3306);
        this.database = instance.getConfigManager().getByConfigName("config").getConfiguration()
                .getString("database.database", "database");
        this.username = instance.getConfigManager().getByConfigName("config").getConfiguration()
                .getString("database.username", "root");
        this.password = instance.getConfigManager().getByConfigName("config").getConfiguration()
                .getString("database.password", "password");
        this.playerTable = instance.getConfigManager().getByConfigName("config").getConfiguration()
                .getString("database.playerTable", "delicateguild_player");
        this.guildTable = instance.getConfigManager().getByConfigName("config").getConfiguration()
                .getString("database.guildTable", "delicateguild_guild");
        this.relationTable = instance.getConfigManager().getByConfigName("config").getConfiguration()
                .getString("database.relationTable", "delicateguild_relation");
        this.logTable = instance.getConfigManager().getByConfigName("config").getConfiguration()
                .getString("database.logTable", "delicateguild_log");
        init();
    }

    private @NotNull String getDatabaseURL(){
        switch (driverType){
            case MYSQL:
                return "jdbc:" + driverType.name().toLowerCase() + "://" + host + ":" + port
                        + "/" + database + "?useSSL=" + false + "&autoReconnect=true";
            case SQLITE:
                File path = new File(instance.getDataFolder() + File.separator + "data");
                if(!path.exists())
                    if(!path.mkdir())throw new RuntimeException("cannot create data folder!");
                return "jdbc:sqlite:" + new File(instance.getDataFolder() + File.separator + "data", database + ".db");
            default:
                throw new RuntimeException("unsupported sql driver!");
        }
    }

    private void init(){

        if(driverType == DriverType.MYSQL){
            sql = new MySQL(getDatabaseURL(), username, password);
        }else {
            sql = new SQLite(getDatabaseURL());
        }

        //建立公会数据表
        if(!sql.execute("CREATE TABLE IF NOT EXISTS " + guildTable + " (" +
                "  GUILD_ID VARCHAR(20) NOT NULL," +
                "  GUILD_ALIAS VARCHAR(40) NULL," +
                "  GUILD_POINTS INT(16) NULL," +
                "  GUILD_MAX_MEMBERS INT(16) NULL," +
                "  GUILD_MONEY DOUBLE(16, 3) NULL," +
                "  PRIMARY KEY (GUILD_ID))" )){
            instance.getLogger().info("guild data table creation failed.");
        }

        //建立玩家数据表
        if(!sql.execute("CREATE TABLE IF NOT EXISTS " + playerTable + " (" +
                "  PLAYER_UUID VARCHAR(40) NOT NULL," +
                "  PLAYER_NAME VARCHAR(20) NULL," +
                "  PLAYER_CPOINT INT(16) NULL," +
                "  PRIMARY KEY (PLAYER_UUID))" )){
            instance.getLogger().info("player data table creation failed.");
        }

        //建立关系数据表
        if(!sql.execute("CREATE TABLE IF NOT EXISTS " + relationTable + " (" +
                "  PLAYER_UUID VARCHAR(40) NOT NULL," +
                "  GUILD_UUID VARCHAR(20) NOT NULL," +
                "  ROLE VARCHAR(20) NOT NULL," +
                "  JOINED_TIME DATETIME(6) NOT NULL," +
                "  PRIMARY KEY (PLAYER_UUID))" )){
            instance.getLogger().info("guild data table creation failed.");
        }

        //建立日志数据表
        if(!sql.execute("CREATE TABLE IF NOT EXISTS " + logTable + " (" +
                "  ID INT(16) NOT NULL," +
                "  GUILD_ID VARCHAR(20) NOT NULL," +
                "  PLAYER_ID VARCHAR(40) NOT NULL," +
                "  TIME DATETIME(6) NOT NULL," +
                "  CONTEXT VARCHAR(40) NOT NULL," +
                "  PRIMARY KEY (ID))" )){
            instance.getLogger().info("player data table creation failed.");
        }
    }

    /**
     * 获取数据库
     * @return 当前数据库
     */
    public SQL getSql() {
        return sql;
    }

    public boolean createAnMember(Member member){
        if(!exist(playerTable, "PLAYER_UUID", member.getUuid().toString())){
            try (PreparedStatement ps = sql.getConnection().prepareStatement("INSERT INTO " + playerTable + " VALUES (?,?,?)")){
                ps.setString(1, member.getUuid().toString());
                ps.setString(2, member.getName());
                ps.setInt(3, 0);
                ps.executeUpdate();
                return true;
            }catch (SQLException e){
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    public Set<Member> loadAllMembers(){
        Set<Member> members = new HashSet<>();
        try(PreparedStatement ps = sql.getConnection().prepareStatement("SELECT * FROM " + playerTable)){
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                UUID uuid = UUID.fromString(rs.getString(1));
                String name = rs.getString(2);
                int cpoints = rs.getInt(3);
                Member member = new SimpleMember(uuid, name, cpoints);
                members.add(member);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }

        return members;
    }

    private boolean exist(String table, String field, String value){
        try(PreparedStatement ps = sql.getConnection().prepareStatement("SELECT * FROM " + table + " WHERE "+ field +"=?")){
            ps.setString(1, value);
            ResultSet results = ps.executeQuery();
            return results.next();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }
}
