package github.serliunx.delicateguild.file;

import github.serliunx.delicateguild.DelicateGuild;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public abstract class AbstractYamlFile implements YamlFile{

    protected final String fileName;
    protected final String pathName;
    protected File file;
    protected FileConfiguration configuration;

    public AbstractYamlFile(String pathName, String fileName){
        this.pathName = pathName;
        this.fileName = fileName;
    }

    public AbstractYamlFile(String fileName){
        this(DelicateGuild.getInstance().getDataFolder().toString(), fileName);
    }

    @Override
    public boolean reloadFile() {
        try{
            file = new File(pathName, fileName);
            configuration = YamlConfiguration.loadConfiguration(file);
            return true;
        }catch(Exception e){
            DelicateGuild.getInstance().getLogger().warning(e.toString());
            return false;
        }
    }

    @Override
    public void saveDefaultFile() {
        file = new File(pathName, fileName);
        if(!file.exists())
            DelicateGuild.getInstance().saveResource(fileName,false);
        configuration = YamlConfiguration.loadConfiguration(file);
    }

    /**
     * 返回此配置文件的 {@link FileConfiguration}
     * @return 可操作的 FileConfiguration
     */
    @Override
    public FileConfiguration getConfiguration(){
        return configuration;
    }

    /**
     * 将此文件保存到磁盘中
     */
    @Override
    public void save(){
        try{
            configuration.save(file);
        }catch (IOException e){
            DelicateGuild.getInstance().getLogger().warning(e.toString());
        }
    }
}
