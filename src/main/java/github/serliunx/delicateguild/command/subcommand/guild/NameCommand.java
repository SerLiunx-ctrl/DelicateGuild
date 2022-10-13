package github.serliunx.delicateguild.command.subcommand.guild;

import github.serliunx.delicateguild.command.Command;
import github.serliunx.delicateguild.entity.Guild;
import github.serliunx.delicateguild.entity.Member;
import github.serliunx.delicateguild.util.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import java.time.Duration;
import java.util.Collections;
import java.util.List;

public class NameCommand extends Command {

    public NameCommand() {
        super(Collections.singletonList("name"), "change the guild alias name",
                "/dguild guild name <new_name>", "", true, Duration.ofSeconds(2));
    }

    @Override
    public boolean execute(CommandSender sender, String[] arguments) {
        if(arguments.length != 3){
            showSyntax(sender);
            return true;
        }
        Player player = (Player)sender;
        Member member = instance.getMemberManager().getMember(player.getUniqueId());
        if(member == null)return true;
        Guild guild = member.getGuildBelong();
        if(guild == null){
            sender.sendMessage(StringUtils.Color("&cplease join a guild before do that."));
            return true;
        }
        if(!guild.isAnAdministrator(member)){
            sender.sendMessage(StringUtils.Color("&cyou do not have permission to do that!"));
            return true;
        }
        guild.setAlias(arguments[2]);
        sender.sendMessage(StringUtils.Color("&ename changed to: &r" + arguments[2]));
        for(Member m:guild.getMembers()){
            Player p = Bukkit.getPlayer(m.getUuid());
            if(p != null && p.isOnline()){
                p.sendMessage(StringUtils.Color("&ename changed to:&r " + arguments[2] + " &r&eby &b" + player.getName()));
            }
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, org.bukkit.command.Command command, String label, String[] args) {
        return Collections.emptyList();
    }
}
