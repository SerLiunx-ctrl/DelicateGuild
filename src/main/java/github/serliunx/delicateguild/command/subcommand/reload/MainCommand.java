package github.serliunx.delicateguild.command.subcommand.reload;

import github.serliunx.delicateguild.DelicateGuild;
import github.serliunx.delicateguild.allenum.YamlFiles;
import github.serliunx.delicateguild.command.Command;
import github.serliunx.delicateguild.util.StringUtils;
import org.bukkit.command.CommandSender;

import java.time.Duration;
import java.util.Collections;
import java.util.List;

public class MainCommand extends Command {

    public MainCommand() {
        super(Collections.singletonList("main"), "reload main config file","/dguild reload main", "", false, Duration.ZERO);
    }

    @Override
    public boolean execute(CommandSender sender, String[] arguments) {
        if(DelicateGuild.getInstance().getConfigManager().getByConfigName(YamlFiles.YAML_MAIN.getValue()).reloadFile()){
            sender.sendMessage(StringUtils.Color("&amain config reloaded!"));
            return true;
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, org.bukkit.command.Command command, String label, String[] args) {
        return Collections.emptyList();
    }
}
