package modelo; // Declara que esta clase pertenece al paquete 'modelo'

import java.util.concurrent.RecursiveAction; // Importa la clase base para tareas sin retorno
import java.util.concurrent.ForkJoinPool;    // Importa el pool de hilos que ejecuta tareas en paralelo

// Clase que implementa el algoritmo Merge Sort de forma concurrente usando Fork/Join
public class MergeSortConcurrente extends RecursiveAction {
    private int[] array; // Arreglo que se va a ordenar
    private int left;    // Índice inicial del subarreglo a ordenar
    private int right;   // Índice final del subarreglo a ordenar

    private static final int UMBRAL = 500; // Umbral mínimo para dividir tareas; debajo de este se usa ordenamiento secuencial

    // Constructor: inicializa la tarea con el arreglo y los límites a ordenar
    public MergeSortConcurrente(int[] array, int left, int right) {
        this.array = array;   // Guarda el arreglo a ordenar
        this.left = left;     // Guarda el índice izquierdo
        this.right = right;   // Guarda el índice derecho
    }

    // Método obligatorio que ejecuta la tarea cuando se llama compute()
    @Override
    protected void compute() {
        // Si el tamaño del segmento es menor al umbral, se ordena secuencialmente
        if (right - left < UMBRAL) {
            MergeSortSecuencial.mergeSortSecuencial(array, left, right); // Ordenamiento secuencial tradicional
        } else {
            int mid = (left + right) / 2; // Calcula el índice del medio del segmento

            // Crea dos subtareas para dividir el trabajo: una para la mitad izquierda y otra para la mitad derecha
            MergeSortConcurrente leftTask = new MergeSortConcurrente(array, left, mid);       // Subtarea izquierda
            MergeSortConcurrente rightTask = new MergeSortConcurrente(array, mid + 1, right); // Subtarea derecha

            // Ejecuta ambas tareas en paralelo y espera a que ambas terminen
            invokeAll(leftTask, rightTask);

            // Después de que ambas mitades están ordenadas, se mezclan (merge)
            merge(array, left, mid, right);
        }
    }

    
    // Método estático para ordenar un arreglo completo usando ForkJoinPool
    public static void ordenar(int[] array) {
        ForkJoinPool pool = ForkJoinPool.commonPool(); // Obtiene el pool común del sistema (reutilizable, eficiente)
        pool.invoke(new MergeSortConcurrente(array, 0, array.length - 1)); // Lanza la tarea principal
    }
 // Método para mezclar dos subarreglos ordenados: array[left..mid] y array[mid+1..right]
    private static void merge(int[] array, int left, int middle, int right) {
        int n1 = middle - left + 1; // Tamaño del subarreglo izquierdo
        int n2 = right - middle;    // Tamaño del subarreglo derecho

        int[] L = new int[n1]; // Arreglo auxiliar para la mitad izquierda
        int[] R = new int[n2]; // Arreglo auxiliar para la mitad derecha

        // Copia los datos del arreglo original a los arreglos auxiliares
        System.arraycopy(array, left, L, 0, n1);
        System.arraycopy(array, middle + 1, R, 0, n2);

        int i = 0, j = 0, k = left; // Índices para recorrer L, R y array

        // Compara elementos de L y R, y los copia en orden al arreglo original
        while (i < n1 && j < n2) {
            array[k++] = (L[i] <= R[j]) ? L[i++] : R[j++];
        }

        // Copia los elementos restantes de L, si hay
        while (i < n1) {
            array[k++] = L[i++];
        }

        // Copia los elementos restantes de R, si hay
        while (j < n2) {
            array[k++] = R[j++];
        }
    }



}   



