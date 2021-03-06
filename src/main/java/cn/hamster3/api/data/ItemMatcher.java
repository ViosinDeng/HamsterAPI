package cn.hamster3.api.data;

import cn.hamster3.api.HamsterAPI;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import java.util.List;
import java.util.Objects;

@SuppressWarnings("unused")
public class ItemMatcher {
    private final int limit;
    private final int id;
    private final int subID;
    private Material type;
    private String name;
    private String keyLore;
    private List<String> lore;

    public ItemMatcher(ConfigurationSection config) {
        limit = config.getInt("limit", 10);
        String idString = config.getString("id");
        if (idString != null) {
            if (idString.contains(":")) {
                String[] args = idString.split(":");
                id = Integer.parseInt(args[0]);
                subID = Integer.parseInt(args[1]);
            } else {
                id = Integer.parseInt(idString);
                subID = -1;
            }
        } else {
            id = subID = -1;
        }
        if (config.contains("type")) {
            type = Material.valueOf(config.getString("type"));
        }
        if (config.contains("name")) {
            name = HamsterAPI.replaceColorCode(config.getString("name"));
        }
        if (config.contains("keyLore")) {
            keyLore = HamsterAPI.replaceColorCode(config.getString("keyLore"));
        }
        if (config.contains("lore")) {
            lore = HamsterAPI.replaceColorCode(config.getStringList("lore"));
        }
    }

    public int getLimit() {
        return limit;
    }

    @SuppressWarnings("deprecation")
    public boolean match(ItemStack stack) {
        if (type != null) {
            if (stack.getType() != type) {
                return false;
            }
        }
        if (id != -1) {
            if (stack.getType().getId() != id) {
                return false;
            }
            if (subID != -1) {
                MaterialData data = stack.getData();
                if (data == null) {
                    return false;
                }
                if (data.getData() != subID) {
                    return false;
                }
            }
        }
        if (name != null && !name.equals(HamsterAPI.getItemName(stack))) {
            return false;
        }
        ItemMeta meta = stack.getItemMeta();
        if (meta == null) {
            return keyLore == null && lore == null;
        }
        List<String> itemLore = meta.getLore();
        if (itemLore == null) {
            return keyLore == null && lore == null;
        }

        boolean flag = false;
        if (keyLore != null) {
            for (String s : itemLore) {
                if (s.contains(keyLore)) {
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                return false;
            }
        }
        if (lore != null) {
            if (lore.size() != itemLore.size()) {
                return false;
            }
            for (int i = 0; i < lore.size(); i++) {
                if (!lore.get(i).equals(itemLore.get(i))) {
                    return false;
                }
            }
        }
        return true;
    }

    public int getId() {
        return id;
    }

    public int getSubID() {
        return subID;
    }

    public Material getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getKeyLore() {
        return keyLore;
    }

    public List<String> getLore() {
        return lore;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o instanceof ItemStack) {
            return match((ItemStack) o);
        }
        if (o == null || getClass() != o.getClass()) return false;

        ItemMatcher that = (ItemMatcher) o;

        if (limit != that.limit) return false;
        if (id != that.id) return false;
        if (subID != that.subID) return false;
        if (type != that.type) return false;
        if (!Objects.equals(name, that.name)) return false;
        if (!Objects.equals(keyLore, that.keyLore)) return false;
        return Objects.equals(lore, that.lore);
    }

    @Override
    public int hashCode() {
        int result = limit;
        result = 31 * result + id;
        result = 31 * result + subID;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (keyLore != null ? keyLore.hashCode() : 0);
        result = 31 * result + (lore != null ? lore.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Item{" +
                "limit=" + limit +
                ", id=" + id +
                ", subID=" + subID +
                ", name='" + name + '\'' +
                ", keyLore='" + keyLore + '\'' +
                ", lore=" + lore +
                '}';
    }
}
