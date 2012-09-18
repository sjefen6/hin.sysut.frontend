package hikst.frontend.shared;

import java.io.Serializable;

@SuppressWarnings("serial")
public class SimulationRequest implements Serializable 
{
	private int simulation_object_id;
	private long intervall;
	private long from;
	private long to;
	
	public SimulationRequest(){
		
	}
	
	public int getSimulation_object_id() {
		return simulation_object_id;
	}

	public long getIntervall() {
		return intervall;
	}

	public long getFrom() {
		return from;
	}

	public long getTo() {
		return to;
	}
	
	public SimulationRequest(int simulation_object_id, long intervall, long from, long to)
	{
		this.simulation_object_id = simulation_object_id;
		this.intervall = intervall; 
		this.from = from;
		this.to = to;
	} 
}
