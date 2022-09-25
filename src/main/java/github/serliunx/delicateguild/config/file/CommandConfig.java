package github.serliunx.delicateguild.config.file;

import github.serliunx.delicateguild.DelicateGuild;
import github.serliunx.delicateguild.config.Config;
public class CommandConfig extends Config {

    public CommandConfig() {
        super(DelicateGuild.getInstance().getDataFolder().toString(), "command.yml");
    }
}
