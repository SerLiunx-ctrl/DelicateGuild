package github.serliunx.delicateguild.file;

import org.bukkit.configuration.file.FileConfiguration;

public interface YamlFile {
    /**
     * 从磁盘中重载此配置文件
     * <li> 请确保插件有存取文件的权限
     * <li> 此项操作无法撤回
     * @return result
     */
    boolean reloadFile();
    void saveDefaultFile();
    FileConfiguration getConfiguration();
    void save();
}
