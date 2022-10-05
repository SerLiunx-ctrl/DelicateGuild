package github.serliunx.delicateguild.menu.inventory;

import github.serliunx.delicateguild.DelicateGuild;
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
        for(Map<ActionType, String> atv: actionTypeAndValue){
            for(ActionType type:atv.keySet()){
                switch (type){
                    case PLAY_SOUND:
                        playSound(player,atv.get(type));
                        break;
                    case RUN_COMMAND:
                        player.performCommand(atv.get(type));
                        break;
                    case OPEN_MENU:
                        break;
                    default:
                        player.closeInventory();
                }
            }
        }
    }

    private void playSound(Player player, String value){
        try{
            String[] v = value.split("-");
            Sound sound = Sound.valueOf(v[0]);
            int volume = Integer.parseInt(v[1]);
            int pitch = Integer.parseInt(v[2]);
            player.playSound(player.getLocation(), sound, volume, pitch);
        }catch (Exception e){
            DelicateGuild.getInstance().getLogger().info("sound error! ");
        }
    }
}
