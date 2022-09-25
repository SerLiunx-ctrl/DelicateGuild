package github.serliunx.delicateguild.config.file;

import github.serliunx.delicateguild.DelicateGuild;
import github.serliunx.delicateguild.config.Config;

public class LanguageConfig extends Config {

    public LanguageConfig() {
        super(DelicateGuild.getInstance().getDataFolder().toString(), "lang.yml");
    }
}
