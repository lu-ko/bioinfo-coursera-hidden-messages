package sk.elko.bioinfo.hidden;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Motifs {

    private final List<Genome> dna;
    private final int size, k;

    public Motifs(List<String> motifs) {
        this(motifs.toArray(new String[motifs.size()]));
    }

    public Motifs(String... motifs) {
        if (motifs == null || motifs.length == 0) {
            throw new IllegalArgumentException();
        }

        dna = new ArrayList<>();
        for (String motif : motifs) {
            dna.add(new Genome(motif));
        }
        size = dna.size();
        k = dna.get(0).length();
    }

    @Override
    public String toString() {
        return "Motifs: " + dna;
    }

    public List<String> getDnaAsList() {
        return dna.stream().map(g -> g.getText()).collect(Collectors.toList());
    }

    public List<Genome> getGenomes() {
        return dna;
    }

    public Genome getGenome(int index) {
        return dna.get(index);
    }

    public Motifs getSubMotifs(final int k, final int t) {
        if (t != size) {
            throw new IllegalArgumentException("size=" + size + " != t=" + t);
        }

        String[] motifs = new String[t];
        for (int i = 0; i < t; i++) {
            motifs[i] = dna.get(i).substring(0, k).toString();
        }
        return new Motifs(motifs);
    }

    public Set<String> getEnumerations(final int k, final int d) {
        Set<String> patterns = new HashSet<String>();

        for (Genome genome : dna) {
            for (Genome kmer : genome.getAllKmers(k)) {
                Set<String> neighbors = kmer.getNeighbors(d);
                for (String neighbor : neighbors) {
                    if (contains(dna, new Genome(neighbor), d)) {
                        patterns.add(neighbor);
                    }
                }
            }
        }

        return patterns;
    }

    private boolean contains(final List<Genome> dna, final Genome pattern, final int d) {
        for (Genome dnaElement : dna) {
            List<Integer> count = pattern.getApproximatePatternMatching(dnaElement, d);
            if (count.size() == 0) {
                return false;
            }
        }
        return true;
    }

    public int getHammingDistance(final Genome other) {
        int distance = 0;

        for (Genome genome : dna) {
            int minHamDistance = Integer.MAX_VALUE;
            for (Genome kmer : genome.getAllKmers(other.length())) {
                int hamDistance = other.getHammingDistance(kmer);
                if (hamDistance < minHamDistance) {
                    minHamDistance = hamDistance;
                }
            }
            distance += minHamDistance;
        }

        return distance;
    }

    public String getMedianString(final int k) {
        if (k == 0) {
            throw new IllegalArgumentException();
        }

        String median = "";
        int minHamDistance = Integer.MAX_VALUE;

        int limit = Double.valueOf(Math.pow(4, k)).intValue();
        for (int index = 0; index < (limit - 1); index++) {
            final String pattern = PatternNumberConverter.getNumberToPattern(index, k);
            int hamDistance = getHammingDistance(new Genome(pattern));
            if (hamDistance < minHamDistance) {
                minHamDistance = hamDistance;
                median = pattern;
            }
        }

        return median;
    }

    public int getScore() {
        int[] countA = new int[k];
        int[] countC = new int[k];
        int[] countG = new int[k];
        int[] countT = new int[k];

        counts(dna, k, countA, countC, countG, countT);

        int score = 0;
        for (int index = 0; index < k; index++) {
            int maxValue = maxValue(countA[index], countC[index], countG[index], countT[index]);
            score += size - maxValue;
        }

        return score;
    }

    private void counts(final List<Genome> motifs, final int k, int[] countA, int[] countC, int[] countG, int[] countT) {
        for (Genome motif : motifs) {
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
    }

    private int maxValue(int... numbers) {
        int max = numbers[0];
        for (int index = 0; index < numbers.length; index++) {
            if (numbers[index] > max) {
                max = numbers[index];
            }
        }
        return max;
    }

    public String getConsensus() {
        int[] countA = new int[k];
        int[] countC = new int[k];
        int[] countG = new int[k];
        int[] countT = new int[k];

        counts(dna, k, countA, countC, countG, countT);

        StringBuilder builder = new StringBuilder();
        for (int index = 0; index < k; index++) {
            builder.append(maxNucleobase(countA[index], countC[index], countG[index], countT[index]).getBase());
        }

        return builder.toString();
    }

    private Nucleobase maxNucleobase(int... numbers) {
        int nucleo = 0;
        int max = numbers[0];
        for (int index = 0; index < numbers.length; index++) {
            if (numbers[index] > max) {
                max = numbers[index];
                nucleo = index;
            }
        }
        return Nucleobase.parse(nucleo);
    }

}
