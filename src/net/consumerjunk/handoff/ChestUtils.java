package net.consumerjunk.handoff;

import org.bukkit.Location;
import org.bukkit.block.Chest;
import org.bukkit.block.DoubleChest;
import org.bukkit.inventory.DoubleChestInventory;
import org.bukkit.inventory.InventoryHolder;

public class ChestUtils {

	public static boolean isDouble(Chest chest) {
		InventoryHolder chestInventoryHolder = chest.getBlockInventory().getHolder();
		return chestInventoryHolder.getInventory() instanceof DoubleChestInventory;
	}

	public static Location getLeft(Chest chest) {
		if(!isDouble(chest)) {
			return chest.getLocation();
		}
		InventoryHolder chestInventoryHolder = chest.getBlockInventory().getHolder();
		DoubleChestInventory doubleChestInventory = (DoubleChestInventory) chestInventoryHolder.getInventory();
		return doubleChestInventory.getLeftSide().getLocation();
	}

	public static Location getRight(Chest chest) {
		if(!isDouble(chest)) {
			return chest.getLocation();
		}
		InventoryHolder chestInventoryHolder = chest.getBlockInventory().getHolder();
		DoubleChestInventory doubleChestInventory = (DoubleChestInventory) chestInventoryHolder.getInventory();
		return doubleChestInventory.getRightSide().getLocation();
	}

}
