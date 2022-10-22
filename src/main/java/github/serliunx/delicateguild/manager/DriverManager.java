package github.serliunx.delicateguild.manager;

import github.serliunx.delicateguild.DelicateGuild;
import github.serliunx.delicateguild.allenum.DriverType;
import github.serliunx.delicateguild.database.IDriver;
import github.serliunx.delicateguild.database.driver.MySQL;
import github.serliunx.delicateguild.database.driver.SQLite;
import org.bukkit.configuration.file.FileConfiguration;

public class DriverManager {
    protected final DriverType driverType;
    private final int port;
    private final String host, database, username, password, playerTable, guildTable, relationTable, logTable;
    protected final DelicateGuild instance;
    private IDriver driver;

    public DriverManager() {
        this.instance = DelicateGuild.getInstance();
        FileConfiguration dateBaseConfiguration = instance.getConfigManager().getByConfigName("config").getConfiguration();
        this.driverType = DriverType.valueOf(dateBaseConfiguration.getString("database.driver", "SQLITE"));
        this.host = dateBaseConfiguration.getString("database.host", "localhost");
        this.port = dateBaseConfiguration.getInt("database.port", 3306);
        this.database = dateBaseConfiguration.getString("database.database", "database");
        this.username = dateBaseConfiguration.getString("database.username", "root");
        this.password = dateBaseConfiguration.getString("database.password", "password");
        this.playerTable = dateBaseConfiguration.getString("database.playerTable", "delicateguild_player");
        this.guildTable = dateBaseConfiguration.getString("database.guildTable", "delicateguild_guild");
        this.relationTable = dateBaseConfiguration.getString("database.relationTable", "delicateguild_relation");
        this.logTable = dateBaseConfiguration.getString("database.logTable", "delicateguild_log");
    }

    public IDriver getDriver(){
        if(driver == null){
            newDrive();
        }
        return driver;
    }

    private void newDrive(){
        switch (driverType){
            case MYSQL:
                driver = new MySQL(host, port, database, username, password, playerTable, guildTable, relationTable,
                        logTable);
            case SQLITE:
                driver = new SQLite(host, port, database, username, password, playerTable, guildTable, relationTable,
                        logTable);
        }
    }
}
