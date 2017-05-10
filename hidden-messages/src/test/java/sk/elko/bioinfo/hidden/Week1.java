package sk.elko.bioinfo.hidden;

import java.util.Collection;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class Week1 {

    @DataProvider(name = "testHammingDistance_provider")
    private Object[][] testHammingDistance_provider() {
        return new Object[][] { new Object[] { "actaa", "agtaa", 1 }, //
                new Object[] { "CTTGAAGTGGACCTCTAGTTCCTCTACAAAGAACAGGTTGACCTGTCGCGAAG", "ATGCCTTACCTAGATGCAATGACGGACGTATTCCTTTTGCCTCAACGGCTCCT", 43 }, //
                new Object[] { "CTACAGCAATACGATCATATGCGGATCCGCAGTGGCCGGTAGACACACGT", "CTACCCCGCTGCTCAATGACCGGGACTAAAGAGGCGAAGATTATGGTGTG", 36 }, //
                new Object[] {
                        "GCCCAAACGTGGCAGTAATGTGATTCTGTTGACTAGGCACACCCCGAAATTCGAGGTGACTTCATGGATAGATTTCCACTTTGATATACTCCGACCGCAGGACCCCTTTGCGCACTGCACTGGCGAGAAAAGGCGTGCTATACAAATAATGGGTCCTATACCTTCGCATTAAGGCTCTATCATGACTTGCCAACACCTACTTATCGTTTCGAAAGTGTGCACACGAAATTACAGATGGGCTTTCGCAGAAGGGTCGTTTGCGGATAGCCTCCCGATGCAGCTGAAAGCATGATACAGATCAGTCGAGTTTTTCAGAGCCTATAAGCTTAGTCGGCAGGCGGCATAGAATGTATATATAACCACTGTGTGGACAGTTCCACGGACGTCCAATCGCCGACCTTTCCTCTCTCATTCCGTTGTAACAATGTCGGGGCCTCTTGCCGTCAAACTATCCGCTCATTCAGCCTCGCGTCTGGAAAGCCCAGAAGGCAAGAAGCAAGAAAGGCTACGGGTACATACTACTTGGCAGGCAGGCAATATGACGAGATTATCTCCCGTGGCGGTCCCCGTTGCTGGGCTCGCATCACGACATGCTTCCCCGGAGCTCTTTAACCGAGTGCCCCCCAGGCATCGGAGGTCTTCCCGCTGCGTCTGAGCAGATCGTAGCACCATTGCAGGAATCCCTTATACGCGTCTCTTGATCCGCCACTCCACTCTCTACTTGGTTTAAATCTCGGAGTCTTGAGTACCTTCACACGAAGTTTTGGCACCCCTACCCGAACGATTGCGTGCTATGATTGCTCTTATGAGAGCTTGACCACCATGGAGCACCTCTGGAAACACATCGAATCGTTAATGAATCAATTTTCCCCCTCCGTTTGCGAATGATTATGCGTAATAGAGATACTCAGGAGTGTCGTGCCGGCTGCACGTGTGAAATCAATAGAAGCTTTCGGACCCCATGCGAGCAGGGTTCAATAGGCGATCAAGAACGGCTGGTCAATATCCGTAGTGCATTCTAGTCGGCATAAACGTTTACATGCAAAGATGTATGAACTGCATTGCGGCTACGCCATAAGGCTGATAGAAAGGGAAGTGAGTGTCCCGACGTCATCGTGCTCCGGCTGCCACTGACCAACCTTCGCAATTTGGCCACTTCTTCGGTCCTAAAACCTTAACCCAGACTGCCCGCGAAAAA",
                        "CACGCGCACTTGCACCACATACCGCAGATGCGAATTCTTTATATCTGTCTGTGGGGAGACTCATGTAATTCCGGCAAATGTCGGCACCCCGTCGCGAGTCCGCGTCTGCGAACGCTCGTGAACCGAGCGGAACAGGCGTCGGGGCACCGAGATTCGTCGTATCATTTGTTAGGAACTGGGCATCCCCCATAACGTCGCGGGATGCTGCGTCACGTGCTCTAGCTGGTTGCGACGCAAAAATTTACGTCGCGTGCATTCAGTCGGGGAGAGGGTTGAATCAGTAAGTTACGACGTACACGAGGAACACAACCGACTATGCTTGACTGAGTGCGGTCCCATTCTACACTCCCGGCAGACCGACGGCCGCGGGAACCCATTGGTACTCCTGGTACTGGGAGCGAATGGTCAGTAGACCGGCAATCCTTTTTCAACGGTACTGTTAAACAGGCTGACATAACCTGAAATCCAGTTCATCAATCGGGTGGTCCGTGGAATCTACAGCGGTCCTCATAAGCCTCTTAACTCTGTTTCTGCATTGTGTACAACTGTAGGTTAACGGCTCGATCAGGATTGGACTAGAAGTTATGACCCCGACGTGGGCAGAGTGATATTCATTAGAAGCCTCACATGTGATGGCTCGATCCGCTAAGATAATTGTCCGGAAGGGCATATGATCAACAGTCTGGGGGTTCTGAGCATATCCCGCATACAGCCGTAAGTCATCTTTGCCTATAGAATCTTAATGTGGGGGATAACTACTGAGAGAGGAAGTCCCTTGGATCCGTCACACGGGAATTTTTAATAACTGGATTCGCTGGTTAATACCCACGACCGTTATGGTGAGCGTGGATTTTTTAGGCTAAGGCGATAGGGTCGGTGAATTTCGGTGCATTACATAACCCAAGATTATGGCAAGTATCACGTCTTATGCGAAACCGCTAATAATATCCCGGGTGGTGATTCGAACCAAAATGTCAGAACACCGCATTATCCGTATGGTATAGTACCTATCTTAGGACTCAGATTGGTAATCATTATTGCTTGGCTGACACTGAGGTCATATTCTGTCTGTAGTGGGGCCACGTGCACTGCTTAGGGATGAAATACTCGAAAGTGTCGGGTTAGTTCGCTCGGGACGCCGCGGGGTGCATCGTGAGTGAGTGGCGACTACTATATGGGAGTGTTCCACTGTGAGAAC",
                        906 } };
    }

    @Test(dataProvider = "testHammingDistance_provider")
    public void testHammingDistance(String str1, String str2, int expected) {
        Genome genome1 = new Genome(str1);
        Genome genome2 = new Genome(str2);
        Assert.assertEquals(genome1.getHammingDistance(genome2), expected);
    }

    @DataProvider(name = "testNeighbors_provider")
    private Object[][] testNeighbors_provider() {
        return new Object[][] { new Object[] { "ACG", 1, 10, "ACC AGG ACT ATG ACG CCG AAG TCG GCG ACA" }, //
                new Object[] { "ACG", 0, 1, "ACG" }, //
                new Object[] { "ACGT", 3, 175, null }, //
                new Object[] { "TGCAT", 2, 106, null }, //
                new Object[] { "GAGACTCA", 2, 277, null } };
    }

    @Test(dataProvider = "testNeighbors_provider")
    public void testNeighbors(String str, int d, int count, String expected) {
        Genome genome = new Genome(str);
        Collection<?> result = genome.getNeighbors(d);

        Assert.assertEquals(result.size(), count);
        if (expected != null) {
            Assert.assertEquals(Utils.toString(result), expected);
        }
    }

    @DataProvider(name = "testReverseComplement_provider")
    private Object[][] testReverseComplement_provider() {
        return new Object[][] { new Object[] { "ACGT", "ACGT" }, //
                new Object[] { "ACGTTACGTT", "AACGTAACGT" }, //
                new Object[] { "TTGTGTC", "GACACAA" }, //
                new Object[] { "AAAACCCGGT", "ACCGGGTTTT" } };
    }

    @Test(dataProvider = "testReverseComplement_provider")
    public void testReverseComplement(String str, String expected) {
        Genome genome = new Genome(str);
        Assert.assertEquals(genome.getReverseComplement().getText(), expected);
    }

    @DataProvider(name = "testExactOccurences_provider")
    private Object[][] testExactOccurences_provider() {
        return new Object[][] { new Object[] { "GCGCG", "GCG", 2 }, //
                new Object[] {
                        "TATTTGCCATATTTGCGTATTTGCAACTTGTATTTGCGATATTTGCGAATATTTGCGCAGTATTTGCCCTATTTGCAGCTTTATTTGCATATTTGCTATTTGCTATTTGCTATTTGCTATTTGCATTATTTGCTGTTTTGGTATTTGCCTATTTGCAATATTTGCTTATTTGCTATTTGCGTATTTGCCATTATTTGCTTTGGAACATATTTGCGTATTTGCCATATTTGCCTATTTGCTTTATTTGCCTATTTGCTATTTGCTATTTGCTATTTGCTCTGGATGCTCTATTTGCGCGTTTATTTGCGTGGTATTTGCTATTTGCCCAACTATTTGCCAACTTATTTGCTTATTTGCCAATATTTGCTATTTGCTATTTGCCCGTTGTATTTGCGTATTTGCTATTTGCATCGTATTTGCTATTTGCTTATTTGCACCTTATATTTGCGATATTTGCTAATATTTGCCACACATATTTGCGCTATTTGCCCTATTTGCGGTCCACTTATTTGCGTATTTGCATCAATCAACTATATTTGCACCCTATGCTATTTGCTATTTGCGGGTTATTTGCCTCAGTGTTGTATTTGCTTATTTGCTCTATTTGCTATTTGCTATTTGCGGTATTTGCGGAGTATTTGCGCTATTTGCAATGCGGGGTGTATTTGCTGTATTTGCTATTTGCTATTTGCAGTATTTGCCAGTATTTGCTAATTATTTGCTAGATATTTGCTATTTGCTATTTGCTATTTGCCATTATATTTGCTTCAGCGCCATATTTGCAATATTTGCTGTATTTGCTTTATTTGCTATTTGCTTATTTGCGGGTATTTGCAGCTTCTATTTGCTATTTGCCGACTATTTGCTGACCTTATTTGCAATATTTGCGTATTTGCTATTTGCGTAAATATTTGCTATTTGCTTAACTATTTGC",
                        "TATTTGCTA", 28 }, //
                new Object[] { "CGCGATACGTTACATACATGATAGACCGCGCGCGATCATATCGCGATTATC", "CGCG", 5 }, //
                new Object[] { "GACCATCAAAACTGATAAACTACTTAAAAATCAGT", "AAA", 6 }, //
                new Object[] { Utils.getFile("week1/Vibrio_cholerae.txt"), "CTTGATCAT", 16 }, //
                new Object[] { Utils.getFile("week1/Vibrio_cholerae.txt"), "ATGATCAAG", 17 } };
    }

    @Test(dataProvider = "testExactOccurences_provider")
    public void testExactOccurences(String str1, String str2, int expected) {
        Genome genome1 = new Genome(str1);
        Genome genome2 = new Genome(str2);
        Assert.assertEquals(genome1.getExactOccurences(genome2), expected);
    }

    @DataProvider(name = "testAllOccurences_provider")
    private Object[][] testAllOccurences_provider() {
        return new Object[][] { new Object[] { "GCGCG", "GCG", 3, "0 1 2" }, //
                new Object[] { "GATATATGCATATACTT", "ATAT", 3, "1 3 9" }, //
                new Object[] { Utils.getFile("week1/Vibrio_cholerae.txt"), "ATGATCAAG", 33, null }, //
                new Object[] { Utils.getFile("week1/allOccurences.txt"), "GCTTTGAGC", 223, null } };
    }

    @Test(dataProvider = "testAllOccurences_provider")
    public void testAllOccurences(String str1, String str2, int count, String expected) {
        Genome genome1 = new Genome(str1);
        Genome genome2 = new Genome(str2);
        List<?> list = genome1.getAllOccurences(genome2);

        Assert.assertEquals(list.size(), count);
        if (expected != null) {
            Assert.assertEquals(Utils.toString(list), expected);
        }
    }

    @DataProvider(name = "testPatternNumberConverter_provider")
    private Object[][] testPatternNumberConverter_provider() {
        return new Object[][] { new Object[] { "T", 3, 1 }, //
                new Object[] { "AGT", 11, 3 }, //
                new Object[] { "AGTC", 45, 4 }, //
                new Object[] { "ATGCAA", 912, 6 }, //
                new Object[] { "CCCATTC", 5437, 7 }, //
                new Object[] { "ACCCATTC", 5437, 8 }, //
                new Object[] { "AAAAGAACGAC", 8289, 11 }, //
                new Object[] { "TTTGGACAGCTCAGTAGC", 68321661641L, 18 } };
    }

    @Test(dataProvider = "testPatternNumberConverter_provider")
    public void testPatternNumberConverter(String pattern, long number, int k) {
        long resultNumber = PatternNumberConverter.getPatternToNumber(pattern);
        Assert.assertEquals(resultNumber, number);

        String resultPattern = PatternNumberConverter.getNumberToPattern(number, k);
        Assert.assertEquals(resultPattern, pattern);
    }

    @DataProvider(name = "testSkew_provider")
    private Object[][] testSkew_provider() {
        return new Object[][] { new Object[] { "ACCG", "3", "1" }, //
                new Object[] { "ACCC", "4", "1" }, //
                new Object[] { "CCGGGT", "2", "5 6" }, //
                new Object[] { "CCGGCCGG", "2 6", "4 8" }, //
                new Object[] { "CATTCCAGTACTTCGATGATGGCGTGAAGA", "14", "29 30" }, //
                new Object[] { "GCATACACTTCCCAGTAGGTACTG", "13 14", "1" }, //
                new Object[] { Utils.getFile("week1/skew.txt"), "16334 16335 16336 16337 16338 16339 16340",
                        "94261 94266 94268 94269 94270 94272" } };
    }

    @Test(dataProvider = "testSkew_provider")
    public void testSkew(String pattern, String minimum, String maximum) {
        Genome genome = new Genome(pattern);
        Assert.assertEquals(Utils.toString(genome.getSkewMinimum()), minimum);
        Assert.assertEquals(Utils.toString(genome.getSkewMaximum()), maximum);
    }

}
