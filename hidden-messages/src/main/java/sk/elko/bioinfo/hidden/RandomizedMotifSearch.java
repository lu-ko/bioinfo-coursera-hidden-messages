package sk.elko.bioinfo.hidden;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomizedMotifSearch {

    private final int LOOP_ITERATIONS = 1000;
    private final Motifs source;

    public RandomizedMotifSearch(String[] dna) {
        this(new Motifs(dna));
    }

    public RandomizedMotifSearch(Motifs motifs) {
        this.source = motifs;
    }

    public Motifs search(final int k, final int t) {
        if (k == 0 || t == 0) {
            throw new IllegalArgumentException();
        }

        int loop = LOOP_ITERATIONS;

        Motifs best = source.getSubMotifs(k, t);
        int scoreBest = best.getScore();

        while (0 < loop--) {
            Motifs current = searchStep(k, t);
            int scoreMotifs = current.getScore();
            if (scoreMotifs < scoreBest) {
                best = current;
                scoreBest = scoreMotifs;
            }
        }
        return best;
    }

    private Motifs searchStep(final int k, final int t) {
        List<String> bestList = new ArrayList<>(t);
        List<String> newMotifsList = new ArrayList<>(t);

        Random random = new Random();
        for (Genome kmer : source.getGenomes()) {
            int start = random.nextInt(kmer.length() - k);
            // System.err.println("k=" + k + " , bound=" + (kmer.length() - k) + " - " + start);
            bestList.add(kmer.substring(start, start + k).getText());
            newMotifsList.add(kmer.substring(start, start + k).getText());
        }

        Motifs best = new Motifs(bestList);
        Motifs newMotifs = new Motifs(newMotifsList);

        int scoreBest = best.getScore();
        // System.err.println(motifs);

        while (true) {
            Profile profile = Profile.parse(newMotifs.getDnaAsList(), true);
            newMotifs = profile.getMotifs(source.getGenomes());

            int scoreMotifs = newMotifs.getScore();
            if (scoreMotifs < scoreBest) {
                best = newMotifs;
                scoreBest = scoreMotifs;
            } else {
                return best;
            }
        }
    }

}
