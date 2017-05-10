package sk.elko.bioinfo.hidden;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class Week2 {

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

    @DataProvider(name = "testFrequentWordsSlow_provider")
    private Object[][] testFrequentWordsSlow_provider() {
        return new Object[][] { new Object[] { "ACTGACTCCCACCCC", 3, 1, "{CCC=3}" }, //
                new Object[] { "ACGTTGCATGTCGCATGATGCATGAGAGCT", 4, 2, "{GCAT=3, CATG=3}" }, //
                new Object[] { "CGGAGGACTCTAGGTAACGCTTATCAGGTCCATAGGACATTCA", 3, 1, "{AGG=4}" }, //
                new Object[] {
                        "GGGACTAAATCCCATAAGGAAGGGAGGGACTAAATACGATTAAGGAAGGGAAGGAAGGGAGATCTAGGGGACTAAAGGAAGGGAATACGATTAGGGACTAAGGGACTAAGATCTAGATACGATTAGATCTAGATCCCATAATACGATTAATACGATTAGGGACTAAGATCTAGGATCTAGAGGAAGGGAATCCCATAGATCTAGAGGAAGGGAGGGACTAAATCCCATAATACGATTAAGGAAGGGAGATCTAGATACGATTAGGGACTAAATCCCATAGGGACTAAATACGATTAATCCCATAGGGACTAAAGGAAGGGAATACGATTAGATCTAGAGGAAGGGAATACGATTAGATCTAGATCCCATAATACGATTAATACGATTAATACGATTAGATCTAGATACGATTAAGGAAGGGAATCCCATAAGGAAGGGAGGGACTAAGGGACTAAATACGATTAATCCCATAGGGACTAAAGGAAGGGAATACGATTAGGGACTAAATACGATTAGGGACTAAAGGAAGGGAGATCTAGGGGACTAAGGGACTAAGATCTAGGATCTAGAGGAAGGGAGATCTAGAGGAAGGGAATACGATTAGATCTAGATACGATTAAGGAAGGGAAGGAAGGGAGGGACTAAATACGATTAATCCCATAAGGAAGGGAGGGACTAAGGGACTAAATACGATTAGATCTAGATCCCATAATACGATTAATACGATTAGATCTAGATCCCATAAGGAAGGGAGGGACTAAATACGATTAGATCTAGGATCTAGAGGAAGGGAGGGACTAAGGGACTAAATACGATTAATCCCATAATACGATTAAGGAAGGGA",
                        11, 2, "{AATACGATTAG=11, AATACGATTAA=11}" }, //
                new Object[] {
                        "atcaatgatcaacgtaagcttctaagcatgatcaaggtgctcacacagtttatccacaacctgagtggatgacatcaagataggtcgttgtatctccttcctctcgtactctcatgaccacggaaagatgatcaagagaggatgatttcttggccatatcgcaatgaatacttgtgacttgtgcttccaattgacatcttcagcgccatattgcgctggccaaggtgacggagcgggattacgaaagcatgatcatggctgttgttctgtttatcttgttttgactgagacttgttaggatagacggtttttcatcactgactagccaaagccttactctgcctgacatcgaccgtaaattgataatgaatttacatgcttccgcgacgatttacctcttgatcatcgatccgattgaagatcttcaattgttaattctcttgcctcgactcatagccatgatgagctcttgatcatgtttccttaaccctctattttttacggaagaatgatcaagctgctgctcttgatcatcgtttc",
                        9, 4, "{atgatcaag=3, ctcttgatc=3, tcttgatca=3, cttgatcat=3}" } };
    }

    @Test(dataProvider = "testFrequentWordsSlow_provider")
    public void testFrequentWordsSlow(String str1, int k, int expectedSize, String expectedMap) {
        Genome genome = new Genome(str1);
        Map<Genome, Integer> result = FrequentWords.slow(genome, k);

        Assert.assertEquals(result.size(), expectedSize, "Result: " + result);
        Assert.assertEquals(result.toString(), expectedMap);
    }

    @DataProvider(name = "testFrequentWordsMedium_provider")
    private Object[][] testFrequentWordsMedium_provider() {
        return new Object[][] { new Object[] { "ACTGACTCCCACCCC", 3, 1, "{CCC=3}" }, //
                new Object[] { "ACGTTGCATGTCGCATGATGCATGAGAGCT", 4, 2, "{CATG=3, GCAT=3}" }, //
                new Object[] { "CGGAGGACTCTAGGTAACGCTTATCAGGTCCATAGGACATTCA", 3, 1, "{AGG=4}" }, //
                new Object[] {
                        "GGGACTAAATCCCATAAGGAAGGGAGGGACTAAATACGATTAAGGAAGGGAAGGAAGGGAGATCTAGGGGACTAAAGGAAGGGAATACGATTAGGGACTAAGGGACTAAGATCTAGATACGATTAGATCTAGATCCCATAATACGATTAATACGATTAGGGACTAAGATCTAGGATCTAGAGGAAGGGAATCCCATAGATCTAGAGGAAGGGAGGGACTAAATCCCATAATACGATTAAGGAAGGGAGATCTAGATACGATTAGGGACTAAATCCCATAGGGACTAAATACGATTAATCCCATAGGGACTAAAGGAAGGGAATACGATTAGATCTAGAGGAAGGGAATACGATTAGATCTAGATCCCATAATACGATTAATACGATTAATACGATTAGATCTAGATACGATTAAGGAAGGGAATCCCATAAGGAAGGGAGGGACTAAGGGACTAAATACGATTAATCCCATAGGGACTAAAGGAAGGGAATACGATTAGGGACTAAATACGATTAGGGACTAAAGGAAGGGAGATCTAGGGGACTAAGGGACTAAGATCTAGGATCTAGAGGAAGGGAGATCTAGAGGAAGGGAATACGATTAGATCTAGATACGATTAAGGAAGGGAAGGAAGGGAGGGACTAAATACGATTAATCCCATAAGGAAGGGAGGGACTAAGGGACTAAATACGATTAGATCTAGATCCCATAATACGATTAATACGATTAGATCTAGATCCCATAAGGAAGGGAGGGACTAAATACGATTAGATCTAGGATCTAGAGGAAGGGAGGGACTAAGGGACTAAATACGATTAATCCCATAATACGATTAAGGAAGGGA",
                        11, 2, "{AATACGATTAG=11, AATACGATTAA=11}" }, //
                new Object[] {
                        "atcaatgatcaacgtaagcttctaagcatgatcaaggtgctcacacagtttatccacaacctgagtggatgacatcaagataggtcgttgtatctccttcctctcgtactctcatgaccacggaaagatgatcaagagaggatgatttcttggccatatcgcaatgaatacttgtgacttgtgcttccaattgacatcttcagcgccatattgcgctggccaaggtgacggagcgggattacgaaagcatgatcatggctgttgttctgtttatcttgttttgactgagacttgttaggatagacggtttttcatcactgactagccaaagccttactctgcctgacatcgaccgtaaattgataatgaatttacatgcttccgcgacgatttacctcttgatcatcgatccgattgaagatcttcaattgttaattctcttgcctcgactcatagccatgatgagctcttgatcatgtttccttaaccctctattttttacggaagaatgatcaagctgctgctcttgatcatcgtttc",
                        9, 4, "{TCTTGATCA=3, CTTGATCAT=3, ATGATCAAG=3, CTCTTGATC=3}" } };
    }

    @Test(dataProvider = "testFrequentWordsMedium_provider")
    public void testFrequentWordsMedium(String str1, int k, int expectedSize, String expectedMap) {
        Genome genome = new Genome(str1);
        Map<Genome, Integer> result = FrequentWords.medium(genome, k);

        Assert.assertEquals(result.size(), expectedSize, "Result: " + result);
        Assert.assertEquals(result.toString(), expectedMap);
    }

    @DataProvider(name = "testFrequentWordsFast_provider")
    private Object[][] testFrequentWordsFast_provider() {
        return new Object[][] { new Object[] { "ACTGACTCCCACCCC", 3, 1, "{CCC=3}" }, //
                new Object[] { "ACGTTGCATGTCGCATGATGCATGAGAGCT", 4, 2, "{CATG=3, GCAT=3}" }, //
                new Object[] { "CGGAGGACTCTAGGTAACGCTTATCAGGTCCATAGGACATTCA", 3, 1, "{AGG=4}" }, //
                new Object[] {
                        "GGGACTAAATCCCATAAGGAAGGGAGGGACTAAATACGATTAAGGAAGGGAAGGAAGGGAGATCTAGGGGACTAAAGGAAGGGAATACGATTAGGGACTAAGGGACTAAGATCTAGATACGATTAGATCTAGATCCCATAATACGATTAATACGATTAGGGACTAAGATCTAGGATCTAGAGGAAGGGAATCCCATAGATCTAGAGGAAGGGAGGGACTAAATCCCATAATACGATTAAGGAAGGGAGATCTAGATACGATTAGGGACTAAATCCCATAGGGACTAAATACGATTAATCCCATAGGGACTAAAGGAAGGGAATACGATTAGATCTAGAGGAAGGGAATACGATTAGATCTAGATCCCATAATACGATTAATACGATTAATACGATTAGATCTAGATACGATTAAGGAAGGGAATCCCATAAGGAAGGGAGGGACTAAGGGACTAAATACGATTAATCCCATAGGGACTAAAGGAAGGGAATACGATTAGGGACTAAATACGATTAGGGACTAAAGGAAGGGAGATCTAGGGGACTAAGGGACTAAGATCTAGGATCTAGAGGAAGGGAGATCTAGAGGAAGGGAATACGATTAGATCTAGATACGATTAAGGAAGGGAAGGAAGGGAGGGACTAAATACGATTAATCCCATAAGGAAGGGAGGGACTAAGGGACTAAATACGATTAGATCTAGATCCCATAATACGATTAATACGATTAGATCTAGATCCCATAAGGAAGGGAGGGACTAAATACGATTAGATCTAGGATCTAGAGGAAGGGAGGGACTAAGGGACTAAATACGATTAATCCCATAATACGATTAAGGAAGGGA",
                        11, 2, "{AATACGATTAG=11, AATACGATTAA=11}" }, //
                new Object[] {
                        "atcaatgatcaacgtaagcttctaagcatgatcaaggtgctcacacagtttatccacaacctgagtggatgacatcaagataggtcgttgtatctccttcctctcgtactctcatgaccacggaaagatgatcaagagaggatgatttcttggccatatcgcaatgaatacttgtgacttgtgcttccaattgacatcttcagcgccatattgcgctggccaaggtgacggagcgggattacgaaagcatgatcatggctgttgttctgtttatcttgttttgactgagacttgttaggatagacggtttttcatcactgactagccaaagccttactctgcctgacatcgaccgtaaattgataatgaatttacatgcttccgcgacgatttacctcttgatcatcgatccgattgaagatcttcaattgttaattctcttgcctcgactcatagccatgatgagctcttgatcatgtttccttaaccctctattttttacggaagaatgatcaagctgctgctcttgatcatcgtttc",
                        9, 4, "{TCTTGATCA=3, CTTGATCAT=3, ATGATCAAG=3, CTCTTGATC=3}" } };
    }

    @Test(dataProvider = "testFrequentWordsFast_provider")
    public void testFrequentWordsFast(String str1, int k, int expectedSize, String expectedMap) {
        Genome genome = new Genome(str1);
        Map<Genome, Integer> result = FrequentWords.fast(genome, k);

        Assert.assertEquals(result.size(), expectedSize, "Result: " + result);
        Assert.assertEquals(result.toString(), expectedMap);
    }

    @DataProvider(name = "testClumpFindingSlow_provider")
    private Object[][] testClumpFindingSlow_provider() {
        return new Object[][] { new Object[] { "ACGTACGT", 1, 5, 2, 4, "A C T G" }, //
                new Object[] { "AAAACGTCGAAAAA", 2, 4, 2, 1, "AA" }, //
                new Object[] { "gatcagcataagggtccCTGCAATGCATGACAAGCCTGCAGTtgttttac", 4, 25, 3, 1, "TGCA" }, //
                new Object[] { "CGGACTCGACAGATGTGAAGAACGACAATGTGAAGACTCGACACGACAGAGTGAAGAGAAGAGGAAACATTGTAA", 5, 50, 4, 2, "GAAGA CGACA" }, //
                new Object[] {
                        "CCACGCGGTGTACGCTGCAAAAAGCCTTGCTGAATCAAATAAGGTTCCAGCACATCCTCAATGGTTTCACGTTCTTCGCCAATGGCTGCCGCCAGGTTATCCAGACCTACAGGTCCACCAAAGAACTTATCGATTACCGCCAGCAACAATTTGCGGTCCATATAATCGAAACCTTCAGCATCGACATTCAACATATCCAGCG",
                        3, 25, 3, 6, "CCA AAA TTC GCC CAT CAG" }, //
        };
    }

    @Test(dataProvider = "testClumpFindingSlow_provider")
    public void testClumpFindingSlow(String str, int k, int L, int t, int expectedSize, String expectedSet) {
        Genome genome = new Genome(str);
        Set<Genome> result = ClumpFinding.slow(genome, k, L, t);

        Assert.assertEquals(result.size(), expectedSize, "Result: " + result);
        Assert.assertEquals(Utils.toString(result), expectedSet);
    }

    @DataProvider(name = "testClumpFindingFast_provider")
    private Object[][] testClumpFindingFast_provider() {
        return new Object[][] { new Object[] { "ACGTACGT", 1, 5, 2, 4, "A C T G" }, //
                new Object[] { "AAAACGTCGAAAAA", 2, 4, 2, 1, "AA" }, //
                new Object[] { "gatcagcataagggtccCTGCAATGCATGACAAGCCTGCAGTtgttttac", 4, 25, 3, 1, "TGCA" }, //
                new Object[] { "CGGACTCGACAGATGTGAAGAACGACAATGTGAAGACTCGACACGACAGAGTGAAGAGAAGAGGAAACATTGTAA", 5, 50, 4, 2, "GAAGA CGACA" }, //
                new Object[] {
                        "CCACGCGGTGTACGCTGCAAAAAGCCTTGCTGAATCAAATAAGGTTCCAGCACATCCTCAATGGTTTCACGTTCTTCGCCAATGGCTGCCGCCAGGTTATCCAGACCTACAGGTCCACCAAAGAACTTATCGATTACCGCCAGCAACAATTTGCGGTCCATATAATCGAAACCTTCAGCATCGACATTCAACATATCCAGCG",
                        3, 25, 3, 6, "CCA AAA TTC GCC CAT CAG" }, //
                new Object[] { Utils.getFile("week2/clump.txt"), 12, 489, 17, 4, "CTGCAACTTGTA GTTTTATGTTCC GGGTGGCGGAAA CAAGGGAGCGCC" } //
        };
    }

    @Test(dataProvider = "testClumpFindingFast_provider")
    public void testClumpFindingFast(String str, int k, int L, int t, int expectedSize, String expectedSet) {
        Genome genome = new Genome(str);
        Set<Genome> result = ClumpFinding.fast(genome, k, L, t);

        Assert.assertEquals(result.size(), expectedSize, "Result: " + result);
        Assert.assertEquals(Utils.toString(result), expectedSet);
    }

    @DataProvider(name = "testApproximatePatternMatching_provider")
    private Object[][] testApproximatePatternMatching_provider() {
        return new Object[][] { new Object[] { "GG", "ACGT", 1, 2, "1 2" }, //
                new Object[] { "GAGG", "TTTAGAGCCTTCAGAGG", 2, 4, "2 4 11 13" }, //
                new Object[] { "AAAAA", "AACAAGCTGATAAACATTTAAAGAG", 2, 11, "0 1 8 9 10 11 12 17 18 19 20" }, //
                new Object[] { "CCC", "CATGCCATTCGCATTGTCCCAGTGA", 2, 15, "0 2 3 4 5 7 8 9 10 11 15 16 17 18 19" }, //
                new Object[] { "ATTCTGGA", "CGCCCGAATCCAGAACGCATTCCCATATTTCGGGACCACTGGCCTCCACGGTACGGACGTCAATCAAAT", 3, 4, "6 7 26 27" }, //
                new Object[] { "TTGCCTT",
                        "GGATTATCCTTACGGGCTTTAGACATGTGGCATCTAGGCAAAAACGTAAAGAAGATTCTCCGGTCTTTGACATGAGAACCCGCTCCGAGTCTCTGGGTCGGACGGATTTGCCTTCACTAGATACAGTGCTTTAGCGTGGGCAGGATGTCGTCCCCGTAGGCGCAGAGGCACGCGCCTTAGTCTGGAAAATAGTCTTGCGTCATGGTGTGGCATGTGTCACCCATTCACGAGATCAAGCTCATAAGAAAAGAACTATTGCACCTCGAAGCTGTACTGAAAGCATCTATGTTCAGGCGTAATTTGGAAAGCACATCTACGCTGTGAGATGCCCACGGTGCCCAAG",
                        3, 26, "3 4 12 13 19 26 27 60 66 106 107 112 125 130 131 144 150 171 177 189 194 206 207 255 325 334" }, //
                new Object[] { "CTTAATTAACG", Utils.getFile("week2/approxPatternMatching.txt"), 4, 156, null }, //
        };
    }

    @Test(dataProvider = "testApproximatePatternMatching_provider")
    public void testApproximatePatternMatching(String str1, String str2, int d, int expectedSize, String expectedList) {
        Genome pattern = new Genome(str1);
        Genome text = new Genome(str2);
        List<Integer> result = pattern.getApproximatePatternMatching(text, d);

        Assert.assertEquals(result.size(), expectedSize, "Result: " + result);
        if (expectedList != null) {
            Assert.assertEquals(Utils.toString(result), expectedList);
        }
    }

    @DataProvider(name = "testFreqWordsWithMismatches_provider")
    private Object[][] testFreqWordsWithMismatches_provider() {
        return new Object[][] { new Object[] { "ACGTTGCATGTCGCATGATGCATGAGAGCT", 4, 1, 3, "ATGC GATG ATGT" }, //
                new Object[] { "AAAAAAAAAA", 2, 1, 1, "AA" }, //
                new Object[] { "AGTCAGTC", 4, 2, 1, "AGTC" }, //
                new Object[] { "TAGCG", 2, 1, 2, "CG AG" }, //
                new Object[] { "AATTAATTGGTAGGTAGGTA", 4, 0, 1, "GGTA" }, //
                new Object[] { "ATA", 3, 1, 1, "ATA" }, //
                new Object[] { "AAT", 3, 0, 1, "AAT" }, //
                new Object[] {
                        "TCGCGCTTTTACGCCTATGCTTCGCCGCCCGCCTCGCTATGCTGCTTATTTTATCGCTCGCTATGCTGCTTCGCTCGCTATCGCCTTTATATCGCCCGCCTTTACGCCTATTCGCTTTATCGCGCTTATTATTTTATATTATGCTTTTATATTTTATCGCTCGCTCGCTATGCTTTTATATTCGCTCGCGCTTTTATCGCTTTACGCCCGCCTCGCTCGCTTTAGCTGCTGCTGCTTCGCCGCCTTTACGCCCGCCTCGCTATGCTTCGCTATCGCCTATTCGCGCTCGCCGCTTCGCTTTACGCCTATTATCGCCTATTCGCTTTATTTATCGCTATGCTGCTTATTATCGCCTATTCGCTCGCTCGCGCTCGCCTATGCTTAT",
                        6, 2, 1, "TCGCGC" } //
        };
    }

    @Test(dataProvider = "testFreqWordsWithMismatches_provider")
    public void testFreqWordsWithMismatches(String str, int k, int d, int expectedSize, String expectedSet) {
        Genome genome = new Genome(str);
        Set<Genome> result = FrequentWords.withMismatches(genome, k, d);

        Assert.assertEquals(result.size(), expectedSize, "Result: " + result);
        Assert.assertEquals(Utils.toString(result), expectedSet);
    }

}
