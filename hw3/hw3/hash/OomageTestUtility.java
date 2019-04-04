package hw3.hash;

import java.util.HashSet;
import java.util.List;

public class OomageTestUtility {
    public static boolean haveNiceHashCodeSpread(List<Oomage> oomages, int M) {
        double low = oomages.size() / 50.0;
        double high = oomages.size() / 2.5;
        int[] buckets = new int[M];
        HashSet<Oomage> set = new HashSet<>();
        for (Oomage oomage : oomages) {
            int bucketNum = (oomage.hashCode() & 0x7FFFFFFF) % M;
            if (!set.contains(oomage)) {
                set.add(oomage);
                buckets[bucketNum]++;
            }
        }
        for (int num : buckets) {
            if (num != 0 && (num < low || num > high)) {
                return false;
            }
        }
        return true;
    }
}
