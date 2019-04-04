package hw3.hash;

import java.util.List;

public class OomageTestUtility {
    public static boolean haveNiceHashCodeSpread(List<Oomage> oomages, int M) {
        int[] buckets = new int[M];
        double low = oomages.size() / 50.0;
        double high = oomages.size() / 2.5;
        for (Oomage oomage : oomages) {
            int bucketNum = (oomage.hashCode() & 0x7FFFFFFF) % M;
            buckets[bucketNum]++;
        }
        for (int num : buckets) {
            if (num < low || num > high) {
                return false;
            }
        }
        return true;
    }
}
