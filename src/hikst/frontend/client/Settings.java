package hikst.frontend.client;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.Properties;

public class Settings {
	private static Properties configFile = new Properties();;
	private final static String FILENAME = "frontend.properties";
	private static File file = new File(FILENAME);;
	private static String db_hostname, db_port, db_db, db_user, db_pw;
	private static Connection dbc;
	
	
	public Settings() throws SettingsDotPrefNotFoundException {
		if (loadable()){
			load();
		} else {
			throw new SettingsDotPrefNotFoundException();
		}
	}
	
	public static boolean writeConfig(String hostname, String port, String db, String user, String password){
		configFile.setProperty("DB_HOSTNAME", hostname);
		configFile.setProperty("DB_PORT", port);
		configFile.setProperty("DB_DB", db);
		configFile.setProperty("DB_USER", user);
		configFile.setProperty("DB_PW", password);
		
		try {
			configFile.store(new FileWriter(file),null);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
		
	}
	
	public static boolean loadable(){
		try {
			new FileReader(file);
			return true;
		} catch (IOException e) {
			return false;
		}
	}
	
	private void load(){
		try {
			configFile.load(new FileReader(file));
			
			db_hostname = configFile.getProperty("DB_HOSTNAME");
			db_port = configFile.getProperty("DB_PORT");
			db_db = configFile.getProperty("DB_DB");
			db_user = configFile.getProperty("DB_USER");
			db_pw = configFile.getProperty("DB_PW");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * Did anyone need a database connection???
	 * This lets the application use the database connection everywhere.
	 */
	public static Connection getDBC(){
		if (openDatabaseConnection()){
			return dbc;
		}
		return null;
	}
	
	private static boolean openDatabaseConnection(){
		try {
			if (!dbc.isClosed()){
				return true;
			}
		} catch (Exception e) {
			// This is not unexpected and will always fail before the dbc is created.
			//Please just continue!
		}
		
		try {
			dbc = DriverManager.getConnection("jdbc:postgresql://" + db_hostname + ":" + db_port + "/" + db_db,db_user,db_pw);
			return !dbc.isClosed();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}
