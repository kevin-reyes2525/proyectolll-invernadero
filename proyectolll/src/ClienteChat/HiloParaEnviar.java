package ClienteChat;

import java.awt.event.*;

import java.io.*;
import java.net.*;

import INVERNADERO.Cliente;


public class HiloParaEnviar extends Thread{
	
	
	private final Cliente ventanaCliente;
    private ObjectOutputStream salida;
    private String mensaje;
    private Socket conexion;
    
//Constructor    

    public HiloParaEnviar(Socket conexion, final Cliente ventana) {
        this.conexion = conexion;
        this.ventanaCliente = ventana;
        

//Evento que ocurre al escribir en el areaTexto
        ventanaCliente.ingresoMensaje.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                mensaje = event.getActionCommand();
                enviarMensaje(mensaje); //se envia el mensaje
                ventanaCliente.ingresoMensaje.setText(""); //el area donde se ingresa el texto se lo borra para poder ingresar el nuevo texto
            }
        });
    }

//enviar objeto a cliente 
    private void enviarMensaje(String mensaje) {
        try {
            salida.writeObject(ventanaCliente.usuario + " dice: " + mensaje);
            salida.flush(); //flush salida a cliente //borra el buffer
            ventanaCliente.mostrarMensaje("YO: " + mensaje);
        } catch (IOException ioException) {
            ventanaCliente.mostrarMensaje("Servidor Perdido");
        }
    }

//manipula areaPantalla en el hilo despachador de eventos
    public void mostrarMensaje(String mensaje) {
        ventanaCliente.pantallaChat.append(mensaje);
    }

    public void run() {
        try {
            salida = new ObjectOutputStream(conexion.getOutputStream());
            salida.flush();
        } catch (SocketException ex) {
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } catch (NullPointerException ex) {
        }
    }
	

}
