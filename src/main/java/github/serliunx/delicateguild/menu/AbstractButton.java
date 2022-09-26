package github.serliunx.delicateguild.menu;

import github.serliunx.delicateguild.allenum.ActionType;
import github.serliunx.delicateguild.util.StringUtils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public abstract class AbstractButton implements Button {
    protected String title;
    protected List<String> descriptions;
    protected final String id;
    protected final ItemStack item;
    protected List<Map<ActionType, String>> actionTypeAndValue;
    public AbstractButton(String title, String id, List<String> descriptions, Material material,
                          List<Map<ActionType, String>> actionTypeAndValue) {
        this.title = title;
        this.descriptions = descriptions;
        this.id = id;
        this.actionTypeAndValue = actionTypeAndValue;
        item = initItem(new ItemStack(material));
    }

    public AbstractButton(String title, String id) {
        this(title, id, new ArrayList<>(), Material.STONE, new ArrayList<>());
    }

    public AbstractButton(){
        this("none", UUID.randomUUID().toString());
    }

    /**
     * 初始化物品图标
     */
    private ItemStack initItem(ItemStack itemStack){
        ItemMeta itemMeta = itemStack.getItemMeta();
        if(itemMeta != null){
            itemMeta.setLore(descriptions == null ? new ArrayList<>() : descriptions);
            itemMeta.setDisplayName(StringUtils.Color(title));
        }
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    /**
     * 更新物品图标, 执行有关物品的方法时，均会调用一次
     */
    private void updateItem(){
        ItemMeta itemMeta = item.getItemMeta();
        if(itemMeta != null){
            itemMeta.setLore(descriptions);
            itemMeta.setDisplayName(title);
        }
        item.setItemMeta(itemMeta);
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
        updateItem();
    }

    @Override
    public List<String> getDescriptions() {
        return descriptions;
    }

    @Override
    public void setDescriptions(List<String> descriptions) {
        this.descriptions = descriptions;
        updateItem();
    }

    @Override
    public void addDescriptions(String...strings){
        descriptions.addAll(Arrays.asList(strings));
        updateItem();
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public ItemStack getItem(){
        return item;
    }

    @Override
    public void setIcon(Material materialIcon){
        item.setType(materialIcon);
    }

}
