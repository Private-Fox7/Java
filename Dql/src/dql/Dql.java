/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package dql;

/**
 *
 * @author santh
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
public class Dql {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        String url = "jdbc:sqlite:employees.db";

        Connection conn = null;

        try {

            // 1. Connect to SQLite
            conn = DriverManager.getConnection(url);

            System.out.println("Connection to SQLite has been established.");

            // 2. Create employees table
            String createTableSQL =
                    "CREATE TABLE IF NOT EXISTS employees ("
                    + "id INTEGER PRIMARY KEY,"
                    + "name TEXT NOT NULL,"
                    + "salary REAL,"
                    + "department TEXT"
                    + ");";

            Statement stmt = conn.createStatement();

            stmt.execute(createTableSQL);

            System.out.println("Employee table created successfully.");

            // 3. Insert employee records
            String insertSQL =
                    "INSERT INTO employees (id, name, salary, department) VALUES "
                    + "(1, 'A', 5000, 'HR'),"
                    + "(2, 'B', 7000, 'IT'),"
                    + "(3, 'C', 6000, 'IT');";

            stmt.executeUpdate(insertSQL);

            System.out.println("Employee records inserted successfully.");

            // 4. Fetch employee records
            String querySQL = "SELECT * FROM employees";

            ResultSet rs = stmt.executeQuery(querySQL);

            // 5. Display employee details
            System.out.println();
            System.out.println("Employee Details:");
            System.out.println("--------------------------------------------------");

            System.out.printf(
                    "%-10s %-15s %-15s %-15s%n",
                    "ID",
                    "Name",
                    "Salary",
                    "Department"
            );

            System.out.println("--------------------------------------------------");

            while (rs.next()) {

                System.out.printf(
                        "%-10d %-15s %-15.2f %-15s%n",
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("salary"),
                        rs.getString("department")
                );
            }

            System.out.println("--------------------------------------------------");

            // 6. Close resources
            rs.close();
            stmt.close();

        } catch (SQLException e) {

            System.out.println("Error: " + e.getMessage());

        } finally {

            // 7. Close database connection
            try {

                if (conn != null) {
                    conn.close();
                    System.out.println("Database connection closed.");
                }

            } catch (SQLException e) {

                System.out.println("Error while closing connection: "
                        + e.getMessage());
            }
        }
    }
}

