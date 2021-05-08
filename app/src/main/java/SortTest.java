/**
 * @author：张宝全
 * @date：2021/1/27
 * @Description：
 * @Reviser：
 * @RevisionTime：
 * @RevisionDescription：
 */

public class SortTest {

    /**
     * 堆排序
     *
     * @param arr 待排序数组
     */
    private void sort(int[] arr) {
        // 1、构建大顶堆，从左至右
        for (int i = arr.length / 2 - 1; i >= 0; i--) {
            adjustHeap(arr, i, arr.length);
        }
    }

    /**
     * 调整大顶堆（仅是调整过程，建立在大顶堆已构建的基础上）
     *
     * @param arr
     * @param i
     * @param length
     */
    private void adjustHeap(int[] arr, int i, int length) {
        // 1、先取出第一个元素
        int temp = arr[i];
        // 大顶堆：arr[i] >= arr[2i+1] && arr[i] >= arr[2i+2]
        // 从i结点的左子结点开始，也就是2i+1处开始
        for (int k = 2 * i + 1; k < length; k = k * 2 + 1) {

        }
    }

}
