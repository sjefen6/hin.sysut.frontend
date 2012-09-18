package hikst.frontend.server;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.google.gwt.dev.util.collect.HashMap;

public class AccessLevel 
{
	private static AccessLevel instance;
	
	public static AccessLevel getInstance()
	{
		return instance;
	}
	
	private HashMap<Integer, String> access_levels = new HashMap<Integer,String>();
	private HashMap<String, Integer> access_level_ids = new HashMap<String,Integer>();
	
	private AccessLevel()
	{
		Connection connection = DB_REMOVE_ASAP111111.getDBC();
		
		try
		{
			String query = "SELECT ID, Name From Access_Level";
			PreparedStatement statement = connection.prepareStatement(query);
			ResultSet set = statement.executeQuery();
			
			while(set.next())
			{
				int id = set.getInt(1);
				String name = set.getString(2);
				
				access_levels.put(id, name);
				access_level_ids.put(name, id);
			}
		}
		catch(SQLException ex)
		{
			ex.printStackTrace();
		}
	}
	
	public String getAccessLevel(int id)throws AccessLevelNotFoundException
	{
		if(access_levels.containsKey(id))
		{
			return access_levels.get(id);
		}
		else
		{
			throw new AccessLevelNotFoundException();
		}
	}
	
	public int getID(String accessLevel)throws AccessLevelIDNotFoundException
	{
		if(access_level_ids.containsKey(accessLevel)){
		return access_level_ids.get(accessLevel);
		}
		else
		{
			throw new AccessLevelIDNotFoundException();
		}
	}
}
