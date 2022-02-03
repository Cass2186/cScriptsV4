package scripts.rsitem_services;

import java.util.Optional;

/**
 * Provides an interface for classes that can fetch RSItem pricing data
 */
public interface IRsItemPriceService
{

	Optional<Integer> getPrice(int ItemID);

	Optional<String> getName(int ItemID);

	Optional<Boolean> isMembers(int ItemID);

}
