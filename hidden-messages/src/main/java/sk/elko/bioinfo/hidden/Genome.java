package sk.elko.bioinfo.hidden;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Genome {

    private final String genome;
    private final int length;

    public Genome(String text) {
        this.genome = text;
        this.length = genome.length();
    }

    @Override
    public String toString() {
        return genome;
    }

    @Override
    public int hashCode() {
        return genome.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof Genome) {
            return genome.equals(((Genome) other).getText());
        } else {
            return false;
        }
    }

    public String getText() {
        return genome;
    }

    public int length() {
        return length;
    }

    public char charAt(int index) {
        return genome.charAt(index);
    }

    public Genome substring(int beginIndex, int endIndex) {
        return new Genome(genome.substring(beginIndex, endIndex));
    }

    public boolean equalsIgnoreCase(Genome other) {
        return this.getText().equalsIgnoreCase(other.getText());
    }

    public Genome getReverseComplement() {
        StringBuilder builder = new StringBuilder(this.length());
        for (int index = 0; index < this.length(); index++) {
            Nucleobase nucleo = Nucleobase.parse(this.charAt(index));
            if (nucleo != null) {
                builder.append(nucleo.getReverse());
            }
        }
        return new Genome(builder.reverse().toString());
    }

    public int getHammingDistance(Genome other) {
        if (this.length() != other.length()) {
            throw new IllegalArgumentException();
        }

        int diffs = 0;
        for (int index = 0; index < this.length(); index++) {
            diffs += this.charAt(index) == other.charAt(index) ? 0 : 1;
        }
        return diffs;
    }

    public List<Integer> getApproximatePatternMatching(final Genome other, final int d) {
        final int lengthPattern = this.length();
        final int lengthText = other.length();

        if (d < 0 || lengthPattern < d || lengthText < d) {
            throw new IllegalArgumentException();
        }

        List<Integer> positions = new ArrayList<>();
        for (int index = 0; index <= (lengthText - lengthPattern); index++) {
            Genome toCompare = other.substring(index, index + lengthPattern);
            if (this.getHammingDistance(toCompare) <= d) {
                positions.add(index);
            }
        }
        return positions;
    }

    public Set<String> getNeighbors(final int d) {
        if (d < 0 || this.length() < d) {
            throw new IllegalArgumentException();
        }

        Set<String> all = Utils.generateKMers(this.length());

        Set<String> neighbors = new HashSet<String>();
        neighbors.add(this.getText());

        for (String pattern : all) {
            if (this.getHammingDistance(new Genome(pattern)) <= d) {
                neighbors.add(pattern);
            }
        }
        return neighbors;
    }

    public List<Genome> getAllKmers(final int k) {
        List<Genome> kmers = new ArrayList<Genome>();
        for (int index = 0; index <= (this.length() - k); index++) {
            int limit = Math.min(index + k, this.length());
            final Genome kmer = this.substring(index, limit);
            kmers.add(kmer);
        }
        return kmers;
    }

    public int getExactOccurences(Genome other) {
        if (this.length() < other.length()) {
            throw new IllegalArgumentException();
        }

        int matches = 0;
        for (int index = 0; index <= (this.length() - other.length()); index++) {
            Genome window = this.substring(index, index + other.length());
            matches += window.match(other) ? 1 : 0;
        }
        return matches;
    }

    private boolean match(Genome other) {
        return this.equalsIgnoreCase(other);
    }

    public List<Integer> getAllOccurences(Genome other) {
        if (this.length() < other.length()) {
            throw new IllegalArgumentException();
        }

        List<Integer> matches = new ArrayList<Integer>();
        for (int index = 0; index <= (this.length() - other.length()); index++) {
            Genome window = this.substring(index, index + other.length());
            if (window.matchAll(other)) {
                matches.add(index);
            }
        }
        return matches;
    }

    private boolean matchAll(Genome other) {
        return this.equalsIgnoreCase(other) || this.equalsIgnoreCase(other.getReverseComplement());
    }

    public List<Integer> getSkewMinimum() {
        int currentState = 0;
        int minimum = 0;
        List<Integer> positions = new ArrayList<>();

        for (int index = 0; index < this.length(); index++) {
            Nucleobase nucleo = Nucleobase.parse(this.charAt(index));
            if (nucleo == Nucleobase.Cytosine) {
                currentState--;
            } else if (nucleo == Nucleobase.Guanine) {
                currentState++;
            }

            if (currentState < minimum) {
                currentState = minimum;
                positions.clear();
            }
            if (currentState == minimum) {
                positions.add(index + 1);
            }
        }

        return positions;
    }

    public List<Integer> getSkewMaximum() {
        int currentState = 0;
        int maximum = 0;
        List<Integer> positions = new ArrayList<>();

        for (int index = 0; index < this.length(); index++) {
            Nucleobase nucleo = Nucleobase.parse(this.charAt(index));
            if (nucleo == Nucleobase.Cytosine) {
                currentState--;
            } else if (nucleo == Nucleobase.Guanine) {
                currentState++;
            }

            if (maximum < currentState) {
                currentState = maximum;
                positions.clear();
            }
            if (currentState == maximum) {
                positions.add(index + 1);
            }
        }

        return positions;
    }

}
