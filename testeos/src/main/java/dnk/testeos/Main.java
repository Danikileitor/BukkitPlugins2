package dnk.testeos;

import java.util.logging.Logger;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {
  private static final Logger LOGGER = Logger.getLogger("testeos");

  public void onEnable() {
    LOGGER.info("testeos enabled");
  }

  public void onDisable() {
    LOGGER.info("testeos disabled");
  }
}
