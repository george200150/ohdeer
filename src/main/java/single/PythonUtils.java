package single;

public class PythonUtils {
    public static int[] range(int len) {
        int[] res = new int[len];
        for (int i = 0; i < len; i++) {
            res[i] = i;
        }
        return res;
    }
}
