package github.serliunx.delicateguild.command.subcommand.guild;

import github.serliunx.delicateguild.DelicateGuild;
import github.serliunx.delicateguild.allenum.Permission;
import github.serliunx.delicateguild.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.time.Duration;
import java.util.Collections;
import java.util.List;

public class CreateCommand extends Command {

    public CreateCommand() {
        super(Collections.singletonList("create"), "create a guild.", "/dguild guild create <name>",
                Permission.COMMAND_GUILD_CREATE.getValue(), false, Duration.ofSeconds(2));
    }

    public boolean execute(CommandSender sender, String[] arguments) {
        if(arguments.length != 3){
            showSyntax(sender);
            return true;
        }
        if(DelicateGuild.getInstance().getGuildManager().contain(arguments[2])){
            sender.sendMessage(arguments[2] + " already exist.");
            return true;
        }

        if(sender instanceof Player){
            if(instance.getGuildManager().createGuild(arguments[2], (Player)sender)){
                sender.sendMessage("guild " + arguments[2] + " successfully created!");
                return true;
            }
            sender.sendMessage("guild create failed.");
        }else{
            if(DelicateGuild.getInstance().getGuildManager().createGuild(arguments[2])){
                sender.sendMessage("guild " + arguments[2] + " successfully created!");
                return true;
            }
            sender.sendMessage("guild create failed.");
        }
        return true;
    }

    public List<String> onTabComplete(CommandSender commandSender, org.bukkit.command.Command command, String label, String[] args) {
        return Collections.emptyList();
    }
}
