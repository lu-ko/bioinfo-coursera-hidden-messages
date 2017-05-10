package sk.elko.bioinfo.hidden;

public enum Nucleobase {

    Adenine('A', 'T', 0, new char[] { 'C', 'G', 'T' }),

    Cytosine('C', 'G', 1, new char[] { 'A', 'G', 'T' }),

    Guanine('G', 'C', 2, new char[] { 'A', 'C', 'T' }),

    Thymine('T', 'A', 3, new char[] { 'A', 'C', 'G' });

    private final char base, reverse;
    private final int number;
    private final char[] neighbors;

    private Nucleobase(char base, char reverse, int number, char[] neighbors) {
        this.base = base;
        this.reverse = reverse;
        this.number = number;
        this.neighbors = neighbors;
    }

    public char getBase() {
        return base;
    }

    public char getReverse() {
        return reverse;
    }

    public int getNumber() {
        return number;
    }

    public char[] getNeighbors() {
        return neighbors;
    }

    public static Nucleobase parse(char nucleo) {
        switch (nucleo) {
        case 'A':
        case 'a':
            return Adenine;
        case 'C':
        case 'c':
            return Cytosine;
        case 'G':
        case 'g':
            return Guanine;
        case 'T':
        case 't':
            return Thymine;
        default:
            // System.err.println("Unknown character: '" + nucleo + "'");
            // throw new IllegalArgumentException("Unknown character: '" + nucleo + "'");
            return null;
        }
    }

    public static Nucleobase parse(int number) {
        switch (number) {
        case 0:
            return Adenine;
        case 1:
            return Cytosine;
        case 2:
            return Guanine;
        case 3:
            return Thymine;
        default:
            // System.err.println("Unknown number: '" + number + "'");
            // throw new IllegalArgumentException("Unknown number: '" + number + "'");
            return null;
        }
    }

}
