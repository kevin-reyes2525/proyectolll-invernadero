package INVERNADERO;


import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.logging.*;
import javax.swing.*;

import ClienteChat.HiloParaEnviar;
import ClienteChat.HiloParaRecibir;
import INVERNADERO.Cliente;


public class Cliente extends JFrame {
	
	
	 public JTextField ingresoMensaje;
	    public JTextArea pantallaChat;
	    public JMenuItem adjuntar;
	    private static ServerSocket servidor;
	    private static Socket cliente;
	    private static String ipServidor;// = "127.0.0.1";
	    public static Cliente ventanaCliente;
	    public static String usuario;
	    public boolean recibir;

	//Creamos la ventana del chat del cliente
	    public Cliente() {
	    	setTitle("                                                                        Cliente");
	        
	//Campo de Texto en la parte inferior
	        ingresoMensaje = new JTextField();
	        ingresoMensaje.setFont(new Font("Arial", Font.PLAIN, 11));
	        ingresoMensaje.setEditable(false);
	        getContentPane().add(ingresoMensaje, BorderLayout.SOUTH);

	//Hoja del chat centrado
	        pantallaChat = new JTextArea();
	        pantallaChat.setFont(new Font("Arial", Font.PLAIN, 15));
	        pantallaChat.setEditable(false);
	        getContentPane().add(new JScrollPane(pantallaChat), BorderLayout.CENTER);
	        pantallaChat.setBackground(Color.white);
	        pantallaChat.setForeground(Color.black);
	        ingresoMensaje.setForeground(Color.BLACK);

	//Crea opcione de Salir
	        JMenuItem salir = new JMenuItem("Salir");
	        salir.setFont(new Font("Arial", Font.PLAIN, 15));
	        salir.setBackground(Color.WHITE);
	        salir.setForeground(Color.BLACK);
	       
	        JMenuBar barra = new JMenuBar();
	        setJMenuBar(barra);
	        barra.add(salir);
	        

	//Accion que se realiza Salir
	        salir.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	                System.exit(0); //Sale de la aplicacion
	            }
	        });


	        ipServidor = JOptionPane.showInputDialog(null, "Introduzca IP del servidor: ");
	        setSize(574, 341);//tamano de la ventana del chat
	        setVisible(true);//hace visible a la ventana
	    }

	    public static void main(String[] args) {
	        ventanaCliente = new Cliente();
	        ventanaCliente.setLocationRelativeTo(null);
	        ventanaCliente.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        usuario = JOptionPane.showInputDialog(null, "Introduzca su nombre: ");// intrduce el nombre del usuario o el nick

	        try {
	//Coneccion con el cliente
	            ventanaCliente.mostrarMensaje("Buscando Servidor ...");
	            cliente = new Socket(InetAddress.getByName(ipServidor), 11111);
	            ventanaCliente.mostrarMensaje("Conectado a :" + cliente.getInetAddress().getHostName());
	            ventanaCliente.habilitar(true);

	//Correr los hilos de enviar y recibir
	            HiloParaEnviar hiloEnviarCliente = new HiloParaEnviar(cliente, ventanaCliente);
	            hiloEnviarCliente.start();
	            HiloParaRecibir hiloRecibirCliente = new HiloParaRecibir(cliente, ventanaCliente);
	            hiloRecibirCliente.start();
	        } catch (IOException ex) {
	            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
	            ventanaCliente.mostrarMensaje("No se encuentra al Servidor");
	        }
	    }

	    public void mostrarMensaje(String mensaje) {
	        pantallaChat.append(mensaje + "\n");
	    }

	    public void habilitar(boolean editable) {
	        ingresoMensaje.setEditable(editable);
	        
	    }

}
