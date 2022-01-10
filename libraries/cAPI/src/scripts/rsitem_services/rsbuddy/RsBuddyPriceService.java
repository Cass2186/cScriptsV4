package scripts.rsitem_services.rsbuddy;

import com.google.gson.*;
import org.tribot.util.Util;

import scripts.rsitem_services.CacheablePriceService;
import scripts.rsitem_services.IRsItemPriceService;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * This Price Service gets info by downloading the entire RSBuddy Exchange API Summary and caching the result
 * in the file system and RAM.
 * <p>
 * Will work when the RSBuddy API is down as long as a cached copy of the data exists in the filesystem and is not considered "outdated"
 */

public class RsBuddyPriceService extends CacheablePriceService<RsBuddyItem> implements IRsItemPriceService
{

	private static final String URL = "https://rsbuddy.com/exchange/summary.json";
	private static final String CACHE_FILEPATH = Util.getWorkingDirectory() + File.separator + "cache"
		+ File.separator + "RsBuddyPricesCache.json";

	private final HashMap<String, Integer> itemNameIdPairs;

	public RsBuddyPriceService()
	{
		super(URL, CACHE_FILEPATH);
		this.itemNameIdPairs = new HashMap<>();
		updateCacheIfNecessary();
	}

	@Override
	protected void parseCache() throws IOException
	{
		Gson gson = new GsonBuilder().create();
		JsonObject data = JsonParser.parseReader(new FileReader(CACHE_FILEPATH)).getAsJsonObject();
		for (Map.Entry<String, JsonElement> entry : data.entrySet())
		{
			RsBuddyItem itemData = gson.fromJson(entry.getValue(), RsBuddyItem.class);
			itemNameIdPairs.put(itemData.getName(), itemData.getId());
			dataCache.put(itemData.getId(), itemData);
		}
	}

	public Optional<Integer> tryGetPrice(String itemName)
	{
		Optional<RsBuddyItem> item = tryGetItemData(itemNameIdPairs.get(itemName));
		return item.map(RsBuddyItem::getAveragePrice);
	}

	@Override
	public Optional<Integer> getPrice(int itemId)
	{
		Optional<RsBuddyItem> item = tryGetItemData(itemId);
		return item.map(RsBuddyItem::getAveragePrice);
	}

	@Override
	public Optional<String> getName(int itemId)
	{
		Optional<RsBuddyItem> item = tryGetItemData(itemId);
		return item.map(RsBuddyItem::getName);
	}

	@Override
	public Optional<Boolean> isMembers(int itemId)
	{
		Optional<RsBuddyItem> item = tryGetItemData(itemId);
		return item.map(RsBuddyItem::isMembers);
	}

}
