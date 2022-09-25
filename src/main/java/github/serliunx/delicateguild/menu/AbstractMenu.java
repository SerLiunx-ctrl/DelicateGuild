package github.serliunx.delicateguild.menu;

import github.serliunx.delicateguild.allenum.GUISize;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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

    @Override
    public void create() {
        this.inventory = Bukkit.createInventory(inventoryHolder, size, title);
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
