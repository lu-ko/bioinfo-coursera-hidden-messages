package sk.elko.bioinfo.hidden;

import java.util.ArrayList;
import java.util.List;

public class ComputingFrequencies {

    public static int[] array(final Genome genome, final int k) {
        if (genome.length() == 0 || k <= 0 || genome.length() < k) {
            throw new IllegalArgumentException();
        }

        int freqSize = Double.valueOf(Math.pow(4, k)).intValue();
        int[] frequencies = new int[freqSize];

        for (int index = 0; index <= (genome.length() - k); index++) {
            int limit = Math.min(genome.length(), index + k);
            // System.out.println("LENGTH=" + LENGTH + ", index=" + index + ", k=" + k + ", limit=" + limit);

            Genome pattern = genome.substring(index, limit);
            int j = Long.valueOf(PatternNumberConverter.getPatternToNumber(pattern.getText())).intValue();
            frequencies[j] = frequencies[j] + 1;
        }
        return frequencies;
    }

    public static List<Integer> list(final Genome genome, final int k) {
        int[] frequencies = array(genome, k);

        List<Integer> freq = new ArrayList<>(frequencies.length);
        for (int frequency : frequencies) {
            freq.add(frequency);
        }
        return freq;
    }

}
