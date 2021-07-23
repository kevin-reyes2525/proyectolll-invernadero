package ClienteChat;

import java.io.*;

import java.net.*;
import java.util.logging.*;
import ClienteChat.HiloParaRecibir;
import INVERNADERO.Cliente;

public class HiloParaRecibir extends Thread{
	
	private final Cliente ventanaCliente;
    private String mensaje;
    private ObjectInputStream entrada;
    private Socket cliente;

//Constructor del Hilo
    public HiloParaRecibir(Socket cliente, Cliente ventana) {
        this.cliente = cliente;
        this.ventanaCliente = ventana;
    }
//metodo para mostrar el mensaje

    public void mostrarMensaje(String mensaje) {
        ventanaCliente.pantallaChat.append(mensaje);
    }

    public void run() {
        try {
            entrada = new ObjectInputStream(cliente.getInputStream());
        } catch (IOException ex) {
            Logger.getLogger(HiloParaRecibir.class.getName()).log(Level.SEVERE, null, ex);
        }

        do {
//leer el mensaje y mostrarlo
            try {
                mensaje = (String) entrada.readObject();
                ventanaCliente.mostrarMensaje(mensaje);
            } catch (SocketException ex) {
            } catch (EOFException eofException) {
                ventanaCliente.mostrarMensaje("Conexion Servidor Perdida");
                mensaje ="xxxx";
            } catch (IOException ex) {
                Logger.getLogger(HiloParaRecibir.class.getName()).log(Level.SEVERE, null, ex);
                ventanaCliente.mostrarMensaje("Conexion Servidor Perdida");
                mensaje ="xxxx";
            } catch (ClassNotFoundException classNotFoundException) {
                ventanaCliente.mostrarMensaje("Objeto desconocido");
                mensaje ="xxxx";
            }

        } while (!mensaje.equals("xxxx")); //Ejecuta hasta que el server escriba TERMINATE

        try {
            entrada.close();//cierra la entrada
            cliente.close();//cierra el socket
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        ventanaCliente.mostrarMensaje("Fin de la conexion");
    }

}
