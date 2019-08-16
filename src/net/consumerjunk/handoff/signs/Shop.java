package net.consumerjunk.handoff.signs;

import org.bukkit.Location;

public interface Shop {
	public Location getChestLocationLeft();
	public Location getChestLocationRight();
	public Location getSignLocation();
	public int getType();
	public boolean isChestDouble();
}
