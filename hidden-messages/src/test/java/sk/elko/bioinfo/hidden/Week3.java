package sk.elko.bioinfo.hidden;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class Week3 {

    @DataProvider(name = "testComputingFrequencies_provider")
    private Object[][] testComputingFrequencies_provider() {
        return new Object[][] { new Object[] { "ACGCGGCTCTGAAA", 2, 16, "2 1 0 0 0 0 2 2 1 2 1 0 0 1 1 0" }, //
                new Object[] { "AAGCAAAGGTGGG", 2, 16, "3 0 2 0 1 0 0 0 0 1 3 1 0 0 1 0" }, //
                new Object[] {
                        "GGATTTGAAAGTTCATTGGGCCCCTCCCTGTTTATCCCTATAAATTTACGGAAGGCATTGAGCCCTTAAGGCGAATCGAATATGCCACCCGTTGTCCGCATGGTCGACCCGCTTAATGTGCGTTGCTTACCTTAGAAGCAGTTATTCCATTAGTCATAAACTGTGTAAGTCTATGTATACGGCACGTCAATCTGTACGAAAAACTGGGTCCTCGGAGTAGGAGTCCTCCTGTACTCGGAAGTATTGTATCCTCGGCGCTTGGGTGCCCAGGCATCTACGGAAGTCTGTTAGTACGCCGACGGCTTTTACTTTCTCCTCGTCTGACGGGCTGAATAATTCGTGAAGCATAAGGGTGCACGACCATGGCCCCTCTTAGCTCTTGATCAACGACAAGGAGATACCAATACTCAACATGGAGTTATTCTATCGAAGGGTAGGTCCTTTCAGTTTGGTTACTTTGGCCAATATGTGGATCTTGCGTTCGTAGTCTCCATTAGGCGACGCAGGATATATCAGTAAAAGGTAGCAATCCGCGGGAGGGCCGTCAGCCAGCAATTCGTTTCAAAAGATACCGCGTCCCTGGGTCGCTAACGTTAGACGACGATGATCCAAATACCCCCTCTTAGAT",
                        5, 1024, null } //
        };
    }

    @Test(dataProvider = "testComputingFrequencies_provider")
    public void testComputingFrequencies(String str, int k, int expectedSize, String expectedList) {
        Genome genome = new Genome(str);
        List<Integer> result = ComputingFrequencies.list(genome, k);

        Assert.assertEquals(result.size(), expectedSize, "Result: " + result);
        if (expectedList != null) {
            Assert.assertEquals(Utils.toString(result), expectedList);
        }
    }

    @DataProvider(name = "testGetMotifs_provider")
    private Object[][] testGetMotifs_provider() {
        return new Object[][] { //
                new Object[] { Utils.parseNucleobaseRow("0.8 0 0 0.2"), //
                        Utils.parseNucleobaseRow("0 0.6 0.2 0"), //
                        Utils.parseNucleobaseRow("0.2 0.2 0.8 0"), //
                        Utils.parseNucleobaseRow("0 0.2 0 0.8"), //
                        new String[] { "ttaccttaac", "gatgtctgtc", "acggcgttag", "ccctaacgag", "cgtcagaggt" }, //
                        "acct atgt gcgt acga aggt" } };
    }

    @Test(dataProvider = "testGetMotifs_provider")
    public void testGetMotifs(double[] adenine, double[] cytosine, double[] guanine, double[] thymine, String[] dna, String expected) {
        Profile profile = new Profile(adenine, cytosine, guanine, thymine);
        List<Genome> genomes = Arrays.stream(dna).map(s -> new Genome(s)).collect(Collectors.toList());
        Motifs motifs = profile.getMotifs(genomes);
        Assert.assertEquals(Utils.toString(motifs.getGenomes()), expected);
    }

    @DataProvider(name = "testMotifsEnumerations_provider")
    private Object[][] testMotifsEnumerations_provider() {
        return new Object[][] { //
                new Object[] { new String[] { "ATTTGGC", "TGCCTTA", "CGGTATC", "GAAAATT" }, 3, 1, 4, "ATT TTT GTT ATA" }, //
                new Object[] { new String[] { "ACGT", "ACGT", "ACGT", "ACGT" }, 3, 0, 2, "CGT ACG" }, //
                new Object[] { new String[] { "AAAAA", "AAAAA", "AAAAA" }, 3, 1, 10, "AAA CAA AAC AAT TAA GAA AAG AGA ATA ACA" }, //
                new Object[] { new String[] { "AAAAA", "AAAAA", "AAAAA" }, 3, 3, 64, null }, //
                new Object[] { new String[] { "AAAAA", "AAAAA", "AACAA" }, 3, 0, 0, null }, //
                new Object[] { new String[] { "AACAA", "AAAAA", "AAAAA" }, 3, 0, 0, null }, //
                new Object[] { new String[] { "GCTACGGGGCAGCTAACGTTCCTCC", "TTGTGAATGAACCGGTCGTAGGCGC", "GGTTCACATCCTCATCTTCCTGCAG",
                        "GGAGCCATGATCATCATATTACGGT", "GGATCGAATTAGTTGGTAACGATTC", "TGCAGGGGACTAGTAGTGGATATAG" }, 5, 2, 458, null } //
        };
    }

    @Test(dataProvider = "testMotifsEnumerations_provider")
    public void testMotifsEnumerations(String[] str, int k, int d, int expectedSize, String expectedList) {
        Motifs motifs = new Motifs(str);
        Set<String> result = motifs.getEnumerations(k, d);

        Assert.assertEquals(result.size(), expectedSize, "Result: " + result);
        if (expectedList != null) {
            Assert.assertEquals(Utils.toString(result), expectedList);
        }
    }

    @DataProvider(name = "testMotifsHammingDistance_provider")
    private Object[][] testMotifsHammingDistance_provider() {
        return new Object[][] { //
                new Object[] { new String[] { "TTACCTTAAC", "GATATCTGTC", "ACGGCGTTCG", "CCCTAAAGAG", "CGTCAGAGGT" }, "AAA", 5 }, //
                new Object[] { new String[] { "TTTATTT", "CCTACAC", "GGTAGAG" }, "TAA", 3 }, //
                new Object[] { new String[] { "AAACT", "AAAC", "AAAG" }, "AAA", 0 }, //
                new Object[] { new String[] { "TTTTAAA", "CCCCAAA", "GGGGAAA" }, "AAA", 0 }, //
                new Object[] { new String[] { "AAATTTT", "AAACCCC", "AAAGGGG" }, "AAA", 0 } //
                // , new Object[] { Utils.getFile("week3/motifsHammingDistance.txt").split(" "), "GAATCGC", 77 } //
                // fails
        };
    }

    @Test(dataProvider = "testMotifsHammingDistance_provider")
    public void testMotifsHammingDistance(String[] str, String other, int expected) {
        Motifs motifs = new Motifs(str);
        int result = motifs.getHammingDistance(new Genome(other));

        Assert.assertEquals(result, expected);
    }

    @DataProvider(name = "testMotifsMedianString_provider")
    private Object[][] testMotifsMedianString_provider() {
        return new Object[][] { //
                new Object[] { new String[] { "AAATTGACGCAT", "GACGACCACGTT", "CGTCAGCGCCTG", "GCTGAGCACCGG", "AGTTCGGGACAG" }, 3,
                        new String[] { "ACG", "GAC" } }, //
                new Object[] { new String[] { "ACGT", "ACGT", "ACGT" }, 3, new String[] { "ACG", "CGT" } }, //
                new Object[] { new String[] { "ATA", "ACA", "AGA", "AAT", "AAC" }, 3, new String[] { "AAA" } }, //
                new Object[] { new String[] { "AAG", "AAT" }, 3, new String[] { "AAG", "AAT" } }, //
                new Object[] { new String[] { "CTCGATGAGTAGGAAAGTAGTTTCACTGGGCGAACCACCCCGGCGCTAATCCTAGTGCCC",
                        "GCAATCCTACCCGAGGCCACATATCAGTAGGAACTAGAACCACCACGGGTGGCTAGTTTC",
                        "GGTGTTGAACCACGGGGTTAGTTTCATCTATTGTAGGAATCGGCTTCAAATCCTACACAG" }, 7, new String[] { "AATCCTA" } }, //
                new Object[] { Utils.getFile("week3/motifsMedianString.txt").split("\n"), 6, new String[] { "ACAGAA", "AAAAAT" } } //
        };
    }

    @Test(dataProvider = "testMotifsMedianString_provider")
    public void testMotifsMedianString(String[] str, int k, String[] allowedResults) {
        Motifs motifs = new Motifs(str);
        String result = motifs.getMedianString(k);

        Assert.assertNotNull(result);
        Assert.assertTrue(Arrays.asList(allowedResults).contains(result), result);
    }

    @DataProvider(name = "testMotifsScore_provider")
    private Object[][] testMotifsScore_provider() {
        return new Object[][] { //
                new Object[] { new String[] { "TCGGGGgTTTtt", "cCGGtGAcTTaC", "aCGGGGATTTtC", "TtGGGGAcTTtt", "aaGGGGAcTTCC", //
                        "TtGGGGAcTTCC", "TCGGGGATTcat", "TCGGGGATTcCt", "TaGGGGAacTaC", "TCGGGtATaaCC" }, 30 }, //
                new Object[] { new String[] { "AAATTGACGCAT", "GACGACCACGTT", "CGTCAGCGCCTG", "GCTGAGCACCGG", "AGTTCGGGACAG" }, 27 }, //
                new Object[] { new String[] { "ACGT", "ACGT", "ACGT" }, 0 }, //
                new Object[] { new String[] { "ATA", "ACA", "AGA", "AAT", "AAC" }, 5 }, //
                new Object[] { new String[] { "AAG", "AAT" }, 1 }, //
                new Object[] { new String[] { "CTCGATGAGTAGGAAAGTAGTTTCACTGGGCGAACCACCCCGGCGCTAATCCTAGTGCCC",
                        "GCAATCCTACCCGAGGCCACATATCAGTAGGAACTAGAACCACCACGGGTGGCTAGTTTC",
                        "GGTGTTGAACCACGGGGTTAGTTTCATCTATTGTAGGAATCGGCTTCAAATCCTACACAG" }, 75 }, //
        };

    }

    @Test(dataProvider = "testMotifsScore_provider")
    public void testMotifsScore(String[] str, int expectedScore) {
        Motifs motifs = new Motifs(str);
        Assert.assertEquals(motifs.getScore(), expectedScore);
    }

    @DataProvider(name = "testMotifsConsensus_provider")
    private Object[][] testMotifsConsensus_provider() {
        return new Object[][] { //
                new Object[] { new String[] { "TCGGGGgTTTtt", "cCGGtGAcTTaC", "aCGGGGATTTtC", "TtGGGGAcTTtt", "aaGGGGAcTTCC", //
                        "TtGGGGAcTTCC", "TCGGGGATTcat", "TCGGGGATTcCt", "TaGGGGAacTaC", "TCGGGtATaaCC" }, "TCGGGGATTTCC" }, //
                new Object[] { new String[] { "AAATTGACGCAT", "GACGACCACGTT", "CGTCAGCGCCTG", "GCTGAGCACCGG", "AGTTCGGGACAG" }, "AATGAGCACCAG" }, //
                new Object[] { new String[] { "ACGT", "ACGT", "ACGT" }, "ACGT" }, //
                new Object[] { new String[] { "ATA", "ACA", "AGA", "AAT", "AAC" }, "AAA" }, //
                new Object[] { new String[] { "AAG", "AAT" }, "AAG" }, //
                new Object[] {
                        new String[] { "CTCGATGAGTAGGAAAGTAGTTTCACTGGGCGAACCACCCCGGCGCTAATCCTAGTGCCC",
                                "GCAATCCTACCCGAGGCCACATATCAGTAGGAACTAGAACCACCACGGGTGGCTAGTTTC",
                                "GGTGTTGAACCACGGGGTTAGTTTCATCTATTGTAGGAATCGGCTTCAAATCCTACACAG" },
                        "GCAGTTGAACCAGAGGGTAAATTTCATCAGCAAAAAGAACCGGCACCAATCCCTACACAC" }, //
        };

    }

    @Test(dataProvider = "testMotifsConsensus_provider")
    public void testMotifsConsensus(String[] str, String expectedConsensus) {
        Motifs motifs = new Motifs(str);
        Assert.assertEquals(motifs.getConsensus(), expectedConsensus);
    }

}
