package net.consumerjunk.handoff;

import org.bukkit.block.Chest;
import org.bukkit.inventory.DoubleChestInventory;
import org.bukkit.inventory.InventoryHolder;

public class ChestUtils {

	public static void getLeft(Chest chest) {
		InventoryHolder chestInventoryHolder = chest.getBlockInventory().getHolder();
		DoubleChestInventory doubleChestInventory = (DoubleChestInventory) chestInventoryHolder.getInventory();
		System.out.println(doubleChestInventory.getLeftSide().getLocation().getX() + ", " + doubleChestInventory.getLeftSide().getLocation().getY() + ", " + doubleChestInventory.getLeftSide().getLocation().getZ());
	}

	public static void getRight() {

	}

}
