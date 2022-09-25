package github.serliunx.delicateguild.manager;

import github.serliunx.delicateguild.DelicateGuild;
import github.serliunx.delicateguild.allenum.YamlFiles;
import github.serliunx.delicateguild.command.Command;
import github.serliunx.delicateguild.command.Commands;
import github.serliunx.delicateguild.util.StringUtils;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import java.lang.reflect.Field;
import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

public class CommandManager implements CommandExecutor, TabCompleter {

    private final List<Command> commands = new ArrayList<>();

    private final FileConfiguration commandConfiguration;

    public CommandManager(String command){
        PluginCommand pluginCommand = DelicateGuild.getInstance().getCommand(command);
        if(pluginCommand != null){
            pluginCommand.setExecutor(this);
            pluginCommand.setTabCompleter(this);
        }
        commandConfiguration = DelicateGuild.getInstance().getConfigManager().getByConfigName(YamlFiles.YAML_COMMAND.getValue()).getConfiguration();
        registerCommands();
    }

    private void registerCommands(){
        Commands commands = DelicateGuild.getInstance().getCommands();
        for (Field field : commands.getClass().getFields()) {
            try {
                Command command = (Command) field.get(commands);
                registerCommand(command);
            } catch (IllegalAccessException exception) {
                exception.printStackTrace();
            }
        }
    }

    public void registerCommand(Command command) {
        if (command.isEnabled()) {
            int index = Collections.binarySearch(commands, command, Comparator.comparing(cmd -> cmd.getAliases().get(0)));
            for(String key:commandConfiguration.getKeys(false)){
                if(key.equals(command.getAliases().get(0)))
                    command = loadCommandLanguage(command,key);
            }
            commands.add(index < 0 ? -(index + 1) : index, command);
        }
    }

    public Command loadCommandLanguage(Command command, String path){
        command.setDescription(commandConfiguration.getString(path + ".description",command.getDescription()));
        command.setEnabled(commandConfiguration.getBoolean(path + ".enable",true));
        command.setSyntax(commandConfiguration.getString(path + ".syntax",command.getSyntax()));
        if(!command.getChilds().isEmpty()){
            for(int i = 0; i < command.getChilds().size(); i++){
                command.setChild(i, loadCommandLanguage(command.getChilds().get(i),path + ".childs."+
                        command.getChilds().get(i).getAliases().get(0)));
            }
        }
        return command;
    }

    public void unregisterCommand(Command command) {
        commands.remove(command);
    }

    public void reloadCommands(){
        commands.clear();
        registerCommands();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull org.bukkit.command.Command cmd, @NotNull String label, @NotNull String[] args) {
        if(args.length == 0){
            DelicateGuild.getInstance().getCommands().helpCommand.execute(commandSender,new String[]{});
            return false;
        }
        for(Command command : commands){
            if(!(command.getAliases().contains(args[0])))
                continue;

            Command executingCommand = findExecutingCommand(command, args);
            if (executionBlocked(executingCommand, commandSender)) {
                return false;
            }

            boolean success = executingCommand.execute(commandSender, args);
            if (success) executingCommand.getCooldownProvider().applyCooldown(commandSender);
            return true;
        }

        //未找到该指令
        commandSender.sendMessage("command doesn't exits");
        return false;
    }

    /**
     *
     * 检查是否能顺利执行该指令.
     *
     * @param command 指令.
     * @param commandSender 执行者.
     * @return 返回真如果可以执行, 否则返回假.
     */
    private boolean executionBlocked(Command command, @NotNull CommandSender commandSender) {
        if (command.isForPlayer() && !(commandSender instanceof Player)) {
            commandSender.sendMessage(StringUtils.Color("&c only for player!"));
            return true;
        }

        if (!hasPermissions(commandSender, command)) {
            commandSender.sendMessage("no permission");
            return true;
        }

        CooldownProvider<CommandSender> cooldownProvider = command.getCooldownProvider();

        if (commandSender instanceof Player && cooldownProvider.isOnCooldown(commandSender)) {
            Duration remainingTime = cooldownProvider.getRemainingTime(commandSender);
            String formattedTime = StringUtils.formatDuration("{seconds}", remainingTime);
            commandSender.sendMessage("please wait...");
            return true;
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull org.bukkit.command.Command cmd, @NotNull String label, @NotNull String[] args) {

        //当位于第一个参数时, 返回所有指令.
        if (args.length == 1) {
            ArrayList<String> result = new ArrayList<>();
            for (Command command : commands) {
                for (String alias : command.getAliases()) {
                    if (alias.toLowerCase().startsWith(args[0].toLowerCase()) && hasPermissions(commandSender, command)) {
                        result.add(alias);
                    }
                }
            }

            return result;
        }

        //返回指令的子命令.
        for (Command command : commands) {
            if (command.getAliases().contains(args[0])) {
                Command executingCommand = findExecutingCommand(command, args);
                if (hasPermissions(commandSender, executingCommand)) {
                    List<String> tabCompletion = new ArrayList<>(executingCommand.onTabComplete(commandSender, cmd, label, args));

                    executingCommand.getChilds().stream()
                            .filter(subCommand -> hasPermissions(commandSender, subCommand))
                            .map(subCommand -> subCommand.getAliases().get(0))
                            .forEach(tabCompletion::add);

                    return filterTabCompletionResults(tabCompletion, args);
                }
            }
        }

        //如果上述逻辑都没执行到则返回一个空列表, 以防止默认返回玩家列表.
        return Collections.emptyList();
    }

    /**
     *
     * 检测指令执行者是否有权限.
     *
     * @param command 指令.
     * @param commandSender 执行者.
     * @return 返回真如果有权限, 否则返回假.
     */
    private boolean hasPermissions(@NotNull CommandSender commandSender, Command command) {
        return commandSender.hasPermission(command.getPermission())
                || command.getPermission().equalsIgnoreCase("")
                || command.getPermission().equalsIgnoreCase("delicateguild.");
    }

    private Command findExecutingCommand(Command baseCommand, String[] args) {
        Command executingCommand = baseCommand;

        for (int currentArgument = 1; currentArgument < args.length; currentArgument++) {
            Optional<Command> child = executingCommand.getChildByName(args[currentArgument]);
            if (!child.isPresent()) break;

            executingCommand = child.get();
        }

        return executingCommand;
    }

    private List<String> filterTabCompletionResults(List<String> tabCompletion, String[] arguments) {
        return tabCompletion.stream()
                .filter(completion -> completion.toLowerCase().contains(arguments[arguments.length - 1].toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Command> getCommands() {
        return commands;
    }
}
