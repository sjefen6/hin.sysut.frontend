package hikst.frontend.server;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpSession;

import hikst.frontend.client.DatabaseService;
import hikst.frontend.shared.Description;
import hikst.frontend.shared.LoginRequest;
import hikst.frontend.shared.Plot;
import hikst.frontend.shared.RegisterRequest;
import hikst.frontend.shared.SimObject;
import hikst.frontend.shared.SimObjectTree;
import hikst.frontend.shared.SimulationRequest;
import hikst.frontend.shared.SimulationTicket;
import hikst.frontend.shared.SimulatorObject;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class DatabaseServiceImpl extends RemoteServiceServlet implements DatabaseService
{	
	private static final int SALT_LENGTH = 20;
	private static final SecureRandom randomizer = new SecureRandom();
	
	@Override
	public List<Plot> getData(int sim_description_id) throws IllegalArgumentException {
		
		Connection connection = Settings.getDBC();
		List<Plot> plots = new ArrayList<Plot>();
		
		try
		{
			String query = "SELECT Time, Effect, Power_Consumption, Voltage, Current FROM Simulations WHERE Sim_Description_ID=?";
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, sim_description_id);
			ResultSet set = statement.executeQuery();
			
			while(set.next())
			{
				Date time = new Date(set.getLong(1));
				float effect = set.getFloat(2);
				float power_consumption = set.getFloat(3);
				float voltage = set.getFloat(4);
				float current = set.getFloat(5);
				
				plots.add(new Plot(time,effect,power_consumption,voltage,current));
			}
		}
		catch(SQLException ex)
		{
			//send out error message some way....
			ex.printStackTrace();
		}
	
		return plots;
	}

	@Override
	public SimulatorObject getSimulatorObject(int simulation_object_id)throws IllegalArgumentException
	{
		Connection connection = Settings.getDBC();
		
		try
		{		
			String query = "SELECT Name FROM Objects WHERE ID=?";
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, simulation_object_id);
			ResultSet set = statement.executeQuery();
			
			if(set.next())
			{
				String name = set.getString(1);
				return new SimulatorObject(name, new ArrayList<SimulatorObject>());
				
				//TODO: hente ut sønn-objekter også
			}
			else
			{
				throw new IllegalArgumentException();
			}
			
		}catch(SQLException ex)
		{
			ex.printStackTrace();
			
		}
	
		return null;
	}

	@Override
	public List<Integer> getSimulationDescriptionsIDs(){
		
		List<Integer> simulation_descriptions = new ArrayList<Integer>();
		
		Connection connection = Settings.getDBC();
		
		try
		{
			
			String query = "SELECT Simulation_Descriptions.ID FROM Simulation_Descriptions, Simulator_Queue_Objects WHERE Simulation_Descriptions.ID = Simulator_Queue_Objects.Simulation_Descriptions_ID and Status_ID=4";
			PreparedStatement statement = connection.prepareStatement(query);
			ResultSet set = statement.executeQuery();
			
			while(set.next())
			{
				simulation_descriptions.add(set.getInt(1));
			}	
		}
		catch(SQLException ex)
		{
			//send message back to client in some way...
			//paste in here...
			ex.printStackTrace();
			throw new IllegalArgumentException();
		}
		
		return simulation_descriptions;
	}
	
	@Override
	public Description getSimulation(int sim_description_id)
	{
		try
		{
			Connection connection = Settings.getDBC();
			
			String query = "SELECT Object_ID, minimumTime, maximumTime FROM Simulation_Descriptions WHERE ID=?";
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, sim_description_id);
			ResultSet set = statement.executeQuery();
			
			if(set.next())
			{
				int object_id = set.getInt(1);
				long minimumTime = set.getLong(2);
				long maximumTime = set.getLong(3);
				
				SimulatorObject simulator_object = getSimulatorObject(object_id);
				List<Plot> plots = getData(sim_description_id);
				
				return new Description(simulator_object,plots,minimumTime,maximumTime);
			}
			else
			{
				throw new IllegalArgumentException();
			}
			
		}catch(SQLException ex)
		{
			//send message back to server that the request failed
			ex.printStackTrace();
			throw new IllegalArgumentException();
			
		}
	}

	@Override
	public List<Description> getSimulations() throws IllegalArgumentException {
		
		List<Description> simulations = new ArrayList<Description>();
		
		List<Integer> descriptionIDs = getSimulationDescriptionsIDs();
		
		for(int i = 0; i<descriptionIDs.size(); i++)
		{
			simulations.add(this.getSimulation(descriptionIDs.get(i)));
		}
		
		return simulations;
	}

	@Override
	public boolean authenticate(LoginRequest request) {
		
		/*String username = request.getUsername();
		String password = request.getPassword();
		
		Connection connection = Settings.getDBC();
		
		try
		{
			String query = "SELECT Firstname, Lastname, Email, Password, Salt, Access_Level_ID FROM Users WHERE Username=?";
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, username);
			ResultSet set = statement.executeQuery();
			
			if(set.next())
			{
				String firstname = set.getString(1);
				String lastname = set.getString(2);
				String email = set.getString(3);
				String passwordFromDB = set.getString(4);
				String salt = set.getString(5);
				int access_level_id = set.getInt(6);
				
				String access_level = AccessLevel.getInstance().getAccessLevel(access_level_id);
				
				try
				{
					String hashedPassword = getHash(password,salt);
					
					//log-in attempt successful!!
					if(passwordFromDB.equals(hashedPassword))
					{
						HttpSession session = getSession();
						
						User user = new User(username,firstname,lastname,email,salt,access_level);
						session.setAttribute(USER_KEY, user);
						return true;
					}
					else
					{
						return false;
					}	
				}
				catch(NoSuchAlgorithmException ex)
				{
					ex.printStackTrace();
					return false;
				}
				catch(UnsupportedEncodingException ex)
				{
					ex.printStackTrace();
					return false;
				}
			}
			else
			{
				return false;
			}
			
		}
		catch(SQLException ex)
		{
			ex.printStackTrace();
			return false;
		}
		catch(AccessLevelNotFoundException ex)
		{
			ex.printStackTrace();
			return false;
		}*/
		
		return true;
	}
	
	private String getHash(String key, String salt) throws NoSuchAlgorithmException, UnsupportedEncodingException
	{
			MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
			String text = key + salt;
			messageDigest.update(text.getBytes("UTF-8"));
			byte[] hash = messageDigest.digest();
			return new String(hash);
	}
	
	private String generateSalt()
	{
		byte[] salt = new byte[SALT_LENGTH];
		randomizer.nextBytes(salt);
		return new String(salt);
	}
	
	private HttpSession getSession()
	{
		return this.getThreadLocalRequest().getSession();
	}

	@Override
	public boolean logOff() {
		
		HttpSession session = getSession();
		session.invalidate();
		return true;
	}

	@Override
	public boolean register(RegisterRequest request) 
	{	
		Connection connection = Settings.getDBC();
		
		try
		{
			String query = "INSERT INTO Users VALUES(?,?,?,?,?,?,?);";
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, request.getUsername());
			statement.setString(2, request.getFirstname());
			statement.setString(3, request.getLastname());
			statement.setString(4, request.getEmail());
			
			String salt = generateSalt();
			String hashedPassword = getHash(request.getPassword(),salt);
			statement.setString(5, hashedPassword);
			statement.setString(6,salt);
			//sets to 0 for regular user-access
			statement.setInt(7, 0);
			
			statement.executeQuery();
			
			return true;
		}
		catch(SQLException ex)
		{
			ex.printStackTrace();
			return false;
		}
		catch(NoSuchAlgorithmException ex)
		{
			ex.printStackTrace();
			return false;
		}
		catch(UnsupportedEncodingException ex)
		{
			ex.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean exists(String username) {
	
		Connection connection = Settings.getDBC();
		
		try
		{
			String query = "SELECT Username FROM Users WHERE Username=?";
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1,username);
			ResultSet set = statement.executeQuery();
			
			return set.next();
		}
		catch(SQLException ex)
		{
			ex.printStackTrace();
			return false;
		}
	}

//	private Integer saveImpactDegree(ImpactDegree impactDegree)
//	{
//		if(impactDegree == null)
//			return null;
//		
//		try
//		{
//			Connection connection = Settings.getDBC();
//			
//			String query = "INSERT INTO Impact_Degrees(Percent, Type_ID) VALUES(?,?);";
//			PreparedStatement statement = connection.prepareStatement(query);
//			statement.setDouble(1, impactDegree.getPercent());
//			statement.setInt(2, impactDegree.getTypeID());
//			
//			return statement.executeUpdate();
//		}
//		catch(SQLException ex)
//		{
//			ex.printStackTrace();
//			return null;
//		}
//	}
	
	private int saveSimObject(SimObject simObject) throws SQLException
	{
		Connection connection = Settings.getDBC();
		String query = "INSERT INTO Objects(Name,Effect,Voltage,Current,Impact_Degree_ID) VALUES(?,?,?,?,?) RETURNING ID";
		PreparedStatement statement;
		
		statement = connection.prepareStatement(query);
		
		statement.setString(1, simObject.name);
		statement.setDouble(2, simObject.effect);
		statement.setDouble(3, simObject.volt);
		statement.setDouble(4, simObject.volt);
		statement.setFloat(5, simObject.impactDegree);
		ResultSet set = statement.executeQuery();
		
		int object_id = set.getInt(1);
		
		Iterator<SimObject> children = simObject.getChildIterator();
		
		while(children.hasNext())
		{
			SimObject child = children.next();
			int child_id = child.getID();
			
			if(child.isFromDatabase())
			{
				updateSimObject(child);
			}
			else
			{
				saveSimObject(child);
			}
			
			saveChildLink(object_id,child_id);
		}
		
		return object_id;
	}
	
	private void updateSimObject(SimObject simObject) throws SQLException
	{
		Connection connection = Settings.getDBC();
		String query = "UPDATE INTO Objects(Name,Effect,Voltage,Current,Impact_Degree_ID) VALUES(?,?,?,?,?) where ID=?";
		PreparedStatement statement;
		
		statement = connection.prepareStatement(query);
		
		int objectID = simObject.getID();
		statement.setString(1, simObject.name);
		statement.setDouble(2, simObject.effect);
		statement.setDouble(3, simObject.volt);
		statement.setDouble(4, simObject.volt);
		statement.setFloat(5, simObject.impactDegree);
		statement.setInt(6, objectID);
		statement.executeUpdate();
		
		Iterator<SimObject> children = simObject.getChildIterator();
		
		while(children.hasNext())
		{
			SimObject child = children.next();
			int child_id = child.getID();
			
			if(child.isFromDatabase())
			{
				updateSimObject(child);
			}
			else
			{
				child_id = saveSimObject(child);
			}
			

			saveChildLink(objectID,child_id);
		}
	}
	
	private void saveChildLink(int object_id, int child_id)throws SQLException
	{
		Connection connection = Settings.getDBC();
		
		String anotherQuery = "INSERT INTO Part_Objects(Father_ID,Son_ID) VALUES(?,?)";
		PreparedStatement anotherStatement = connection.prepareStatement(anotherQuery);
		anotherStatement.setInt(1, object_id);
		anotherStatement.setInt(2, child_id);
		anotherStatement.executeUpdate();
	}
	
	//saves the simulator object in the database and returns the ID in the database
	@Override
	public int saveObject(SimObjectTree simulatorObject) {
		
		try
		{
			if(simulatorObject.rootObject.isFromDatabase()){
				//returns the id of the object if object saved
				return saveSimObject(simulatorObject.rootObject);
			}
			else
			{
				updateSimObject(simulatorObject.rootObject);
			}

			//returns -1 if just updating
			return -1;
		}
		catch(SQLException ex)
		{
			//returns -2 for sql-exception
			ex.printStackTrace();
			return -2;
		}
		
	}

	@Override
	public boolean updateObject(int id,SimulatorObject object) {
		
		Connection connection = Settings.getDBC();
		
		try
		{
			String query = "UPDATE Objects SET Name=?, Effect=?, Voltage=?, Current=?, Impact_Degree_ID=? WHERE ID=?";
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, object.getName());
			statement.setDouble(2, object.getEffect());
			statement.setDouble(3, object.getVoltage());
			statement.setDouble(4, object.getCurrent());
			//use impact id in the simulator-object-class instead
			//statement.setInt(5, object.get)
			statement.setInt(6, id);
			
			List<SimulatorObject> sons = object.getSons();
			
			for(int i = 0; i<sons.size(); i++)
			{
				if(updateObject(id, object));
			}
			
			statement.executeUpdate();
		}
		catch(SQLException ex)
		{
			ex.printStackTrace();
			return false;
		}
		return false;
	//TODO
		
	}

	//only delete objects that hasnt been used in a simulation!
	//will throw SQLException if it has been used in a simulation!
	@Override
	public boolean deleteObject(int object_id) {
	
		try
		{
			Connection connection = Settings.getDBC();
			
			String query = "DELETE FROM Part_Objects WHERE Father_ID=?";
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, object_id);
			statement.executeUpdate();
			
			String nextQuery = "DELETE FROM Part_Objects WHERE Son_ID=?";
			PreparedStatement nextStatement = connection.prepareStatement(nextQuery);
			nextStatement.setInt(1, object_id);
			nextStatement.executeUpdate();
			
			String anotherQuery = "DELETE FROM Objects WHERE ID=?";
			PreparedStatement anotherStatement = connection.prepareStatement(anotherQuery);
			anotherStatement.setInt(2, object_id);
			anotherStatement.executeUpdate();
			
			return true;
		}catch(SQLException ex)
		{
			ex.printStackTrace();
			return false;
		}
	}

	@Override
	public SimulationTicket requestSimulation(SimulationRequest request) {
		
		try
		{
			Connection connection = Settings.getDBC();
			
			String query = "INSERT INTO Simulation_Descriptions(Object_ID, timeIntervall,minimumTime,maximumTime) VALUES(?,?,?,?) RETURNING ID";
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, request.getSimulation_object_id());
			statement.setLong(2, request.getIntervall());
			statement.setLong(3,request.getFrom());
			statement.setLong(4, request.getTo());
			ResultSet set = statement.executeQuery();
			
			int description_id = 0;
			
			if(set.next())
			{
				description_id = set.getInt(1);
			}
			else
			{
				description_id = Integer.MAX_VALUE;
			}
			
			String anotherQuery = "INSERT INTO Simulator_Queue_Objects(Simulator_ID,Status_ID,Simulation_Descriptions_ID) VALUES(11,?,?) RETURNING ID";
			PreparedStatement anotherStatement = connection.prepareStatement(anotherQuery);
			anotherStatement.setInt(1, 2);
			anotherStatement.setInt(2, description_id);
			ResultSet anotherSet = anotherStatement.executeQuery();
			
			int queue_id = 0;
			
			if(anotherSet.next())
			{
				queue_id = anotherSet.getInt(1);
			}
			else
			{
				queue_id  = Integer.MAX_VALUE;
			}
			
			return new SimulationTicket(queue_id,true,description_id);
		}
		catch(SQLException ex)
		{
			ex.printStackTrace();
			return null;
		}
		
	}

	@Override
	public boolean deleteSimulations(int id) {
		
		try
		{
			Connection connection = Settings.getDBC();
			
			String query = "DELETE FROM Simulations WHERE Sim_Description_ID=?";
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, id);
			statement.executeUpdate();
			
			String anotherQuery = "DELETE FROM Simulation_Descriptions WHERE ID=?";
			PreparedStatement anotherStatement = connection.prepareStatement(anotherQuery);
			anotherStatement.setInt(1, id);
			anotherStatement.executeUpdate();
			
			return true;
		}
		catch(SQLException ex)
		{
			ex.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean changeAccessLevel(String username, String access_level) {
		
		try
		{
			Connection connection = Settings.getDBC();
			
			int access_level_id = AccessLevel.getInstance().getID(access_level);
			
			String query = "UPDATE Users set Access_Level_ID=? WHERE Username=?";
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, access_level_id);
			statement.setString(2, username);
			
			return true;
		}
		catch(SQLException ex)
		{
			ex.printStackTrace();
			return false;
		}
		catch(AccessLevelIDNotFoundException ex)
		{
			ex.printStackTrace();
			return true;
		}
	}

	@Override
	public int getSimulationStatus(SimulationTicket ticket) {
		
		try
		{
			Connection connection = Settings.getDBC();
			
			int queueID = ticket.getQueueID();
			System.out.println("Ticket queue id: "+ticket.getQueueID());
			System.out.println("Ticket description id: "+ticket.getDescriptionID());
			String query = "SELECT Status_ID FROM Simulator_Queue_Objects WHERE ID=?";
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, queueID);
			ResultSet set = statement.executeQuery();
			
			if(set.next())
			{
				return set.getInt(1);
			}
			else
			{
				return Integer.MAX_VALUE;
			}
		}
		catch(SQLException ex)
		{
			ex.printStackTrace();
			
			return Integer.MAX_VALUE;
		}
	}

	@Override
	public boolean setUp(String hostname, String port, String name,
			String user, String password) {
	
		return Settings.writeConfig(hostname, port,  name, user, password);
		
	}

	@Override
	public boolean settingsLoadable() {
		// TODO Auto-generated method stub
		return Settings.loadable();
	}
}
