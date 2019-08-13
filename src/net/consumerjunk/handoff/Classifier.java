package net.consumerjunk.handoff;

import org.bukkit.ChatColor;
import org.bukkit.block.Sign;

public class Classifier {

	public static final int UNKNOWN = -1;

	public static final int ADMIN_TRADE = 0;
	public static final int ADMIN_BUY = 1;
	public static final int ADMIN_SELL = 2;

	public static final int TRADE = 3;
	public static final int BUY = 4;
	public static final int SELL = 5;

	public static int classify(Sign sign) {
		String firstLine = ChatColor.stripColor(sign.getLine(0)).toUpperCase();
		switch (firstLine) {
			case "[*TRADE]":
				return ADMIN_TRADE;
			case "[*BUY]":
				return ADMIN_BUY;
			case "[*SELL]":
				return ADMIN_SELL;
			case "[TRADE]":
				return TRADE;
			case "[BUY]":
				return BUY;
			case "[SELL]":
				return SELL;
		}
		return UNKNOWN;
	}

}
