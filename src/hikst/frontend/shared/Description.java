package hikst.frontend.shared;

import hikst.frontend.server.Settings;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SuppressWarnings("serial")
public class Description implements Serializable
{
	long startTime;
	long endTime;
	int object_id;
	int simulation_description_id;
	List<Plot> plots = new ArrayList<Plot>();
	
	public long getStartTime() {
		return startTime;
	}

	public int getObject_id() {
		return object_id;

	}

	public long getMaximumTime() {
		return endTime;
	}

	public List<Plot> getPlots() {
		return plots;
	}
	
	public Description()
	{
		
	}
	
	public Description(int simulation_description_id,List<Plot> plots, long startTime, long maximum)
	{
		this.simulation_description_id = simulation_description_id;
		this.plots = plots;
		this.startTime = startTime;
		this.endTime = maximum;
	}
	
	public double[] getEffects()
	{
		double[] effects = new double[plots.size()];
		
		for(int i = 0; i<plots.size(); i++)
		{
			effects[i] = plots.get(i).getEffectY();
		}
		
		return effects;
	}
	
	public double[] getCurrents()
	{
		double[] currents = new double[plots.size()];
		
		for(int i = 0; i<plots.size(); i++)
		{
			currents[i] = plots.get(i).getCurrentY();
		}
		
		return currents;
	}
	
	public double[] getPowerConsumptions()
	{
		double[] consumptions = new double[plots.size()];
		
		for(int i = 0; i<plots.size(); i++)
		{
			consumptions[i] = plots.get(i).getCurrentY();
		}
		
		return consumptions;
	}
	
	public double[] getVoltage()
	{
		double[] voltages = new double[plots.size()];
		
		for(int i = 0; i<plots.size(); i++)
		{
			voltages[i] = plots.get(i).getVoltageY();
		}
		
		return voltages;
	}
	
	public Date[] getTimes()
	{
		Date[] times = new Date[plots.size()];
		
		for(int i = 0; i<plots.size(); i++)
		{
			times[i] = plots.get(i).getDateX();
		}
		
		return times;
	}
}
