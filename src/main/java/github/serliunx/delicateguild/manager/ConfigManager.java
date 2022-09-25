package github.serliunx.delicateguild.manager;

import github.serliunx.delicateguild.allenum.YamlFile;
import github.serliunx.delicateguild.config.Config;
import github.serliunx.delicateguild.config.file.CommandConfig;
import github.serliunx.delicateguild.config.file.LanguageConfig;
import github.serliunx.delicateguild.config.file.MainConfig;

import java.util.HashMap;
import java.util.Map;

public class ConfigManager {

    private final Map<String, Config> configs = new HashMap<>();

    public ConfigManager(){
        loadConfigs();
    }

    public void loadConfigs(){
        configs.put(YamlFile.YAML_MAIN.getValue(), new MainConfig());
        configs.put(YamlFile.YAML_LANGUAGE.getValue(), new LanguageConfig());
        configs.put(YamlFile.YAML_COMMAND.getValue(), new CommandConfig());
    }

    public Config getByConfigName(String name){
        return configs.get(name);
    }

    public void reloadConfigs(){
        for(Config c: configs.values()){
            c.reloadConfig();
        }
    }
}
