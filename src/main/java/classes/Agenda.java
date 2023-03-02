package classes;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import static com.sun.beans.introspect.ClassInfo.clear;

public class Agenda extends JFrame{
    Connection con;
    PreparedStatement ps;
    Statement st;
    ResultSet r;
    DefaultListModel mod = new DefaultListModel();
    private JPanel panel1;
    private JList ContactList;
    private JTextField MailText;
    private JTextField NameText;
    private JTextField TelephoneText;
    private JTextField SurnameText;
    private JButton AddButton;
    private JButton EditButton;
    private JButton ConsultButton;
    private JButton DeleteButton;
    private JTextField IdText;


    public Agenda() {
        ConsultButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    consult();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });


        AddButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    add();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        EditButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    edit();
                } catch (SQLException ex) {
                    try {
                        delete();
                    } catch (SQLException exc) {
                        throw new RuntimeException(exc);
                    }
                }
            }
        });
        DeleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    delete();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }
    public void consult() throws SQLException {
        connect();
        ContactList.setModel(mod);
        st = con.createStatement();
        r = st.executeQuery("SELECT id, Name, Surname, Telephone, Mail FROM ListaContactos");
        mod.removeAllElements();
        while (r.next()){
            mod.addElement(r.getString(1)+" "+r.getString(2)+" "+r.getString(3)+" "+r.getString(4)+" "+r.getString(5)+" ");


        }
    }

    public void add() throws SQLException {
        connect();
        ps = con.prepareStatement("INSERT INTO ListaContactos VALUES (?,?,?,?,?)");
        ps.setInt(1, Integer.parseInt(IdText.getText()));
        ps.setString(2, NameText.getText() );
        ps.setString(3, SurnameText.getText());
        ps.setInt(4, Integer.parseInt(TelephoneText.getText()));
        ps.setString(5, MailText.getText());
        if(ps.executeUpdate()>0){
            ContactList.setModel(mod);
            mod.removeAllElements();
            mod.addElement("¡Añadido con éxito!");
            IdText.setText("");
            NameText.setText("");
            SurnameText.setText("");
            TelephoneText.setText("");
            MailText.setText("");

        }
    }
    public void edit () throws SQLException {
        connect();

            String id = IdText.getText();
            String Name = NameText.getText();
            String Surname = SurnameText.getText();
            String Telephone = TelephoneText.getText();
            String Mail = MailText.getText();

            if (Name == null || Name.isEmpty() || Name.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please Enter Name");
                NameText.requestFocus();
                return;
            }
            if (Surname == null || Surname.isEmpty() || Surname.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please Enter Surname");
                SurnameText.requestFocus();
                return;
            }
            if (Telephone == null || Telephone.isEmpty() || Telephone.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please Enter Telephone");
                TelephoneText.requestFocus();
                return;
            }
            if (Mail == null || Mail.isEmpty() || Mail.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please Enter Mail");
                MailText.requestFocus();
                return;
        }

            if (!IdText.getText().isEmpty()) {
                try {
                    String sql = ("UPDATE ListaContactos SET  Name=?,Surname=?,Telephone=?,Mail=?  WHERE id=?");
                    ps = con.prepareStatement(sql);
                    ps.setInt(1, Integer.parseInt(IdText.getText()));
                    ps.setString(2, NameText.getText() );
                    ps.setString(3, SurnameText.getText());
                    ps.setInt(4, Integer.parseInt(TelephoneText.getText()));
                    ps.setString(5, MailText.getText());
                    ps.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Data Update Success");
                    clear();


                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
    }



    public void delete() throws SQLException {
        connect();

            String id = IdText.getText();
                if (!IdText.getText().isEmpty()) {

                    int result = JOptionPane.showConfirmDialog(null, "Sure? You want to Delete?", "Delete",
                            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                    if (result == JOptionPane.YES_OPTION) {
                        try {
                            String sql = "delete from users where ID=?";
                            ps = con.prepareStatement(sql);
                            ps.setString(1, id);
                            ps.executeUpdate();
                            JOptionPane.showMessageDialog(null, "Data Deleted Success");
                            clear();


                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        }
                    }
                }

            }


public static void main(String[]args){
        Agenda f = new Agenda();
        f.setContentPane(new Agenda().panel1);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
        f.pack();

}
    public void connect(){

            try {
                con= DriverManager.getConnection("jdbc:mysql://localhost:3306/Agenda","root","Fedorizo88");
                System.out.println("Connect");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

    }
}
