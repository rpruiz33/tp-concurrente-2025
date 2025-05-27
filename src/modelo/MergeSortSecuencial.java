package modelo; // Define que esta clase pertenece al paquete 'modelo'

public class MergeSortSecuencial { // Clase pública que implementa el algoritmo Merge Sort secuencial

    // Método principal de Merge Sort (divide el arreglo en mitades)
    public static void mergeSort(int[] array, int left, int right) {
        if (left < right) { // Verifica si hay más de un elemento en el subarreglo
            int middle = (left + right) / 2; // Calcula el índice medio para dividir el arreglo

            mergeSort(array, left, middle);       // Ordena recursivamente la mitad izquierda
            mergeSort(array, middle + 1, right);  // Ordena recursivamente la mitad derecha

            merge(array, left, middle, right); // Une (merge) ambas mitades ya ordenadas
        }
    }

    // Método que une dos mitades ordenadas en un solo arreglo ordenado
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
