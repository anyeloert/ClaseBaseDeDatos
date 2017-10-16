/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clasebasededatos;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;


public class Formulario extends JFrame {

  private JPanel contentPane;
  private JTextField tf1;
  private JTextField tf2;
  private JLabel labelResultado;
  private JButton btnConsultaPorCdigo;
  private JLabel lblIngreseCdigoDe;
  private JTextField tf3;

  /**
   * Launch the application.
   */
  

  /**
   * Create the frame.
   */
  
  
  public static void evaluarString (String String) throws MisExcepciones{
        if (String.isEmpty()){
            throw new MisExcepciones();
        }
    }
  
  public Formulario() {
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setBounds(100, 100, 606, 405);    
    contentPane = new JPanel();
    contentPane.setBorder(new EmptyBorder(10,10,10,10));
    setContentPane(contentPane);
    contentPane.setLayout(null);
    
    JLabel lblDescripcinDelArtculo = new JLabel("Descripción del artículo:");
    lblDescripcinDelArtculo.setBounds(23, 38, 193, 14);
    contentPane.add(lblDescripcinDelArtculo);
    
    tf1 = new JTextField();
    tf1.setBounds(247, 35, 193, 20);
    contentPane.add(tf1);
    //tf1.setColumns(10);
    
    JLabel lblPrecio = new JLabel("Precio:");
    lblPrecio.setBounds(23, 74, 95, 14);
    contentPane.add(lblPrecio);
    
    tf2 = new JTextField();
    tf2.setBounds(247, 71, 107, 20);
    contentPane.add(tf2);
    tf2.setColumns(10);
    
    JButton btnInsert = new JButton("Insertar");
    btnInsert.addActionListener((ActionEvent arg0) -> {
        labelResultado.setText("");
        try {
            Connection conexion=DriverManager.getConnection("jdbc:mysql://localhost/base1","root" ,"");
            Statement comando=conexion.createStatement();
            evaluarString(tf2.getText());
            evaluarString(tf1.getText());
            comando.executeUpdate("insert into articulos(descripcion,precio) values ('"+tf1.getText()+"',"+tf2.getText()+")");
            conexion.close();
            labelResultado.setText("se registraron los datos");
            tf1.setText("");
            tf2.setText("");
        }catch(MisExcepciones e3){
                JOptionPane.showMessageDialog(null, "No debe dejar campos vacíos", "¡Error!", JOptionPane.WARNING_MESSAGE);
        }catch(SQLException ex){
            setTitle(ex.toString());
        }
    });
    btnInsert.setBounds(250, 118, 89, 23);
    contentPane.add(btnInsert);
    
    JButton btnUpdate = new JButton("Actualizar");
    btnUpdate.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent arg0) {
            labelResultado.setText("");
            try {
                Connection conexion=DriverManager.getConnection("jdbc:mysql://localhost/base1","root" ,"");
                Statement comando=conexion.createStatement();
                evaluarString(tf3.getText());
                evaluarString(tf2.getText());
                evaluarString(tf1.getText());
                comando.executeUpdate("update articulos set descripcion ='"+tf1.getText()+"', precio = "+tf2.getText()+" where codigo="+tf3.getText());
                conexion.close();
                labelResultado.setText("se actualizaron los datos");
                tf1.setText("");
                tf2.setText("");
            } catch(MisExcepciones e3){
                JOptionPane.showMessageDialog(null, "No debe dejar campos vacíos", "¡Error!", JOptionPane.WARNING_MESSAGE);
            }catch(SQLException ex){
                setTitle(ex.toString());
            }
        }
    });
    btnUpdate.setBounds(350, 118, 89, 23);
    contentPane.add(btnUpdate);
    JButton btnDelete = new JButton("Eliminar");
    btnDelete.addActionListener((ActionEvent arg0) -> {
        labelResultado.setText("");        
        try {
            Connection conexion=DriverManager.getConnection("jdbc:mysql://localhost/base1","root" ,"");
            Statement comando=conexion.createStatement();
            evaluarString(tf3.getText());
            comando.executeUpdate("delete from articulos where codigo="+tf3.getText());
            conexion.close();
            labelResultado.setText("se eliminó el artículo");
            tf1.setText("");
            tf2.setText("");
        }catch(MisExcepciones e3){
            JOptionPane.showMessageDialog(null, "Debe ingresar código de producto a eliminar", "¡Error!", JOptionPane.WARNING_MESSAGE);
        }catch(SQLException ex){
            setTitle(ex.toString());
        }
    });
    btnDelete.setBounds(450, 118, 89, 23);
    contentPane.add(btnDelete);
    
    labelResultado = new JLabel("resultado");
    labelResultado.setBounds(250, 150, 229, 14);
    contentPane.add(labelResultado);
    
    btnConsultaPorCdigo = new JButton("Consulta por código");
    btnConsultaPorCdigo.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
        labelResultado.setText("");
        tf1.setText("");
        tf2.setText("");        
        try {
          Connection conexion=DriverManager.getConnection("jdbc:mysql://localhost/base1","root" ,"");
          Statement comando=conexion.createStatement();
          evaluarString(tf3.getText());
          ResultSet registro = comando.executeQuery("select descripcion,precio from articulos where codigo="+tf3.getText());
          if (registro.next()==true) {
            tf1.setText(registro.getString("descripcion"));
            tf2.setText(registro.getString("precio"));
          } else { 
            labelResultado.setText("No existe un artículo con dicho código");
          }
          conexion.close();
        }catch(MisExcepciones e3){
            JOptionPane.showMessageDialog(null, "Debe ingresar código de producto a consultar", "¡Error!", JOptionPane.WARNING_MESSAGE);
        }catch(SQLException ex){
          setTitle(ex.toString());
        }
      }
    });
    btnConsultaPorCdigo.setBounds(23, 212, 177, 23);
    contentPane.add(btnConsultaPorCdigo);
    
    lblIngreseCdigoDe = new JLabel("Ingrese código de articulo a consultar:");
    lblIngreseCdigoDe.setBounds(10, 179, 243, 14);
    contentPane.add(lblIngreseCdigoDe);
    
    tf3 = new JTextField();
    tf3.setBounds(247, 176, 86, 20);
    contentPane.add(tf3);
    tf3.setColumns(10);
    
    cargarDriver();
  }
  
  private void cargarDriver() {
    try {
      Class.forName("com.mysql.jdbc.Driver");
    }catch(Exception ex) {
      setTitle(ex.toString());
    }
  }
}