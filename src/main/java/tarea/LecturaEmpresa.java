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
import java.util.stream.Collectors;

/**
 *
 * @author Cris
 */
public class LecturaEmpresa {

    private static ArrayList<Empresa> lista = new ArrayList<>();

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

    private static List<Empresa> buscarInformaticos(ArrayList<Empresa> listita) {
        List<Empresa> listaInformaticos = listita.stream()
                .filter(empleado -> empleado.getPuesto().equalsIgnoreCase("Informática P.E.S."))
                .collect(Collectors.toList());

        return listaInformaticos;
    }

    private static List<Empresa> buscarBioCoord(ArrayList<Empresa> listita) {
        List<Empresa> biologosCoordinadores = listita.stream()
                .filter(empleado -> empleado.getPuesto().equalsIgnoreCase("Biología y Geología P.E.S.") && empleado.isCoordinador())
                .collect(Collectors.toList());

        return biologosCoordinadores;

    }

    private static List<Empresa> buscarNEnDni(ArrayList<Empresa> listaEmpleados) {
        List<Empresa> EmpleadosConN = listaEmpleados.stream()
                .filter(empleado -> empleado.getDni().contains("N"))
                .sorted((e1, e2) -> e1.getEmpleado().compareTo(e2.getEmpleado()))
                .collect(Collectors.toList());

        return EmpleadosConN;
    }

    private static boolean buscarJonh(ArrayList<Empresa> listaEmpleados) {
        List<Empresa> Jonh = listaEmpleados.stream()
                .filter(empleado -> empleado.getNombre().equals("Jonh"))
                .collect(Collectors.toList());

        return !Jonh.isEmpty();
    }

    public static void main(String[] args) throws FileNotFoundException {

        String idFichero = "RelPerCen.csv";
        String[] tokens;
        String linea;

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
            //Los que se llaman John
            for (int i = 0; i < lista.size(); i++) {
                if (lista.get(i).getEmpleado().contains("Jonh")) {
                    contador2++;
                }
            }
            for (Empresa a : lista) {
                System.out.println(a);
            }
            System.out.println("Las personas que tienen John en su nombre son: " + contador2);
            System.out.println("---------------CON LA API-------------");
            System.out.println();
            //Busca los que den informática
            List<Empresa> listaInformatica = buscarInformaticos(lista);
            System.out.println("Los profesores de informática son: " + listaInformatica.size());
            listaInformatica.forEach(trabajador -> {
                System.out.println(trabajador.toString());
            });

            System.out.println();
            //Busca a los de Biología y que sean coordenadores
            List<Empresa> profeBio = buscarBioCoord(lista);
            System.out.println("Los profesores de Biología y Coordinadores son: " + profeBio.size());
            profeBio.forEach(trabajador -> {
                System.out.println(trabajador.toString());
            });

            System.out.println();
            System.out.println("----------------Los empleados con N en su DNI son: ");
            //los que tienen la N en su DNI
            List<Empresa> listaDniConN = buscarNEnDni(lista);
            listaDniConN.forEach(trabajador -> {
                System.out.println(trabajador.getEmpleado()
                        + "\t" + trabajador.getDni());
            });

            System.out.println();
            //Los que se llaman John
            Boolean personaJonh = buscarJonh(lista);
            if (!personaJonh) {
                System.out.println("Nadie de la lista tiene de nombre John");
            } else {
                System.out.println("Hay 1 o más personas con el nombre de John");

            }

        }
        escribirFichero(lista);
    }
}
