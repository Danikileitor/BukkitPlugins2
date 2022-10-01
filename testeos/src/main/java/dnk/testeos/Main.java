package dnk.testeos;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.EntityEffect;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
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

  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    Player p = (Player) sender;
    if (command.getName().equalsIgnoreCase("testeo")) {
      if (sender.hasPermission("test.testeo")) {
        if (p.getHealth() > 1) {
          p.chat("Me muero");
          p.damage(1);
        } else {
          p.chat("Casi me muero");
          p.damage(0);
          p.playEffect(EntityEffect.TOTEM_RESURRECT);
          p.setHealth(p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
        }
      } else {
        sender.sendMessage("§4You don't have permission to use this command.");
      }
      return true;
    }
    if (command.getName().equalsIgnoreCase("maxhp")) {
      if (sender.hasPermission("test.maxhp")) {
        if (args.length == 0) {
          p.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(20.0);
          sender.sendMessage("Máxima vida por defecto: " + p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
          return true;
        }
        if (args.length == 1) {
          p.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(Double.parseDouble(args[0]));
          sender.sendMessage("Máxima vida modificada: " + p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
          return true;
        }
        if (args.length == 2 && Bukkit.getPlayer(args[0]) != null) {
          Player o = Bukkit.getPlayer(args[0]);
          o.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(Double.parseDouble(args[1]));
          sender.sendMessage("La vida máxima de " + o.getDisplayName() + " ha sido modificada: "
              + p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
          return true;
        } else {
          sender.sendMessage("§4Has introducido mal los parámetros del comando.");
        }
        return true;
      } else {
        sender.sendMessage("§4You don't have permission to use this command.");
      }
      return true;
    }
    if (command.getName().equalsIgnoreCase("speed")) {
      if (sender.hasPermission("test.speed")) {
        if (args.length == 0) {
          p.setWalkSpeed(0.2f);
          float s = p.getWalkSpeed()*10.0f;
          sender.sendMessage("Velocidad por defecto: " + s);
          return true;
        }
        if (args.length == 1) {
          p.setWalkSpeed(Float.parseFloat(args[0]) / 10.0f);
          float s = p.getWalkSpeed()*10.0f;
          sender.sendMessage("Velocidad modificada: " + s);
          return true;
        }
        if (args.length == 2 && Bukkit.getPlayer(args[0]) != null) {
          Player o = Bukkit.getPlayer(args[0]);
          o.setWalkSpeed(Float.parseFloat(args[1]) / 10.0f);
          float s = o.getWalkSpeed()*10.0f;
          sender.sendMessage("Velocidad de " + o.getDisplayName() + " modificada: " + s);
        } else {
          sender.sendMessage("§4Has introducido mal los parámetros del comando.");
        }
        return true;
      } else {
        sender.sendMessage("§4You don't have permission to use this command.");
      }
      return true;
    }
    return false;
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
    }, 200L);
    Bukkit.getScheduler().runTaskLater(this, () -> {
      w.playSound(p.getLocation(), Sound.ITEM_GOAT_HORN_SOUND_5, 100, 1);
    }, 250L);
  }
}
