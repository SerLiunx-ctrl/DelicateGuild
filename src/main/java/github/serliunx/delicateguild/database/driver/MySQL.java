package github.serliunx.delicateguild.database.driver;

import github.serliunx.delicateguild.database.AbstractDriver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQL extends AbstractDriver {

    public MySQL(String host, int port, String database, String username, String password, String playerTable,
                 String guildTable, String relationTable, String logTable) {
        super(host, port, database, username, password, playerTable, guildTable, relationTable, logTable);
    }

    @Override
    public String getDatabaseUrl() {
        return "jdbc:mysql://" + host + ":" + port
                + "/" + database + "?autoReconnect=true&useSSL=" + false;
    }

    @Override
    public void init(){
        try{
            connection = DriverManager.getConnection(this.getDatabaseUrl(), username, password);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
