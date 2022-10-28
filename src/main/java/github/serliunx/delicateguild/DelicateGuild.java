package github.serliunx.delicateguild;

import github.serliunx.delicateguild.command.Commands;
import github.serliunx.delicateguild.listener.GuildListener;
import github.serliunx.delicateguild.listener.PlayerListener;
import github.serliunx.delicateguild.manager.*;
import github.serliunx.delicateguild.placeholder.DelicateGuildExpansion;
import github.serliunx.delicateguild.placeholder.PlaceholderAdapter;
import github.serliunx.delicateguild.util.Language;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class DelicateGuild extends JavaPlugin {

    private boolean usePapi;
    private static DelicateGuild instance;
    private Commands commands;
    private ConfigManager configManager;
    private CommandManager commandManager;
    private Language language;
    private DataManager dataManager;
    private MemberManager memberManager;
    private GuildManager guildManager;
    private PlaceholderAdapter placeholderAdapter;

    private DriverManager driverManager;

    /**
     * 公会升级所需经验较前一级别所需要的倍率
     *
     * <li> 所需经验最后的结果为: 四舍五入
     *
     * <li>如:
     * <p> 0 -> 1级(100经验)
     * <p> 1 -> 2级(120经验)
     * <p> 3 -> 4级(144经验)
     */
    private static double guildExpMagnification = 1.2;

    /**
     * 公会允许的最大等级
     */
    private static int guildMaxLevel = 50;

    /**
     * 公会从 0 -> 1级 所需要的经验.
     * <p>
     * <li> 后续等级将按照系数{@link #guildExpMagnification}j计算
     */
    private static int guildExpBegin = 100;

    private final static String COMMAND = "delicateguild";

    private List<Integer> levelExpList;

    @Override
    public void onLoad(){
        instance = this;
    }

    @Override
    public void onEnable() {
        //创建实例
        createInstance();

        //事件监听器注册
        Bukkit.getPluginManager().registerEvents(new PlayerListener(this), this);
        Bukkit.getPluginManager().registerEvents(new GuildListener(this), this);

        //(必须/非必须)前置插件检测
        checkDependencies();

        levelExpList = new ArrayList<>();

        //计算公会升级所需经验表
        for(int i = 0; i <= guildMaxLevel; i++){
            if(i == 0){
                levelExpList.add(guildExpBegin);
            }else{
                levelExpList.add((int) Math.round(levelExpList.get( i - 1) * guildExpMagnification));
            }
        }
    }

    @Override
    public void onDisable() {
        driverManager.getDriver().disConnect();
    }

    private void createInstance(){
        commands = new Commands();
        configManager = new ConfigManager();
        getProperties();
        commandManager = new CommandManager(COMMAND);
        language = new Language();
        driverManager = new DriverManager();
        dataManager = new DataManager();
        memberManager = new MemberManager();
        guildManager = new GuildManager();
        getLogger().info(getDataManager().loadRelation() +
                " available relation(s) has been loaded from database.");
        placeholderAdapter = new PlaceholderAdapter();
    }

    private void checkDependencies(){
        usePapi = Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null;
        if(usePapi){
            new DelicateGuildExpansion().register();
        }
    }

    private void getProperties(){

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

    public PlaceholderAdapter getPlaceholderAdapter() {
        return placeholderAdapter;
    }

    public DriverManager getDriverManager() {
        return driverManager;
    }

    public boolean isUsePapi() {
        return usePapi;
    }

    public List<Integer> getLevelExpList() {
        return levelExpList;
    }

    public static int getGuildMaxLevel() {
        return guildMaxLevel;
    }
}
