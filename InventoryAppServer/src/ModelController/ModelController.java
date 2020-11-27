package ModelController;

import Common.Message;

public abstract class ModelController {
	public abstract Message notify(Message data);
	
	public abstract Message processMessage(Message data);
}
