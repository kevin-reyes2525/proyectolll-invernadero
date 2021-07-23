package ServidorChat;


import java.awt.event.*;
import java.io.*;
import java.net.*;

import INVERNADERO.Servidor;

public class HiloParaEnviar extends Thread {
	
	
	private final Servidor ventanaServidor;
    private ObjectOutputStream salida;
    private String mensaje;
    private Socket conexion;

//Constructor 
    public HiloParaEnviar(Socket conexion, final Servidor ventana) {
        this.conexion = conexion;
        this.ventanaServidor = ventana;

//Evento que ocurre al escribir en el areaTexto
        ventanaServidor.ingresoMensaje.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                mensaje = event.getActionCommand();
                enviarMensaje(mensaje); //se envia el mensaje
                ventanaServidor.ingresoMensaje.setText(""); //el area donde se ingresa el texto se lo borra para poder ingresar el nuevo texto
            }
        });
    }

//enviar objeto a cliente 
    private void enviarMensaje(String mensaje) {
        try {
            salida.writeObject(ventanaServidor.usuario + " dice: " + mensaje);
            salida.flush(); //flush salida a cliente //borra el buffer
            ventanaServidor.mostrarMensaje("YO: " + mensaje);
        } catch (IOException ioException) {
            ventanaServidor.mostrarMensaje("Cliente perdido");
        }
    }

//manipula areaPantalla en el hilo despachador de eventos
    public void mostrarMensaje(String mensaje) {
        ventanaServidor.pantallaChat.append(mensaje);
    }

    public void run() {
        try {
            salida = new ObjectOutputStream(conexion.getOutputStream());
            salida.flush(); //flush salida a cliente //borra el buffer
        } catch (SocketException ex) {
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } catch (NullPointerException ex) {
        }
    }

}
