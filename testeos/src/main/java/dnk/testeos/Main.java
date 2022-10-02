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

  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    Player p = (Player) sender;
    if (command.getName().equalsIgnoreCase("testeo")) {
      if (sender.hasPermission("test.testeo")) {
        if (p.getHealth() > 1) {
          p.chat("Me muero");
          p.damage(1);
          return true;
        } else {
          p.chat("Casi me muero");
          p.damage(0);
          p.playEffect(EntityEffect.TOTEM_RESURRECT);
          p.setHealth(p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
          return true;
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
          sender.sendMessage("Vida máxima por defecto: " + p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
          return true;
        }
        if (args.length == 1) {
          p.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(Double.parseDouble(args[0]));
          sender.sendMessage("Vida máxima modificada: " + p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
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
    if (command.getName().equalsIgnoreCase("extrahp")) {
      if (sender.hasPermission("test.extrahp")) {
        if (args.length == 0) {
          p.setAbsorptionAmount(2);
          sender.sendMessage("Obtienes vida extra: " + p.getAbsorptionAmount());
          return true;
        }
        if (args.length == 1) {
          p.setAbsorptionAmount(Double.parseDouble(args[0]));
          sender.sendMessage("Obtienes vida extra: " + p.getAbsorptionAmount());
          return true;
        }
        if (args.length == 2 && Bukkit.getPlayer(args[0]) != null) {
          Player o = Bukkit.getPlayer(args[0]);
          o.setAbsorptionAmount(Double.parseDouble(args[1]));
          sender.sendMessage(o.getDisplayName() + " obtiene vida extra: " + o.getAbsorptionAmount());
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
          float s = p.getWalkSpeed() * 10.0f;
          sender.sendMessage("Velocidad por defecto: " + s);
          return true;
        }
        if (args.length == 1) {
          p.setWalkSpeed(Float.parseFloat(args[0]) / 10.0f);
          float s = p.getWalkSpeed() * 10.0f;
          sender.sendMessage("Velocidad modificada: " + s);
          return true;
        }
        if (args.length == 2 && Bukkit.getPlayer(args[0]) != null) {
          Player o = Bukkit.getPlayer(args[0]);
          o.setWalkSpeed(Float.parseFloat(args[1]) / 10.0f);
          float s = o.getWalkSpeed() * 10.0f;
          sender.sendMessage("Velocidad de " + o.getDisplayName() + " modificada: " + s);
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
    if (command.getName().equalsIgnoreCase("fspeed")) {
      if (sender.hasPermission("test.fspeed")) {
        if (args.length == 0) {
          p.setFlySpeed(0.1f);
          sender.sendMessage("Velocidad de vuelo por defecto: " + p.getFlySpeed());
          return true;
        }
        if (args.length == 1 && Float.parseFloat(args[0]) >= 0.0f && Float.parseFloat(args[0]) <= 1.0f) {
          p.setFlySpeed(Float.parseFloat(args[0]));
          sender.sendMessage("Velocidad de vuelo modificada: " + p.getFlySpeed());
          return true;
        }
        if (args.length == 2 && Bukkit.getPlayer(args[0]) != null && Float.parseFloat(args[1]) >= 0.0f
            && Float.parseFloat(args[1]) <= 1.0f) {
          Player o = Bukkit.getPlayer(args[0]);
          o.setFlySpeed(Float.parseFloat(args[1]));
          sender.sendMessage("Velocidad de vuelo de " + o.getDisplayName() + " modificada: " + p.getFlySpeed());
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
    if (command.getName().equalsIgnoreCase("atk")) {
      if (sender.hasPermission("test.atk")) {
        if (args.length == 0) {
          p.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(1.0);
          sender
              .sendMessage("Daño de ataque por defecto: " + p.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).getValue());
          return true;
        }
        if (args.length == 1) {
          p.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(Double.parseDouble(args[0]));
          sender
              .sendMessage("Daño de ataque modificado: " + p.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).getValue());
          return true;
        }
        if (args.length == 2 && Bukkit.getPlayer(args[0]) != null) {
          Player o = Bukkit.getPlayer(args[0]);
          o.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(Double.parseDouble(args[1]));
          sender.sendMessage("El daño de ataque de " + o.getDisplayName() + " ha sido modificado: "
              + p.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).getValue());
          return true;
        } else {
          sender.sendMessage("§4Has introducido mal los parámetros del comando.");
        }
        return true;
      } else {
        sender.sendMessage("§4You don't have permission to use this command.");
      }
      return true;
    } /*
       * if (command.getName().equalsIgnoreCase("knb")) {
       * if (sender.hasPermission("test.knb")) {
       * if (args.length == 0) {
       * p.getAttribute(Attribute.GENERIC_ATTACK_KNOCKBACK).setBaseValue(1.0);
       * sender.sendMessage(
       * "Daño de retroceso por defecto: " +
       * p.getAttribute(Attribute.GENERIC_ATTACK_KNOCKBACK).getValue());
       * return true;
       * }
       * if (args.length == 1) {
       * p.getAttribute(Attribute.GENERIC_ATTACK_KNOCKBACK).setBaseValue(Double.
       * parseDouble(args[0]));
       * sender.sendMessage(
       * "Daño de retroceso modificado: " +
       * p.getAttribute(Attribute.GENERIC_ATTACK_KNOCKBACK).getValue());
       * return true;
       * }
       * if (args.length == 2 && Bukkit.getPlayer(args[0]) != null) {
       * Player o = Bukkit.getPlayer(args[0]);
       * o.getAttribute(Attribute.GENERIC_ATTACK_KNOCKBACK).setBaseValue(Double.
       * parseDouble(args[1]));
       * sender.sendMessage("El daño de retroceso de " + o.getDisplayName() +
       * " ha sido modificado: "
       * + p.getAttribute(Attribute.GENERIC_ATTACK_KNOCKBACK).getValue());
       * return true;
       * } else {
       * sender.sendMessage("§4Has introducido mal los parámetros del comando.");
       * }
       * return true;
       * } else {
       * sender.sendMessage("§4You don't have permission to use this command.");
       * }
       * return true;
       * }
       */
    if (command.getName().equalsIgnoreCase("spa")) {
      if (sender.hasPermission("test.spa")) {
        if (args.length == 0) {
          p.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(4.0);
          sender.sendMessage(
              "Velocidad de ataque por defecto: " + p.getAttribute(Attribute.GENERIC_ATTACK_SPEED).getValue());
          return true;
        }
        if (args.length == 1) {
          p.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(Double.parseDouble(args[0]));
          sender.sendMessage(
              "Velocidad de ataque modificada: " + p.getAttribute(Attribute.GENERIC_ATTACK_SPEED).getValue());
          return true;
        }
        if (args.length == 2 && Bukkit.getPlayer(args[0]) != null) {
          Player o = Bukkit.getPlayer(args[0]);
          o.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(Double.parseDouble(args[1]));
          sender.sendMessage("La velocidad de ataque de " + o.getDisplayName() + " ha sido modificada: "
              + p.getAttribute(Attribute.GENERIC_ATTACK_SPEED).getValue());
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
    if (command.getName().equalsIgnoreCase("fly")) {
      if (sender.hasPermission("test.fly")) {
        if (args.length == 0 && p.getAllowFlight() == true) {
          p.setAllowFlight(false);
          sender.sendMessage("Vuelo desactivado");
          return true;
        }
        if (args.length == 0 && p.getAllowFlight() == false) {
          p.setAllowFlight(true);
          sender.sendMessage("Vuelo activado");
          return true;
        }
        if (args.length == 1 && args[0].equals("1") || args.length == 1 && args[0].equals("true")) {
          p.setAllowFlight(true);
          sender.sendMessage("Vuelo activado");
          return true;
        }
        if (args.length == 1 && args[0].equals("0") || args.length == 1 && args[0].equals("false")) {
          p.setAllowFlight(false);
          sender.sendMessage("Vuelo desactivado");
          return true;
        }
        if (args.length == 2 && Bukkit.getPlayer(args[0]) != null) {
          Player o = Bukkit.getPlayer(args[0]);
          if (args[1].equals("1") || args[1].equals("true")) {
            o.setAllowFlight(true);
            sender.sendMessage("Vuelo activado para " + o.getDisplayName());
            return true;
          }
          if (args[1].equals("0") || args[1].equals("false")) {
            o.setAllowFlight(false);
            sender.sendMessage("Vuelo desactivado para " + o.getDisplayName());
            return true;
          }
        } else {
          sender.sendMessage("§4Has introducido mal los parámetros del comando.");
        }
        return true;
      } else {
        sender.sendMessage("§4You don't have permission to use this command.");
      }
      return true;
    }
    if (command.getName().equalsIgnoreCase("hide")) {
      if (sender.hasPermission("test.hide")) {
        if (args.length == 0) {
          for (Player ps : Bukkit.getOnlinePlayers()) {
            ps.hidePlayer(this, p);
          }
          sender.sendMessage("Invisibilidad activada");
          return true;
        }
        if (args.length == 1 && Bukkit.getPlayer(args[0]) != null) {
          Player o = Bukkit.getPlayer(args[0]);
          for (Player ps : Bukkit.getOnlinePlayers()) {
            ps.hidePlayer(this, o);
          }
          sender.sendMessage("Invisibilidad activada para " + o.getDisplayName());
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
    if (command.getName().equalsIgnoreCase("unhide")) {
      if (sender.hasPermission("test.unhide")) {
        if (args.length == 0) {
          for (Player ps : Bukkit.getOnlinePlayers()) {
            ps.showPlayer(this, p);
          }
          sender.sendMessage("Invisibilidad desactivada");
          return true;
        }
        if (args.length == 1 && Bukkit.getPlayer(args[0]) != null) {
          Player o = Bukkit.getPlayer(args[0]);
          for (Player ps : Bukkit.getOnlinePlayers()) {
            ps.showPlayer(this, o);
          }
          sender.sendMessage("Invisibilidad desactivada para " + o.getDisplayName());
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
    if (command.getName().equalsIgnoreCase("invisible")) {
      if (sender.hasPermission("test.invisible")) {
        if (args.length == 0 && p.isInvisible() == true) {
          p.setInvisible(false);
          sender.sendMessage("Supuesta invisibilidad desactivada");
          return true;
        }
        if (args.length == 0 && p.isInvisible() == false) {
          p.setInvisible(true);
          sender.sendMessage("Supuesta invisibilidad activada");
          return true;
        }
        if (args.length == 1 && args[0].equals("1") || args.length == 1 && args[0].equals("true")) {
          p.setInvisible(true);
          sender.sendMessage("Supuesta invisibilidad activada");
          return true;
        }
        if (args.length == 1 && args[0].equals("0") || args.length == 1 && args[0].equals("false")) {
          p.setInvisible(false);
          sender.sendMessage("Supuesta invisibilidad desactivada");
          return true;
        }
        if (args.length == 2 && Bukkit.getPlayer(args[0]) != null) {
          Player o = Bukkit.getPlayer(args[0]);
          if (args[1].equals("1") || args[1].equals("true")) {
            o.setInvisible(true);
            sender.sendMessage("Supuesta invisibilidad activada para " + o.getDisplayName());
            return true;
          }
          if (args[1].equals("0") || args[1].equals("false")) {
            o.setInvisible(false);
            sender.sendMessage("Supuesta invisibilidad desactivada para " + o.getDisplayName());
            return true;
          }
        } else {
          sender.sendMessage("§4Has introducido mal los parámetros del comando.");
        }
        return true;
      } else {
        sender.sendMessage("§4You don't have permission to use this command.");
      }
      return true;
    }
    if (command.getName().equalsIgnoreCase("nick")) {
      if (sender.hasPermission("test.nick")) {
        if (args.length == 0) {
          p.setDisplayName(p.getName());
          p.setPlayerListName(p.getName());
          sender.sendMessage("Nick cambiado a " + p.getDisplayName());
          return true;
        }
        if (args.length == 1 && Bukkit.getPlayerExact(args[0]) == null) {
          p.setDisplayName(args[0]);
          p.setPlayerListName(args[0]);
          sender.sendMessage("Nick cambiado a " + p.getDisplayName());
          return true;
        }
        if (args.length == 2 && Bukkit.getPlayer(args[0]) != null && Bukkit.getPlayerExact(args[1]) != null) {
          Player o = Bukkit.getPlayer(args[0]);
          o.setDisplayName(args[1]);
          o.setPlayerListName(args[1]);
          sender.sendMessage("Nick de " + o.getName() + " cambiado a " + o.getDisplayName());
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
    if (command.getName().equalsIgnoreCase("aire")) {
      if (sender.hasPermission("test.aire")) {
        if (args.length == 0) {
          p.setMaximumAir(300);
          sender.sendMessage("Aguante de respiración modificado a " + p.getMaximumAir() / 20 + " segundos.");
          if (p.getRemainingAir() > p.getMaximumAir()) {
            p.setRemainingAir(p.getMaximumAir());
          }
          return true;
        }
        if (args.length == 1) {
          p.setMaximumAir(Integer.parseInt(args[0]) * 20);
          sender.sendMessage("Aguante de respiración modificado a " + p.getMaximumAir() / 20 + " segundos.");
          if (p.getRemainingAir() > p.getMaximumAir()) {
            p.setRemainingAir(p.getMaximumAir());
          }
          return true;
        }
        if (args.length == 2 && Bukkit.getPlayer(args[0]) != null) {
          Player o = Bukkit.getPlayer(args[0]);
          o.setMaximumAir(Integer.parseInt(args[1]) * 20);
          sender.sendMessage("Aguante de respiración modificado a " + o.getMaximumAir() / 20 + " segundos.");
          if (o.getRemainingAir() > o.getMaximumAir()) {
            o.setRemainingAir(o.getMaximumAir());
          }
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
    return false;
  }
}
