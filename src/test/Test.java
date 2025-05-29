package test; // Declara que esta clase pertenece al paquete 'test'

import java.util.Arrays; // Importa Arrays para mostrar arreglos como texto
import java.util.Random; // Importa Random para generar números aleatorios
import modelo.MergeSortSecuencial; // Importa la clase del algoritmo secuencial
import modelo.MergeSortConcurrente; // Importa la clase del algoritmo concurrente

public class Test { // Declaración de la clase principal

    public static void main(String[] args) { // Método principal

        int size = 10; // Tamaño por defecto del arreglo si no se pasa argumento

        // Verifica si se pasó un argumento (como tamaño del arreglo)
        if (args.length > 0) {
            try {
                size = Integer.parseInt(args[0]); // Intenta convertir el argumento a entero
            } catch (NumberFormatException e) {
                System.out.println("Argumento inválido, usando tamaño por defecto de 10."); // Muestra mensaje si falla
            }
        }

        // Genera un arreglo de enteros aleatorios del tamaño especificado
        int[] dataSec = generarArregloAleatorio(size); // Arreglo para la versión secuencial
        int[] dataConc = dataSec.clone(); // Copia exacta del arreglo para la versión concurrente

        // --- Versión Secuencial ---
        long startSec = System.currentTimeMillis(); // Toma el tiempo antes de ejecutar el algoritmo (en milisegundos)
        MergeSortSecuencial.mergeSortSecuencial(dataSec, 0, dataSec.length - 1); // Ejecuta Merge Sort secuencial
        long endSec = System.currentTimeMillis(); // Toma el tiempo después de ejecutar

        // Muestra el arreglo ordenado y el tiempo de ejecución
        System.out.println("Secuencial: " + Arrays.toString(dataSec));
        System.out.println("Tiempo secuencial (ms): " + (endSec - startSec));

        // --- Versión Concurrente ---
     // --- Versión Concurrente ---
        long startConc = System.currentTimeMillis(); // Toma el tiempo inicial para el concurrente

        MergeSortConcurrente.ordenar(dataConc); // ✅ Usamos el método estático que maneja el ForkJoinPool

        long endConc = System.currentTimeMillis(); // Tiempo final

        // Muestra el arreglo ordenado y el tiempo de ejecución concurrente
        System.out.println("Concurrente: " + Arrays.toString(dataConc));
        System.out.println("Tiempo concurrente (ms): " + (endConc - startConc));
    }

    // Método auxiliar para generar un arreglo aleatorio de tamaño dado
    public static int[] generarArregloAleatorio(int size) {
        Random rand = new Random(); // Crea una instancia de generador aleatorio
        int[] arreglo = new int[size]; // Declara el arreglo

        for (int i = 0; i < size; i++) {
            arreglo[i] = rand.nextInt(1000); // Llena cada posición con un número aleatorio entre 0 y 999
        }

        return arreglo; // Devuelve el arreglo generado
    }
}
