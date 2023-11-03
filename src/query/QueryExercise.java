package query;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class QueryExercise {

    public static void main(String[] args) {


        String jdbcUrl = "jdbc:mysql://localhost:3306/db_nations";
        String username = "root";
        String password = "root";

        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter a search string: ");
            String searchString = scanner.nextLine();

            String query = "SELECT " +
                    "countries.country_id AS CountryID, " +
                    "countries.name AS CountryName, " +
                    "regions.name AS RegionName, " +
                    "continents.name AS ContinentName " +
                    "FROM " +
                    "countries " +
                    "JOIN regions ON countries.region_id = regions.region_id " +
                    "JOIN continents ON regions.continent_id = continents.continent_id " +
                    "WHERE countries.name LIKE ? " +
                    "ORDER BY countries.name";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, "%" + searchString + "%");

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int countryID = resultSet.getInt("CountryID");
                String countryName = resultSet.getString("CountryName");
                String regionName = resultSet.getString("RegionName");
                String continentName = resultSet.getString("ContinentName");

                System.out.println("ID: " + countryID + ", Country: " + countryName + ", Region: " + regionName + ", Continent: " + continentName);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
