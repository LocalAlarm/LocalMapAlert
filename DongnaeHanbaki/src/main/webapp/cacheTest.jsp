<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>User Search</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script>
        $(document).ready(function() {
            $("#searchByTokenForm").submit(function(event) {
                event.preventDefault();
                var token = $("#tokenInput").val();
                if (token) {
                    $.ajax({
                        url: "/dongnae/usersCache/token",
                        method: "POST",
                        data: { token: token },
                        success: function(data) {
                            $("#result").html(
                                "<p>Email: " + data.email + "</p>" +
                                "<p>Token: " + data.token + "</p>" +
                                "<p>Nickname: " + data.nickname + "</p>" +
                                "<p>Image: <img src='" + data.image + "' alt='" + data.nickname + "' /></p>"
                            );
                        },
                        error: function() {
                            $("#result").html("<p>User not found.</p>");
                        }
                    });
                } else {
                    $("#result").html("<p>Please enter a token.</p>");
                }
            });

            $("#searchByEmailForm").submit(function(event) {
                event.preventDefault();
                var email = $("#emailInput").val();
                if (email) {
                    $.ajax({
                        url: "/dongnae/usersCache/email",
                        method: "POST",
                        data: { email: email },
                        success: function(data) {
                            $("#result").html(
                                "<p>Email: " + data.email + "</p>" +
                                "<p>Token: " + data.token + "</p>" +
                                "<p>Nickname: " + data.nickname + "</p>" +
                                "<p>Image: <img src='" + data.image + "' alt='" + data.nickname + "' /></p>"
                            );
                        },
                        error: function() {
                            $("#result").html("<p>User not found.</p>");
                        }
                    });
                } else {
                    $("#result").html("<p>Please enter an email.</p>");
                }
            });

            $("#searchByEmailPartialForm").submit(function(event) {
                event.preventDefault();
                var email = $("#emailPartialInput").val();
                if (email) {
                    $.ajax({
                        url: "/dongnae/usersCache/search",
                        method: "POST",
                        data: { email: email },
                        success: function(data) {
                            var resultHtml = "<table border='1'><tr><th>Email</th><th>Token</th><th>Nickname</th><th>Image</th></tr>";
                            data.forEach(function(user) {
                                resultHtml += "<tr>" +
                                    "<td>" + user.email + "</td>" +
                                    "<td>" + user.token + "</td>" +
                                    "<td>" + user.nickname + "</td>" +
                                    "<td><img src='" + user.image + "' alt='" + user.nickname + "' width='50' /></td>" +
                                    "</tr>";
                            });
                            resultHtml += "</table>";
                            $("#result").html(resultHtml);
                        },
                        error: function() {
                            $("#result").html("<p>No users found.</p>");
                        }
                    });
                } else {
                    $("#result").html("<p>Please enter a partial email.</p>");
                }
            });

            $("#cacheButton").click(function(event) {
                event.preventDefault();
                $.ajax({
                    url: "/dongnae/usersCache/cache",
                    method: "POST",
                    success: function(data) {
                    	console.log(data);
                        var resultHtml = "<table border='1'><tr><th>Key</th><th>Value</th></tr>";
                        for (var key in data) {
                            resultHtml += "<tr><td>" + key + "</td><td>" + JSON.stringify(data[key]) + "</td></tr>";
                        }
                        resultHtml += "</table>";
                        $("#result").html(resultHtml);
                    },
                    error: function() {
                        $("#result").html("<p>Error retrieving cache entries.</p>");
                    }
                });
            });
        });
    </script>
</head>
<body>
    <h1>User Search</h1>
    <form id="searchByTokenForm">
        <input type="text" id="tokenInput" placeholder="Search by token">
        <button type="submit">Search by Token</button>
    </form>
    <br>
    <form id="searchByEmailForm">
        <input type="text" id="emailInput" placeholder="Search by email">
        <button type="submit">Search by Email</button>
    </form>
    <br>
    <form id="searchByEmailPartialForm">
        <input type="text" id="emailPartialInput" placeholder="Search by partial email">
        <button type="submit">Search by Partial Email</button>
    </form>
    <br>
    <button id="cacheButton">View Cache Entries</button>
    <br>
    <div id="result"></div>
</body>
</html>