package github.serliunx.delicateguild.file.config;

import github.serliunx.delicateguild.DelicateGuild;
import github.serliunx.delicateguild.file.AbstractYamlFile;

/**
 * 配置文件主类
 */
public class YamlFileConfig extends AbstractYamlFile {

    public YamlFileConfig(String fileName) {
        super(DelicateGuild.getInstance().getDataFolder().toString(), fileName);
        super.saveDefaultFile();
    }
}
