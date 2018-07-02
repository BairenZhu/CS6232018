package Database;


import java.sql.*;
public class Group2 {

	public static void main(String args[]) throws Exception {
		
		Class.forName("com.mysql.jdbc.Driver");
				
		String myUrl = "jdbc:mysql://localhost:3306/Project";

		Connection conn = DriverManager.getConnection(myUrl, "root", "12332321"); 

	
		conn.setAutoCommit(false);
		conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE); 
		
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate("DROP TABLE IF EXISTS Stock CASCADE");
			stmt.executeUpdate("DROP TABLE IF EXISTS Product CASCADE");
			stmt.executeUpdate("DROP TABLE IF EXISTS Depot CASCADE");
			
			//Create Table
			stmt.executeUpdate("Create table IF NOT EXISTS Product("
					+ "prod_id CHAR(10),"
					+ "pname VARCHAR(128),"
					+ "price DECIMAL,"
					+ "PRIMARY KEY (prod_id),"
					+ "CHECK (price > 0)"
					+ ")");
			stmt.executeUpdate("Create table IF NOT EXISTS Depot("
					+ "dep_id CHAR(10),"
					+ "addr VARCHAR(128),"
					+ "volume INTEGER,"
					+ "PRIMARY KEY (dep_id),"
					+ "CHECK (volume >= 0)"
					+ ")");
			stmt.executeUpdate("Create table IF NOT EXISTS Stock("
					+ "prod_id CHAR(10),"
					+ "dep_id CHAR(10),"
					+ "quantity INTEGER,"
					+ "PRIMARY KEY (prod_id, dep_id),"
					+ "FOREIGN KEY (prod_id) REFERENCES Product (prod_id) ON UPDATE CASCADE," 
					+ "FOREIGN KEY (dep_id) REFERENCES Depot (dep_id) ON UPDATE CASCADE"
					+ ")");
			
			stmt.executeUpdate("INSERT INTO Product (prod_id, pname, price) Values" 
					+ "('p1', 'tape', 2.5)," 
					+ "('p2', 'tv', 250), "
					+ "('p3', 'vcr', 80);");
			stmt.executeUpdate("INSERT INTO Depot (dep_id, addr, volume) Values" 
					+ "('d1', 'New Yrok', 9000)," 
					+ "('d2', 'Syracuse', 6000), "
					+ "('d4', 'New Yrok', 2000);");
			stmt.executeUpdate("INSERT INTO Stock (prod_id, dep_id, quantity) Values" 
					+ "('p1', 'd2', -100)," 
					+ "('p1', 'd4', 1200)," 
					+ "('p3', 'd1', 3000)," 
					+ "('p3', 'd4', 2000)," 
					+ "('p2', 'd4', 1500)," 
					+ "('p2', 'd1', -400)," 
					+ "('p2', 'd2', 2000);");

			//output before update
			System.out.println("before Update");
			ResultSet rs2 = stmt.executeQuery("select * from Depot");
			System.out.println(" Depot");
			System.out.println("dep_Id  " + "addr " + " volume ");
			while(rs2.next()) {
				System.out.println( rs2.getString("dep_Id ") 
						 + "\t " + rs2.getString("addr")
						 + "\t " + rs2.getInt("volume"));
			} 
			
			ResultSet rs3 = stmt.executeQuery("select * from Stock");
			System.out.println("\n Stock");
			System.out.println("Prod_Id  " + "Dep_Id " + " Quantity ");
			while(rs3.next()) {
				System.out.println(rs3.getString("Prod_Id") 
						+ "\t " + rs3.getString("Dep_Id") 
						+ "\t " + rs3.getInt("quantity"));
			} 
			
			
			//output after update
			System.out.println("\nAfter Update");
			//our operations
			stmt.executeUpdate("DELETE FROM Stock WHERE dep_id='d1'");
			stmt.executeUpdate("DELETE FROM Depot WHERE dep_id = 'd1'");
			
			ResultSet rs1 = stmt.executeQuery("select * from Depot");
			System.out.println("Depot");
			System.out.println("dep_Id  " + "addr " + " volume ");
			while(rs1.next()) {
				System.out.println( rs1.getString("dep_Id") 
						+ "\t " + rs1.getString("addr") 
						+ "\t " + rs1.getInt("volume"));
			} 
			
			ResultSet rs = stmt.executeQuery("select * from Stock");
			System.out.println("\n Stock");
			System.out.println("Prod_Id  " + "Dep_Id " + " Quantity ");
			while(rs.next()) {
				System.out.println(rs.getString("Prod_Id") 
						+ "\t " + rs.getString("Dep_Id") 
						+ "\t " + rs.getInt("quantity"));
			} 
			
		} catch (Exception e) {
			System.out.println( e);
			conn.rollback();
			stmt.close();
			conn.close();
			return;
		} 
		conn.commit();
		stmt.close();
		conn.close();
	}
}