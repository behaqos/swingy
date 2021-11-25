package model;

import java.sql.Connection;

/**
 * Data access object (DAO) is a pattern that provides an abstract interface to some type of database or other persistence mechanism.
 */
public class DAO {
	private static boolean loged = ApplicationProperties.propertyEqual("db_log", "true");
	private static String LOGIN = "login";
	private static String PASSWORD = "password";
	private static String ID = "id";

	private static String SELECT_QUERY_BY_LOGIN_AND_PASSWORD = "SELECT * FROM players WHERE login=? AND password=?";
	private static String SELECT_QUERY_BY_LOGIN = "SELECT * FROM players WHERE login=?";
	private static String SELECT_QUERY_ALL = "SELECT * FROM players";

	private static String INSERT_QUERY_START = "INSERT INTO 'players' ";
	private static String UPDATE_QUERY_START = "UPDATE players SET ";
	private static String DROP_TABLE_QUERY = "DROP TABLE if exists 'players';";

	private static String CREATE_TABLE_QUERY =
			"CREATE TABLE if not exist 'players' (" +
					"'" + ID + "' INTEGER PRIMARY KEY AUTOINCREMENT, " +
					"'" + LOGIN + "' text, " +
					"'" + PASSWORD + "' text, " +
					"'" + TYPE + "' text, " + // константы типа определены в Warrior
					"'" + LEVEL + "' INT, " +
					"'" + EXP + "' INT, " +
					"'" + HP + "' INT, " +
					"'" + ATTACK + "' INT, " +
					"'" + DEFENSE + "' INT, " +
					"'" + HIT_POINTS + "' INT, " +
					"'" + WEAPON + "' text, " +
					"'" + ARMOR + "' text, " +
					"'" + HELM + "' text);";

	private static Connection connection;
	private StringBuilder request;


}
