<%-- 
    Document   : index
    Created on : 24 Oct 2026, 1:51:11 pm
    Author     : santh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ page import="java.sql.*" %>
<%@ page import="java.io.File" %>
<%@ page import="java.util.*" %>

<%
    String dbPath = application.getRealPath("/WEB-INF/todo.db");
    File dbFile = new File(dbPath);
    dbFile.getParentFile().mkdirs();

    String url = "jdbc:sqlite:" + dbPath;

    Class.forName("org.sqlite.JDBC");

    try (Connection con = DriverManager.getConnection(url);
         Statement st = con.createStatement()) {

        st.executeUpdate(
            "CREATE TABLE IF NOT EXISTS tasks (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "title TEXT NOT NULL, " +
            "description TEXT, " +
            "status TEXT NOT NULL DEFAULT 'Pending')"
        );

        String action = request.getParameter("action");

        if ("add".equals(action)) {
            String title = request.getParameter("title");
            String description = request.getParameter("description");

            if (title != null && !title.trim().isEmpty()) {
                try (PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO tasks(title, description) VALUES(?, ?)")) {
                    ps.setString(1, title.trim());
                    ps.setString(2, description);
                    ps.executeUpdate();
                }
            }

            response.sendRedirect("index.jsp");
            return;
        }

        if ("update".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            String title = request.getParameter("title");
            String description = request.getParameter("description");

            if (title != null && !title.trim().isEmpty()) {
                try (PreparedStatement ps = con.prepareStatement(
                    "UPDATE tasks SET title=?, description=? WHERE id=?")) {
                    ps.setString(1, title.trim());
                    ps.setString(2, description);
                    ps.setInt(3, id);
                    ps.executeUpdate();
                }
            }

            response.sendRedirect("index.jsp");
            return;
        }

        if ("toggle".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));

            try (PreparedStatement ps = con.prepareStatement(
                "UPDATE tasks SET status = CASE " +
                "WHEN status='Pending' THEN 'Completed' " +
                "ELSE 'Pending' END WHERE id=?")) {
                ps.setInt(1, id);
                ps.executeUpdate();
            }

            response.sendRedirect("index.jsp");
            return;
        }

        if ("delete".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));

            try (PreparedStatement ps = con.prepareStatement(
                "DELETE FROM tasks WHERE id=?")) {
                ps.setInt(1, id);
                ps.executeUpdate();
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
    <title>To-Do List</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            max-width: 850px;
            margin: 40px auto;
            padding: 20px;
            background: #f4f6f9;
        }

        h1 { text-align: center; }

        .panel {
            background: white;
            padding: 20px;
            margin-bottom: 20px;
            border-radius: 8px;
        }

        input[type=text], textarea {
            box-sizing: border-box;
            width: 100%;
            padding: 10px;
            margin: 8px 0;
        }

        button, .button {
            display: inline-block;
            padding: 8px 12px;
            border: 0;
            border-radius: 4px;
            cursor: pointer;
            text-decoration: none;
            background: #1769aa;
            color: white;
        }

        .delete { background: #c62828; }
        .completed { color: green; }
        .pending { color: #b26a00; }
        .task { border-bottom: 1px solid #ddd; padding: 15px 0; }
    </style>
</head>

<body>
    <h1>My To-Do List</h1>

    <div class="panel">
        <h2>Add New Task</h2>

        <form action="index.jsp" method="post">
            <input type="hidden" name="action" value="add">

            <label>Task title</label>
            <input type="text" name="title" required>

            <label>Description</label>
            <textarea name="description" rows="3"></textarea>

            <button type="submit">Add Task</button>
        </form>
    </div>

    <div class="panel">
        <h2>All Tasks</h2>

        <%
            try (Connection con = DriverManager.getConnection(url);
                 PreparedStatement ps = con.prepareStatement(
                     "SELECT * FROM tasks ORDER BY id DESC");
                 ResultSet rs = ps.executeQuery()) {

                boolean found = false;

                while (rs.next()) {
                    found = true;

                    int id = rs.getInt("id");
                    String title = rs.getString("title");
                    String description = rs.getString("description");
                    String status = rs.getString("status");

                    String safeTitle = title == null ? "" :
                        title.replace("&", "&amp;")
                             .replace("<", "&lt;")
                             .replace(">", "&gt;")
                             .replace("\"", "&quot;");

                    String safeDescription = description == null ? "" :
                        description.replace("&", "&amp;")
                                   .replace("<", "&lt;")
                                   .replace(">", "&gt;")
                                   .replace("\"", "&quot;");

                    String safeStatus = status == null ? "Pending" :
                        status.replace("&", "&amp;")
                              .replace("<", "&lt;")
                              .replace(">", "&gt;");
        %>

        <div class="task">
            <h3><%= safeTitle %></h3>
            <p><%= safeDescription %></p>

            <p class="<%= "Completed".equals(status)
                ? "completed" : "pending" %>">
                Status: <%= safeStatus %>
            </p>

            <form action="index.jsp" method="post">
                <input type="hidden" name="action" value="toggle">
                <input type="hidden" name="id" value="<%= id %>">
                <button type="submit">
                    Mark <%= "Completed".equals(status)
                        ? "Pending" : "Completed" %>
                </button>
            </form>

            <h4>Edit Task</h4>

            <form action="index.jsp" method="post">
                <input type="hidden" name="action" value="update">
                <input type="hidden" name="id" value="<%= id %>">

                <input type="text" name="title"
                       value="<%= safeTitle %>" required>

                <textarea name="description" rows="2"><%= safeDescription %></textarea>

                <button type="submit">Update Task</button>
            </form>

            <form action="index.jsp" method="post"
                  onsubmit="return confirm('Delete this task?');">
                <input type="hidden" name="action" value="delete">
                <input type="hidden" name="id" value="<%= id %>">
                <button class="delete" type="submit">Delete Task</button>
            </form>
        </div>

        <%
                }

                if (!found) {
        %>
            <p>No tasks yet. Add your first task above!</p>
        <%
                }
            }
        %>
    </div>
</body>
</html>
