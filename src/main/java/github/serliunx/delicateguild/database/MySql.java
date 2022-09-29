package github.serliunx.delicateguild.database;

import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQL extends SQL{
    public MySQL(String databaseURL, String username, String password) {
        try{
            connection = DriverManager.getConnection(databaseURL, username, password);
        }catch (SQLException s){
            s.printStackTrace();
        }
    }
}
