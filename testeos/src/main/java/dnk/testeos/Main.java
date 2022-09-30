package dnk.testeos;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
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
    LOGGER.info("testeos enabled");

  }

  @Override
  public void onDisable() {
    LOGGER.info("testeos disabled");
  }

  @EventHandler
  public void onPlayerJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
    LOGGER.info(p.getDisplayName()+" ha entrado al server.");
    p.sendTitle("Bienvenido al server", p.getDisplayName(),20,100,50);
    p.playSound(p.getLocation(), Sound.ITEM_GOAT_HORN_SOUND_2, 0, 0);
  }
}
