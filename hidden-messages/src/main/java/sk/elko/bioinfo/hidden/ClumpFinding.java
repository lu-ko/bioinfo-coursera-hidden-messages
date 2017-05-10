package sk.elko.bioinfo.hidden;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ClumpFinding {

    public static Set<Genome> slow(final Genome genome, final int k, final int L, final int t) {
        if (genome.length() == 0 || k == 0 || L == 0 || t == 0) {
            throw new IllegalArgumentException();
        }

        Set<Genome> ltClumps = new HashSet<>();

        for (int index = 0; index <= (genome.length() - L); index++) {
            final Genome subgenome = genome.substring(index, index + L);
            // Map<Genome, Integer> kmers = FrequentWords.slow(subgenome, k);
            // Map<Genome, Integer> kmers = FrequentWords.medium(subgenome, k);
            Map<Genome, Integer> kmers = FrequentWords.fast(subgenome, k);

            for (Genome kmer : kmers.keySet()) {
                if (kmers.get(kmer).intValue() >= t) {
                    ltClumps.add(kmer);
                }
            }
        }

        return ltClumps;
    }

    public static Set<Genome> fast(final Genome genome, final int k, final int L, final int t) {
        if (genome.length() == 0 || k == 0 || L == 0 || t == 0) {
            throw new IllegalArgumentException();
        }

        int limit = Double.valueOf(Math.pow(4, k)).intValue();
        int[] clump = new int[limit];

        for (int i = 0; i < limit; i++) {
            clump[i] = 0;
        }

        Genome text = genome.substring(0, L);
        int[] frequencies = ComputingFrequencies.array(text, k);

        for (int i = 0; i < limit; i++) {
            if (frequencies[i] >= t) {
                clump[i] = 1;
            }
        }

        for (int i = 1; i <= (genome.length() - L); i++) {
            String firstPattern = genome.substring(i - 1, i - 1 + k).getText();
            int index = Long.valueOf(PatternNumberConverter.getPatternToNumber(firstPattern)).intValue();
            frequencies[index] = frequencies[index] - 1;

            String lastPattern = genome.substring(i + L - k, i + L).getText();
            index = Long.valueOf(PatternNumberConverter.getPatternToNumber(lastPattern)).intValue();
            frequencies[index] = frequencies[index] + 1;

            if (frequencies[index] >= t) {
                clump[index] = 1;
            }
        }

        Set<Genome> ltClumps = new HashSet<>();

        for (int i = 0; i < limit; i++) {
            if (clump[i] == 1) {
                String pattern = PatternNumberConverter.getNumberToPattern(i, k);
                ltClumps.add(new Genome(pattern));
            }
        }

        return ltClumps;
    }

}
