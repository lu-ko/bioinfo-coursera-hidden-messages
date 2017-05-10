package sk.elko.bioinfo.hidden;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class Utils {

    public static String getFileRaw(final String filename) {
        try {
            URI uri = Utils.class.getResource(filename).toURI();
            System.err.println(Utils.class.getResource(filename));

            byte[] encoded = Files.readAllBytes(Paths.get(uri));
            return new String(encoded, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String getFile(final String filename) {
        return getFileRaw(filename).replaceAll("[^a^c^g^t^A^C^G^T]+", "");
    }

    public static String toString(final Collection<?> collection) {
        return collection.toString().replaceAll(",", "").replaceAll("[\\[\\]]", "");
    }

    public static Set<String> generateKMers(final int k) {
        if (k == 1) {
            Set<String> genomes = new HashSet<>();
            genomes.add("A");
            genomes.add("C");
            genomes.add("G");
            genomes.add("T");
            return genomes;
        } else {
            Set<String> genomes = new HashSet<>();
            Set<String> children = generateKMers(k - 1);
            for (String genome : children) {
                genomes.add(genome + "A");
                genomes.add(genome + "C");
                genomes.add(genome + "G");
                genomes.add(genome + "T");
            }
            return genomes;
        }
    }

    public static double[] parseNucleobaseRow(String rowString) {
        String[] rowStrings = rowString.split(" ");
        double[] rowNumbers = new double[rowStrings.length];
        for (int index = 0; index < rowStrings.length; index++) {
            rowNumbers[index] = Double.valueOf(rowStrings[index]);
        }
        return rowNumbers;
    }

    public static double getEntropy(final double[] numbers) {
        double entropy = 0;
        for (double number : numbers) {
            Double log = number * (Math.log(number) / Math.log(2));
            if (!log.isNaN()) {
                entropy += log.doubleValue();
            }
        }
        return entropy * -1;
    }

}
