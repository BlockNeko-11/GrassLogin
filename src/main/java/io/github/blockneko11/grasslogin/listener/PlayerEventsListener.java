package io.github.blockneko11.grasslogin.listener;

import io.github.blockneko11.grasslogin.GrassLoginPlugin;
import io.github.blockneko11.grasslogin.core.LoginManager;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.*;

import java.util.List;

public class PlayerEventsListener implements Listener {
    // join & quit

    @EventHandler
    public void onPlayerJoin(AsyncPlayerPreLoginEvent e) {
        String playerName = e.getName();
        if (!playerName.matches("^\\w+$")) {
            e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, "Player whose name only includes letters, numbers and underlines can join the server.");
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        LoginManager.removeLogged(e.getPlayer().getName());
    }

    // chat & command

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e) {
        String playerName = e.getPlayer().getName();
        if (LoginManager.isLogged(playerName)) {
           return;
        }

        e.setCancelled(true);
    }

    @EventHandler
    public void onPlayerCommand(PlayerCommandPreprocessEvent e) {
        String playerName = e.getPlayer().getName();
        if (LoginManager.isLogged(playerName)) {
            return;
        }

        String command = e.getMessage().toLowerCase().split(" ")[0];
        List<String> commandWhitelist = (List<String>) GrassLoginPlugin.getPluginConfig().getList("commandWhitelist");
        if (commandWhitelist.contains(command)) {
            return;
        }

        e.setCancelled(true);
    }

    // movement

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        String playerName = e.getPlayer().getName();
        if (LoginManager.isLogged(playerName)) {
            return;
        }

        Location from = e.getFrom();
        Location to = e.getTo();

        if (from.getX() == to.getX() && from.getZ() == to.getZ() && from.getY() - to.getY() >= 0) {
            return;
        }

        e.setCancelled(true);
    }

    // interaction

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        String playerName = e.getPlayer().getName();
        if (LoginManager.isLogged(playerName)) {
            return;
        }

        e.setCancelled(true);
    }

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent e) {
        String playerName = e.getPlayer().getName();
        if (LoginManager.isLogged(playerName)) {
            return;
        }

        e.setCancelled(true);
    }

    @EventHandler
    public void onPlayerInteractAtEntity(PlayerInteractAtEntityEvent e) {
        String playerName = e.getPlayer().getName();
        if (LoginManager.isLogged(playerName)) {
            return;
        }

        e.setCancelled(true);
    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent e) {
        String playerName = e.getPlayer().getName();
        if (LoginManager.isLogged(playerName)) {
            return;
        }

        e.setCancelled(true);
    }

    @EventHandler
    public void onEntityPickupItem(EntityPickupItemEvent e) {
        LivingEntity entity = e.getEntity();
        if (!(entity instanceof Player)) {
            return;
        }

        String playerName = entity.getName();
        if (LoginManager.isLogged(playerName)) {
            return;
        }

        e.setCancelled(true);
    }

    @EventHandler
    public void onPlayerInventoryOpen(InventoryOpenEvent e) {
        HumanEntity entity = e.getPlayer();
        if (!(entity instanceof Player)) {
            return;
        }

        String playerName = entity.getName();
        if (LoginManager.isLogged(playerName)) {
            return;
        }

        e.setCancelled(true);
    }

    @EventHandler
    public void onPlayerInventoryClick(InventoryClickEvent e) {
        HumanEntity entity = e.getWhoClicked();
        if (!(entity instanceof Player)) {
            return;
        }

        String playerName = entity.getName();
        if (LoginManager.isLogged(playerName)) {
            return;
        }

        e.setCancelled(true);
    }

    @EventHandler
    public void onPlayerAttack(EntityDamageByEntityEvent e) {
        Entity entity = e.getDamager();
        if (!(entity instanceof Player)) {
            return;
        }

        String playerName = entity.getName();
        if (LoginManager.isLogged(playerName)) {
            return;
        }

        e.setCancelled(true);
    }

    @EventHandler
    public void onPlayerHurt(EntityDamageEvent e) {
        Entity entity = e.getEntity();
        if (!(entity instanceof Player)) {
            return;
        }

        String playerName = entity.getName();
        if (LoginManager.isLogged(playerName)) {
            return;
        }

        e.setCancelled(true);
    }
}
