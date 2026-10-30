/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package librarymanagementsysytem;

import java.sql.*;
import java.util.Scanner;

/**
 *
 * @author santh
 */
public class LibraryManagementSysytem {

    /**
     * @param args the command line arguments
     */
    private static final String URL = "jdbc:sqlite:library.db";
    private static final Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {
       try {
            Class.forName("org.sqlite.JDBC");
            createTables();

            while (true) {
                System.out.println("\n===== LIBRARY MANAGEMENT SYSTEM =====");
                System.out.println("1. Add Book");
                System.out.println("2. View Books");
                System.out.println("3. Update Book");
                System.out.println("4. Delete Book");
                System.out.println("5. Search Books");
                System.out.println("6. Add Member");
                System.out.println("7. View Members");
                System.out.println("8. Borrow Book");
                System.out.println("9. Return Book");
                System.out.println("10. View Transactions");
                System.out.println("0. Exit");
                System.out.print("Enter choice: ");

                int choice = Integer.parseInt(sc.nextLine());

                switch (choice) {
                    case 1: addBook(); break;
                    case 2: viewBooks(); break;
                    case 3: updateBook(); break;
                    case 4: deleteBook(); break;
                    case 5: searchBooks(); break;
                    case 6: addMember(); break;
                    case 7: viewMembers(); break;
                    case 8: borrowBook(); break;
                    case 9: returnBook(); break;
                    case 10: viewTransactions(); break;
                    case 0:
                        System.out.println("Application closed.");
                        return;
                    default:
                        System.out.println("Invalid choice.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Connection connect() throws SQLException {
        Connection con = DriverManager.getConnection(URL);
        try (Statement st = con.createStatement()) {
            st.execute("PRAGMA foreign_keys = ON");
        }
        return con;
    }

    private static void createTables() throws SQLException {
        try (Connection con = connect();
             Statement st = con.createStatement()) {

            st.executeUpdate(
                "CREATE TABLE IF NOT EXISTS Books (" +
                "book_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "title TEXT NOT NULL, author TEXT NOT NULL, " +
                "publisher TEXT, year_published INTEGER, " +
                "isbn TEXT UNIQUE, available_copies INTEGER NOT NULL)"
            );

            st.executeUpdate(
                "CREATE TABLE IF NOT EXISTS Members (" +
                "member_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT NOT NULL, email TEXT UNIQUE, " +
                "phone TEXT, membership_date TEXT NOT NULL)"
            );

            st.executeUpdate(
                "CREATE TABLE IF NOT EXISTS Transactions (" +
                "transaction_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "book_id INTEGER NOT NULL, member_id INTEGER NOT NULL, " +
                "borrow_date TEXT NOT NULL, return_date TEXT, " +
                "status TEXT NOT NULL CHECK(status IN " +
                "('borrowed','returned')), " +
                "FOREIGN KEY(book_id) REFERENCES Books(book_id), " +
                "FOREIGN KEY(member_id) REFERENCES Members(member_id))"
            );

            System.out.println("Database connected successfully.");
        }
    }

    private static void addBook() throws SQLException {
        try (Connection con = connect();
             PreparedStatement ps = con.prepareStatement(
                 "INSERT INTO Books(title,author,publisher," +
                 "year_published,isbn,available_copies) " +
                 "VALUES(?,?,?,?,?,?)")) {

            System.out.print("Title: ");
            ps.setString(1, sc.nextLine());
            System.out.print("Author: ");
            ps.setString(2, sc.nextLine());
            System.out.print("Publisher: ");
            ps.setString(3, sc.nextLine());
            System.out.print("Year published: ");
            ps.setInt(4, Integer.parseInt(sc.nextLine()));
            System.out.print("ISBN: ");
            ps.setString(5, sc.nextLine());
            System.out.print("Available copies: ");
            ps.setInt(6, Integer.parseInt(sc.nextLine()));

            if (ps.executeUpdate() > 0)
                System.out.println("Book added successfully.");
        }
    }

    private static void viewBooks() throws SQLException {
        try (Connection con = connect();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM Books")) {

            while (rs.next()) {
                System.out.println(
                    rs.getInt("book_id") + " | " +
                    rs.getString("title") + " | " +
                    rs.getString("author") + " | " +
                    rs.getString("publisher") + " | " +
                    rs.getInt("year_published") + " | " +
                    rs.getString("isbn") + " | Copies: " +
                    rs.getInt("available_copies"));
            }
        }
    }

    private static void updateBook() throws SQLException {
        System.out.print("Enter book ID: ");
        int id = Integer.parseInt(sc.nextLine());

        try (Connection con = connect();
             PreparedStatement ps = con.prepareStatement(
                 "UPDATE Books SET title=?, author=?, publisher=?," +
                 "year_published=?, isbn=?, available_copies=? " +
                 "WHERE book_id=?")) {

            System.out.print("New title: ");
            ps.setString(1, sc.nextLine());
            System.out.print("New author: ");
            ps.setString(2, sc.nextLine());
            System.out.print("New publisher: ");
            ps.setString(3, sc.nextLine());
            System.out.print("New year: ");
            ps.setInt(4, Integer.parseInt(sc.nextLine()));
            System.out.print("New ISBN: ");
            ps.setString(5, sc.nextLine());
            System.out.print("New available copies: ");
            ps.setInt(6, Integer.parseInt(sc.nextLine()));
            ps.setInt(7, id);

            System.out.println(ps.executeUpdate() > 0
                ? "Book updated." : "Book ID not found.");
        }
    }

    private static void deleteBook() throws SQLException {
        System.out.print("Enter book ID: ");
        int id = Integer.parseInt(sc.nextLine());

        try (Connection con = connect();
             PreparedStatement ps = con.prepareStatement(
                 "DELETE FROM Books WHERE book_id=? " +
                 "AND NOT EXISTS (SELECT 1 FROM Transactions " +
                 "WHERE Transactions.book_id=Books.book_id)")) {

            ps.setInt(1, id);
            System.out.println(ps.executeUpdate() > 0
                ? "Book deleted." :
                "Book not found or has transaction history.");
        }
    }

    private static void searchBooks() throws SQLException {
        System.out.print("Enter title, author, or ISBN: ");
        String query = "%" + sc.nextLine() + "%";

        try (Connection con = connect();
             PreparedStatement ps = con.prepareStatement(
                 "SELECT * FROM Books WHERE title LIKE ? " +
                 "OR author LIKE ? OR isbn LIKE ?")) {

            ps.setString(1, query);
            ps.setString(2, query);
            ps.setString(3, query);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    System.out.println(
                        rs.getInt("book_id") + " | " +
                        rs.getString("title") + " | " +
                        rs.getString("author") + " | ISBN: " +
                        rs.getString("isbn"));
                }
            }
        }
    }

    private static void addMember() throws SQLException {
        try (Connection con = connect();
             PreparedStatement ps = con.prepareStatement(
                 "INSERT INTO Members(name,email,phone,membership_date) " +
                 "VALUES(?,?,?,date('now'))")) {

            System.out.print("Member name: ");
            ps.setString(1, sc.nextLine());
            System.out.print("Email: ");
            ps.setString(2, sc.nextLine());
            System.out.print("Phone: ");
            ps.setString(3, sc.nextLine());

            ps.executeUpdate();
            System.out.println("Member added successfully.");
        }
    }

    private static void viewMembers() throws SQLException {
        try (Connection con = connect();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM Members")) {

            while (rs.next()) {
                System.out.println(
                    rs.getInt("member_id") + " | " +
                    rs.getString("name") + " | " +
                    rs.getString("email") + " | " +
                    rs.getString("phone") + " | " +
                    rs.getString("membership_date"));
            }
        }
    }

    private static void borrowBook() throws SQLException {
        System.out.print("Book ID: ");
        int bookId = Integer.parseInt(sc.nextLine());
        System.out.print("Member ID: ");
        int memberId = Integer.parseInt(sc.nextLine());

        try (Connection con = connect()) {
            con.setAutoCommit(false);

            try {
                int copies;

                try (PreparedStatement ps = con.prepareStatement(
                    "SELECT available_copies FROM Books WHERE book_id=?")) {
                    ps.setInt(1, bookId);
                    try (ResultSet rs = ps.executeQuery()) {
                        if (!rs.next())
                            throw new SQLException("Book not found.");
                        copies = rs.getInt(1);
                    }
                }

                if (copies <= 0)
                    throw new SQLException("No copies available.");

                try (PreparedStatement ps = con.prepareStatement(
                    "SELECT 1 FROM Members WHERE member_id=?")) {
                    ps.setInt(1, memberId);
                    try (ResultSet rs = ps.executeQuery()) {
                        if (!rs.next())
                            throw new SQLException("Member not found.");
                    }
                }

                try (PreparedStatement ps = con.prepareStatement(
                    "UPDATE Books SET available_copies=" +
                    "available_copies-1 WHERE book_id=?")) {
                    ps.setInt(1, bookId);
                    ps.executeUpdate();
                }

                try (PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO Transactions(book_id,member_id," +
                    "borrow_date,status) VALUES(?,?,date('now'),'borrowed')")) {
                    ps.setInt(1, bookId);
                    ps.setInt(2, memberId);
                    ps.executeUpdate();
                }

                con.commit();
                System.out.println("Book borrowed successfully.");
            } catch (SQLException e) {
                con.rollback();
                System.out.println("Borrow failed: " + e.getMessage());
            } finally {
                con.setAutoCommit(true);
            }
        }
    }

    private static void returnBook() throws SQLException {
        System.out.print("Transaction ID: ");
        int id = Integer.parseInt(sc.nextLine());

        try (Connection con = connect()) {
            con.setAutoCommit(false);

            try {
                int bookId;

                try (PreparedStatement ps = con.prepareStatement(
                    "SELECT book_id FROM Transactions " +
                    "WHERE transaction_id=? AND status='borrowed'")) {
                    ps.setInt(1, id);
                    try (ResultSet rs = ps.executeQuery()) {
                        if (!rs.next())
                            throw new SQLException(
                                "Active transaction not found.");
                        bookId = rs.getInt("book_id");
                    }
                }

                try (PreparedStatement ps = con.prepareStatement(
                    "UPDATE Transactions SET status='returned'," +
                    "return_date=date('now') WHERE transaction_id=?")) {
                    ps.setInt(1, id);
                    ps.executeUpdate();
                }

                try (PreparedStatement ps = con.prepareStatement(
                    "UPDATE Books SET available_copies=" +
                    "available_copies+1 WHERE book_id=?")) {
                    ps.setInt(1, bookId);
                    ps.executeUpdate();
                }

                con.commit();
                System.out.println("Book returned successfully.");
            } catch (SQLException e) {
                con.rollback();
                System.out.println("Return failed: " + e.getMessage());
            } finally {
                con.setAutoCommit(true);
            }
        }
    }

    private static void viewTransactions() throws SQLException {
        System.out.println("1. Search by member ID");
        System.out.println("2. Search by book ID");
        System.out.print("Choice: ");
        int choice = Integer.parseInt(sc.nextLine());

        String column;
        if (choice == 1) {
            column = "member_id";
        } else if (choice == 2) {
            column = "book_id";
        } else {
            System.out.println("Invalid choice.");
            return;
        }

        System.out.print("Enter ID: ");
        int id = Integer.parseInt(sc.nextLine());

        String sql = "SELECT * FROM Transactions WHERE " +
                     column + "=?";

        try (Connection con = connect();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    System.out.println(
                        "Transaction: " + rs.getInt("transaction_id") +
                        " | Book: " + rs.getInt("book_id") +
                        " | Member: " + rs.getInt("member_id") +
                        " | Borrowed: " + rs.getString("borrow_date") +
                        " | Returned: " + rs.getString("return_date") +
                        " | Status: " + rs.getString("status"));
                }
            }
        }
    }
}
