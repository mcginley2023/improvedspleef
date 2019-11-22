package improvedspleef;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class main extends JavaPlugin implements Listener {
  public void onEnable() {
    getServer().getPluginManager().registerEvents(new main(), this);
  }
  
  
  @EventHandler
  public void onPlayerJoin(PlayerJoinEvent e) {
    
  }
}
