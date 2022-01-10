package scripts.rsitem_services;


import scripts.rsitem_services.rsbuddy.RsBuddyPriceService;
import scripts.rsitem_services.runelite.RuneLitePriceService;

import java.util.Optional;

/**
 * Provides a GrandExchange wrapper that includes static methods for looking up item prices and info
 * <p>
 * By default, uses the RuneLite API service primarily, and the RSBuddy and GE API as a backup
 */
public class GrandExchange extends org.tribot.api2007.GrandExchange
{

	private static final IRsItemPriceService PRICE_SERVICE = new RsItemPriceService.Builder()
		.addPriceService(new RuneLitePriceService())
//		.addPriceService(new RsBuddyPriceService())
		//.addPriceService(new GrandExchangePriceService())
		.build();

	/**
	 * Attempts to get the price of the item via the RuneLite, RSBuddy or GE API (whichever is accessible)
	 *
	 * @param itemId The item to lookup
	 * @return The current price of the item, or an empty Optional if it can't be found
	 */
	public static Optional<Integer> tryGetPrice(int itemId)
	{
		return PRICE_SERVICE.getPrice(itemId);
	}

	/**
	 * Attempts to get the name of the item via the RSBuddy API or GE API (whichever is accessible)
	 *
	 * @param itemId The item to lookup
	 * @return The name of the item, or an empty Optional if it can't be found
	 */
	public static Optional<String> tryGetName(int itemId)
	{
		return PRICE_SERVICE.getName(itemId);
	}

	/**
	 * Attempts to get the members status of the item via the RSBuddy API or GE API (whichever is accessible)
	 *
	 * @param itemId The item to lookup
	 * @return The members status of the item, or an empty Optional if it can't be found
	 */
	public static Optional<Boolean> tryGetMembers(int itemId)
	{
		return PRICE_SERVICE.isMembers(itemId);
	}

	/**
	 * Attempts to getInstance the price of the item via the RuneLite, RSBuddy or GE API (whichever is accessible)
	 *
	 * @param itemId The item to lookup
	 * @return The current price of the item, or -1 if it can't be found
	 */
	public static int getPrice(int itemId)
	{
		return PRICE_SERVICE.getPrice(itemId).orElse(-1);
	}

	/**
	 * Attempts to getInstance the name of the item via the RSBuddy API or GE API (whichever is accessible)
	 *
	 * @param itemId The item to lookup
	 * @return The name of the item, or null if it can't be found
	 */
	public static String getName(int itemId)
	{
		return PRICE_SERVICE.getName(itemId).orElse(null);
	}

	/**
	 * Attempts to getInstance the members status of the item via the RSBuddy API or GE API (whichever is accessible)
	 *
	 * @param itemId The item to lookup
	 * @return The members status of the item, or null if it can't be found
	 */
	public static Boolean isMembers(int itemId)
	{
		return PRICE_SERVICE.isMembers(itemId).orElse(null);
	}

}
