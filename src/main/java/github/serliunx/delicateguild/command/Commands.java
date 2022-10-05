package github.serliunx.delicateguild.command;

import github.serliunx.delicateguild.command.subcommand.GuildCommand;
import github.serliunx.delicateguild.command.subcommand.HelpCommand;
import github.serliunx.delicateguild.command.subcommand.ReloadCommand;

public class Commands {
    public HelpCommand helpCommand = new HelpCommand();
    public ReloadCommand reloadCommand = new ReloadCommand();
    public GuildCommand guildCommand = new GuildCommand();
}
