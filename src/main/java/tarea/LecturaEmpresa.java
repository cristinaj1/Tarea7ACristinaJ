/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tarea;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Cris
 */
public class LecturaEmpresa {

    //Quita las comillas del principio y del final 
    private static String comilla(String s) {
        String quitar = s.substring(1, s.length() - 1);
        return quitar;
    }

    //Guarda a los empleados que llevan más de 20 años en una lista de objetos
    private static ArrayList<Empresa> empleados(ArrayList<Empresa> lista) {
        ArrayList<Empresa> empleadosAntiguos = new ArrayList<>();
        LocalDate actual = LocalDate.now();

        //Para que mire la lista de empleados completa
        for (int i = 0; i < lista.size(); i++) {
            LocalDate fechaInicio = lista.get(i).getfInicio();
            LocalDate fechaFin = lista.get(i).getfFin();

            //para añadir en una lista a los antiguos
            if (fechaFin == null) {
                //POner el código que ha puesto Vico
                long antiguedad2 = ChronoUnit.YEARS.between(fechaInicio, actual);
                if (antiguedad2 > 20) {
                    empleadosAntiguos.add(lista.get(i));
                }
            }
        }
        return empleadosAntiguos;
    }

    private static void escribirFichero(ArrayList<Empresa> lista) {
        ArrayList<Empresa> empleados = new ArrayList<>(empleados(lista));
        String idFichero2 = "TrabajadoresAntiguos.csv";

        try ( BufferedWriter flujo = new BufferedWriter(new FileWriter(idFichero2))) {
            flujo.write("Empleado/a,DNI/Pasaporte,Puesto,Fecha de toma de posesión,Fecha de cese,Teléfono,Evaluador,Coordinador");
            flujo.newLine();
            for (int i = 0; i < empleados.size(); i++) {

                flujo.write(empleados.get(i).getEmpleado() + ",");
                flujo.write(empleados.get(i).getDni() + ",");
                flujo.write(empleados.get(i).getPuesto() + ",");

                DateTimeFormatter prueba = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                String fechain = empleados.get(i).getfInicio().format(prueba);
                flujo.write(fechain + ",");

                if (empleados.get(i).getfFin() == null) {
                    flujo.write("sin datos,");
                } else {
                    flujo.write(empleados.get(i).getfFin().format(prueba) + ",");
                }
                if (empleados.get(i).getTelefono().isEmpty()) {
                    flujo.write("sin datos,");
                } else {
                    flujo.write(empleados.get(i).getTelefono() + ",");
                }
                flujo.write(empleados.get(i).isEvaluador() + ",");
                flujo.write(empleados.get(i).isCoordinador() + ",");
                flujo.newLine();
            }
            flujo.flush();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) throws FileNotFoundException {

        String idFichero = "RelPerCen.csv";
        String[] tokens;
        String linea;
        ArrayList<Empresa> lista = new ArrayList<>();
        int contador = 0;
        int contador2 = 0;
        ArrayList<String> listaDni = new ArrayList<>();

        try ( Scanner datosFichero = new Scanner(new File(idFichero), "ISO-8859-1")) {
            datosFichero.nextLine();
            while (datosFichero.hasNextLine()) {
                linea = datosFichero.nextLine();
                tokens = linea.split(",");
                Empresa prueba1 = new Empresa();

                //Quita comillas(Empleado no lo separo de nombre y apellidos ya que no lo especifica)
                prueba1.setEmpleado(comilla(tokens[0] + tokens[1]));
                prueba1.setNombre(tokens[1]);
                prueba1.setDni(comilla(tokens[2]));
                prueba1.setPuesto(comilla(tokens[3]));
                String inicio = comilla(tokens[4]);
                prueba1.setfInicio(LocalDate.parse(inicio, DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                String fin = comilla(tokens[5]);

                if (fin.length() > 0) {
                    prueba1.setfFin(LocalDate.parse(fin, DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                }
                if (tokens[6].length() > 0) {
                    prueba1.setTelefono(comilla(tokens[6]));
                }

                //Funciona con el no pero no funciona con el sí(Preguntar)
                if (comilla(tokens[7]).equalsIgnoreCase("no")) {
                    prueba1.setEvaluador(false);
                } else {
                    prueba1.setEvaluador(true);
                }
                if (comilla(tokens[8]).equalsIgnoreCase("no")) {
                    prueba1.setCoordinador(false);
                } else {
                    prueba1.setCoordinador(true);
                }
                lista.add(prueba1);
                if (prueba1.getPuesto().equalsIgnoreCase("Informática P.E.S.")) {
                    contador++;
                }
                if (prueba1.getPuesto().equalsIgnoreCase("Biología y Geología P.E.S.")) {
                    if (prueba1.isCoordinador() == true) {
                        System.out.println("----------El profesor que es coordinador y de Bilogía es: " + prueba1);
                    }
                }
                if (prueba1.getDni().contains("N")) {
                    listaDni.add(prueba1.getEmpleado() + "\t" + prueba1.getDni());
                }

            }
            for (Empresa a : lista) {
                System.out.println(a);
            }
            //Los Dni que contiene N organizados alfabéticamente
            System.out.println("Organizados alfabéticamente");
            Collections.sort(listaDni);
            for (String z : listaDni) {
                System.out.println(z);
            }
            //Profesores de informática
            System.out.println("Los profesores de informática son: " + contador);
            for (int i = 0; i < lista.size(); i++) {
                if (lista.get(i).getEmpleado().contains("Jonh")) {
                    contador2++;
                }
            }
            for (Empresa a : lista) {
                System.out.println(a);
            }
            System.out.println("Las personas que tienen John en su nombre son: " + contador2);
//            System.out.println("------------------PUNTO 1-----------------");
//            System.out.println();
//
//            //Preguntar a Vico mañana todo
//            
////      PUNTO 2:
////      Saber si algún profesor/a de Biología es también coordinador. CON API
//            System.out.println();
//            System.out.println("------------------PUNTO 2-----------------");
//            System.out.println();
//            List<Empresa> lBioCoord = buscarProfesoresBioCoord(lista);
//            System.out.println("Hay " + lBioCoord.size() + " profesores de biologia que son coordinadores");
//            lBioCoord.forEach(trabajador -> {
//                System.out.println(trabajador.toString());
//            });
//
////      PUNTO 3:
////      Obtener una lista ordenada alfabéticamente con todos los apellidos de 
////      los empleados cuyo NIF contenga la letra N. CON API
//            System.out.println();
//            System.out.println("------------------PUNTO 3-----------------");
//            System.out.println();
//
//            List<Empresa> listaDNIN = buscarN(lista);
//            listaDNIN.forEach(trabajador -> {
//                System.out.println(trabajador.getApellidos() + "," + trabajador.getNombre()
//                        + "\t" + prueba1.getDni());
//            });
//
////      PUNTO 4:
////      Verificar que ningún profesor se llama "Jonh". CON API
//            System.out.println();
//            System.out.println("------------------PUNTO 4-----------------");
//            System.out.println();
//
//            Jonh = buscarJonh(Empresa);
//
//            if (Jonh) {
//                System.out.println("Si hay alguien que se llama Jonh");
//            } else {
//                System.out.println("No hay nadie que se llama Jonh");
//            }
        }
        escribirFichero(lista);
    }
}
