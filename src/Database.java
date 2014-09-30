import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.lang.time.StopWatch;


public class Database {

	public static void main(String[] args) {
		


	    Connection connection = null;
	    
	    try
	    {
			Class.forName("org.sqlite.JDBC");
			String db = "/Users/josericardo/Projects/Dominoes/Interface/db/gitdataminer.sqlite";
			
	      // create a database connection
	      connection = DriverManager.getConnection("jdbc:sqlite:" + db);
	      Statement statement = connection.createStatement();
	      
	      String sql = "SELECT TC.HashCode, TF.NewName, TF.PackageName, TCL.name FROM TCOMMIT TC, " +
	    		  "TREPOSITORY TR, TFILE TF LEFT JOIN TCLASS AS TCL ON TCL.fileid = TF.id " +
	    		  "WHERE TF.CommitId = TC.id AND TR.name = 'derby' AND " + 
	    		  "TC.date >= '2013-10-01 00:00:00' AND TC.date <= '2014-01-01 00:00:00' " + 
	    		  "ORDER BY TC.Date, TF.PackageName, TF.NewName, TCL.Name;";
	      
			StopWatch stopWatch = new StopWatch();
			stopWatch.reset();
			stopWatch.start();
			System.out.println("*Loading query...");

	      ResultSet rs = statement.executeQuery(sql);
	      stopWatch.stop();
	      System.out.println("Time: " + stopWatch.getTime() );
	      while(rs.next())
	      {
	        // read the result set
	        System.out.println("name = " + rs.getString("HashCode"));
	      }
	    }

	    catch(SQLException e)
	    {
	      // if the error message is "out of memory", 
	      // it probably means no database file is found
	      System.err.println(e.getMessage());
	    }
	    
	    catch(ClassNotFoundException e){
	    	System.err.println(e.getMessage());
	    }
	    
	    finally
	    {
	      try
	      {
	        if(connection != null)
	          connection.close();
	      }
	      catch(SQLException e)
	      {
	        // connection close failed.
	        System.err.println(e);
	      }
	    }

	}

}
