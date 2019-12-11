package improvedspleef;

import net.minecraft.server.v1_8_R3.ExceptionPlayerNotFound;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.entity.Player;
import com.coloredcarrot.api.sidebar.*;


public class Main extends JavaPlugin implements Listener {

  //region Variables
  public World world = Bukkit.getWorlds().get(0);
  public Location team1start = new Location(world,299,112,134, -180, 0);
  public Location team2start = new Location(world,299,112,107, 0, 0);
  public boolean gameActive = false;
  private Team team1, team2;
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
            try {
              Player other = this.getServer().getPlayer(args[1]);
              startGame(p, other);
            } catch(NullPointerException e) {
              p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cERROR: &fPlayer not found!"));
            }

          } else {p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&CERROR: &fPlayer not found (arg 1)"));}
        } else if(args[0].equalsIgnoreCase("stop")) {
          stopGame(p);
        } else {
          p.sendMessage(ChatColor.translateAlternateColorCodes('&', "Usage: \n/spleef start - Starts spleef"));
        }
      }
    }
      return false;
  }

  public void stopGame(Player sender) {
    gameActive = false;
    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aSUCCESS: &fGame stopped"));
  }

  public void startGame(Player sender, Player other) {
    gameActive = true;
    team1 = new Team(sender);
    team2 = new Team(other);
    team1.getPlayer().teleport(team1start);
    scoreboard();
    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aSUCCESS: &fGame started"));
  }

  public void scoreboard() {
    SidebarString line1 = new SidebarString(ChatColor.translateAlternateColorCodes('&', "&f====================="));
    SidebarString line2 = new SidebarString(ChatColor.translateAlternateColorCodes('&', "&c" + team1.getPlayer().getDisplayName() + ": " + team1.getPoints()));
    SidebarString line3 = new SidebarString(ChatColor.translateAlternateColorCodes('&', "&c" + team2.getPlayer().getDisplayName() + ": " + team2.getPoints()));
    
    Sidebar sidebar = new Sidebar(ChatColor.RED + "Spleef" , this, 10, line1, line2, line3);
    for (Player p : this.getServer().getOnlinePlayers()) {
      sidebar.showTo(p);
    }
  }

  @EventHandler
  public void onPlayerMove(PlayerMoveEvent e) {
    Player p = (Player) e.getPlayer();
    if(gameActive) {
      if (e.getFrom().add(0, -1, 0).getBlock().getType().equals(Material.EMERALD_BLOCK)) {
        p.setHealth(0);
      }
    }
  }
}
