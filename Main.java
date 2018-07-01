

import java.sql.* ;


public class Main {
		public static void main(String[] args) throws Exception {
			
		Connection conn=getConnection();
		
		 Statement stmt = null;
         ResultSet rs = null;
         ResultSet rs2 = null;
         ResultSet rs3 = null;
         ResultSet rs4 = null;
         
         try {	
         stmt = conn.createStatement(); 
			// create statement object
         
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
				String sql= "DELETE Depot, Stock FROM Depot LEFT JOIN Stock ON Depot.dep_id = Stock.dep_id WHERE Depot.dep_id = 'd1'";
				Statement stmt2 = conn.createStatement();
				stmt2.executeUpdate(sql);
				
				rs3 = stmt2.executeQuery("select * from Depot");				
				System.out.println("Depot");
				System.out.println("Dep_id  " + "  Addr    " + "     Volume");
				while(rs3.next()) {
					System.out.println( rs3.getString("dep_id") + "\t " +  rs3.getString("addr") + "\t " + rs3.getInt("volume"));
				} 
				
				rs4 = stmt2.executeQuery("select * from Stock");
				System.out.println("\nStock");
				System.out.println("prod_Id  " + "dep_Id " + " quantity ");
				while(rs4.next()) {
					System.out.println(rs4.getString("Prod_Id") + "\t "+ rs4.getString("Dep_Id") + "\t " + rs4.getInt("Quantity"));
				} 
             

         }catch (Exception exc) {
			
         rs.close();
         stmt.close();
         conn.close();
         }
}
		
		
		public static Connection getConnection() throws Exception{
			try {
			String driver="com.mysql.jdbc.Driver";
			String url="jdbc:mysql://localhost:3306/Project";
			String username="Bairen";
			String password="abc";
			Class.forName(driver);
			
			Connection conn = DriverManager.getConnection(url,username,password);
			System.out.println("Connected");
			return conn;
			}catch (Exception e) {
				System.out.println(e);
			}
			return null;
		}
		
}