import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class MatchController {
    private Connection connection;
    private TeamController teamController;
    private PlayerController playerController;
    public MatchController(Connection c) {
        this.connection = c;
        teamController = new TeamController(c);
        playerController = new PlayerController(c);
    }

    public void createMatch() throws SQLException, IOException {


        Scanner sc = new Scanner(System.in);
        teamController.showTeams();
        System.out.println("Introdueixi l'equip local");
        String local = sc.nextLine();
        System.out.println("Introdueixi l'equip visitant");
        String away = sc.nextLine();

        System.out.println("Introdueix la data del partit(yyyy-mm-dd)");
        java.util.Date data = java.sql.Date.valueOf(sc.nextLine());
        System.out.println("introdueixi l'assistencia al parit");
        int assistencia = sc.nextInt();
        sc.nextLine();
        playerController.showPlayerFromTeam();
        System.out.println("Introdueixi el codi de la llicencia de la federació del jugador que ha sigut el MVP ");
        String code = sc.nextLine();
        String sql = "INSERT INTO match (home_team, visitor_team, match_date, attendance, mvp_player) VALUES (?,?,?,?,?)";
        PreparedStatement pst = connection.prepareStatement(sql);
        pst.clearParameters();
        pst.setString(1,local);
        pst.setString(2,away);
        pst.setDate(3, (Date) data);
        pst.setInt(4, assistencia);
        pst.setString(5,code);
        pst.executeUpdate();
        //st.executeUpdate("INSERT INTO match (name, type, country, city, court_name) VALUES('"+name+"','"+type+"','"+country+"','"+city+"','"+court+"')");


        connection.commit();

    }
    public void insertStats() throws SQLException, NumberFormatException, IOException, ParseException {

        BufferedReader bufferedReader = new BufferedReader(new FileReader("PRACTICA_2_ACB-main/PracAcb/src/estadistiques.csv"));
        String line = bufferedReader.readLine();
        String[] array;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        java.util.Date date;


        while (line != null) {
            System.out.println(line);
            line = bufferedReader.readLine();
            if (line == null){
                break;
            }else{
                if (line.length() > 2){
                    array = line.split(",");
                    date = sdf.parse(array[2]);
                    Date data = new Date(date.getTime());
                    if (searchGame(array[0], array[1], data)){
                        updateMatchStats(array);
                    }else if (!searchGame(array[0], array[1], data)){
                        createMatchStats(array);
                    }
                }
            }

        }
    }

    public boolean searchGame(String local, String away, Date matchDate) throws SQLException {
        Statement st = connection.createStatement();
        ResultSet rs;
        rs = st.executeQuery("SELECT * FROM match_statistics where home_team ='" + local + "' and visitor_team='" + away + "' and match_date='" + matchDate + "'");
        if (!rs.next()) {
            System.out.println("El partit no s'ha trobat");
            System.out.println("Creant partit");
            return false;
        } else {
            System.out.println("Partit trobat");
            return true;
        }
    }



    public void createMatchStats(String[] array) throws SQLException, NumberFormatException, ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        java.util.Date date;
        date = sdf.parse(array[2]);
        Date data = new Date(date.getTime());

        String sql = "INSERT INTO match_statistics (home_team, visitor_team, match_date, player, minutes_played,points, offensive_rebounds, defensive_rebounds, assists, committed_fouls,received_fouls, free_throw_attempts, free_throw_made, two_point_attempts,two_point_made, three_point_attempts, three_point_made, blocks,blocks_against, steals, turnovers, mvp_score) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        connection.prepareStatement(sql);
        PreparedStatement pst = connection.prepareStatement(sql);

        pst.setString(1,array[0]);
        pst.setString(2,array[1]);
        pst.setDate(3, data);
        pst.setString(4, array[3]);
        pst.setInt(5, Integer.parseInt(array[4]));
        pst.setInt(6, Integer.parseInt(array[5]));
        pst.setInt(7, Integer.parseInt(array[6]));
        pst.setInt(8, Integer.parseInt(array[7]));
        pst.setInt(9, Integer.parseInt(array[8]));
        pst.setInt(10, Integer.parseInt(array[9]));
        pst.setInt(11, Integer.parseInt(array[10]));
        pst.setInt(12, Integer.parseInt(array[11]));
        pst.setInt(13, Integer.parseInt(array[12]));
        pst.setInt(14, Integer.parseInt(array[13]));
        pst.setInt(15, Integer.parseInt(array[14]));
        pst.setInt(16, Integer.parseInt(array[15]));
        pst.setInt(17, Integer.parseInt(array[16]));
        pst.setInt(18, Integer.parseInt(array[17]));
        pst.setInt(19, Integer.parseInt(array[18]));
        pst.setInt(20, Integer.parseInt(array[19]));
        pst.setInt(21, Integer.parseInt(array[20]));
        pst.setInt(22, Integer.parseInt(array[21]));
        pst.executeUpdate();
    }

    public void updateMatchStats(String[] array) throws SQLException, NumberFormatException, IOException, ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        java.util.Date date;

        date = sdf.parse(array[2]);
        Date data = new Date(date.getTime());
        String sql = "UPDATE match_statistics SET minutes_played = ?,points = ?, offensive_rebounds = ?, defensive_rebounds = ?, assists = ?, committed_fouls = ?,received_fouls = ?, free_throw_attempts = ?, free_throw_made = ?, two_point_attempts = ?,two_point_made = ?, three_point_attempts = ?, three_point_made = ?, blocks = ?,blocks_against = ?, steals = ?, turnovers = ?, mvp_score = ? WHERE home_team = ? and visitor_team = ? and match_date = ? and player = ?";
        connection.prepareStatement(sql);
        PreparedStatement pst = connection.prepareStatement(sql);

        pst.setInt(1, Integer.parseInt(array[4]));
        pst.setInt(2, Integer.parseInt(array[5]));
        pst.setInt(3, Integer.parseInt(array[6]));
        pst.setInt(4, Integer.parseInt(array[7]));
        pst.setInt(5, Integer.parseInt(array[8]));
        pst.setInt(6, Integer.parseInt(array[9]));
        pst.setInt(7, Integer.parseInt(array[10]));
        pst.setInt(8, Integer.parseInt(array[11]));
        pst.setInt(9, Integer.parseInt(array[12]));
        pst.setInt(10, Integer.parseInt(array[13]));
        pst.setInt(11, Integer.parseInt(array[14]));
        pst.setInt(12, Integer.parseInt(array[15]));
        pst.setInt(13, Integer.parseInt(array[16]));
        pst.setInt(14, Integer.parseInt(array[17]));
        pst.setInt(15, Integer.parseInt(array[18]));
        pst.setInt(16, Integer.parseInt(array[19]));
        pst.setInt(17, Integer.parseInt(array[20]));
        pst.setInt(18, Integer.parseInt(array[21]));
        pst.setString(19,array[0]);
        pst.setString(20, array[1]);
        pst.setDate(21, data);
        pst.setString(22, array[3]);
        pst.executeUpdate();
    }

}
