package github.serliunx.delicateguild.menu;

import github.serliunx.delicateguild.allenum.ActionType;
import github.serliunx.delicateguild.menu.inventory.InventoryButton;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ButtonBuilder {

    private final FileConfiguration menuFile;

    private static final String ERROR = "error";
    private final List<String> buttonNames;

    public ButtonBuilder(FileConfiguration menuFile) {
        this.menuFile = menuFile;
        buttonNames = menuFile.getStringList("buttons");
    }

    public Map<Integer, Button> build(){
        Map<Integer, Button> buttons = new HashMap<>();

        for(String name:buttonNames){
            String title = menuFile.getString(name + ".title", ERROR);
            List<Integer> pos;

            if(menuFile.isList(name + ".pos")){
                pos = menuFile.getIntegerList(name + ".pos");
            }else{
                pos = new ArrayList<>();
                pos.add(menuFile.getInt(name + ".pos"));
            }

            Material material = Material.valueOf(menuFile.getString(name + ".material", "STONE"));
            List<String> descriptions = menuFile.getStringList(name + ".descriptions");
            List<Map<ActionType, String>> actionTypeAndValue = new ArrayList<>();
            List<String> actions = menuFile.getStringList(name + ".action");

            for(String action:actions){
                if(action.startsWith(ActionType.OPEN_MENU.getValue())){
                    Map<ActionType , String> actionTypeStringMap = new HashMap<>();
                    actionTypeStringMap.put(ActionType.OPEN_MENU, action.replace(ActionType.OPEN_MENU.getValue(),
                            ""));
                    actionTypeAndValue.add(actionTypeStringMap);
                }
                if(action.startsWith(ActionType.RUN_COMMAND.getValue())){
                    Map<ActionType , String> actionTypeStringMap = new HashMap<>();
                    actionTypeStringMap.put(ActionType.RUN_COMMAND, action.replace(ActionType.RUN_COMMAND.getValue(),
                            ""));
                    actionTypeAndValue.add(actionTypeStringMap);
                }
                if(action.startsWith(ActionType.PLAY_SOUND.getValue())){
                    Map<ActionType , String> actionTypeStringMap = new HashMap<>();
                    actionTypeStringMap.put(ActionType.PLAY_SOUND, action.replace(ActionType.PLAY_SOUND.getValue(),
                            ""));
                    actionTypeAndValue.add(actionTypeStringMap);
                }
                if(action.startsWith(ActionType.CLOSE.getValue())){
                    Map<ActionType , String> actionTypeStringMap = new HashMap<>();
                    actionTypeStringMap.put(ActionType.CLOSE, action.replace(ActionType.CLOSE.getValue(),
                            ""));
                    actionTypeAndValue.add(actionTypeStringMap);
                }

            }

            for(Integer p:pos){
                Button button = new InventoryButton(title, name, descriptions, material, actionTypeAndValue);
                buttons.put(p, button);
            }
        }

        return buttons;
    }
}
