package github.serliunx.delicateguild.database;

import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLite extends SQL{

    public SQLite(String databaseURL) {
        try{
            connection = DriverManager.getConnection(databaseURL);
        }catch (SQLException s){
            s.printStackTrace();
        }
    }
}
