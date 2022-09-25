package github.serliunx.delicateguild.file.menu;

import github.serliunx.delicateguild.DelicateGuild;
import github.serliunx.delicateguild.file.AbstractYamlFile;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class MenuFile extends AbstractYamlFile {

    public MenuFile(String fileName) {
        super(DelicateGuild.getInstance().getDataFolder() +
                File.separator + "menu", fileName);
        this.saveDefaultFile();
    }

    @Override
    public void saveDefaultFile() {
        file = new File(pathName, fileName);

        if(!file.exists()){
            File path = new File(pathName);
            if(!path.exists()){
                if(!path.mkdir()) return;
            }

            InputStream inputStream = DelicateGuild.getInstance().getResource(fileName);
            if(inputStream == null) return;
            Reader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            configuration = YamlConfiguration.loadConfiguration(reader);
            save();
            return;
        }
        configuration = YamlConfiguration.loadConfiguration(file);
    }
}
