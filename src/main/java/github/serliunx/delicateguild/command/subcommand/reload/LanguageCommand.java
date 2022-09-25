package github.serliunx.delicateguild.command.subcommand.reload;

import github.serliunx.delicateguild.DelicateGuild;
import github.serliunx.delicateguild.allenum.YamlFile;
import github.serliunx.delicateguild.command.Command;
import github.serliunx.delicateguild.util.StringUtils;
import org.bukkit.command.CommandSender;

import java.time.Duration;
import java.util.Collections;
import java.util.List;

public class LanguageCommand extends Command {

    public LanguageCommand(){
        super(Collections.singletonList("lang"), "reload language file","/dguild reload lang", "", false, Duration.ZERO);
    }

    @Override
    public boolean execute(CommandSender sender, String[] arguments) {
        if(DelicateGuild.getInstance().getConfigManager().getByConfigName(YamlFile.YAML_LANGUAGE.getValue()).reloadConfig()){
            sender.sendMessage(StringUtils.Color("language config reloaded!"));
            return true;
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, org.bukkit.command.Command command, String label, String[] args) {
        return Collections.emptyList();
    }
}
