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

public class AcceptCommand extends Command {

    public AcceptCommand() {
        super(Collections.singletonList("accept"), "accept a player to join the guild.",
                "/dguild guild accept <player_name>", "", true, Duration.ofSeconds(2));
    }

    @Override
    public boolean execute(CommandSender sender, String[] arguments) {
        if(arguments.length > 3){
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
        if(!(member.getRole() == Role.OWNER || member.getRole() == Role.CO_OWNER)){
            sender.sendMessage(StringUtils.Color("&cyou do not have permission to do that!"));
            return true;
        }

        if(arguments.length == 2){
            sender.sendMessage(StringUtils.Color("&aAll applications:"));
            for(Player p:guild.getPlayerApplications()){
                sender.sendMessage(StringUtils.Color("  &e" + p.getName()));
            }
            return true;
        }
        Player targetPlayer = Bukkit.getPlayer(arguments[2]);
        if(targetPlayer == null){
            sender.sendMessage(StringUtils.Color("&c cannot find this player: &e" + arguments[2]));
            return true;
        }
        Member targetMember = instance.getMemberManager().getMember(targetPlayer.getUniqueId());
        if(targetMember == null) return true;
        if(targetMember.getGuildBelong() != null){
            if(targetMember.getGuildBelong() == guild){
                sender.sendMessage(StringUtils.Color("&cThis player has been joined your guild!"));
            }else{
                sender.sendMessage(StringUtils.Color("&cThis player has been joined another guild!"));
            }
            return true;
        }
        instance.getGuildManager().joinGuild(guild, targetPlayer, player);
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
