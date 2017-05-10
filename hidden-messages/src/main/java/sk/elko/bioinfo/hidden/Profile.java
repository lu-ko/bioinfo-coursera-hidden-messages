package sk.elko.bioinfo.hidden;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Profile {

    private final Map<Nucleobase, double[]> map;
    private int length;

    public Profile(double[] adenine, double[] cytosine, double[] guanine, double[] thymine) {
        map = new HashMap<Nucleobase, double[]>(Nucleobase.values().length);
        map.put(Nucleobase.Adenine, adenine);
        map.put(Nucleobase.Cytosine, cytosine);
        map.put(Nucleobase.Guanine, guanine);
        map.put(Nucleobase.Thymine, thymine);
        length = adenine.length;
    }

    public int length() {
        return length;
    }

    public double getElement(char nucleo, int index) {
        return getElement(Nucleobase.parse(nucleo), index);
    }

    public double getElement(Nucleobase nucleo, int index) {
        // if (nucleo == null) {
        // System.err.println("index=" + index);
        // }
        return map.get(nucleo)[index];
    }

    public double getScore(final Genome kmer) {
        double probability = 1;
        for (int i = 0; i < kmer.length(); i++) {
            char nucleo = kmer.charAt(i);
            probability *= getElement(nucleo, i);
        }
        return probability;
    }

    public String print() {
        StringBuilder builder = new StringBuilder();
        print(builder, map.get(Nucleobase.Adenine));
        print(builder, map.get(Nucleobase.Cytosine));
        print(builder, map.get(Nucleobase.Guanine));
        print(builder, map.get(Nucleobase.Thymine));
        return builder.toString();
    }

    private void print(StringBuilder builder, double[] row) {
        for (double element : row) {
            builder.append(element + " ");
        }
        builder.append("\n");
    }

    public String getConsensus() {
        StringBuilder builder = new StringBuilder();
        double[] countA = map.get(Nucleobase.Adenine);
        double[] countC = map.get(Nucleobase.Cytosine);
        double[] countG = map.get(Nucleobase.Guanine);
        double[] countT = map.get(Nucleobase.Thymine);

        for (int index = 0; index < length; index++) {
            Nucleobase max = maxNucleobase(countA[index], countC[index], countG[index], countT[index]);
            builder.append(max.getBase());
        }

        return builder.toString();
    }

    private Nucleobase maxNucleobase(double... numbers) {
        int nucleo = 0;
        double max = numbers[0];
        for (int index = 0; index < numbers.length; index++) {
            if (numbers[index] > max) {
                max = numbers[index];
                nucleo = index;
            }
        }
        return Nucleobase.parse(nucleo);
    }

    public String getMostProbableKmer(final Genome genome) {
        String result = ""; // dna.substring(0, profile.length()); // FIXME
        double max = -1;
        final int LENGTH = genome.length() - length();

        for (int index = 0; index <= LENGTH; index++) {
            Genome kmer = genome.substring(index, index + length());
            double probability = getScore(kmer);
            if (max < probability) {
                max = probability;
                result = kmer.getText();
            }
        }

        return result;
    }

    public Motifs getMotifs(List<Genome> dna) {
        List<String> motifs = new ArrayList<>();
        for (Genome kmer : dna) {
            String mostProbable = getMostProbableKmer(kmer);
            motifs.add(mostProbable);
        }

        return new Motifs(motifs);
    }

    public static Profile parse(final List<String> motifs, final boolean applyLaplace) {
        if (motifs == null || motifs.size() == 0) {
            throw new IllegalArgumentException();
        }

        final int k = motifs.get(0).length();
        final double COUNT = motifs.size();
        int[] countA = new int[k];
        int[] countC = new int[k];
        int[] countG = new int[k];
        int[] countT = new int[k];

        for (String motif : motifs) {
            final int limit = Math.min(k, motif.length());
            for (int index = 0; index < limit; index++) {
                Nucleobase nucleo = Nucleobase.parse(motif.charAt(index));
                switch (nucleo) {
                case Adenine:
                    countA[index]++;
                    continue;
                case Cytosine:
                    countC[index]++;
                    continue;
                case Guanine:
                    countG[index]++;
                    continue;
                case Thymine:
                    countT[index]++;
                    continue;
                }
            }
        }

        double[] adenine = convert(countA, k, COUNT, applyLaplace);
        double[] cytosine = convert(countC, k, COUNT, applyLaplace);
        double[] guanine = convert(countG, k, COUNT, applyLaplace);
        double[] thymine = convert(countT, k, COUNT, applyLaplace);

        return new Profile(adenine, cytosine, guanine, thymine);
    }

    private static double[] convert(final int[] rowInt, final int k, final double count, final boolean applyLaplace) {
        double[] rowDouble = new double[k];
        for (int index = 0; index < k; index++) {
            if (applyLaplace) {
                rowDouble[index] = (rowInt[index] + 1) / (count + 4);
            } else {
                rowDouble[index] = rowInt[index] / count;
            }
        }
        return rowDouble;
    }

}
