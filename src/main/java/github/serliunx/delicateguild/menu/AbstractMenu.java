package github.serliunx.delicateguild.menu;

import github.serliunx.delicateguild.allenum.GUISize;
import github.serliunx.delicateguild.placeholder.DelicateGuildHolder;
import github.serliunx.delicateguild.util.StringUtils;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public abstract class AbstractMenu implements Menu{

    private String title;
    private final String id;
    private int size;
    private Inventory inventory;
    @Nullable
    private InventoryHolder inventoryHolder;
    private final Map<Integer, Button> buttons;

    public AbstractMenu(String title, String id, GUISize size) {
        this.title = title;
        this.id = id;
        this.size = size.getValue();
        this.buttons = new HashMap<>();
    }

    public AbstractMenu() {
        this("none", UUID.randomUUID().toString(), GUISize.SIX_ROWS);
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public void setSize(GUISize size) {
        this.size = size.getValue();
    }

    @Override
    public Map<Integer, Button> getButtons() {
        return buttons;
    }

    @Override
    public void addButton(int index, Button button){
        buttons.put(index, button);
    }

    public void removeButton(int index){
        buttons.remove(index);
    }

    @Override
    public void create(Player player) {
        this.inventory = Bukkit.createInventory(inventoryHolder, size, StringUtils.Color(
                DelicateGuildHolder.setPlaceholders(player, title)
        ));

        for(Integer index:buttons.keySet()){
            List<String> descriptions = new ArrayList<>();
            for(String s:buttons.get(index).getDescriptions()){
                descriptions.add(StringUtils.Color(DelicateGuildHolder.setPlaceholders(player, s)));
            }
            buttons.get(index).setDescriptions(descriptions);
            buttons.get(index).setTitle(StringUtils.Color(DelicateGuildHolder.setPlaceholders(player,
                    buttons.get(index).getTitle())));

            this.inventory.setItem(index, buttons.get(index).getItem());
        }
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }

    @Override
    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    @Nullable
    @Override
    public InventoryHolder getInventoryHolder() {
        return inventoryHolder;
    }

    @Override
    public void setInventoryHolder(@Nullable InventoryHolder inventoryHolder) {
        this.inventoryHolder = inventoryHolder;
    }
}
