package net.consumerjunk.handoff;

import net.consumerjunk.handoff.signs.BuySign;
import net.consumerjunk.handoff.signs.Shop;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
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

				Chest chest = (Chest)block.getState();

				if (!StorageManager.isChestLocationClear(chest.getLocation())) {
					Main.messagePlayer(player, "This chest is already used in another shop.", true);
					e.setCancelled(true);
					return;
				}

				StorageManager.savedLocations.put(player, block.getLocation());
				Main.messagePlayer(player, "Chest location saved.", true);
				e.setCancelled(true);


			} else {
				if(Classifier.isSign(block)) {

					Sign sign = (Sign)block.getState();

					if(StorageManager.savedLocations.containsKey(player)) {

						int classification = Classifier.classify(sign);

						if(!StorageManager.isSignLocationClear(block.getLocation())) {
							Main.messagePlayer(player, "This sign is already used in another shop.", true);
							e.setCancelled(true);
							return;
						}

						if(classification == Classifier.UNKNOWN) {e.setCancelled(true); return;}

						if(classification >= 0 && classification <= 2 && !player.hasPermission("handoff.adminshop")) {
							Main.messagePlayer(player, "You do not have permission to create Admin shops.", true);
							e.setCancelled(true);
							return;
						}

						Shop addSign = null;

						if(classification == Classifier.BUY) {
							addSign = new BuySign(player, block.getLocation(), StorageManager.savedLocations.get(player), false);
						}

						if(addSign != null) {
							StorageManager.shops.add(addSign);
							Main.messagePlayer(player, "Shop created.", true);
							StorageManager.savedLocations.remove(player);
							sign.setLine(0, ChatColor.WHITE + sign.getLine(0));
							sign.update();
						}

					} else {

						Main.messagePlayer(player, "You need to use redstone to save the location of a chest.", true);
						e.setCancelled(true);
						return;

					}
				}
			}
		} else {
			if(!StorageManager.isSignLocationClear(block.getLocation())) {
				Shop shop = StorageManager.getShop(block.getLocation());
				Main.sgh.openShop(player, shop);
			}
		}
	}

}
