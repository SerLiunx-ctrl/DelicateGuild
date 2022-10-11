package github.serliunx.delicateguild.command.subcommand.guild;

import github.serliunx.delicateguild.command.Command;
import github.serliunx.delicateguild.entity.Guild;
import github.serliunx.delicateguild.entity.Member;
import github.serliunx.delicateguild.util.StringUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class JoinCommand extends Command {

    public JoinCommand() {
        super(Collections.singletonList("join"), "apply to join a guild",
                "/dguild guild join <guild_name>", "", true, Duration.ofSeconds(2));
    }

    @Override
    public boolean execute(CommandSender sender, String[] arguments) {
        if(arguments.length != 3){
            showSyntax(sender);
            return true;
        }
        Player player = (Player) sender;
        Member member = instance.getMemberManager().getMember(player.getUniqueId());
        if(member == null) return true;
        //检查该用户是否已经属于一个公会
        if(member.getGuildBelong() != null){
            sender.sendMessage(StringUtils.Color("&cYou have already joined a guild! Cannot apply for another"));
            return true;
        }

        //检查指定id的公会是否存在
        Guild guild = instance.getGuildManager().getById(arguments[2]);
        if(guild == null) {
            sender.sendMessage(StringUtils.Color("&cThe guild was not found: " + arguments[2]));
            return true;
        }

        //检查该用户是否已经申请过该公会
        if(guild.getPlayerApplications().contains(player)){
            sender.sendMessage(StringUtils.Color("&cYou have already applied for this guild!"));
            return true;
        }

        guild.addAnApplication(player);
        sender.sendMessage(StringUtils.Color("&aApply to join a guild: &d" + arguments[2] + " &aplease wait a result."));

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, org.bukkit.command.Command command, String label, String[] args) {
        return new ArrayList<>(instance.getGuildManager().getAllGuilds().keySet());
    }
}
