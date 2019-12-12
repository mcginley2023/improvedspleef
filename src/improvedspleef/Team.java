package improvedspleef;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Team {
    private int points;
    private Player player;

    public Team(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public int getPoints() {
        return points;
    }

    public void addPoint(int add) {
        this.points += add;
    }

    public void giveItem() {
        Player p = this.player;
        ItemStack item = new ItemStack(Material.DIAMOND_SPADE);
        ItemMeta meta = item.getItemMeta();
        meta.addEnchant(Enchantment.DIG_SPEED, 50, false);
        item.setItemMeta(meta);
        p.getInventory().addItem(item);
        p.updateInventory();
    }
}
