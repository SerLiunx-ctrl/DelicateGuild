package github.serliunx.delicateguild.database;

import github.serliunx.delicateguild.DelicateGuild;
import github.serliunx.delicateguild.allenum.Role;
import github.serliunx.delicateguild.entity.Guild;
import github.serliunx.delicateguild.entity.Member;
import github.serliunx.delicateguild.entity.guild.SimpleGuild;
import github.serliunx.delicateguild.entity.member.SimpleMember;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.UUID;

public abstract class AbstractDriver implements IDriver{
    protected final String host;
    protected final int port;
    protected final String database;
    protected final String username;
    protected final String password;
    protected final String playerTable, guildTable,relationTable,logTable;
    protected final DelicateGuild instance;
    protected Connection connection;
    protected static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public AbstractDriver(String host, int port, String database, String username, String password, String playerTable,
                          String guildTable, String relationTable, String logTable) {
        this.host = host;
        this.port = port;
        this.database = database;
        this.username = username;
        this.password = password;
        this.playerTable = playerTable;
        this.guildTable = guildTable;
        this.relationTable = relationTable;
        this.logTable = logTable;
        instance = DelicateGuild.getInstance();
        createTable();
    }

    @Override
    public Connection getConnection() {
        if(connection == null){
            init();
        }
        return connection;
    }

    @Override
    public String getHost() {
        return host;
    }

    @Override
    public int getPort() {
        return port;
    }

    @Override
    public String getDatabaseName() {
        return database;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getPlayerTable() {
        return playerTable;
    }

    @Override
    public String getGuildTable() {
        return guildTable;
    }

    @Override
    public String getRelationTable() {
        return relationTable;
    }

    @Override
    public String getLogTable() {
        return logTable;
    }

    @Override
    public void disConnect() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    protected void createTable() {
        //创建公会数据表
        try(PreparedStatement ps = getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS " + guildTable + " (" +
                "  GUILD_ID VARCHAR(20) NOT NULL," +
                "  GUILD_ALIAS VARCHAR(40) NULL," +
                "  GUILD_POINTS INT(16) NULL," +
                "  GUILD_MAX_MEMBERS INT(16) NULL," +
                "  GUILD_MONEY DOUBLE(16, 3) NULL," +
                "  GUILD_LEVEL INT(16) NULL," +
                "  GUILD_EXP_NOW INT(16) NULL," +
                "  CREATE_TIME VARCHAR(20) NOT NULL," +
                "  PRIMARY KEY (GUILD_ID))" )){
            ps.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
        //创建玩家数据表
        try(PreparedStatement ps = getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS " + playerTable + " (" +
                "  PLAYER_UUID VARCHAR(40) NOT NULL," +
                "  PLAYER_NAME VARCHAR(20) NULL," +
                "  PLAYER_CPOINT INT(16) NULL," +
                "  PRIMARY KEY (PLAYER_UUID))" )){
            ps.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
        //创建关系数据表
        try(PreparedStatement ps = getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS " + relationTable + " (" +
                "  PLAYER_UUID VARCHAR(40) NOT NULL," +
                "  GUILD_ID VARCHAR(20) NOT NULL," +
                "  ROLE VARCHAR(20) NOT NULL," +
                "  JOINED_TIME VARCHAR(20) NOT NULL," +
                "  PRIMARY KEY (PLAYER_UUID))" )){
            ps.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
        //创建日志数据表
        try(PreparedStatement ps = getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS " + logTable + " (" +
                "  ID INT(16) NOT NULL," +
                "  GUILD_ID VARCHAR(20) NOT NULL," +
                "  PLAYER_ID VARCHAR(40) NOT NULL," +
                "  TIME DATETIME(6) NOT NULL," +
                "  CONTEXT VARCHAR(100) NOT NULL," +
                "  PRIMARY KEY (ID))")){
            ps.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public boolean createAnMember(Member member) {
        if(exist(playerTable, "PLAYER_UUID", member.getUuid().toString())) return true;
        try (PreparedStatement ps = getConnection().prepareStatement("INSERT INTO " + playerTable + " VALUES (?,?,?)")){
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

    @Override
    public boolean createAnGuild(Guild guild) {
        if(!exist(guildTable, "GUILD_ID", guild.getId())){
            try(PreparedStatement ps = getConnection().prepareStatement("INSERT INTO " + guildTable + " VALUES (?,?,?,?,?,?,?,?)")){
                java.util.Date date = new java.util.Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
                String currentTime = dateFormat.format(date);
                ps.setString(1, guild.getId());
                ps.setString(2, guild.getAlias());
                ps.setInt(3, guild.getPoints());
                ps.setInt(4, guild.getMaxMembers());
                ps.setDouble(5, guild.getMoney());
                ps.setInt(6, guild.getLevel());
                ps.setInt(7, guild.getExpNow());
                ps.setString(8, currentTime);
                ps.executeUpdate();
                return true;
            }catch (SQLException e){
                e.printStackTrace();
                return false;
            }
        }

        return true;
    }

    @Override
    public void createRelation(Guild guild, Player player) {
        if(createRelation(guild, player, Role.MEMBER)){
            instance.getLogger().info("a relation has been updated!");
        }
    }

    @Override
    public boolean createRelation(Guild guild, Player player, Role role) {
        if(!exist(relationTable, "PLAYER_UUID", player.getUniqueId().toString())){
            try(PreparedStatement ps = getConnection().prepareStatement("INSERT INTO " + relationTable + " VALUES (?,?,?,?)")){
                java.util.Date date = new java.util.Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
                String currentTime = dateFormat.format(date);
                ps.setString(1, player.getUniqueId().toString());
                ps.setString(2, guild.getId());
                ps.setString(3, role.toString());
                ps.setString(4, currentTime);
                ps.executeUpdate();
                return true;
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return true;
    }

    @Override
    public Map<UUID, Member> loadAllMembers() {
        Map<UUID, Member> members = new HashMap<>();
        try(PreparedStatement ps = getConnection().prepareStatement("SELECT * FROM " + playerTable)){
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                UUID uuid = UUID.fromString(rs.getString(1));
                String name = rs.getString(2);
                int cpoints = rs.getInt(3);
                Member member = new SimpleMember(uuid, name, cpoints);
                members.put(uuid, member);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }

        return members;
    }

    @Override
    public Map<String, Guild> loadAllGuilds() {
        Map<String, Guild> guilds = new HashMap<>();
        try(PreparedStatement ps = getConnection().prepareStatement("SELECT * FROM " + guildTable)){
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Guild guild = new SimpleGuild(
                        new HashSet<>(),
                        null,
                        rs.getString(1),
                        rs.getString(2),
                        rs.getInt(3),
                        rs.getDouble(5),
                        rs.getInt(4),
                        rs.getInt(6),
                        rs.getInt(7)
                );
                SimpleDateFormat dateFormat = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
                guild.setCreateDate(dateFormat.parse(rs.getString(8)));
                guilds.put(guild.getId(), guild);
            }
        }catch (SQLException | ParseException e){
            e.printStackTrace();
        }

        return guilds;
    }

    @Override
    public int loadRelation() {
        int relationCounts = 0;
        try(PreparedStatement ps = getConnection().prepareStatement("SELECT * FROM " + relationTable)){
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                if(rs.getString(1) == null || rs.getString(2) == null)
                    continue;
                Member member = instance.getMemberManager().getMember(UUID.fromString(rs.getString(1)));
                if(member == null) continue;
                Guild guild = instance.getGuildManager().getById(rs.getString(2));
                if(guild == null) {
                    deleteRecord(relationTable, "PLAYER_UUID", member.getUuid().toString());
                    continue;
                }

                guild.addMember(member);
                SimpleDateFormat dateFormat = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
                member.setJoinedDate(dateFormat.parse(rs.getString(4)));
                member.setRole(Role.valueOf(rs.getString(3)));
                if(member.getRole().equals(Role.OWNER)) guild.setOwner(member);

                relationCounts++;
            }

        }catch (SQLException | ParseException e){
            e.printStackTrace();
        }
        return relationCounts;
    }

    @Override
    public boolean exist(String table, String field, String value) {
        try(PreparedStatement ps = getConnection().prepareStatement("SELECT * FROM " + table + " WHERE "+ field +"=?")){
            ps.setString(1, value);
            ResultSet results = ps.executeQuery();
            return results.next();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void deleteRecord(String table, String field, String value) {
        try(PreparedStatement ps = getConnection().prepareStatement("DELETE FROM " + table + " WHERE " + field + "=?")){
            ps.setString(1, value);
            ps.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
