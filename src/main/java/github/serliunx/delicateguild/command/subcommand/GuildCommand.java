package github.serliunx.delicateguild.command.subcommand;

import github.serliunx.delicateguild.command.Command;
import github.serliunx.delicateguild.command.subcommand.guild.*;
import org.bukkit.command.CommandSender;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GuildCommand extends Command {

    public Command listCommand, createCommand, joinCommand, acceptCommand, quitCommand;

    public GuildCommand() {
        super(Collections.singletonList("guild"), "command about guild", "/dguild guild",
                "", false, Duration.ofSeconds(2));

        listCommand = new ListCommand();
        createCommand = new CreateCommand();
        joinCommand = new JoinCommand();
        acceptCommand = new AcceptCommand();
        quitCommand = new QuitCommand();

        addChilds(listCommand, createCommand, joinCommand, acceptCommand, quitCommand);
    }

    @Override
    public boolean execute(CommandSender sender, String[] arguments) {
        if(arguments.length < 2){
            showAllChildSyntax(sender);
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, org.bukkit.command.Command command, String label, String[] args) {
        List<String> subs = new ArrayList<>();
        getChilds().forEach(sub -> subs.add(sub.getAliases().get(0)));
        return subs;
    }
}
