
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class TeamController {

	private Connection connection;

	public TeamController(Connection connection) {
		this.connection = connection;
	}

	public void showTeams() throws SQLException, IOException {

		Statement st = connection.createStatement();
		ResultSet rs;

		rs = st.executeQuery("SELECT * FROM team");
		while (rs.next()) {
			System.out.println("Name: " + rs.getString("name") + " " +
							   "Type: " + rs.getString("type") + " " +
							   "Country: " + rs.getString("country") + " " +
							   "City: " + rs.getString("city") + " " +
							   "Court name: " + rs.getString("court_name"));
		}

		rs.close();
		st.close();
	}
	public void createTeam() throws SQLException, IOException {

		Statement st = connection.createStatement();

		Scanner sc = new Scanner(System.in);

		System.out.println("Introdueix el nom de l'equip");
		String name = sc.nextLine();
		System.out.println("Introdueix el tipus d'equip");
		String type = sc.nextLine();
		System.out.println("Introdueix el país de l'equip");
		String country = sc.nextLine();
		System.out.println("Introdueix la ciutat de l'equip");
		String city = sc.nextLine();
		System.out.println("Introdueix el nom de la pista de l'equip");
		String court = sc.nextLine();
		st.executeUpdate("INSERT INTO team (name, type, country, city, court_name) VALUES('"+name+"','"+type+"','"+country+"','"+city+"','"+court+"')");


		showTeams();
		st.close();
	}




}
