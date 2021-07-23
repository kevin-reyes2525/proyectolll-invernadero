package INVERNADERO;

import java.awt.Color;
 
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import java.sql.*;

public class VerPlantas extends JFrame {
	public JPanel panel;
	public JLabel Manzanilla;
	public JLabel Cereza;
	public JLabel Salvia;
	public JLabel Hinojo;
	public JLabel Tomillo;
	public JLabel Anis;
	public JLabel Achiote;
	public JLabel Oregano;
	public JLabel Romero;
	public JLabel Limon;
	public JLabel Guayaba;
	public JLabel Menta;
	public JLabel Eneldo;
	public JLabel Ponsigue;
	public JLabel Boldo;
	public JTextField IngresarPlanta;
	public JTextField ClavePlanta;
	public JLabel PlantaIntroducida;
	public JLabel PlantaHierba;
	public JLabel PlantaMata;
	public JLabel PlantaArbustos;
	PreparedStatement ps;
	ResultSet res;
	public String dbName = "Register";
	public String URL = "jdbc:postgresql://localhost:5432/Planta";
	public String USER = "postgres";
	public String PASSWORD = "";
	
	public JTextField ingresoMensaje;
    public JTextArea pantallaChat;
    public JMenuItem adjuntar;
    private static ServerSocket servidor;
    private static Socket cliente;
    private static String ipServidor;// = "127.0.0.1";
    public static Cliente ventanaCliente;
    public static String usuario;
    public boolean recibir;
	
	
	
	public VerPlantas() {
		this.setTitle("Plantas");
		this.setSize(500, 500);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		InicicarPlantas();
		this.setLocationRelativeTo(null);}
	
	
	public Connection getConection() {
		Connection con = null;
		try {
			
			Class.forName("org.postgresql.Driver");
			con = (Connection) DriverManager.getConnection(URL,USER,PASSWORD);
			System.out.println("conexion exitosa");
			JOptionPane.showMessageDialog(null,"conexion exitosa");
			
		}catch(Exception e){
			System.out.println(e.getMessage());
		}return con;
	 }

	
	
	private void LimpiarCajas() {
		ClavePlanta.setText(null);
		IngresarPlanta.setText(null);
		
	}
	
	public void InicicarPlantas(){
		ColocarPlantas();
		ColocarCosas();
		TodasLasPlantas();
		PlantaNueva();
		ColocarPlantas();
		Boton2();
		Boton3();
		BotonEliminar();
		BotonModificar();
		BotonBuscar();
		ClavePlanta();
		ListaPlantas();
	}
	public void ColocarPlantas()  {
	panel = new JPanel();
	panel.setLayout(null);
	this.getContentPane().add(panel);
	}
	public void ColocarCosas() {
	JLabel plantas = new JLabel();
	plantas.setText("INVERNADERO");
	plantas.setBounds(440,20,1000,50);
	plantas.setForeground(Color.GREEN);
	plantas.setFont(new Font("arial",1,20));
	panel.add(plantas);
	}
	public void TodasLasPlantas() {
		JButton boton1 = new JButton();
		boton1.setText("Lista de plantas");
		boton1.setEnabled(false);
		boton1.setBounds(100, 100, 200, 40);
		
		panel.add(boton1);
		JLabel PlantaNuevaRepetida = new JLabel();
		PlantaNuevaRepetida.setBounds(100, 215, 100, 200);
		panel.add(PlantaNuevaRepetida);
		
		ActionListener Boton01 = new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				
					PlantaNuevaRepetida.setText(""+IngresarPlanta.getText());
					try {
					Connection con =null;
					con =  getConection();
					
					ps= con.prepareStatement("Select * FROM plantas");
					res= ps.executeQuery();
					if(res.next()) {
						JOptionPane.showMessageDialog(null, res.getString("Planta"));
					}else {
						JOptionPane.showMessageDialog(null,"NO EXISTEN DATOS");
						}
					con.close();
					}catch(Exception a) {
						System.out.println(a);
					}
					}
			
		};
		boton1.addActionListener(Boton01);
	}
	public void PlantaNueva() {
		JLabel	AnimalN = new JLabel();
		AnimalN.setBounds(450, 60, 100, 30);
		AnimalN.setText("Ingrese La Planta");
		panel.add(AnimalN);
	}		
	public void Boton2() {
		JButton Boton02 = new JButton();
		Boton02.setBounds(400, 240, 200, 40);
		Boton02.setText("Guardar");
		
		PlantaIntroducida = new JLabel();
		PlantaIntroducida.setBounds(400, 200, 250, 30);
		panel.add(PlantaIntroducida);
		panel.add(Boton02);
		JLabel PlantaRepetida2 = new JLabel();
		PlantaRepetida2.setBounds(100, 215, 100, 200);
		panel.add(PlantaRepetida2);
		
		
		
		ActionListener AgregarPlanta = new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				Connection con = null;
				try {
					con = getConection();
					ps= con.prepareStatement("INSERT INTO plantas (Planta,Id) VALUES");
					ps.setString(1,IngresarPlanta.getText());
					ps.setString(2, ClavePlanta.getText());
					int res= ps.executeUpdate();
					if(res>0) {
						JOptionPane.showMessageDialog(null, "Planta Guardada");
						LimpiarCajas();
					}else {
						JOptionPane.showMessageDialog(null, "Error al Guardar");
						LimpiarCajas();
					}
					con.close();
				}catch(Exception c) {
					System.err.print(c);
					
				}
				
			}
			
		};
		Boton02.addActionListener(AgregarPlanta);
	}
	public void ColocarPlanta() {
		IngresarPlanta = new JTextField();
		IngresarPlanta.setBounds(400, 100, 200, 40);
		panel.add(IngresarPlanta);
		
	}
	public void Boton3() {

		JButton Boton03 = new JButton();
		Boton03.setBounds(700, 100, 280, 40);
		Boton03.setText("Cosas que hacen las plantas");
		panel.add(Boton03);
		PlantaHierba= new JLabel();
		PlantaHierba.setBounds(790, 250, 100, 40);
		panel.add(PlantaHierba);
		PlantaMata = new JLabel();
		PlantaMata.setBounds(790,230, 100, 40);
		panel.add(PlantaMata);
		PlantaArbustos = new JLabel();
		PlantaArbustos.setBounds(790,200,100,40);
		panel.add(PlantaArbustos);
		
		
		ActionListener Compplantas = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PlantaHierba.setText("plantas que saborizan las comidas");
				PlantaMata.setText("plantas que aportan beneficios medicinales");
				PlantaArbustos.setText("plantas que decoran");
				
				
			}
			
		};
		Boton03.addActionListener(Compplantas);
		
	
	}

	public void BotonEliminar() {
		JButton BotonEliminar = new JButton();
		BotonEliminar.setBounds(400, 340, 200, 40);
		BotonEliminar.setText("Eliminar");
		panel.add(BotonEliminar);
		
		ActionListener BotonEliminarAcc = new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				Connection con = null;
				try {
					con = getConection();
					ps= con.prepareStatement("DELETE FROM plantas WHERE Id=?");
					ps.setString(1,ClavePlanta.getText());
					int res= ps.executeUpdate();
					if(res>0) {
						JOptionPane.showMessageDialog(null, "Planta Borrada");
						LimpiarCajas();
					}else {
						JOptionPane.showMessageDialog(null, "Error Al Borrar");
						LimpiarCajas();
					}
					con.close();
				}catch(Exception c) {
					System.err.print(c);
					
				}
				
			}
			
		};
		BotonEliminar.addActionListener(BotonEliminarAcc);
		
	}
	public void BotonModificar() {
		JButton BotonModificar = new JButton();
		BotonModificar.setBounds(400, 440, 200, 40);
		BotonModificar.setText("Modificar");
		panel.add(BotonModificar);
		ActionListener BotonModificarAcc = new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				Connection con = null;
				try {
					con = getConection();
					ps= con.prepareStatement("UPDATE plantas SET Planta=? WHERE Id=?");
					ps.setString(1,IngresarPlanta.getText());
					ps.setString(2,ClavePlanta.getText());
					int res= ps.executeUpdate();
					if(res>0) {
						JOptionPane.showMessageDialog(null, "Planta Modificada");
						LimpiarCajas();
					}else {
						JOptionPane.showMessageDialog(null, "Error al Modificar");
						LimpiarCajas();
					}
					con.close();
				}catch(Exception c) {
					System.err.print(c);
					
				}
				
			}
			
		};
		BotonModificar.addActionListener(BotonModificarAcc);
	}
	public void BotonBuscar() {
		JButton BotonBuscar = new JButton();
		BotonBuscar.setBounds(400, 540, 200, 40);
		BotonBuscar.setText("Buscar");
		panel.add(BotonBuscar);
		
		ActionListener BotonBuscarAcc = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Connection con = null;
				try {
					con=getConection();
					ps= con.prepareStatement("Select * From plantas WHERE Id = ?");
					ps.setString(1,ClavePlanta.getText() );
					res = ps.executeQuery();
					if(res.next()) {
						ClavePlanta.setText(res.getString("id"));
						IngresarPlanta.setText(res.getString("Planta"));
					}else {
						JOptionPane.showMessageDialog(null, "No existe esa Planta en este Invernadero");
					}
				}catch(Exception d){
					System.err.print(d);
				}
				
				
			}
			
		};
		BotonBuscar.addActionListener(BotonBuscarAcc);
		
	}
	public void ClavePlanta() {
		ClavePlanta = new JTextField();
		JLabel ID = new JLabel();
		ClavePlanta.setBounds(400,175, 200, 40);
		panel.add(ClavePlanta);
		ID.setBounds(500, 110, 100, 100);
		ID.setText("ID");
		panel.add(ID);
	}

	public void ListaPlantas() {
		JLabel Lista = new JLabel();
		Lista.setBounds(175, 105, 100, 100);
		Lista.setText("ID/plantas/Tipo ");
		panel.add(Lista);
		Anis = new JLabel();
		Anis.setBounds(100, 110, 100, 200);
		Anis.setText("1/Anis/Hierba");
		Cereza = new JLabel();
		Cereza.setBounds(100, 125, 100, 200);
		Cereza.setText("2/Cereza/Hierba");
		Salvia = new JLabel();
		Salvia.setBounds(100, 140, 100, 200);
		Salvia.setText("3/Salvia/Arbusto");
		Boldo = new JLabel();
		Boldo.setBounds(100, 155, 100, 200);
		Boldo.setText("4/Boldo/Hierba");
		Tomillo = new JLabel();
		Tomillo.setBounds(100, 170, 100, 200);
		Tomillo.setText("5/Tomillo/Hierba");
		Romero = new JLabel();
		Romero.setText("6/Romero/Mata");
		Romero.setBounds(100, 185, 100, 200);
		Achiote = new JLabel();
		Achiote.setText("7/Achiote/Arbusto");
		Achiote.setBounds(100, 200, 100, 200);
		Limon = new JLabel();
		Limon.setText("8/Limon/Arbusto");
		Limon.setBounds(250, 110, 100, 200);
		Manzanilla = new JLabel();
		Manzanilla.setText("9/Manzanilla/Hierba");
		Manzanilla.setBounds(250, 125, 100, 200);
		Menta = new JLabel();
		Menta.setText("10/Menta/Hierba");
		Menta.setBounds(250, 140, 100, 200);
		Oregano = new JLabel();
		Oregano.setText("11/Oregano/Arbusto");
		Oregano.setBounds(250, 155, 100, 200);
		Hinojo = new JLabel();
		Hinojo.setText("12/Hinojo/Hierba");
		Hinojo.setBounds(250, 170, 100, 200);
		Eneldo = new JLabel();
		Eneldo.setText("13/Eneldo/Mata");
		Eneldo.setBounds(250, 185, 100, 200);
		Ponsigue = new JLabel();
		Ponsigue.setText("14/Ponsigue/Mata");
		Ponsigue.setBounds(250, 200, 100, 200);
		Guayaba = new JLabel();
		Guayaba.setText("15/Guayaba/Arbusto");
		Guayaba.setBounds(250, 215, 100, 200);
		panel.add(Manzanilla); //1
		panel.add(Cereza);
		panel.add(Salvia);
		panel.add(Hinojo);
		panel.add(Tomillo);
		panel.add(Anis);
		panel.add(Achiote);
		panel.add(Oregano);
		panel.add(Romero);
		panel.add(Limon);
		panel.add(Guayaba);
		panel.add(Menta);
		panel.add(Eneldo);
		panel.add(Ponsigue);
		panel.add(Boldo);
	
	}

}
