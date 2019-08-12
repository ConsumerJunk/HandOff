package net.consumerjunk.handoff;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteractionHandler implements Listener {

	@Deprecated
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		if(e.getAction() != Action.RIGHT_CLICK_BLOCK) return;
		Block block = e.getClickedBlock();
		Player player = e.getPlayer();
		if(player.getItemInHand().getType() == Material.REDSTONE) {
			if(block.getType() == Material.CHEST) {
				StorageManager.savedLocations.put(player, block.getLocation());
				Main.messagePlayer(player, "Chest location saved.", true);
				e.setCancelled(true);
			} else {
				Main.printToConsole(block.getType().name(), false);
				if(block.getType().name().contains("SIGN")) {
					Sign sign = (Sign)block.getState();
					if(StorageManager.savedLocations.containsKey(player)) {
						int classification = Classifier.classify(sign);
						if(classification >= 0 && classification <= 2 && !player.hasPermission("handoff.adminshop")) {
							Main.messagePlayer(player, "You do not have permission to create Admin shops.", true);
							return;
						}
						Main.messagePlayer(player, "Shop created.", true);
						StorageManager.savedLocations.remove(player);
						sign.setLine(0, ChatColor.WHITE + sign.getLine(0));
						sign.update();
					} else {
						Main.messagePlayer(player, "You need to use redstone to save the location of a chest.", true);
					}
				}
			}
		}
	}

}
