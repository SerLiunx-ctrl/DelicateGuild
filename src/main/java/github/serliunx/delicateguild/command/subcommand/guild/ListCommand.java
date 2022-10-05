package github.serliunx.delicateguild.command.subcommand.guild;

import github.serliunx.delicateguild.DelicateGuild;
import github.serliunx.delicateguild.command.Command;
import github.serliunx.delicateguild.entity.Guild;
import org.bukkit.command.CommandSender;

import java.time.Duration;
import java.util.Collections;
import java.util.List;

public class ListCommand extends Command {

    public ListCommand() {
        super(Collections.singletonList("list"), "list all guilds", "/dguild guild list",
                "", false, Duration.ofSeconds(2));
    }

    @Override
    public boolean execute(CommandSender sender, String[] arguments) {
        if(DelicateGuild.getInstance().getGuildManager().getAllGuilds().keySet().isEmpty()){
            sender.sendMessage("empty.");
            return true;
        }

        for(String id: DelicateGuild.getInstance().getGuildManager().getAllGuilds().keySet()){
            sender.sendMessage(DelicateGuild.getInstance().getGuildManager().getAllGuilds().get(id).toString());
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, org.bukkit.command.Command command, String label, String[] args) {
        return Collections.emptyList();
    }
}
