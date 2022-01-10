package scripts.rsitem_services.runelite;

import com.allatori.annotations.DoNotRename;
import lombok.Data;

@Data
@DoNotRename
public class RuneLiteItem
{

	@DoNotRename
	private int high;

	@DoNotRename
	private long highTime;

	@DoNotRename
	private int low;

	@DoNotRename
	private long lowTime;

}
