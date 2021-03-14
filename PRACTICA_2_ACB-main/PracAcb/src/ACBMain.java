import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;

public class ACBMain {

	public static void main(String[] args) throws IOException, SQLException {
		ACBMenu menu = new ACBMenu();
		
		ConnectionFactory connectionFactory = ConnectionFactory.getInstance();
		Connection c = connectionFactory.connect();

		TeamController teamController = new TeamController(c);
		PlayerController playerController = new PlayerController(c);
		MatchController matchController = new MatchController(c);
		
//		Connection conn = null;
//		Identity identity;
//		int option;
//		int intents = 0;
//		DBAccessor dbaccessor = new DBAccessor();
//		dbaccessor.init();
//		while (intents < 3 && conn == null) {
//			identity = menu.authenticate(intents);
//			// prova de test
//			identity.toString();
//
//			conn = dbaccessor.getConnection(identity);
//			intents++;
//		}

		int option = menu.mainMenu();
		while (option > 0 && option < 12) {
			switch (option) {
			case 1:
				teamController.showTeams();
				// dbaccessor.mostraAutors();
				break;

			case 2:
				// dbaccessor.mostraRevistes();
				playerController.showPlayerFromTeam();
				break;

			case 3:
				teamController.createTeam();
				// dbaccessor.mostraRevistesArticlesAutors();
				break;

			case 4:
				// dbaccessor.altaAutor();
				playerController.createPlayer();
				break;

			case 5:
				matchController.createMatch();
				// dbaccessor.altaRevista();
				break;

			case 6:
				playerController.showPlayersFA();
				// dbaccessor.altaArticle();
				break;

			case 7:
				playerController.traspaso();
				// dbaccessor.actualitzarTitolRevistes(conn);
				break;

			case 8:
				playerController.rescindir();
				// dbaccessor.afegeixArticleARevista(conn);
				break;

			case 9:
				// dbaccessor.desassignaArticleARevista(conn);
				System.out.println("Opción no disponible");
				break;

			case 10:
				System.exit(0);
				break;

			default:
				System.out.println("Introdueixi una de les opcions anteriors");
				break;

			}
			option = menu.mainMenu();
		}

	}

}
