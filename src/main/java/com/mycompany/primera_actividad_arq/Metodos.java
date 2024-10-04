/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.primera_actividad_arq;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author ADMIN
 */

public class Metodos {
    private ArrayList<Alumno> listaAlumnos;

    // Constructor que inicializa el ArrayList
    public Metodos() {
        listaAlumnos = new ArrayList<>();
    }

    // Método para agregar un Alumno al ArrayList
    public void agregar(Alumno alumno) {
        listaAlumnos.add(alumno);
        System.out.println("Alumno agregado: " + alumno);
    }

    // Método para eliminar un Alumno del ArrayList en una posición específica
    public void eliminar(int index) {
        if (index >= 0 && index < listaAlumnos.size()) {
            Alumno eliminado = listaAlumnos.remove(index);
            System.out.println("Alumno eliminado: " + eliminado);
        } else {
            System.out.println("Índice fuera de rango");
        }
    }

    // Método para modificar un campo específico de un alumno
    public void modificar(int index) {
        if (index >= 0 && index < listaAlumnos.size()) {
            Alumno alumno = listaAlumnos.get(index);
            Scanner sc = new Scanner(System.in);
            System.out.println("Seleccione el campo a modificar:\n1. Nombre\n2. Apellido\n3. Código\n4. DNI\n5. Teléfono\n6. Fecha de Nacimiento");
            int opcion = sc.nextInt();
            sc.nextLine(); // Limpiar el buffer
            switch (opcion) {
                case 1:
                    System.out.println("Ingrese nuevo nombre:");
                    alumno.setNombre(sc.nextLine());
                    break;
                case 2:
                    System.out.println("Ingrese nuevo apellido:");
                    alumno.setApellido(sc.nextLine());
                    break;
                case 3:
                    System.out.println("Ingrese nuevo código:");
                    alumno.setCodigo(sc.nextLine());
                    break;
                case 4:
                    System.out.println("Ingrese nuevo DNI:");
                    alumno.setDni(sc.nextLine());
                    break;
                case 5:
                    System.out.println("Ingrese nuevo teléfono:");
                    alumno.setTelefono(sc.nextLine());
                    break;
                case 6:
                    System.out.println("Ingrese nueva fecha de nacimiento (yyyy-mm-dd):");
                    // Aquí puedes hacer una conversión adecuada de String a Date
                    // Dependiendo de cómo manejes las fechas en tu programa.
                    // alumno.setFechaNacimiento(fechaConvertida);
                    break;
                default:
                    System.out.println("Opción no válida");
                    break;
            }
            System.out.println("Alumno modificado: " + alumno);
        } else {
            System.out.println("Índice fuera de rango");
        }
    }

    // Método para buscar por la primera letra de nombre, apellido, código o dni
    public void buscar(String letra) {
        letra = letra.toLowerCase(); // Convertimos a minúsculas para evitar problemas con mayúsculas
        boolean encontrado = false;
        
        for (Alumno alumno : listaAlumnos) {
            if (alumno.getNombre().toLowerCase().startsWith(letra) || 
                alumno.getApellido().toLowerCase().startsWith(letra) || 
                alumno.getCodigo().toLowerCase().startsWith(letra) || 
                alumno.getDni().toLowerCase().startsWith(letra)) {
                System.out.println("Alumno encontrado: " + alumno);
                encontrado = true;
            }
        }
        
        if (!encontrado) {
            System.out.println("No se encontraron alumnos con la letra: " + letra);
        }
    }

    // Método para eliminar todos los alumnos del ArrayList
    public void clear() {
        listaAlumnos.clear();
        System.out.println("Todos los alumnos han sido eliminados.");
    }

    // Método para mostrar todos los alumnos
    public void mostrarAlumnos() {
        if (listaAlumnos.isEmpty()) {
            System.out.println("No hay alumnos en la lista.");
        } else {
            for (Alumno alumno : listaAlumnos) {
                System.out.println(alumno);
            }
        }
    }

    // Método para obtener la lista de alumnos
    public ArrayList<Alumno> getListaAlumnos() {
        return listaAlumnos;
    }
}
