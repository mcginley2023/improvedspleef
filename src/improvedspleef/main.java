package improvedspleef;

import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.Material;
import org.bukkit.Location; 

public class main extends JavaPlugin implements Listener {
  public void onEnable() {
    getServer().getPluginManager().registerEvents(new main(), this);
    this.getCommand("spleef").setExecutor(new main());
  }
  
  
  @EventHandler
  @Override
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if(cmd.getName().equalsIgnoreCase("spleef")) {
      Player p = (Player) sender;
      sender.sendMessage("You typed the spleef command.");
      World world = p.getWorld();
      Location loc = new Location(world, 0, -1, 0);
      Block block = world.getBlockAt(p.getLocation());
      block.setType(Material.);
    }
    
      return false;
  }
}
