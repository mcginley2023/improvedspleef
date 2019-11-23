package improvedspleef;

import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class main extends JavaPlugin implements Listener {
  public void onEnable() {
    getServer().getPluginManager().registerEvents(new main(), this);
    this.getCommand("hi").setExecutor(new main());
  }
  
  
  @EventHandler
  @Override
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
	  if(sender instanceof Player) {
		  Player p = (Player) sender;
		  if(cmd.getName().equalsIgnoreCase("hi")) {
			  p.sendMessage("HELLO NEW COMER");
		  }
		  
	  }
      return true;
  }
  public void onPlayerJoin(PlayerJoinEvent e) {
    e.getPlayer().sendMessage("HELLO");
  }
  
  
}

