package github.serliunx.delicateguild.placeholder;

import github.serliunx.delicateguild.DelicateGuild;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

public class DelicateGuildExpansion extends PlaceholderExpansion {

    private final DelicateGuild instance;

    public DelicateGuildExpansion() {
        this.instance = DelicateGuild.getInstance();
    }

    @Override
    public @NotNull String getIdentifier() {
        return "delicateguild";
    }

    @Override
    public @NotNull String getAuthor() {
        return "SerLiunx";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0.0";
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public String onRequest(OfflinePlayer player, @NotNull String params){
        return "";
    }
}
