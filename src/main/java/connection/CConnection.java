package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;


public class CConnection {
    public static void main(String[] args) throws Exception {
        // 1. Cargue el controlador de acceso a datos
        Class.forName("com.mysql.cj.jdbc.Driver");
        // 2. Conéctese a la "biblioteca" de datos
        String url = "jdbc:mysql://localhost:3306/Agenda";
        String user= "root";
        String passwd= "******";
        Connection conn= DriverManager.getConnection(url,user,passwd);
        // 3. Construir comandos SQL
        Statement state=conn.createStatement();
        String s="insert into ListaContactos (Name, Surname, Telephone, Mail) values ( 'Aurino','Llera',669541547, 'aurinollera@hotmail.com')";
        state.executeUpdate(s);
    }
}
