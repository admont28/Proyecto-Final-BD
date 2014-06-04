package Controller;
import java.sql.Connection;
import java.sql.DriverManager;

public class jdbcConnection {
    private static jdbcConnection instance;
    private String url = "jdbc:oracle:thin:@192.168.1.7:1521:xe";
    private String usuario = "PROYECTO_FINAL";
    private String password = "PROYECTO_FINAL";
    private String driver = "oracle.jdbc.driver.OracleDriver";
    private Connection connection;

    private  jdbcConnection() {
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, usuario, password);
            connection.setAutoCommit(true);
        } catch (Exception e) {
             System.out.println(e);
        }    
    }
	
    public static jdbcConnection getInstance() {
        if (instance == null) {
            instance = new jdbcConnection();
        }
        return instance;
    }
        
    public Connection obtenerConexion(){
            return connection;
    }
    
}


    
    


    
    
   
   
