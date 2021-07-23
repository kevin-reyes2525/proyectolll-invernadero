package ServidorChat;

import java.io.*;

import java.net.*;
import java.util.logging.*;
import ServidorChat.HiloParaRecibir;
import INVERNADERO.Servidor;

public class HiloParaRecibir extends Thread {
	
	private final Servidor ventanaServidor;
    private String mensaje;
    private ObjectInputStream entrada;
    private Socket cliente;

    //Constructor del Hilo
    public HiloParaRecibir(Socket cliente, Servidor ventana) {
        this.cliente = cliente;
        this.ventanaServidor = ventana;
    }

    public void mostrarMensaje(String mensaje) {
        ventanaServidor.pantallaChat.append(mensaje);
    }

    public void run() {
        try {
            entrada = new ObjectInputStream(cliente.getInputStream());
        } catch (IOException ex) {
            Logger.getLogger(HiloParaRecibir.class.getName()).log(Level.SEVERE, null, ex);
            ventanaServidor.mostrarMensaje("Error al enviar Mensaje");
        }

        //leer el mensaje y mostrarlo 
        do {
            try {
                mensaje = (String) entrada.readObject();
                ventanaServidor.mostrarMensaje(mensaje);
            } catch (SocketException ex) {
                ventanaServidor.mostrarMensaje("Conexion Cliente Perdida");
                mensaje = "xxxx";
                //break;
            } catch (EOFException eofException) {
                ventanaServidor.mostrarMensaje("Conexion Cliente Perdida");
                mensaje = "xxxx";
                //break;
            } catch (IOException ex) {
                Logger.getLogger(HiloParaRecibir.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException classNotFoundException) {
                ventanaServidor.mostrarMensaje("Objeto desconocido");
            }
        } //Cierra el socket y la entrada
        while (!mensaje.equals("xxxx"));

        try {
            entrada.close();// se cierra la entrada
            cliente.close();// se cierra el socket
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        ventanaServidor.mostrarMensaje("Fin de la conexion");
    }

}
