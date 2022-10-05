package github.serliunx.delicateguild.command;

import github.serliunx.delicateguild.DelicateGuild;
import github.serliunx.delicateguild.manager.CooldownProvider;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public abstract class Command {

    private final List<String> aliases;
    private final List<Command> childs;
    private String description;
    private final String permission;
    private String syntax;
    private final boolean isForPlayer;
    private boolean enabled;
    private final long cooldownInSeconds;
    private CooldownProvider<CommandSender> cooldownProvider;

    protected final DelicateGuild instance;

    /**
     *
     * @param aliases 指令别名.
     * @param description 指令描述.
     * @param syntax 执行格式.
     * @param permission 该指令的权限.
     * @param onlyForPlayer 是否只有玩家才可以执行该指令.
     * @param cooldown 指令冷却时间.
     */
    public Command(@NotNull List<String> aliases, @NotNull String description, @NotNull String syntax, @NotNull String permission, boolean onlyForPlayer, Duration cooldown){
        this.aliases = aliases;
        this.childs = new ArrayList<>();
        this.description = description;
        this.syntax = syntax;
        this.permission = permission;
        this.isForPlayer = onlyForPlayer;
        this.enabled = true;
        this.cooldownInSeconds = cooldown.getSeconds();

        instance = DelicateGuild.getInstance();
    }

    public CooldownProvider<CommandSender> getCooldownProvider() {
        if (cooldownProvider == null) {
            this.cooldownProvider = CooldownProvider.newInstance(Duration.ofSeconds(cooldownInSeconds));
        }

        return cooldownProvider;
    }

    public void addChilds(Command... newChilds) {
        childs.addAll(Arrays.asList(newChilds));
    }

    public Optional<Command> getChildByName(String name) {
        return childs.stream()
                .filter(command -> command.aliases.stream()
                        .map(String::toLowerCase)
                        .anyMatch(commandName -> commandName.equalsIgnoreCase(name))
                )
                .findAny();
    }

    public @NotNull List<String> getAliases() {
        return aliases;
    }

    public @NotNull List<Command> getChilds() {
        return childs;
    }

    public @NotNull String getDescription() {
        return description;
    }

    public @NotNull String getPermission() {
        return permission;
    }

    public @NotNull String getSyntax() {
        return syntax;
    }

    public boolean isForPlayer() {
        return isForPlayer;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setSyntax(String syntax) {
        this.syntax = syntax;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setChild(int index,Command command){
        childs.set(index, command);
    }

    /**
     * 执行指令.
     *
     * @param sender 执行指令的实体.
     * @param arguments 参数
     * @return 如果成功返回真，否则返回假
     */
    public abstract boolean execute(CommandSender sender, String[] arguments);

    /**
     *
     * @param commandSender 执行指令的实体
     * @param command //
     * @param label 指令名称
     * @param args 参数列表.
     * @return 一个补全列表
     */
    public abstract List<String> onTabComplete(CommandSender commandSender, org.bukkit.command.Command command, String label, String[] args);

}
