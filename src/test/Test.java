package test; // Declara que esta clase pertenece al paquete 'test'

import java.util.Arrays; // Importa la clase Arrays para usar métodos como toString()

import modelo.MergeSortSecuencial;   // Importa la clase que contiene el algoritmo Merge Sort secuencial
import modelo.MergeSortConcurrente;  // Importa la clase que contiene el algoritmo Merge Sort concurrente

public class Test { // Clase principal de prueba

    public static void main(String[] args) { // Método principal del programa

        // Arreglo de prueba para ambas versiones del algoritmo
        int[] dataSec = {5, 3, 8, 4, 2, 7, 1, 6}; // Crea un arreglo de enteros desordenado
        int[] dataConc = dataSec.clone(); // Crea una copia exacta para la versión concurrente

        // --- Versión Secuencial ---
        long startSec = System.nanoTime(); // Captura el tiempo inicial en nanosegundos
        MergeSortSecuencial.mergeSort(dataSec, 0, dataSec.length - 1); // Llama al algoritmo Merge Sort secuencial
        long endSec = System.nanoTime();   // Captura el tiempo después de ordenar

        System.out.println("Secuencial: " + Arrays.toString(dataSec)); // Muestra el arreglo ordenado secuencialmente
        System.out.println("Tiempo secuencial (ns): " + (endSec - startSec)); // Muestra el tiempo de ejecución en nanosegundos

        // --- Versión Concurrente ---
        long startConc = System.nanoTime(); // Captura el tiempo inicial para la versión concurrente
        MergeSortConcurrente sorter = new MergeSortConcurrente(dataConc, 0, dataConc.length - 1); // Crea un hilo con el algoritmo concurrente
        sorter.start(); // Inicia la ejecución del hilo (llama a run() en un hilo separado)

        try {
            sorter.join(); // Espera a que el hilo termine antes de continuar (bloquea el hilo principal)
        } catch (InterruptedException e) {
            e.printStackTrace(); // Imprime el error si el hilo fue interrumpido
        }

        long endConc = System.nanoTime(); // Captura el tiempo después de que el hilo haya terminado

        System.out.println("Concurrente: " + Arrays.toString(dataConc)); // Muestra el arreglo ordenado concurrentemente
        System.out.println("Tiempo concurrente (ns): " + (endConc - startConc)); // Muestra el tiempo de ejecución concurrente
    }
}
