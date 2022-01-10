package scripts.rsitem_services.rsbuddy;

import com.allatori.annotations.DoNotRename;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
@DoNotRename
public class RsBuddyItem
{

	@DoNotRename
	private int id;

	@SerializedName("overall_average")
	@DoNotRename
	private int averagePrice;

	@DoNotRename
	@SerializedName("sell_average")
	private int sellAveragePrice;

	@DoNotRename
	private int sp;

	@DoNotRename
	@SerializedName("buy_average")
	private int buyAveragePrice;

	@DoNotRename
	private boolean members;

	@DoNotRename
	private String name;

}
