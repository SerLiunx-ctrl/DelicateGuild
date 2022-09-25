package github.serliunx.delicateguild.command.subcommand;

import github.serliunx.delicateguild.DelicateGuild;
import github.serliunx.delicateguild.allenum.Permission;
import github.serliunx.delicateguild.command.Command;
import github.serliunx.delicateguild.command.subcommand.reload.CmdCommand;
import github.serliunx.delicateguild.command.subcommand.reload.LanguageCommand;
import github.serliunx.delicateguild.command.subcommand.reload.MainCommand;
import github.serliunx.delicateguild.util.StringUtils;
import org.bukkit.command.CommandSender;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ReloadCommand extends Command {

    public Command mainCommand, languageCommand, cmdCommand;

    public ReloadCommand() {
        super(Collections.singletonList("reload"),"reload command.","/dguild reload <target>", Permission.COMMAND_ADMIN_RELOAD.getValue(), false, Duration.ZERO);

        languageCommand = new LanguageCommand();
        mainCommand = new MainCommand();
        cmdCommand = new CmdCommand();

        addChilds(languageCommand, mainCommand, cmdCommand);
    }

    @Override
    public boolean execute(CommandSender sender, String[] arguments) {
        if(arguments.length == 1){
            sender.sendMessage(StringUtils.Color("&aAll files reloaded!"));
            return reloadAll();
        }

        for(Command c:getChilds()){
            if(arguments[1].equalsIgnoreCase(c.getAliases().get(0))){
                return c.execute(sender, arguments);
            }
        }

        for(Command c:getChilds()){
            sender.sendMessage(StringUtils.Color(c.getSyntax() + " &f- " + c.getDescription()));
        }

        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, org.bukkit.command.Command command, String label, String[] args) {
        List<String> subs = new ArrayList<>();
        getChilds().forEach(sub -> subs.add(sub.getAliases().get(0)));
        return subs;
    }

    private boolean reloadAll(){
        DelicateGuild.getInstance().getConfigManager().reloadConfigs();
        DelicateGuild.getInstance().getCommandManager().reloadCommands();
        return true;
    }
}
