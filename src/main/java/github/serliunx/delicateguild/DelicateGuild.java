package github.serliunx.delicateguild;

import github.serliunx.delicateguild.command.Commands;
import github.serliunx.delicateguild.listener.PlayerListener;
import github.serliunx.delicateguild.manager.*;
import github.serliunx.delicateguild.util.Language;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class DelicateGuild extends JavaPlugin {

    public static DelicateGuild instance;
    private Commands commands;
    private ConfigManager configManager;
    private CommandManager commandManager;
    private Language language;
    private DataManager dataManager;
    private MemberManager memberManager;
    private GuildManager guildManager;

    @Override
    public void onLoad(){
        instance = this;
    }

    @Override
    public void onEnable() {

        commands = new Commands();
        configManager = new ConfigManager();
        commandManager = new CommandManager("delicateguild");
        language = new Language();
        dataManager = new DataManager();
        memberManager = new MemberManager();
        guildManager = new GuildManager();

        Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
    }

    @Override
    public void onDisable() {

    }

    public static DelicateGuild getInstance() {
        return instance;
    }

    public Commands getCommands() {
        return commands;
    }

    public CommandManager getCommandManager() {
        return commandManager;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public Language getLanguage() {
        return language;
    }

    public MemberManager getMemberManager() {
        return memberManager;
    }

    public GuildManager getGuildManager() {
        return guildManager;
    }

    public DataManager getDataManager() {
        return dataManager;
    }
}
