package github.serliunx.delicateguild.command.subcommand.guild;

import github.serliunx.delicateguild.allenum.Role;
import github.serliunx.delicateguild.command.Command;
import github.serliunx.delicateguild.entity.Guild;
import github.serliunx.delicateguild.entity.Member;
import github.serliunx.delicateguild.util.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DenyCommand extends Command {

    public DenyCommand() {
        super(Collections.singletonList("deny"), "deny a application",
                "/dguild guild deny <player_name>", "", true, Duration.ofSeconds(2));
    }

    @Override
    public boolean execute(CommandSender sender, String[] arguments) {
        if(arguments.length != 3){
            showSyntax(sender);
            return true;
        }
        Player player = (Player) sender;
        Member member = instance.getMemberManager().getMember(player.getUniqueId());
        if(member == null)return true;
        Guild guild = member.getGuildBelong();
        if(guild == null){
            sender.sendMessage(StringUtils.Color("&cplease join a guild before do that."));
            return true;
        }
        if(!(member.getRole() == Role.OWNER || member.getRole() == Role.CO_OWNER)){
            sender.sendMessage(StringUtils.Color("&cyou do not have permission to do that!"));
            return true;
        }
        boolean found = false;
        for(Player p: guild.getPlayerApplications()){
            if(arguments[2].equals(p.getName())){
                found = true;
                break;
            }
        }
        if(!found){
            sender.sendMessage(StringUtils.Color("&ccannot find the player: &e" + arguments[2] + " &cin the guild applications list!"));
            return true;
        }
        Player p = Bukkit.getPlayer(arguments[2]);
        if(p == null) return true;
        guild.getPlayerApplications().remove(p);
        sender.sendMessage(StringUtils.Color("&aalready remove player: &e" + arguments[2] +" &afrom the applications list."));
        if(p.isOnline())
            p.sendMessage(StringUtils.Color("&eguild &c" + guild.getId() + " &ehas denied your application."));
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, org.bukkit.command.Command command, String label, String[] args) {
        if (!(commandSender instanceof Player)) return Collections.emptyList();
        Member member = instance.getMemberManager().getMember(((Player) commandSender).getUniqueId());
        if(member == null) return Collections.emptyList();
        if(!(member.getRole() == Role.CO_OWNER || member.getRole() == Role.OWNER)) return Collections.emptyList();
        Guild guild = member.getGuildBelong();
        if(guild == null) return Collections.emptyList();
        List<String> playerName = new ArrayList<>();
        for(Player player:guild.getPlayerApplications())
            playerName.add(player.getName());
        return playerName;
    }
}
