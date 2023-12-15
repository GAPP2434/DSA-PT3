import java.util.*;

public class QuickSort {
    private static final int N = 10000;
    private static final int NUM = 10;

    public static void insertionSort(int[] arr, int low, int high) {
        for (int i = low + 1; i <= high; i++) {
            int value = arr[i];
            int j = i;

            while (j > low && arr[j - 1] > value) {
                arr[j] = arr[j - 1];
                j--;
            }

            arr[j] = value;
        }
    }

    public static int partition(int[] a, int low, int high, int pivotChoice) {
        int pivot;
        if (pivotChoice == 1) {
            // Use the leftmost element as the pivot
            pivot = a[low];
        } else if (pivotChoice == 2) {
            // Use the middle element as the pivot
            pivot = a[low + (high - low) / 2];
        } else {
            // Use the rightmost element as the pivot (default)
            pivot = a[high];
        }

        int pIndex = low;

        for (int i = low; i < high; i++) {
            if (pivotChoice == 1 && a[i] >= pivot) {
                continue;
            } else if (pivotChoice == 2 && a[i] < pivot) {
                continue;
            } else if (a[i] <= pivot) {
                int temp = a[i];
                a[i] = a[pIndex];
                a[pIndex] = temp;
                pIndex++;
            }
        }

        int temp = a[high];
        a[high] = a[pIndex];
        a[pIndex] = temp;

        return pIndex;
    }

    public static void quicksort(int[] a, int low, int high, int pivotChoice) {
        if (low < high) {
            int pivot = partition(a, low, high, pivotChoice);
            quicksort(a, low, pivot - 1, pivotChoice);
            quicksort(a, pivot + 1, high, pivotChoice);
        }
    }

    public static void optimizedQuicksort(int[] A, int low, int high, int pivotChoice) {
        while (low < high) {
            if (high - low < 10) {
                insertionSort(A, low, high);
                break;
            } else {
                int pivot = partition(A, low, high, pivotChoice);

                if (pivot - low < high - pivot) {
                    optimizedQuicksort(A, low, pivot - 1, pivotChoice);
                    low = pivot + 1;
                } else {
                    optimizedQuicksort(A, pivot + 1, high, pivotChoice);
                    high = pivot - 1;
                }
            }
        }
    }

    public static void main(String[] args) {
        int[] arr = new int[N];
        long begin, end;
        long t1 = 0, t2 = 0;

        Scanner input = new Scanner(System.in);
        System.out.println("Choose pivot:\n1 (left most element)\n2 (middle element)\nOther Numbers (right most element)");
        System.out.print("Answer: ");
        int pivotChoice = input.nextInt();

        for (int i = 1; i <= NUM; i++) {
            Arrays.fill(arr, new Random().nextInt());
            int[] dup = Arrays.copyOf(arr, N);

            begin = System.nanoTime();
            quicksort(arr, 0, N - 1, pivotChoice);
            end = System.nanoTime();
            t1 += (end - begin);

            begin = System.nanoTime();
            optimizedQuicksort(dup, 0, N - 1, pivotChoice);
            end = System.nanoTime();
            t2 += (end - begin);
        }
        System.out.println("The average time taken by the non-optimized Quicksort: " + t1 / NUM + "ns");
        System.out.println("The average time taken by the optimized Quicksort: " + t2 / NUM + "ns");
    }
}
