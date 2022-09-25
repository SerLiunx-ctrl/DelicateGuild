package github.serliunx.delicateguild.config;

import github.serliunx.delicateguild.DelicateGuild;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.File;
import java.io.IOException;

/**
 * 配置文件主类
 */
public abstract class Config {

    private final String fileName;
    private final String pathName;
    private File file;
    private FileConfiguration configuration;

    public Config(String pathName, String fileName){
        this.pathName = pathName;
        this.fileName = fileName;
        saveDefaultConfig();
    }

    /**
     * 从磁盘中重载此配置文件
     * <li> 请确保插件有存取文件的权限
     * <li> 此项操作无法撤回
     * @return result
     */
    public boolean reloadConfig(){
        try{
            file = new File(pathName, fileName);
            configuration = YamlConfiguration.loadConfiguration(file);
            return true;
        }catch(Exception e){
            DelicateGuild.getInstance().getLogger().warning(e.toString());
            return false;
        }
    }

    /**
     * 将.jar中的resource文件夹中的所有.yml文件<p>
     * 释放到插件数据文件夹中.<p>
     * 默认不会覆盖.
     */
    private void saveDefaultConfig() {
        file = new File(pathName,fileName);
        if(!file.exists())
            DelicateGuild.getInstance().saveResource(fileName,false);
        configuration = YamlConfiguration.loadConfiguration(file);
    }

    /**
     * 返回此配置文件的 {@link FileConfiguration}
     * @return 可操作的 FileConfiguration
     */
    public FileConfiguration getConfiguration(){
        return configuration;
    }

    /**
     * 将此文件保存到磁盘中
     */
    public void save(){
        try{
            configuration.save(file);
        }catch (IOException e){
            DelicateGuild.getInstance().getLogger().warning(e.toString());
        }
    }
}
