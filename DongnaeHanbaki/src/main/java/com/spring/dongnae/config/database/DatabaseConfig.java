//package com.spring.dongnae.config.database;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;
//
//public class DatabaseConfig {
//
//	// DB 연결 객체 가져오기
//		public static Connection getConnection() {
//			try {
//				Class.forName("oracle.jdbc.OracleDriver");
//				return DriverManager.getConnection(
//						"jdbc:oracle:thin:@localhost:1521:xe", 
//						"mystudy", "mystudypw");
//			} catch (ClassNotFoundException e) {
//				e.printStackTrace();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//			return null;
//		}
//		
//		public static void close(Connection conn, Statement stmt) {
//			try {
//				if (stmt != null) stmt.close();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//			try {
//				if (conn != null) conn.close();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
//		
//		public static void close(Connection conn, Statement stmt, ResultSet rs) {
//			try {
//				if (rs != null) rs.close();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//			close(conn, stmt);
//		}
//		
//}
