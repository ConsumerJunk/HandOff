package net.consumerjunk.handoff.signs;

import org.bukkit.Location;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class BuySign implements Shop {

	boolean isTrade;
	ItemStack priceItem;
	double priceMoney;
	Location sign;
	Location chestLeft;
	Location chestRight;
	UUID owner;

	public BuySign(Player owner, Location signLocation, Location chestLocation) {
		Sign sign = (Sign)signLocation.getBlock().getState();
	}

}
