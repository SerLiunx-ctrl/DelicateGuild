package github.serliunx.delicateguild.manager;

import github.serliunx.delicateguild.allenum.YamlFiles;
import github.serliunx.delicateguild.file.YamlFile;
import github.serliunx.delicateguild.file.config.YamlFileConfig;
import github.serliunx.delicateguild.file.menu.MenuFile;

import java.util.HashMap;
import java.util.Map;

public class ConfigManager {

    private final Map<String, YamlFile> yamlFileConfigMap = new HashMap<>();

    public ConfigManager(){
        loadConfigs();
    }

    public void loadConfigs(){
        yamlFileConfigMap.put(YamlFiles.YAML_MAIN.getValue(), new YamlFileConfig("config.yml"));
        yamlFileConfigMap.put(YamlFiles.YAML_LANGUAGE.getValue(), new YamlFileConfig("lang.yml"));
        yamlFileConfigMap.put(YamlFiles.YAML_COMMAND.getValue(), new YamlFileConfig("command.yml"));
        yamlFileConfigMap.put("main_menu", new MenuFile("main_menu.yml"));
        yamlFileConfigMap.put("guild_menu", new MenuFile("guild_menu.yml"));
    }

    public YamlFile getByConfigName(String name){
        return yamlFileConfigMap.get(name);
    }

    public void reloadConfigs(){
        for(YamlFile c: yamlFileConfigMap.values()){
            c.reloadFile();
        }
    }
}
