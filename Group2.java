package Database;


import java.sql.*;

public class Group2{
    public static void main(String[] args)throws SQLException, ClassNotFoundException {
        	//Load the MySQL driver
    	
        	String driver="com.mysql.jdbc.Driver";
            Class.forName(driver);
                
            String url="jdbc:mysql://localhost:3306/Project"; 
            
            //Get a connection to database
            Connection conn;
            conn = DriverManager.getConnection(url,"root","12332321");
         	
            // For Auto
    		conn.setAutoCommit(false);
    		
    		// For Isolation 
    	    conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE); 
    		
            Statement stmt = null;
            
            ResultSet rs = null;
            ResultSet rs2 = null;
            ResultSet rs3 = null;
            ResultSet rs4 = null;
            
            try {
            //Create statement object
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
			
            //Before Update
			System.out.println("Before Update:");
			
			rs = stmt.executeQuery("select * from Depot");
			System.out.println("Depot");
			System.out.println("Dep_id  " + " Addr   " + "       Volume");
				while(rs.next()) {
					System.out.println(rs.getString("Dep_id") + "\t " +  rs.getString("Addr") + "\t " + rs.getInt("Volume"));
				} 
				
		    
            rs2 = stmt.executeQuery("select * from Stock");
			System.out.println("\nStock");
			System.out.println("Prod_Id  " + "Dep_Id " + " Quantity ");
				while(rs2.next()) {
					System.out.println(rs2.getString("Prod_Id") + "\t "+ rs2.getString("Dep_Id") + "\t " + rs2.getInt("Quantity"));
				} 
				
			//After Update
			System.out.println("\nAfter Update:");
			
			//Group 2 : delete  dep_id = 'd1' from both Depot and Stock
			//stmt.executeUpdate("DELETE FROM Depot WHERE dep_id = 'd1'");   //Isolation
			stmt.executeUpdate("DELETE FROM Stock WHERE dep_id='d1'");		
			stmt.executeUpdate("DELETE FROM Depot WHERE dep_id = 'd1'"); 
			//stmt.executeUpdate("DELETE FROM Depot WHERE prod_id = 'd1'");  //Auto
			
			rs3 = stmt.executeQuery("select * from Depot");				
			System.out.println("Depot");
			System.out.println("Dep_id  " + "  Addr    " + "     Volume");
				while(rs3.next()) {
					System.out.println( rs3.getString("dep_id") + "\t " +  rs3.getString("addr") + "\t " + rs3.getInt("volume"));
				} 
				
				
			rs4 = stmt.executeQuery("select * from Stock");
			System.out.println("\nStock");
			System.out.println("prod_Id  " + "dep_Id " + " quantity ");
				while(rs4.next()) {
					System.out.println(rs4.getString("Prod_Id") + "\t "+ rs4.getString("Dep_Id") + "\t " + rs4.getInt("Quantity"));
				} 
                

            }catch (Exception e) {
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
