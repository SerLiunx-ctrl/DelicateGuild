package github.serliunx.delicateguild.config.file;

import github.serliunx.delicateguild.DelicateGuild;
import github.serliunx.delicateguild.config.Config;

public class MainConfig extends Config {

    public MainConfig() {
        super(DelicateGuild.getInstance().getDataFolder().toString(), "config.yml");
    }
}
