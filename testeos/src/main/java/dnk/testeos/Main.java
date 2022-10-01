package dnk.testeos;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.player.PlayerJoinEvent;

public class Main extends JavaPlugin implements Listener {
  private static final Logger LOGGER = Logger.getLogger("testeos");

  @Override
  public void onEnable() {
    Bukkit.getServer().getPluginManager().registerEvents(this, this);
    LOGGER.info("Se desactiva el auto guardado");
    for (World w : Bukkit.getWorlds()) {
      w.setAutoSave(false);
      LOGGER.info("F " + w);
    }
  }

  @Override
  public void onDisable() {
    LOGGER.info("testeos disabled");
    LOGGER.info("Se para el mundo");
    for (World w : Bukkit.getWorlds()) {
      Bukkit.getServer().unloadWorld(w, false);
      LOGGER.info("F " + w);
    }
  }

  @EventHandler
  public void onPlayerJoin(PlayerJoinEvent e) {
    Player p = e.getPlayer();
    World w = p.getWorld();
    LOGGER.info(p.getDisplayName() + " ha entrado al server.");
    p.sendTitle("Bienvenido al server", p.getDisplayName(), 50, 300, 50);
    w.playSound(p.getLocation(), Sound.ITEM_GOAT_HORN_SOUND_2, 100, 1);
    Bukkit.getScheduler().runTaskLater(this, () -> {
      w.playSound(p.getLocation(), Sound.ITEM_GOAT_HORN_SOUND_1, 100, 1);
    }, 100L);
    Bukkit.getScheduler().runTaskLater(this, () -> {
      w.playSound(p.getLocation(), Sound.ITEM_GOAT_HORN_SOUND_5, 100, 1);
    }, 150L);
  }
}
