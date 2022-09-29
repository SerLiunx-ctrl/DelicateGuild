package github.serliunx.delicateguild.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class SQL {
    protected Connection connection;

    public Connection getConnection() {
        return connection;
    }

    /**
     * 建立数据表
     * @return 成功返回真, 否则返回假
     */
    public boolean execute(String command){
        try(PreparedStatement ps = getConnection().prepareStatement(command)){
            ps.executeUpdate();
            return true;
        }catch (SQLException s){
            s.printStackTrace();
            return false;
        }
    }
}
