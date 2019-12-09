package improvedspleef;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.entity.Player;
import org.bukkit.ChatColor;
import com.coloredcarrot.api.sidebar.*;


public class main extends JavaPlugin implements Listener {
  //region Variables
  public Player team1 = null;
  public Player team2 = null;
  public boolean gameActive = false;
  public int team1points = 0;
  public int team2points = 0;
  //endregion

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
          if(args[1] != null) {
            scoreboard();
            Player other = this.getServer().getPlayer(args[1]);
            startGame(p, other);
          } else {p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&CERROR: &fPlayer not found (arg 1)"));}
        } else if(args[0].equalsIgnoreCase("stop")) {
          stopGame(p);
        } else {
          p.sendMessage(ChatColor.translateAlternateColorCodes('&', "Usage: \n/spleef start - Starts spleef"));
        }
      } else {}
    }
      return false;
  }

  public void stopGame(Player sender) {
    gameActive = false;
    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aSUCCESS: &fGame stopped"));
  }

  public void startGame(Player sender, Player other) {
    gameActive = true;
    team1 = sender;
    team2 = other;
    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aSUCCESS: &fGame started"));
  }

  public void scoreboard() {
    SidebarString line1 = new SidebarString(ChatColor.translateAlternateColorCodes('&', "&f====================="));
    SidebarString line2 = new SidebarString(ChatColor.translateAlternateColorCodes('&', "&cTeam 1: &c" + team1points));
    SidebarString line3 = new SidebarString(ChatColor.translateAlternateColorCodes('&', "&cTeam 2: &c" + team2points));
    
    Sidebar sidebar = new Sidebar(ChatColor.RED + "Spleef" , this, 10, line1, line2, line3);
    for (Player p : this.getServer().getOnlinePlayers()) {
      sidebar.showTo(p);
    }
  }

  @EventHandler
  public void onPlayerMove(PlayerMoveEvent e) {
    Player p = (Player) e.getPlayer();
    if(gameActive == true) {
      if (e.getFrom().add(0, -1, 0).getBlock().getType().equals(Material.EMERALD_BLOCK)) {
        p.setHealth(0);
      }
    }
  }
}
