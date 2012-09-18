package hikst.frontend.shared;

import java.io.Serializable;

@SuppressWarnings("serial")
public class SimulationTicket implements Serializable
{
	private int queue_id;
	private boolean inQueue;
	private int description_id;
	
	public SimulationTicket(){
		
	}
	
	public int getQueueID()
	{
		return queue_id;
	}
	
	public boolean isInQueue()
	{
		return inQueue;
	}
	
	public int getDescriptionID()
	{
		return description_id;
	} 
	
	public SimulationTicket(int queue_id, boolean inQueue, int description_id)
	{
		this.queue_id = queue_id;
		this.inQueue = inQueue;
		this.description_id = description_id;
	}
}

