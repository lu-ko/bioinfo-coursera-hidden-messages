package sk.elko.bioinfo.hidden;

public class PatternNumberConverter {

    public static long getPatternToNumber(final String genome) {
        char symbol = genome.charAt(genome.length() - 1);
        if (genome.length() == 1) {
            return getSymbolAsNumber(symbol);
        } else {
            return 4 * getPatternToNumber(genome.substring(0, genome.length() - 1)) + getSymbolAsNumber(symbol);
        }
    }

    private static int getSymbolAsNumber(char symbol) {
        Nucleobase nucleo = Nucleobase.parse(symbol);
        if (nucleo != null) {
            return nucleo.getNumber();
        } else {
            throw new IllegalArgumentException("Symbol: " + symbol);
        }
    }

    public static String getNumberToPattern(final long number, final int k) {
        if (number < 0 || k <= 0) {
            throw new IllegalArgumentException();
        }

        if (k == 1) {
            // System.err.println("k=" + k + ", number=" + number);
            return String.valueOf(getNumberAsSymbol(number));
        }
        long prefixNumber = getQuotient(number, 4);
        int r = getRemainder(number, 4);
        // System.err.println("k=" + k + ", number=" + number + ", r=" + r);
        char symbol = getNumberAsSymbol(r);
        String prefixPattern = getNumberToPattern(prefixNumber, k - 1);
        return prefixPattern + symbol;
    }

    private static char getNumberAsSymbol(long number) {
        Nucleobase nucleo = Nucleobase.parse((int) number);
        if (nucleo != null) {
            return nucleo.getBase();
        } else {
            throw new IllegalArgumentException("Number: " + number);
        }
    }

    private static long getQuotient(final long number, final int div) {
        return number / div;
    }

    private static int getRemainder(final long number, final int div) {
        return (int) (number % div);
    }

}
