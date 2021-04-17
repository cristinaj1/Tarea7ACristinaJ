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
    private static ArrayList<POJO> empleados(ArrayList<POJO> lista) {
        ArrayList<POJO> empleadosAntiguos = new ArrayList<>();
        LocalDate actual = LocalDate.now();

        //Para que mire la lista de empleados completa
        for (int i = 0; i < lista.size(); i++) {
            LocalDate fechaInicio = lista.get(i).getfInicio();
            LocalDate fechaFin = lista.get(i).getfFin();

            //para añadir en una lista a los antiguos
            if (fechaInicio == null) {
                long antiguedad = ChronoUnit.YEARS.between(fechaInicio, fechaFin);
                if (antiguedad > 20) {
                    empleadosAntiguos.add(lista.get(i));
                }
            } else {
                long antiguedad2 = ChronoUnit.YEARS.between(fechaInicio, actual);
                if (antiguedad2 > 20) {
                    empleadosAntiguos.add(lista.get(i));
                }
            }
        }
        return empleadosAntiguos;
    }

    private static void escribirFichero(ArrayList<POJO> lista) {
        ArrayList<POJO> empleados = new ArrayList<>(empleados(lista));
        String idFichero2 = "TrabajadoresAntiguos.csv";

        try ( BufferedWriter flujo = new BufferedWriter(new FileWriter(idFichero2))) {
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
        ArrayList<POJO> lista = new ArrayList<>();

        try ( Scanner datosFichero = new Scanner(new File(idFichero), "ISO-8859-1")) {
            datosFichero.nextLine();
            while (datosFichero.hasNextLine()) {
                linea = datosFichero.nextLine();
                tokens = linea.split(",");
                POJO prueba1 = new POJO();

                //Quita comillas
                prueba1.setEmpleado(comilla(tokens[0] + tokens[1]));
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
                if (comilla(tokens[7]).equalsIgnoreCase("si")) {
                    prueba1.setEvaluador(true);
                } else {
                    prueba1.setEvaluador(false);
                }
                if (comilla(tokens[8]).equalsIgnoreCase("si")) {
                    prueba1.setCoordinador(true);
                } else {
                    prueba1.setCoordinador(false);
                }
                lista.add(prueba1);
            }
        }
        escribirFichero(lista);
    }
}
