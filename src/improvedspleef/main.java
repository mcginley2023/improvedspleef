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
import org.bukkit.ChatColor;
import com.coloredcarrot.api.sidebar.*;


public class main extends JavaPlugin implements Listener {
  public int team1points = 0;
  public int team2points = 0;
  public void onEnable() {
    getServer().getPluginManager().registerEvents(this, this);
    this.getCommand("spleef").setExecutor(this);
  }
  
  @Override
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if(cmd.getName().equalsIgnoreCase("spleef")) {
      final Player p = (Player) sender;
      if(args[0] != null) {
        if(args[0].equalsIgnoreCase("start")) {
          scoreboard();
        } else {
          p.sendMessage(ChatColor.translateAlternateColorCodes('&', "Usage: \n/spleef start - Starts spleef"));
        }
      } else {}
    }
      return false;
  }
  public void scoreboard() {
    SidebarString line1 = new SidebarString(ChatColor.translateAlternateColorCodes('&', "&f====================="));
    SidebarString line2 = new SidebarString(ChatColor.translateAlternateColorCodes('&', "&cTeam 1: &f" + team1points));
    SidebarString line3 = new SidebarString(ChatColor.translateAlternateColorCodes('&', "&cTeam 2: &f" + team2points));
    
    Sidebar sidebar = new Sidebar(ChatColor.RED + "Spleef" , this, 10, line1, line2, line3);
    for (Player p : this.getServer().getOnlinePlayers()) {
      sidebar.showTo(p);
    }
  }
  /*
  @EventHandler
  public void onPlayerMove(PlayerMoveEvent e) {
    Player p = (Player) e.getPlayer();
    
    if(p.getInventory().getItemInHand().getType().equals(Material.DIAMOND)) {
      e.getFrom().add( 0 , -1 , 0 ).getBlock().setType(Material.DIAMOND_BLOCK);
    }
    
    if(p.getInventory().getItemInHand().getType().isBlock() && !p.getInventory().getItemInHand().getType().equals(Material.AIR)){
      Material blockType = (Material) p.getInventory().getItemInHand().getType();
      e.getFrom().add(0,-1,0).getBlock().setType(blockType);
    }
  }
  */
}
