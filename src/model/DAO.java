package model;

import main.AppProperties;
import org.sqlite.JDBC;
import sun.net.www.ApplicationLaunchException;

import java.sql.*;

/**
 * Data access object (DAO) is a pattern that provides an abstract interface to some type of database or other persistence mechanism.
 */
public class DAO {
	private static boolean logSwitch = AppProperties.propertiesEqual("dataBaseLogs", "true");
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
					"'" + XP + "' INT, " +
					"'" + HEALTH + "' INT, " +
					"'" + ATTACK + "' INT, " +
					"'" + DEFENSE + "' INT, " +
					"'" + HIT_POINTS + "' INT, " +
					"'" + WEAPON + "' text, " +
					"'" + ARMOR + "' text, " +
					"'" + HELM + "' text);";

	private static Connection connection;
	private StringBuilder request; // TODO прочитать про класс StringBuilder

	/**
	 * В методе switchOnConnetction создаётся база данных.
	 * JDBC установлена зависимость установлена в pom.xml
	 * @throws SQLException
	 */

	public synchronized void switchOnConnetction() throws SQLException {
		if (connection == null) {
			DriverManager.registerDriver(new JDBC());
			connection = DriverManager.getConnection("jdbc:sqlite:" + AppProperties.getProperties("dataBasePath"));
		}
		if (logSwitch) {
			System.out.println("Data Base has connected");
		}
	}

	public DAO() throws SQLException {
		switchOnConnetction();
		if (AppProperties.propertiesEqual("profile", "test")) {
			dropHeroesTable();
			createHeroesTable();
			addHeroesInfo();
			readHeroesTable();
		}
	}

	private void createHeroesTable() {
		try(PreparedStatement statement = connection.prepareStatement(CREATE_TABLE_QUERY)) {
			statement.execute();
			if (logSwitch) {
				System.out.println("Table of heroes has created");
			}
		} catch (SQLException throwables) {
			if (logSwitch) {
				System.out.println("Excuse me. Attempt to create a table of heroes has failed.\nPlease, check configuartions of files and pom dependencies.");
			}
			throwables.printStackTrace();
		}
	}

	private void dropHeroesTable() {
		try(PreparedStatement statement = connection.prepareStatement(DROP_TABLE_QUERY)) {
			statement.execute();
			if (logSwitch) {
				System.out.println("Table of heroes has deleted.");
			}
		} catch (SQLException throwables) {
			if (logSwitch) {
				System.out.println("Excuse me. Attemp to drop the table of heroes" +
						"has failed.\nPlease, check data at the database and configurations of connection.");
			}
			throwables.printStackTrace();
		}
	}
	//FIXME добавить возможность самому внести имя игрока
	private void addHeroesInfo() {
		Hero player = HeroesFabric.createPlayer("bublik", HeroClass.Bublik);
		//TODO добавить типы игроков - бублик,  коржик, кекс
		player.setExperience(0);
		player.setLevel(5);
		createHero(player.getName(),
				"_" + player.getName + "_",
				player);

		player = HeroesFabric.createPlayer("Korzhik", HeroClass.Korzhik);
		createHero(player.getName(),
				"_" + player.getName + "_",
				player);
		player = HeroesFabric.createPlayer("Keks", HeroClass.Keks);
		createHero(player.getName(),
				"_" + player.getName + "_",
				player);
		if (logSwitch) {
			System.out.println("Player has added to database successfuly.");
		}
	}

	private void readHeroesTable() {
		if (logSwitch == false)
			return;
		try (PreparedStatement statement = connection.prepareStatement(SELECT_QUERY_ALL)) {
			// TODO Узнать что есть ResultSet
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				System.out.printf("ID is " + resultSet.getId(ID) + "");
				System.out.printf("\tlogin is " + resultSet.getLogin(LOGIN));
				System.out.printf("\tpassword = " + resultSet.getPassword(PASSWORD));
				System.out.printf("\texperience = " + resultSet.getInt(XP));
				System.out.printf("\nhealth = " + resultSet.getInt(HEALTH));
				System.out.printf("\tattack power = " + resultSet.getInt(ATTACK));
				System.out.printf("\tdefence = " + resultSet.getInt(DEFENCE));
				System.out.printf("\thelmet");
			}

		} catch (SQLException throwables) {
			System.out.println("Have fail to read database.\nPlease, check connection and file of configurations.");
			throwables.printStackTrace();
		}
	}

}
