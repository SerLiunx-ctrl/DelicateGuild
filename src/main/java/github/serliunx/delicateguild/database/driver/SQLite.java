package github.serliunx.delicateguild.database.driver;

import github.serliunx.delicateguild.database.AbstractDriver;

import java.io.File;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLite extends AbstractDriver {

    public SQLite(String host, int port, String database, String username, String password, String playerTable,
                  String guildTable, String relationTable, String logTable) {
        super(host, port, database, username, password, playerTable, guildTable, relationTable, logTable);
    }

    @Override
    public String getDatabaseUrl() {
        File path = new File(instance.getDataFolder() + File.separator + "data");
        if(!path.exists())
            if(!path.mkdir())throw new RuntimeException("cannot create data folder!");
        return "jdbc:sqlite:" + new File(instance.getDataFolder() + File.separator + "data",
                database + ".db");
    }

    @Override
    public void init() {
        try{
            connection = DriverManager.getConnection(getDatabaseUrl());
        }catch (SQLException s){
            s.printStackTrace();
        }
    }
}
