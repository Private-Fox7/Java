<%-- 
    Document   : index.jsp
    Created on : 24 Oct 2026, 2:02:51 pm
    Author     : santh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ page import="java.sql.*" %>
<%@ page import="java.io.File" %>

<%
    String dbPath = application.getRealPath("/WEB-INF/student.db");
    File dbFile = new File(dbPath);
    dbFile.getParentFile().mkdirs();

    String url = "jdbc:sqlite:" + dbPath;

    Class.forName("org.sqlite.JDBC");

    try (Connection con = DriverManager.getConnection(url);
         Statement st = con.createStatement()) {

        st.executeUpdate(
            "CREATE TABLE IF NOT EXISTS Student (" +
            "SRN TEXT PRIMARY KEY, " +
            "Name TEXT NOT NULL, " +
            "Course TEXT NOT NULL, " +
            "CGPA REAL NOT NULL CHECK(CGPA >= 0 AND CGPA <= 10))"
        );

        String action = request.getParameter("action");

        if ("add".equals(action)) {
            String srn = request.getParameter("srn");
            String name = request.getParameter("name");
            String course = request.getParameter("course");
            double cgpa = Double.parseDouble(request.getParameter("cgpa"));

            try (PreparedStatement ps = con.prepareStatement(
                "INSERT INTO Student(SRN, Name, Course, CGPA) " +
                "VALUES(?,?,?,?)")) {
                ps.setString(1, srn.trim());
                ps.setString(2, name.trim());
                ps.setString(3, course.trim());
                ps.setDouble(4, cgpa);
                ps.executeUpdate();
            }

            response.sendRedirect("index.jsp");
            return;
        }

        if ("update".equals(action)) {
            String srn = request.getParameter("srn");
            String name = request.getParameter("name");
            String course = request.getParameter("course");
            double cgpa = Double.parseDouble(request.getParameter("cgpa"));

            try (PreparedStatement ps = con.prepareStatement(
                "UPDATE Student SET Name=?, Course=?, CGPA=? WHERE SRN=?")) {
                ps.setString(1, name.trim());
                ps.setString(2, course.trim());
                ps.setDouble(3, cgpa);
                ps.setString(4, srn.trim());

                int count = ps.executeUpdate();
                session.setAttribute("message",
                    count > 0 ? "Student updated successfully."
                              : "Student SRN not found.");
            }

            response.sendRedirect("index.jsp");
            return;
        }

        if ("delete".equals(action)) {
            String srn = request.getParameter("srn");

            try (PreparedStatement ps = con.prepareStatement(
                "DELETE FROM Student WHERE SRN=?")) {
                ps.setString(1, srn);

                int count = ps.executeUpdate();
                session.setAttribute("message",
                    count > 0 ? "Student deleted successfully."
                              : "Student SRN not found.");
            }

            response.sendRedirect("index.jsp");
            return;
        }
    }
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Student CRUD Application</title>

    <style>
        body {
            font-family: Arial, sans-serif;
            max-width: 950px;
            margin: 30px auto;
            padding: 20px;
            background: #f2f5f9;
        }

        h1 { text-align: center; }

        .panel {
            background: white;
            padding: 20px;
            margin-bottom: 20px;
            border-radius: 8px;
        }

        input {
            width: 100%;
            box-sizing: border-box;
            padding: 10px;
            margin: 7px 0 14px;
        }

        button {
            padding: 9px 15px;
            border: none;
            border-radius: 4px;
            color: white;
            background: #1769aa;
            cursor: pointer;
        }

        .delete { background: #c62828; }

        table {
            width: 100%;
            border-collapse: collapse;
            background: white;
        }

        th, td {
            padding: 10px;
            border: 1px solid #ddd;
            text-align: left;
        }

        th { background: #dce9f7; }

        .message {
            background: #e1f5e7;
            padding: 10px;
        }
    </style>
</head>

<body>

<h1>Student CRUD Application</h1>

<%
    String message = (String) session.getAttribute("message");

    if (message != null) {
        String safeMessage = message.replace("&", "&amp;")
                                    .replace("<", "&lt;")
                                    .replace(">", "&gt;");
%>
    <p class="message"><%= safeMessage %></p>
<%
        session.removeAttribute("message");
    }
%>

<div class="panel">
    <h2>Add Student</h2>

    <form action="index.jsp" method="post">
        <input type="hidden" name="action" value="add">

        <label>SRN</label>
        <input type="text" name="srn" required>

        <label>Name</label>
        <input type="text" name="name" required>

        <label>Course</label>
        <input type="text" name="course" required>

        <label>CGPA (0 to 10)</label>
        <input type="number" name="cgpa"
               min="0" max="10" step="0.01" required>

        <button type="submit">Add Student</button>
    </form>
</div>

<div class="panel">
    <h2>All Students</h2>

    <table>
        <tr>
            <th>SRN</th>
            <th>Name</th>
            <th>Course</th>
            <th>CGPA</th>
            <th>Actions</th>
        </tr>

        <%
            try (Connection con = DriverManager.getConnection(url);
                 Statement st = con.createStatement();
                 ResultSet rs = st.executeQuery(
                     "SELECT * FROM Student ORDER BY SRN")) {

                while (rs.next()) {
                    String srn = rs.getString("SRN");
                    String name = rs.getString("Name");
                    String course = rs.getString("Course");
                    double cgpa = rs.getDouble("CGPA");

                    String safeSrn = srn.replace("&", "&amp;")
                                        .replace("<", "&lt;")
                                        .replace(">", "&gt;")
                                        .replace("\"", "&quot;");

                    String safeName = name.replace("&", "&amp;")
                                          .replace("<", "&lt;")
                                          .replace(">", "&gt;")
                                          .replace("\"", "&quot;");

                    String safeCourse = course.replace("&", "&amp;")
                                              .replace("<", "&lt;")
                                              .replace(">", "&gt;")
                                              .replace("\"", "&quot;");
        %>

        <tr>
            <td><%= safeSrn %></td>
            <td><%= safeName %></td>
            <td><%= safeCourse %></td>
            <td><%= cgpa %></td>
            <td>
                <details>
                    <summary>Edit</summary>

                    <form action="index.jsp" method="post">
                        <input type="hidden" name="action" value="update">
                        <input type="hidden" name="srn"
                               value="<%= safeSrn %>">

                        <label>Name</label>
                        <input type="text" name="name"
                               value="<%= safeName %>" required>

                        <label>Course</label>
                        <input type="text" name="course"
                               value="<%= safeCourse %>" required>

                        <label>CGPA</label>
                        <input type="number" name="cgpa"
                               min="0" max="10" step="0.01"
                               value="<%= cgpa %>" required>

                        <button type="submit">Update</button>
                    </form>
                </details>

                <form action="index.jsp" method="post"
                      onsubmit="return confirm('Delete this student?');">
                    <input type="hidden" name="action" value="delete">
                    <input type="hidden" name="srn"
                           value="<%= safeSrn %>">
                    <button class="delete" type="submit">Delete</button>
                </form>
            </td>
        </tr>

        <%
                }
            }
        %>
    </table>
</div>

</body>
</html>
