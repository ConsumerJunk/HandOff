package net.consumerjunk.handoff;

import net.consumerjunk.handoff.signs.BuySign;
import net.consumerjunk.handoff.signs.Shop;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class ShopGUIHandler implements Listener {

	private ArrayList<HumanEntity> shoppingPlayers = new ArrayList<>();
	private ArrayList<HumanEntity> confirmingPlayers = new ArrayList<>();
	private HashMap<HumanEntity, Shop> openShops = new HashMap<>();

	public void openShop(Player player, Shop shop) {

		if(shop instanceof BuySign) {
			openShop(player, (BuySign) shop);
		}

	}

	private void openShop(Player player, BuySign buy) {

		shoppingPlayers.add(player);
		openShops.put(player, buy);

		Inventory shoppingGui;
		String header = buy.isTrade ? ("Trade " + ChatColor.DARK_PURPLE + buy.priceItem.getType().name() + " x" + buy.priceItem.getAmount() + ChatColor.RESET + " for:") : (header = "R" + ChatColor.DARK_PURPLE + buy.priceMoney + ChatColor.RESET + " for:");
		shoppingGui = Bukkit.createInventory(null, buy.isDouble ? 54 : 27, header);

		// Populate shopping GUI
		Chest leftChest = (Chest) buy.getChestLocationLeft().getBlock().getState();
		Chest rightChest = (Chest) buy.getChestLocationRight().getBlock().getState();
		for (ItemStack content : leftChest.getBlockInventory().getContents()) {
			if(content != null)
				shoppingGui.addItem(content.clone());
		}
		if(buy.isChestDouble()) {
			for (ItemStack content : rightChest.getBlockInventory().getContents()) {
				if(content != null)
					shoppingGui.addItem(content.clone());
			}
		}

		// Remove item from chest to prevent double purchase

		player.openInventory(shoppingGui);

	}

	private void openBuyConfirm(Player player, ItemStack confirmItem) {

		BuySign buy = (BuySign) openShops.get(player);

		Inventory confirmingGui;
		String header = buy.isTrade ? "Trade for " + ChatColor.DARK_GREEN + buy.priceItem.getType().name() + "x" + buy.priceItem.getAmount() + ChatColor.RESET + "?" : "Buy for " + ChatColor.DARK_GREEN + "r" + buy.priceMoney + ChatColor.RESET + "?";
		confirmingGui = Bukkit.createInventory(null, 9, header);

		// Populate confirming GUI
		confirmingGui.setItem(6, confirmItem.clone());

	}

	@Deprecated
	@EventHandler
	public void onInventoryClickEvent(InventoryClickEvent e) {

		Player player = (Player) e.getWhoClicked();

		if(shoppingPlayers.contains(player) || confirmingPlayers.contains(player)) {
			e.setCancelled(true);
		} else {
			return;
		}

		// Check if player is interacting with their own inventory
		if(e.getClickedInventory().equals(player.getInventory())) {
			return;
		}

		// Check if player is dropping the item
		if(e.getClick() == ClickType.DROP) {
			return;
		}

		if(shoppingPlayers.contains(player)) {
			shoppingPlayers.remove(player);
			confirmingPlayers.add(player);
			switch (openShops.get(player).getType()) {
				case BuySign.TYPE:
					openBuyConfirm(player, e.getCurrentItem());
			}
		}

	}

	@EventHandler
	public void onInventoryCloseEvent(InventoryCloseEvent e) {

		Player player = (Player) e.getPlayer();

		// Player is not on confirm GUI
		if(shoppingPlayers.contains(player)) {
			shoppingPlayers.remove(player);
			openShops.remove(player);
			Main.messagePlayer(player, "Transaction canceled", true);
		}

		if(confirmingPlayers.contains(player)) {
			confirmingPlayers.remove(player);

		}

	}

}

/*
public ArrayList<Player> shoppingPlayers = new ArrayList<>();
	private HashMap<Player, Shop> openShops = new HashMap<>();
	public ArrayList<Player> confirmingPlayers = new ArrayList<>();
	public HashMap<Player, Location> cancelTo = new HashMap<>();
	public HashMap<Player, ItemStack> confirmItems = new HashMap<>();

	public void openShop(Player player, Shop shop) {
		openShops.put(player, shop);
		if(shop instanceof BuySign) {
			openBuy(player, shop);
		}
	}

	private void openBuy(Player player, Shop shop) {

		shoppingPlayers.add(player);
		cancelTo.put(player, shop.getChestLocationLeft());

		Inventory gui;
		BuySign sign = (BuySign) shop;

		String header;
		if(sign.isTrade) {
			header = "Trade " + ChatColor.AQUA + sign.priceItem.getType().name() + " x" + sign.priceItem.getAmount() + ChatColor.RESET + " for:";
		} else {
			header = "R" + ChatColor.AQUA + sign.priceMoney + ChatColor.RESET + " for:";
		}
		gui = Bukkit.createInventory(null, (sign.isDouble? 54 : 27), header);

		for(ItemStack item : ((Chest)sign.chestLeft.getBlock().getState()).getBlockInventory()) {
			if(item != null)
				gui.addItem(item.clone());
		}

		shoppingPlayers.add(player);
		openShops.put(player, shop);
		player.openInventory(gui);

	}

	private void openBuyConfirmation(Player player, ItemStack confirmItem) {

		BuySign sign = (BuySign)openShops.get(player);

		String header;
		if(sign.isTrade) {
			header = "Buy for " + ChatColor.AQUA + sign.priceItem.getType().name() + " x" + sign.priceItem.getAmount() + ChatColor.RESET + "?";
		} else {
			header = "Buy for R" + ChatColor.AQUA + sign.priceMoney + ChatColor.RESET + "?";
		}

		Inventory confirmGui = Bukkit.createInventory(null, 9, ChatColor.AQUA + "Confirm?");

		ItemStack cancel = new ItemStack(Material.RED_CONCRETE, 1);
		ItemMeta cancelMeta = cancel.getItemMeta();
		cancelMeta.setDisplayName(ChatColor.RED + "Cancel");
		cancelMeta.setLore(Arrays.asList("Cancel Transaction"));
		cancel.setItemMeta(cancelMeta);

		ItemStack confirm = new ItemStack(Material.GREEN_CONCRETE, 1);
		ItemMeta confirmMeta = confirm.getItemMeta();
		confirmMeta.setDisplayName(ChatColor.GREEN + "Confirm");
		confirmMeta.setLore(Arrays.asList("Confirm Transaction"));
		confirm.setItemMeta(confirmMeta);

		confirmGui.setItem(2, cancel);
		confirmGui.setItem(4, confirmItem);
		confirmGui.setItem(6, confirm);

		player.openInventory(confirmGui);

	}

	@Deprecated
	@EventHandler
	public void onInventoryClickEvent(InventoryClickEvent e) {
		Player player = (Player) e.getWhoClicked();

		System.out.println("SHOPPING: " + shoppingPlayers.contains(player));
		System.out.println("CONFIRMINGmc: " + confirmingPlayers.contains(player));

		if(confirmingPlayers.contains(player)) {
			System.out.println("CONFIRMING");
			System.out.println(e.getCurrentItem().getType().name());
			if(e.getCurrentItem().getItemMeta().getLore().contains("Cancel Transaction") && e.getCurrentItem().getType() == Material.RED_TERRACOTTA) {
				e.setCancelled(true);
				Chest chest = ((Chest)cancelTo.get(player).getBlock().getState());
				chest.getBlockInventory().addItem(confirmItems.get(player));
				confirmingPlayers.remove(player);
				confirmItems.remove(player);
				cancelTo.remove(player);
				openShops.remove(player);
				shoppingPlayers.remove(player);
				player.closeInventory();
				Main.messagePlayer(player, "Canceled.", true);
			} else if(e.getCurrentItem().getItemMeta().getLore().contains("Confirm Transaction") && e.getCurrentItem().getType() == Material.GREEN_TERRACOTTA) {
				e.setCancelled(true);
				Chest chest = ((Chest)cancelTo.get(player).getBlock().getState());
				player.getInventory().addItem(confirmItems.get(player));
				confirmingPlayers.remove(player);
				confirmItems.remove(player);
				cancelTo.remove(player);
				openShops.remove(player);
				shoppingPlayers.remove(player);
				player.closeInventory();
				Main.messagePlayer(player, "Success.", true);
			} else {
				e.setCancelled(true);
			}
		} else if(shoppingPlayers.contains(player)) {

			if(e.getClick() == ClickType.DROP) {
				e.setCancelled(true);
				return;
			}

			if(e.getClickedInventory().equals(player.getInventory())) {
				e.setCancelled(true);
				return;
			}

			ItemStack confirmItem = e.getCurrentItem();
			Chest chest = (Chest)openShops.get(player).getChestLocationLeft().getBlock().getState();

			if(confirmItem != null) {
				e.setCancelled(true);
				player.closeInventory();
				openBuyConfirmation(player, confirmItem.clone());
				chest.getBlockInventory().removeItem(confirmItem);
				shoppingPlayers.remove(player);
				confirmItems.put(player, confirmItem);
				confirmingPlayers.add(player);
			}

			Bukkit.getScheduler().runTask(Main.instance, () -> { player.updateInventory(); });

		}
	}

	@Deprecated
	@EventHandler
	public void onInventoryClose(InventoryCloseEvent e) {

		HumanEntity player = e.getPlayer();

		System.out.println("SHOPPING: " + shoppingPlayers.contains(player));
		System.out.println("CONFIRMINGmc: " + confirmingPlayers.contains(player));

		if(shoppingPlayers.contains(player)) {
			if(confirmingPlayers.contains(player)) {
				cancelTo.remove(player);
				openShops.remove(player);
				shoppingPlayers.remove(player);
				confirmingPlayers.remove(player);
				confirmItems.remove(player);
			}
		}
	}
 */
