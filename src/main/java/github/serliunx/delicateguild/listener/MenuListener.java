package github.serliunx.delicateguild.listener;

import github.serliunx.delicateguild.DelicateGuild;
import github.serliunx.delicateguild.api.event.menu.MenuClickEvent;
import github.serliunx.delicateguild.api.event.menu.MenuOpenEvent;
import github.serliunx.delicateguild.entity.Guild;
import github.serliunx.delicateguild.menu.Button;
import github.serliunx.delicateguild.menu.Menu;
import github.serliunx.delicateguild.menu.inventory.InventoryButton;
import github.serliunx.delicateguild.placeholder.DelicateGuildHolder;
import github.serliunx.delicateguild.util.StringUtils;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import java.util.*;

public class MenuListener implements Listener {
    private final DelicateGuild instance;
    private final FileConfiguration menuConfiguration;
    private final Map<Player, Integer> mainMenuPlayerPage;

    public MenuListener(DelicateGuild instance) {
        this.instance = instance;
        menuConfiguration = instance.getConfigManager().getByConfigName("main_menu").getConfiguration();

        mainMenuPlayerPage = new HashMap<>();
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onMenuClick(MenuClickEvent event){
        Player player = event.getPlayer();
        Menu menu = event.getMenu();
        if(menu.getId().equals("mainmenu")){
            if(event.getButton().getId().equals("fixed_next_button")){
                mainMenuPlayerPage.put(player, mainMenuPlayerPage.get(player) + 1);
            }else if(event.getButton().getId().equals("fixed_pre_button")){
                mainMenuPlayerPage.put(player, mainMenuPlayerPage.get(player) - 1);
            }
            menu.show(player, true);
            player.updateInventory();
        }

    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onMenuOpen(MenuOpenEvent event){
        Menu menu = event.getMenu();
        Player player = event.getPlayer();
        if(!event.isKeepPage()) mainMenuPlayerPage.put(player, 0);
        switch (menu.getId()){
            case "mainmenu":
               event.setMenu(rebuildMainMenu(menu, player, mainMenuPlayerPage.get(player)));
               break;
            default:
        }
    }

    private Menu rebuildMainMenu(Menu menu, Player player, int page){
        List<Integer> pos = menuConfiguration.getIntegerList("fixed_button_guild.pos");
        List<String> info = menuConfiguration.getStringList("fixed_button_guild.description");
        String title = menuConfiguration.getString("fixed_button_guild.title", "error");
        Material icon = Material.valueOf(menuConfiguration.getString("fixed_button_guild.default_icon",
                "STONE"));
        String nexButtonTitle = StringUtils.Color(DelicateGuildHolder.setPlaceholders(player,
                menuConfiguration.getString("fixed_next_button.title", "error")));
        String preButtonTitle = StringUtils.Color(DelicateGuildHolder.setPlaceholders(player,
                menuConfiguration.getString("fixed_pre_button.title", "error")));
        Button nextButton = new InventoryButton(nexButtonTitle, "fixed_next_button");
        Button preButton = new InventoryButton(preButtonTitle, "fixed_pre_button");
        nextButton.setIcon(Material.valueOf(menuConfiguration.getString("fixed_next_button.default_icon",
                "STONE")));
        preButton.setIcon(Material.valueOf(menuConfiguration.getString("fixed_pre_button.default_icon",
                "STONE")));
        menu.addButton(menuConfiguration.getInt("fixed_pre_button.pos",18), preButton);
        List<Guild> sortedGuild = instance.getGuildManager().getReorderedPage(pos.size(), page);
        menu.addButton(menuConfiguration.getInt("fixed_next_button.pos",26), nextButton);

        Map<Integer, Button> buttonToAdd = new HashMap<>();
        for(int i = 0; i < sortedGuild.size(); i++){
            Button button = new InventoryButton();
            List<String> translated = new ArrayList<>();
            for(String s:info){
                translated.add(StringUtils.Color(
                        DelicateGuildHolder.setPlaceholders(sortedGuild.get(i),
                                DelicateGuildHolder.setPlaceholders(player, s)
                        )
                ));
            }
            button.setIcon(icon);
            button.setDescriptions(translated);
            button.setTitle(DelicateGuildHolder.setPlaceholders(sortedGuild.get(i), title));
            buttonToAdd.put(pos.get(i), button);
        }
        for(Integer index: buttonToAdd.keySet()){
            menu.addButton(index, buttonToAdd.get(index));
        }
        if(page == instance.getGuildManager().getReorderedPageSize(pos.size()) - 1){
            for(Integer index:pos){
                if(!buttonToAdd.containsKey(index)) menu.removeButton(index);
            }
        }
        if(page == 0) menu.removeButton(menuConfiguration.getInt("fixed_pre_button.pos",18));
        if(page == instance.getGuildManager().getReorderedPageSize(pos.size()) - 1)
            menu.removeButton(menuConfiguration.getInt("fixed_next_button.pos",26));

        return menu;
    }


}
