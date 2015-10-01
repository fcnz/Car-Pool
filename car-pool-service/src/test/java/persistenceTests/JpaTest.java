package persistenceTests;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Test superclass with useful pre/post test processing behaviour.
 *
 * @author Ian Warren
 *
 */
public abstract class JpaTest {

	private static Logger _logger = LoggerFactory.getLogger(JpaTest.class);

	// JDBC connection to the database.
	private static Connection _jdbcConnection = null;

	// JPA EntityManagerFactory, used to create an EntityManager.
	private static EntityManagerFactory _factory = null;

	// JPA EntityManager, which provides transactional and persistence
	// operations.
	protected EntityManager _entityManager = null;

	/**
	 * One-time setup method for all test cases.
	 *
	 * Initialises the database by dropping any existing tables prior to
	 * creating a JPA EntityManagerFactory. When the JPA EntityManagerFactory
	 * is created, it extracts metadata from domain model classes and creates
	 * the necessary database tables.
	 *
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	@BeforeClass
	public static void initialiseDatabase() throws ClassNotFoundException,
			SQLException {

		// Load H2 database driver.
		Class.forName("org.h2.Driver");

		// Open a connection to the database. This is used solely to delete rows
		// from any database tables and to drop the tables.
		_jdbcConnection = DriverManager.getConnection(
				"jdbc:h2:~/test;mv_store=false", "sa", "sa");

		// Drop any existing tables.
		clearDatabase(true);

		// Create the JPA EntityManagerFactory.
		_factory = Persistence.createEntityManagerFactory("scratchPU");
	}

	/**
	 * One-time finalisation method for all test cases. This method releases
	 * the JDBC database connection.
	 *
	 * @throws SQLException
	 */
	@AfterClass
	public static void releaseEntityManager() throws SQLException {
		_jdbcConnection.close();
	}

	/**
	 * Immediately before each test cases runs, this method runs to remove any
	 * rows in database tables. This ensures that each test begins with an
	 * empty database. In addition it creates a new entityManager for each test.
	 *
	 * @throws SQLException
	 */
	@Before
	public void clearDatabase() throws SQLException {
		// Delete all rows from any existing database tables.
		clearDatabase(false);

		// Create the JPA EntityManager.
		_entityManager = _factory.createEntityManager();
	}

	/**
	 * Immediately after each test case runs, this method releases the JPA
	 * EntityManager used by the test.
	 */
	@After
	public void closeEntityManager() {
		_entityManager.close();
	}

	/**
	 * Helper method to delete rows from existing tables and optionally drop
	 * tables from the database. This method executes regular SQL statements.
	 *
	 * @param dropTables set to true to drop existing tables, false to only
	 * clear content from the tables.
	 *
	 * @throws SQLException
	 */
	protected static void clearDatabase(boolean dropTables) throws SQLException {
		Statement s = _jdbcConnection.createStatement();
		s.execute("SET REFERENTIAL_INTEGRITY FALSE");

		Set<String> tables = new HashSet<String>();
		ResultSet rs = s.executeQuery("select table_name "
				+ "from INFORMATION_SCHEMA.tables "
				+ "where table_type='TABLE' and table_schema='PUBLIC'");

		while (rs.next()) {
			tables.add(rs.getString(1));
		}
		rs.close();
		for (String table : tables) {
			_logger.debug("Deleting content from " + table);
			s.executeUpdate("DELETE FROM " + table);
			if (dropTables) {
				s.executeUpdate("DROP TABLE " + table);
			}
		}

		s.execute("SET REFERENTIAL_INTEGRITY TRUE");
		s.close();
	}
}
