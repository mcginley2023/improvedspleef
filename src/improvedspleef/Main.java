package improvedspleef;


import de.Herbystar.TTA.TTA_Methods;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.entity.Player;
import com.coloredcarrot.api.sidebar.*;


public class Main extends JavaPlugin implements Listener {

  //region Variables
  public World world = this.getServer().getWorld("world");
  public Location team1start = new Location(world, 299.5, 112, 134.5, -180, 0);
  public Location team2start = new Location(world, 299.5, 112, 107.5, 0, 0);
  public int gameActive = 0;
  private Team team1, team2;
  public int ticks = 1;
  public int round;
  public int max_round;
  public Location firstCorner = new Location(world, 306, 111, 106);
  public Location secondCorner = new Location(world, 292, 111, 135);
  //endregion

  public void onEnable() {
      getServer().getPluginManager().registerEvents(this, this);
      this.getCommand("spleef").setExecutor(this);
  }

  @Override
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
      if (cmd.getName().equalsIgnoreCase("spleef")) {
          final Player p = (Player) sender;
          if (args.length >= 1) {
              if (args[0].equalsIgnoreCase("start")) {
                  if (args.length == 2) {
                      try {
                          Player other = this.getServer().getPlayer(args[1]);
                          startGame(p, other);
                      } catch (NullPointerException e) {
                          p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cERROR: &fPlayer not found!"));
                      }

                  } else {
                      p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&CERROR: &fPlayer not found (arg 1)"));
                  }
              } else if (args[0].equalsIgnoreCase("stop")) {
                  stopGame(p);
              } else {
                  p.sendMessage(ChatColor.translateAlternateColorCodes('&', "Usage: \n/spleef start - Starts spleef"));
              }
          }
      }
      return false;
  }

  public void stopGame(Player sender) {
      gameActive = 0;
      sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aSUCCESS: &fGame stopped"));
  }
  public void refillSnow() {
      for(int x = firstCorner.getBlockX(); x < secondCorner.getBlockX(); x++) {
          for(int z = firstCorner.getBlockZ(); z < secondCorner.getBlockZ(); z++) {

              world.getBlockAt(x, 111, z).setType(Material.SNOW_BLOCK);
          }
      }
  }

  public void startGame(Player sender, Player other) {
      team1 = new Team(sender);
      team2 = new Team(other);
      team1.getPlayer().teleport(team1start);
      team2.getPlayer().teleport(team2start);
      round = 1;
      max_round = 5;
      gameActive = 1;
      team1.giveItem();
      team2.giveItem();
      //region Title Message
      String message = ChatColor.WHITE + "Round " + round + " starting in...";
      TTA_Methods.sendTitle(team1.getPlayer(), (ChatColor.RED + "3"), 5, 10, 5, message, 5, 15, 0);
      TTA_Methods.sendTitle(team2.getPlayer(), (ChatColor.RED + "3"), 5, 10, 5, message, 5, 15, 0);
      Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
          public void run() {
              TTA_Methods.sendTitle(team1.getPlayer(), (ChatColor.GOLD + "2"), 5, 10, 5, message, 0, 20, 0);
              TTA_Methods.sendTitle(team2.getPlayer(), (ChatColor.GOLD + "2"), 5, 10, 5, message, 0, 20, 0);
          }
      }, (ticks * 20));
      Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
          public void run() {
              TTA_Methods.sendTitle(team1.getPlayer(), (ChatColor.YELLOW + "1"), 5, 10, 5, message, 0, 20, 0);
              TTA_Methods.sendTitle(team2.getPlayer(), (ChatColor.YELLOW + "1"), 5, 10, 5, message, 0, 20, 0);
          }
      }, (ticks * 38));
      Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
          public void run() {
              TTA_Methods.sendTitle(team1.getPlayer(), (ChatColor.GREEN + "GO"), 5, 10, 5, message, 0, 20, 0);
              TTA_Methods.sendTitle(team2.getPlayer(), (ChatColor.GREEN + "GO"), 5, 10, 5, message, 0, 20, 0);
          }
      }, (ticks * 56));
      //endregion
      gameActive = 2;
      scoreboard();
  }
  public void nextRound(Player dead) {
      refillSnow();
      gameActive = 1;
      round += 1;
      team1.getPlayer().teleport(team1start);
      team2.getPlayer().teleport(team2start);
      if(team1.getPlayer() == dead.getPlayer()) {
          team2.addPoint(1);
      } else if(team2.getPlayer() == dead.getPlayer()) {
          team1.addPoint(1);
      }
      scoreboard();
      //region Title Message
      String message = ChatColor.WHITE + "Round " + round + " starting in...";
      TTA_Methods.sendTitle(team1.getPlayer(), (ChatColor.RED + "3"), 5, 10, 5, message, 5, 15, 0);
      TTA_Methods.sendTitle(team2.getPlayer(), (ChatColor.RED + "3"), 5, 10, 5, message, 5, 15, 0);
      Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
          public void run() {
              TTA_Methods.sendTitle(team1.getPlayer(), (ChatColor.GOLD + "2"), 5, 10, 5, message, 0, 20, 0);
              TTA_Methods.sendTitle(team2.getPlayer(), (ChatColor.GOLD + "2"), 5, 10, 5, message, 0, 20, 0);
          }
      }, (ticks * 20));
      Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
          public void run() {
              TTA_Methods.sendTitle(team1.getPlayer(), (ChatColor.YELLOW + "1"), 5, 10, 5, message, 0, 20, 0);
              TTA_Methods.sendTitle(team2.getPlayer(), (ChatColor.YELLOW + "1"), 5, 10, 5, message, 0, 20, 0);
          }
      }, (ticks * 38));
      Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
          public void run() {
              TTA_Methods.sendTitle(team1.getPlayer(), (ChatColor.GREEN + "GO"), 5, 10, 5, message, 0, 20, 0);
              TTA_Methods.sendTitle(team2.getPlayer(), (ChatColor.GREEN + "GO"), 5, 10, 5, message, 0, 20, 0);
          }
      }, (ticks * 56));
      //endregion
      gameActive = 2;

  }

  public void scoreboard() {
      SidebarString line1 = new SidebarString(ChatColor.translateAlternateColorCodes('&', "&f====================="));
      SidebarString line2 = new SidebarString(ChatColor.translateAlternateColorCodes('&', "&c" + team1.getPlayer().getDisplayName() + ": " + team1.getPoints()));
      SidebarString line3 = new SidebarString(ChatColor.translateAlternateColorCodes('&', "&c" + team2.getPlayer().getDisplayName() + ": " + team2.getPoints()));

      Sidebar sidebar = new Sidebar(ChatColor.RED + "Spleef", this, 10, line1, line2, line3);
      for (Player p : this.getServer().getOnlinePlayers()) {
          sidebar.showTo(p);
      }
  }

  @EventHandler
  public void onPlayerMove(PlayerMoveEvent e) {
      Player p = e.getPlayer();
      if (gameActive == 2) {
          if (e.getFrom().add(0, -1, 0).getBlock().getType().equals(Material.EMERALD_BLOCK)) {
              nextRound(p);
          }
      }
  }
  @EventHandler
  public void onPlayerDeath(PlayerDeathEvent e) {
      if(e.getEntity().getPlayer() == team1.getPlayer()) {
          team2.addPoint(1);
          scoreboard();
      } else if(e.getEntity().getPlayer() == team2.getPlayer()) {
          team1.addPoint(1);
          scoreboard();
      }
  }
}