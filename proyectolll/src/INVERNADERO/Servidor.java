package INVERNADERO;



import ServidorChat.HiloParaEnviar;



import ServidorChat.HiloParaRecibir;
import INVERNADERO.Servidor;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.logging.*;
import javax.swing.*;




public class Servidor extends JFrame {
	
	public JTextField ingresoMensaje;
    public JTextArea pantallaChat;
    public JMenuItem adjuntar;
    private static ServerSocket servidor;
    private static Socket cliente;
    private static String ipCliente;// = "10.0.0.4";
    public static String usuario;
    public static Servidor ventanaServidor;
    String dir_recibo = "C:\\INVERNADEROSQL";

    //Creamos la ventana del chat del servidor
    public Servidor() {
    	setTitle("        Servidor");
   
        //Campo de Texto en la parte inferior
        ingresoMensaje = new JTextField();
        ingresoMensaje.setEditable(false);
        getContentPane().add(ingresoMensaje, BorderLayout.SOUTH);

        //Hoja del chat centrado
        pantallaChat = new JTextArea();
        pantallaChat.setFont(new Font("Arial", Font.PLAIN, 15));
        pantallaChat.setDisabledTextColor(Color.BLACK);
        pantallaChat.setSelectionColor(Color.BLACK);
        pantallaChat.setCaretColor(Color.BLACK);
        pantallaChat.setEditable(false);
        getContentPane().add(new JScrollPane(pantallaChat), BorderLayout.CENTER);
        pantallaChat.setBackground(Color.WHITE);
        pantallaChat.setForeground(Color.black);
        ingresoMensaje.setForeground(Color.black);

        //Crea opcions de Salir 
        JMenuItem salir = new JMenuItem("Salir");
        salir.setHorizontalAlignment(SwingConstants.CENTER);
        salir.setForeground(Color.BLACK);
        salir.setBackground(Color.WHITE);
       
        JMenuBar barra = new JMenuBar();
        barra.setForeground(Color.WHITE);
        setJMenuBar(barra);
        barra.add(salir);
        

        //Accion que se realiza Salir
        salir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0); //Sale de la aplicacion
            }
        });


        ipCliente = JOptionPane.showInputDialog(null, "Introduzca numero IP: ");
        setSize(542, 353);//tamano de la ventana del chat
        setVisible(true); //hace visible a la ventana

    }

    public static void main(String[] args) {
        ventanaServidor = new Servidor();
        ventanaServidor.setLocationRelativeTo(null);
        ventanaServidor.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        usuario = JOptionPane.showInputDialog(null, "Introduzca su nombre: ");// intrduce el nombre del usuario o el nick

        try {
            //Crear el socket Servidor
            servidor = new ServerSocket(11111, 100);
            ventanaServidor.mostrarMensaje("Esperando ...");
            
            while (true) {
                try {
                    //Coneccion con el cliente
                    cliente = servidor.accept();
                    ventanaServidor.mostrarMensaje("Conectado a : " + cliente.getInetAddress().getHostName());
                    ventanaServidor.habilitar(true);
                    //Correr los hilos de enviar y recibir
                    HiloParaEnviar hiloEnviarServidor = new HiloParaEnviar(cliente, ventanaServidor);
                    hiloEnviarServidor.start();
                    HiloParaRecibir hiloRecibirServidor = new HiloParaRecibir(cliente, ventanaServidor);
                    hiloRecibirServidor.start();
                } catch (IOException ex) {
                    Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
                    ventanaServidor.mostrarMensaje("No se puede conectar con el cliente");
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
            ventanaServidor.mostrarMensaje("No se encuentra IP del Servidor");
        }
    }

    public void mostrarMensaje(String mensaje) {
        pantallaChat.append(mensaje + "\n");
    }

    public void habilitar(boolean editable) {
        ingresoMensaje.setEditable(editable);
        
    }


}
