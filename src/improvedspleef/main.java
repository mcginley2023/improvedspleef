package improvedspleef;

import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.EventHandler;

public class main extends JavaPlugin implements Listener {
  public void onEnable() {
    getServer().getPluginManager().registerEvents(this, this);
    this.getCommand("spleef").setExecutor(this);
    
  }
  
  @Override
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if(cmd.getName().equalsIgnoreCase("spleef")) {
      Player p = (Player) sender;
      boolean place = false;
      if(args[0].equalsIgnoreCase("on")) {
        place = true;
      } else if(args[0].equalsIgnoreCase("off")) {
        place = false;
      } else {}
    }
      return false;
  }
  @EventHandler
  public void onPlayerMove(PlayerMoveEvent e) {
    Player p = (Player) e.getPlayer();
    
    /*if(p.getInventory().getItemInHand().getType().equals(Material.DIAMOND)) {
      e.getFrom().add( 0 , -1 , 0 ).getBlock().setType(Material.DIAMOND_BLOCK);
    }
    */
    if(p.getInventory().getItemInHand().getType().isBlock() && !p.getInventory().getItemInHand().getType().equals(Material.AIR)){
      Material blockType = (Material) p.getInventory().getItemInHand().getType();
      e.getFrom().add(0,-1,0).getBlock().setType(blockType);
    }
  }
}
