package net.consumerjunk.handoff;

import net.consumerjunk.handoff.signs.Shop;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;

public class StorageManager {

	public static HashMap<Player, Location> savedLocations = new HashMap<>();
	public static ArrayList<Shop> shops = new ArrayList<>();

	public static void loadShops() {

	}

	public static void saveShops() {

	}

	public static Shop getShop(Location l) {
		for (Shop shop : shops) {
			if (areLocationsTheSame(shop.getSignLocation(), l))
				return shop;
		}
		return null;
	}

	public static boolean isChestLocationClear(Location l) {
		boolean clear = true;
		for (Shop shop : shops) {
			if(shop.isChestDouble()) {
				if (areLocationsTheSame(shop.getChestLocationLeft(), l)) {
					clear = false;
				}
				if (areLocationsTheSame(shop.getChestLocationRight(), l)) {
					clear = false;
				}
			} else {
				if (areLocationsTheSame(shop.getChestLocationLeft(), l))
					clear = false;
			}
		}
		return clear;
	}

	public static boolean isSignLocationClear(Location l) {
		boolean clear = true;
		for (Shop shop : shops) {
			if (areLocationsTheSame(shop.getSignLocation(), l))
				clear = false;
		}
		return clear;
	}

	public static boolean areLocationsTheSame(Location loc1, Location loc2) {
		boolean same = true;
		if(loc1.getBlockX() != loc2.getBlockX()) same = false;
		if(loc1.getBlockY() != loc2.getBlockY()) same = false;
		if(loc1.getBlockZ() != loc2.getBlockZ()) same = false;
		return same;
	}


}
