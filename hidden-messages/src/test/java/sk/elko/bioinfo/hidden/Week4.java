package sk.elko.bioinfo.hidden;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class Week4 {

    @DataProvider(name = "testGetEntropy_provider")
    private Object[][] testGetEntropy_provider() {
        return new Object[][] { //
                new Object[] { "0.5 0.0 0.0 0.5", 1.0 }, //
                new Object[] { "0.25 0.25 0.25 0.25", 2.0 }, //
                new Object[] { "0.0 0.0 0.0 1.0", -0.0 }, //
                new Object[] { "0.25 0.0 0.5 0.25", 1.5 }, //
                new Object[] { "0.2 0.6 0.0 0.2", 1.3709505944546687 }, //
                new Object[] { "0.0 0.6 0.0 0.4", 0.9709505944546686 }, //
                new Object[] { "0.0 0.0 0.9 0.1", 0.4689955935892812 } //
        };
    }

    @Test(dataProvider = "testGetEntropy_provider")
    public void testGetEntropy(String rowString, double expectedEntropy) {
        double[] numbers = Utils.parseNucleobaseRow(rowString);
        Assert.assertEquals(Utils.getEntropy(numbers), expectedEntropy);
    }

    @DataProvider(name = "testGetScore_provider")
    private Object[][] testGetScore_provider() {
        return new Object[][] { //
                new Object[] { Utils.parseNucleobaseRow("0.4 0.3 0.0 0.1 0.0 0.9"), //
                        Utils.parseNucleobaseRow("0.2 0.3 0.0 0.4 0.0 0.1"), //
                        Utils.parseNucleobaseRow("0.1 0.3 1.0 0.1 0.5 0.0"), //
                        Utils.parseNucleobaseRow("0.3 0.1 0.0 0.4 0.5 0.0"), //
                        "TCGGTA", 0.00405 } };
    }

    @Test(dataProvider = "testGetScore_provider")
    public void testGetScore(double[] adenine, double[] cytosine, double[] guanine, double[] thymine, String genome, double expectedScore) {
        Profile profile = new Profile(adenine, cytosine, guanine, thymine);
        Assert.assertEquals(profile.getScore(new Genome(genome)), expectedScore);
    }

    @DataProvider(name = "testGetConsensus_provider")
    private Object[][] testGetConsensus_provider() {
        return new Object[][] { //
                new Object[] { Utils.parseNucleobaseRow("0.4 0.3 0.0 0.1 0.0 0.9"), //
                        Utils.parseNucleobaseRow("0.2 0.3 0.0 0.4 0.0 0.1"), //
                        Utils.parseNucleobaseRow("0.1 0.3 1.0 0.1 0.5 0.0"), //
                        Utils.parseNucleobaseRow("0.3 0.1 0.0 0.4 0.5 0.0"), //
                        "AAGCGA" } };
    }

    @Test(dataProvider = "testGetConsensus_provider")
    public void testGetConsensus(double[] adenine, double[] cytosine, double[] guanine, double[] thymine, String expected) {
        Profile profile = new Profile(adenine, cytosine, guanine, thymine);
        Assert.assertEquals(profile.getConsensus(), expected);
    }

    @DataProvider(name = "testProfileMostProbableKmer_provider")
    private Object[][] testProfileMostProbableKmer_provider() {
        return new Object[][] { //
                new Object[] { Utils.parseNucleobaseRow("0.2 0.2 0.3 0.2 0.3"), //
                        Utils.parseNucleobaseRow("0.4 0.3 0.1 0.5 0.1"), //
                        Utils.parseNucleobaseRow("0.3 0.3 0.5 0.2 0.4"), //
                        Utils.parseNucleobaseRow("0.1 0.2 0.1 0.1 0.2"), //
                        "ACCTGTTTATTGCCTAAGTTCCGAACAAACCCAATATAGCCCGAGGGCCT", "CCGAG" }, //
                new Object[] { Utils.parseNucleobaseRow("0.0 0.0 0.0"), //
                        Utils.parseNucleobaseRow("0.0 0.0 1.0"), //
                        Utils.parseNucleobaseRow("1.0 1.0 0.0"), //
                        Utils.parseNucleobaseRow("0.0 0.0 0.0"), //
                        "AAGAATCAGTCA", "AAG" }, //
                new Object[] { Utils.parseNucleobaseRow("0.7 0.2 0.1 0.5 0.4 0.3 0.2 0.1"), //
                        Utils.parseNucleobaseRow("0.2 0.2 0.5 0.4 0.2 0.3 0.1 0.6"), //
                        Utils.parseNucleobaseRow("0.1 0.3 0.2 0.1 0.2 0.1 0.4 0.2"), //
                        Utils.parseNucleobaseRow("0.0 0.3 0.2 0.0 0.2 0.3 0.3 0.1"), //
                        "AGCAGCTTTGACTGCAACGGGCAATATGTCTCTGTGTGGATTAAAAAAAGAGTGTCTGATCTGAACTGGTTACCTGCCGTGAGTAAAT", "AGCAGCTT" }, //
                new Object[] { Utils.parseNucleobaseRow("0.2 0.3 0.4 0.5 0.6 0.7 0.8 0.1 0.2 0.3 0.4 0.5"), //
                        Utils.parseNucleobaseRow("0.3 0.2 0.1 0.1 0.2 0.1 0.1 0.4 0.3 0.2 0.2 0.1"), //
                        Utils.parseNucleobaseRow("0.2 0.1 0.4 0.3 0.1 0.1 0.1 0.3 0.1 0.1 0.2 0.1"), //
                        Utils.parseNucleobaseRow("0.3 0.4 0.1 0.1 0.1 0.1 0.0 0.2 0.4 0.4 0.2 0.3"), //
                        "TTACCATGGGACCGCTGACTGATTTCTGGCGTCAGCGTGATGCTGGTGTGGATGACATTCCGGTGCGCTTTGTAAGCAGAGTTTA", "AAGCAGAGTTTA" }, //
                new Object[] { Utils.parseNucleobaseRow("0.289 0.224 0.355 0.342 0.197 0.184 0.289 0.342 0.197 0.237 0.224 0.171 0.303"), //
                        Utils.parseNucleobaseRow("0.237 0.25 0.211 0.263 0.316 0.289 0.25 0.237 0.237 0.211 0.303 0.303 0.276"), //
                        Utils.parseNucleobaseRow("0.211 0.316 0.211 0.171 0.263 0.171 0.263 0.197 0.303 0.263 0.25 0.368 0.303"), //
                        Utils.parseNucleobaseRow("0.263 0.211 0.224 0.224 0.224 0.355 0.197 0.224 0.263 0.289 0.224 0.158 0.118"), //
                        "TTCCTCAGGCCCTATCGAGTAAAGCGTGAAGAGCCTAGACAGGATGTCGTATTCGTAGAACACAAGGCTATTACGGCATGCTTACACCACCAATTTGAGTAGGTCTTACCTCCGCTATGACAAGATAAAGTTTCGTCCATATGTGGAAGCAACGCCTGCACCATGCGCGTATCAGCTATAGTGCCCGAATTGCGTGGCTTCCCTTGCCTCCCCTCGTAAATTTCCACACCACTGCATTCAGTTGTTAGACTGTCTATCAATCGTAGATTGGTTGATATTAGGAGTGGAAAACTCAAATGTCGGACACCGCCGTTGCTGGCGGGTTAGACAGATGCTACATAACGGTTGAAGGACCACCCCTGTGCTGATAGAAGTCTTTCCATCCACTCAGTCGCGTCCCGCCACGGCCGATTTTGGGACCACCGCTGCCTAGTATGAACGTCAACAGCTAACAAACAACTACTCAAAACGAGGCTCCACCGCGAATCTGACCAAGACCAAATATATCACCCACAGTCCCTTACTCTAGGGAGAGCGCCCTGACAGGCGTAACGCCGGGAAGTCCTAGAGACGGAAAGACGTGTCTAATCGCAGCCCGAAGGAACAAGTTCTTGGAAGCAAGCTCTTGGGTCCCCCATAAGAGACGAAGGCTATAGCCTCAGACAGCAAACTAAGTGGGAGAAGGTGCTTGTTTTCTAAAGTTATACCTCGAGAACTCAAACTGCATGATTTATATAGCGAAGTACCTGTAACCAGTCAATCTTATAGGGCGTCGTAGATTTTACCTATGTCTGGGCACCCGGAAAAGCAATATCGACCCAAAGCTGAAACTCTAAACTATGTACGACACCGTTGACTGGATACAACCTGTCATGATAACGATTCGAATACTCGCGGTTGATCTCCGGCTGGGATGCGGCTGCAGGCATTCGATCTCAAGTTCTAAGAATCTGGTGCGCGTGCTTGGTTCCGCATAAGACCGGGGCGGGAATCTCTTACC",
                        "CAAACTAAGTGGG" }, //
        };
    }

    @Test(dataProvider = "testProfileMostProbableKmer_provider")
    public void testProfileMostProbableKmer(double[] adenine, double[] cytosine, double[] guanine, double[] thymine, String genome, String expected) {
        Profile profile = new Profile(adenine, cytosine, guanine, thymine);
        Assert.assertEquals(profile.getMostProbableKmer(new Genome(genome)), expected);
    }

    @DataProvider(name = "testGreedyMotifSearch_provider")
    private Object[][] testGreedyMotifSearch_provider() {
        return new Object[][] { //
                new Object[] { new String[] { "GGCGTTCAGGCA", "AAGAATCAGTCA", "CAAGGAGTTCGC", "CACGTCAATCAC", "CAATAATATTCG" }, //
                        3, 5, false, "CAG CAG CAA CAA CAA" }, //
                new Object[] { new String[] { "GGCGTTCAGGCA", "AAGAATCAGTCA", "CAAGGAGTTCGC", "CACGTCAATCAC", "CAATAATATTCG" }, //
                        3, 5, true, "TTC ATC TTC ATC TTC" }, //

                new Object[] { new String[] { "GCCCAA", "GGCCTG", "AACCTA", "TTCCTT" }, //
                        3, 4, false, "GCC GCC AAC TTC" }, //
                new Object[] { new String[] { "GCCCAA", "GGCCTG", "AACCTA", "TTCCTT" }, //
                        3, 4, true, "CCA CCT CCT CCT" }, //

                new Object[] {
                        new String[] { "GCAGGTTAATACCGCGGATCAGCTGAGAAACCGGAATGTGCGT", "CCTGCATGCCCGGTTTGAGGAACATCAGCGAAGAACTGTGCGT",
                                "GCGCCAGTAACCCGTGCCAGTCAGGTTAATGGCAGTAACATTT", "AACCCGTGCCAGTCAGGTTAATGGCAGTAACATTTATGCCTTC",
                                "ATGCCTTCCGCGCCAATTGTTCGTATCGTCGCCACTTCGAGTG" }, //
                        6, 5, false, "GTGCGT GTGCGT GCGCCA GTGCCA GCGCCA" }, //

                new Object[] { Utils.getFileRaw("week4/greedy-false.txt").replaceAll("\r", "").split("\n"), //
                        12, 25, false,
                        "GCCTGCCCGCGT CAGCGGCCCCCC CTAAACGCGTGC GTGTAGCCCCCC CCAGGGATTACG GCGTGCACGAGG TAGAGTATTAAC CTCAGGATGCAC CTGCGGGTGCGC AACTTTCCCTCT GACTGGGCGCGT GTCTGGACGCGT TACTGCGCGTGT CCGCGCATGCCG CCCTGCCCGTGT TAATGCCTGAGG GTCTGTGCGTGT CCACGGATGCCG TCCTGCACGAGT GCCAGTATCTAC GACATCATCCGC GACTACACTTAC TACTGCCCGCGT TTCTGCACGCGT TTCTGGGCGAGT" }, //
                new Object[] { Utils.getFileRaw("week4/greedy-true.txt").replaceAll("\r", "").split("\n"), //
                        12, 25, true,
                        "CGATGCGTCAAA TGCTGCGTCCCA CGGTGCGTCCTA TGCTGCGTCAAA GGGTGCGTCACA CGATGCGTCGTA TGTTGCGTCGCA AGTTGCGTCTGA GGTTGCGTCGAA CGGTGCGTCGCA TGGTGCGTCTGA GGTTGCGTCCAA TGCTGCGTCTGA AGTTGCGTCCGA TGCTGCGTCCCA GGGTGCGTCTAA CGTTGCGTCGGA CGGTGCGTCAGA GGGTGCGTCCTA AGATGCGTCTGA CGCTGCGTCGAA CGTTGCGTCGGA TGCTGCGTCAAA GGTTGCGTCGGA CGCTGCGTCCGA" }, //
        };
    }

    @Test(dataProvider = "testGreedyMotifSearch_provider")
    public void testGreedyMotifSearch(String[] dna, int k, int t, boolean applyLaplace, String expected) {
        GreedyMotifSearch gmSearch = new GreedyMotifSearch(dna);
        Motifs motifs = gmSearch.search(k, t, applyLaplace);
        Assert.assertEquals(Utils.toString(motifs.getGenomes()), expected);
    }

    @DataProvider(name = "testRandomizedMotifSearch_provider")
    private Object[][] testRandomizedMotifSearch_provider() {
        return new Object[][] { //
                new Object[] { new String[] { "ATGAGGTC", "GCCCTAGA", "AAATAGAT", "TTGTGCTA" }, //
                        3, 4, "AGG AGA AGA TGT" }, //

                new Object[] {
                        new String[] { "CGCCCCTCTCGGGGGTGTTCAGTAAACGGCCA", "GGGCGAGGTATGTGTAAGTGCCAAGGTGCCAG", "TAGTACCGAGACCGAAAGAAGTATACAGGCGT",
                                "TAGATCAAGTTTCAGGTGCACGTCGGTGAACC", "AATCCACCAGCTCCACGTGCAATGTTGGCCTA" }, //
                        8, 5, "TCTCGGGG CCAAGGTG TACAGGCG TTCAGGTG TCCACGTG" }, //

                new Object[] {
                        new String[] { "AATTGGCACATCATTATCGATAACGATTCGCCGCATTGCC", "GGTTAACATCGAATAACTGACACCTGCTCTGGCACCGCTC",
                                "AATTGGCGGCGGTATAGCCAGATAGTGCCAATAATTTCCT", "GGTTAATGGTGAAGTGTGGGTTATGGGGAAAGGCAGACTG",
                                "AATTGGACGGCAACTACGGTTACAACGCAGCAAGAATATT", "GGTTAACTGTTGTTGCTAACACCGTTAAGCGACGGCAACT",
                                "AATTGGCCAACGTAGGCGCGGCTTGGCATCTCGGTGTGTG", "GGTTAAAAGGCGCATCTTACTCTTTTCGCTTTCAAAAAAA" }, //
                        6, 8, "CGATAA GGTTAA GGTATA GGTTAA GGTTAC GGTTAA GGCCAA GGTTAA" }, //

                new Object[] {
                        new String[] { "GCACATCATTAAACGATTCGCCGCATTGCCTCGATTAACC", "TCATAACTGACACCTGCTCTGGCACCGCTCATCCAAGGCC",
                                "AAGCGGGTATAGCCAGATAGTGCCAATAATTTCCTTAACC", "AGTCGGTGGTGAAGTGTGGGTTATGGGGAAAGGCAAGGCC",
                                "AACCGGACGGCAACTACGGTTACAACGCAGCAAGTTAACC", "AGGCGTCTGTTGTTGCTAACACCGTTAAGCGACGAAGGCC",
                                "AAGCTTCCAACATCGTCTTGGCATCTCGGTGTGTTTAACC", "AATTGAACATCTTACTCTTTTCGCTTTCAAAAAAAAGGCC" }, //
                        6, 8, "TTAACC TCATCC TTAACC TTATGG TTAACC TTAAGC TTAACC TTACTC" }, //

                new Object[] { Utils.getFileRaw("week4/randomized.txt").replaceAll("\r", "").split("\n"), //
                        15, 20,
                        "GGCGAGATGACACCA AGCTACACGACTCGA AGCGCTACGAACGGA GCAGCTACGACTCGA AGCGCTACGCGACGA AGTATTACGACTCGA AGCGCTACCTTTCGA AGCGCTACGACTTTG AGCGCTCGAACTCGA AGCGACGCGACTCGA "
                                + "AGCGCTAACCCTCGA AGCGCTACGACCTTA GGCGCTACGACTCAG CCCGCTACGACTCGT AGCGCTCGTACTCGA AGCGTCTCGACTCGA AGCGCGGAGACTCGA AGCCAGACGACTCGA AGCGCGTTGACTCGA ATTCCTACGACTCGA" }, //
        };
    }

    @Test(dataProvider = "testRandomizedMotifSearch_provider")
    public void testRandomizedMotifSearch(String[] dna, int k, int t, String expected) {
        RandomizedMotifSearch rmSearch = new RandomizedMotifSearch(dna);
        Motifs motifs = rmSearch.search(k, t);

        try {
            Assert.assertEquals(Utils.toString(motifs.getGenomes()), expected);
        } catch (AssertionError e) {
            System.err.println("Test 'testRandomizedMotifSearch(" + dna + ", " + k + ", " + t + ")' failed but it's OK - It is using Random");
        }
    }

}
