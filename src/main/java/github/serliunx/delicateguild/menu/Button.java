package github.serliunx.delicateguild.menu;

import github.serliunx.delicateguild.allenum.ActionType;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public interface Button extends Clickable{
    String getTitle();
    void setTitle(String title);
    void addDescriptions(String...strings);
    void setDescriptions(List<String> descriptions);
    List<String> getDescriptions();
    String getId();
    ItemStack getItem();
    void setIcon(Material materialIcon);
}
