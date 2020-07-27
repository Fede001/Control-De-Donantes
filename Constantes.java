package com.fede.nextu.controldedonantes;

/**
 * Created by JHOAN on 15/09/2017.
 */

public class Constantes {

    public static final String PREFERENCE_SESION = "inicio_de_sesion";
    public static final String PREF_LLAVE_USU = "nombre_de_usuario";


    public static final String CREAR_TABLA_USUARIOS="CREATE TABLE IF NOT EXISTS USUARIOS (ID INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "NOMBRE TEXT NOT NULL UNIQUE," +
            "CONTRASENA TEXT NOT NULL UNIQUE);";

    public static final String CREAR_TABLA_DONANTES="CREATE TABLE IF NOT EXISTS DONANTES (ID INTEGER PRIMARY KEY NOT NULL UNIQUE,"
            + "NOMBRES TEXT NOT NULL," + "APELLIDOS TEXT NOT NULL," + "EDAD INTEGER NOT NULL," + "PESO INTEGER NOT NULL,"
            + "ESTATURA DOUBLE NOT NULL," + "SANGRE TEXT NOT NULL," + "RH TEXT NOT NULL);";

    public static final String INSERTAR_TABLA_USUARIOS="INSERT INTO USUARIOS (NOMBRE, CONTRASENA) VALUES (?, ?);";

    public static final String INSERTAR_TABLA_DONANTES="INSERT INTO DONANTES (ID,NOMBRES,APELLIDOS,EDAD,PESO,ESTATURA," +
            "SANGRE,RH) VALUES (?,?,?,?,?,?,?,?);";


    public static final String MOSTRAR_TABLA_USUARIOS= "SELECT * FROM USUARIOS;";
    public static final String MOSTRAR_TABLA_DONANTES= "SELECT * FROM DONANTES;";
}
