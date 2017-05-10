package sk.elko.bioinfo.hidden;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class FrequentWords {

    public static Map<Genome, Integer> slow(final Genome genome, final int k) {
        if (genome.length() == 0 || k <= 0 || genome.length() < k) {
            throw new IllegalArgumentException();
        }

        Map<Integer, Integer> counts = new HashMap<Integer, Integer>();
        int maxCount = 0;

        for (int i = 0; i <= (genome.length() - k); i++) {
            Genome pattern = genome.substring(i, i + k);
            int count = genome.getExactOccurences(pattern);
            counts.put(i, count);
            maxCount = maxCount < count ? count : maxCount;
        }

        Map<Genome, Integer> kmers = new HashMap<Genome, Integer>();
        for (int i = 0; i <= (genome.length() - k); i++) {
            if (counts.get(i).intValue() == maxCount) {
                Genome kmer = genome.substring(i, i + k);
                kmers.put(kmer, maxCount);
            }
        }

        return kmers;
    }

    public static Map<Genome, Integer> medium(final Genome genome, final int k) {
        if (genome.length() == 0 || k <= 0 || genome.length() < k) {
            throw new IllegalArgumentException();
        }

        List<Integer> frequencies = ComputingFrequencies.list(genome, k);

        int maxCount = 0;
        for (int number : frequencies) {
            if (maxCount < number) {
                maxCount = number;
            }
        }
        // TODO frequencies.forEach(number -> (maxCount < number ? maxCount = number));

        Map<Genome, Integer> kmers = new HashMap<Genome, Integer>();
        for (int i = 0; i < frequencies.size(); i++) {
            if (frequencies.get(i).intValue() == maxCount) {
                Genome kmer = new Genome(PatternNumberConverter.getNumberToPattern(i, k));
                kmers.put(kmer, maxCount);
            }
        }

        return kmers;
    }

    public static Map<Genome, Integer> fast(final Genome genome, final int k) {
        if (genome.length() == 0 || k <= 0 || genome.length() < k) {
            throw new IllegalArgumentException();
        }

        long[] index = new long[genome.length() - k + 1];
        int[] count = new int[genome.length() - k + 1];

        for (int i = 0; i <= (genome.length() - k); i++) {
            int limit = Math.min(genome.length(), i + k);
            // System.out.println("length=" + genome.length() + ", index=" + index + ", k=" + k + ", limit=" + limit);
            String pattern = genome.substring(i, limit).getText();
            index[i] = PatternNumberConverter.getPatternToNumber(pattern);
            count[i] = 1;
        }

        Arrays.sort(index);
        for (int i = 1; i <= (genome.length() - k); i++) {
            if (index[i] == index[i - 1]) {
                count[i] = count[i - 1] + 1;
            }
        }

        int maxCount = 0;
        for (int number : count) {
            if (maxCount < number) {
                maxCount = number;
            }
        }
        // TODO frequencies.forEach(number -> (maxCount < number ? maxCount = number));

        Map<Genome, Integer> kmers = new HashMap<>();
        for (int i = 0; i <= (genome.length() - k); i++) {
            if (count[i] == maxCount) {
                String kmer = PatternNumberConverter.getNumberToPattern(index[i], k);
                kmers.put(new Genome(kmer), maxCount);
            }
        }

        return kmers;
    }

    public static Set<Genome> withMismatches(final Genome genome, final int k, final int d) {
        if (genome.length() == 0 || d < 0 || genome.length() < d || genome.length() < k) {
            throw new IllegalArgumentException();
        }

        Set<Genome> words = new HashSet<>();
        int maximum = 0;

        for (int index = 0; index <= (genome.length() - k); index++) {
            Genome pattern = genome.substring(index, index + k);
            List<Integer> count = pattern.getApproximatePatternMatching(genome, d);

            if (maximum < count.size()) {
                words.clear();
                maximum = count.size();
            }
            if (maximum == count.size()) {
                words.add(pattern);
            }
        }

        return words;
    }

}
