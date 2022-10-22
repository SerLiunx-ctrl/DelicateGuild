package github.serliunx.delicateguild.command.subcommand.guild;

import github.serliunx.delicateguild.allenum.Role;
import github.serliunx.delicateguild.command.Command;
import github.serliunx.delicateguild.entity.Guild;
import github.serliunx.delicateguild.entity.Member;
import github.serliunx.delicateguild.manager.GuildController;
import github.serliunx.delicateguild.util.StringUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import java.time.Duration;
import java.util.Collections;
import java.util.List;

public class QuitCommand extends Command {

    public QuitCommand() {
        super(Collections.singletonList("quit"), "Quit the current guild",
                "/dguild guild quit", "", true, Duration.ofSeconds(2));
    }

    @Override
    public boolean execute(CommandSender sender, String[] arguments) {
        if(arguments.length != 2){
            showSyntax(sender);
            return true;
        }
        Player player = (Player)sender;
        Member member = instance.getMemberManager().getMember(player.getUniqueId());
        if(member == null) return true;
        Guild guild = member.getGuildBelong();
        if(!GuildController.isInTheGuild(member, guild, true))
            return true;
        if(guild.getOwner().equals(member) && member.getRole() == Role.OWNER){
            sender.sendMessage(StringUtils.Color("&cYow owned this guild! so you cannot quit this guild!"));
            return true;
        }
        instance.getGuildManager().quitGuild(guild, player);
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, org.bukkit.command.Command command, String label, String[] args) {
        return Collections.emptyList();
    }
}
