import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;


public class CConnection {
    public static void main(String[] args) throws Exception {
        // 1. Cargue el controlador de acceso a datos
        Class.forName("com.mysql.cj.jdbc.Driver");
        // 2. Conéctese a la "biblioteca" de datos
        String url = "jdbc:mysql://localhost:3306/ToDoListJava";
        String user= "root";
        String passwd= "Fedorizo88";
        Connection conn= DriverManager.getConnection(url,user,passwd);
        // 3. Construir comandos SQL
        Statement state=conn.createStatement();
        String s="insert into Table_Tasks (Name, Description, StartDate, FinishDate) values ( 'correr','gimnasio','2022-11-30', '2022-11-30')";
        state.executeUpdate(s);
    }
}
