package github.serliunx.delicateguild.menu.inventory;

import github.serliunx.delicateguild.allenum.ActionType;
import github.serliunx.delicateguild.menu.AbstractButton;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class InventoryButton extends AbstractButton {

    public InventoryButton(String title, String id, List<String> descriptions, Material material,
                           List<Map<ActionType, String>> actionTypeAndValue) {
        super(title, id, descriptions, material, actionTypeAndValue);
    }

    public InventoryButton(String title, String id) {
        super(title, id, new ArrayList<>(), Material.STONE, new ArrayList<>());
    }

    public InventoryButton() {
        super("none", UUID.randomUUID().toString());
    }

    @Override
    public void onClick(Player player) {
        if(actionTypeAndValue.isEmpty()) return;
        player.playSound(player.getLocation(), Sound.BLOCK_METAL_BREAK,2,2);
        player.closeInventory();
    }

    private void playSound(){

    }
}
