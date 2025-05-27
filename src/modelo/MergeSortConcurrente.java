package modelo; // Define el paquete donde se encuentra la clase

public class MergeSortConcurrente extends Thread { // La clase extiende Thread, por lo que cada instancia puede ejecutarse como un hilo

    private int[] array; // Arreglo que se va a ordenar
    private int left;    // Índice izquierdo del subarreglo
    private int right;   // Índice derecho del subarreglo

    // Constructor que inicializa el arreglo y los índices a ordenar
    public MergeSortConcurrente(int[] array, int left, int right) {
        this.array = array;       // Guarda referencia al arreglo a ordenar
        this.left = left;         // Guarda el índice izquierdo del subarreglo
        this.right = right;       // Guarda el índice derecho del subarreglo
    }

    // Método que se ejecuta al iniciar el hilo
    @Override
    public void run() {
        if (left < right) { // Condición base de recursión: mientras haya más de un elemento
            int middle = (left + right) / 2; // Calcula el punto medio del subarreglo

            // Crea subprocesos para ordenar recursivamente la mitad izquierda y derecha
            MergeSortConcurrente leftSorter = new MergeSortConcurrente(array, left, middle);
            MergeSortConcurrente rightSorter = new MergeSortConcurrente(array, middle + 1, right);

            // Inicia ambos hilos concurrentes
            leftSorter.start();
            rightSorter.start();

            try {
                // Espera a que ambos hilos terminen antes de continuar con el merge
                leftSorter.join();
                rightSorter.join();
            } catch (InterruptedException e) {
                e.printStackTrace(); // En caso de interrupción, imprime la traza de error
            }

            // Una vez ordenadas ambas mitades, se combinan en orden
            merge(array, left, middle, right);
        }
    }

    // Método que combina dos subarreglos ya ordenados (función estándar de Merge Sort)
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
