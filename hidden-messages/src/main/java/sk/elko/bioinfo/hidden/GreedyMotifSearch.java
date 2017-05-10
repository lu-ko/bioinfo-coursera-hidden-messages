package sk.elko.bioinfo.hidden;

import java.util.ArrayList;
import java.util.List;

public class GreedyMotifSearch {

    private final Motifs source;

    public GreedyMotifSearch(String[] dna) {
        this(new Motifs(dna));
    }

    public GreedyMotifSearch(Motifs motifs) {
        this.source = motifs;
    }

    public Motifs search(final int k, final int t, final boolean applyLaplace) {
        if (k == 0 || t == 0) {
            throw new IllegalArgumentException();
        }

        Motifs best = source.getSubMotifs(k, t);
        int scoreBest = best.getScore();

        Genome first = source.getGenome(0);
        for (Genome kmer : first.getAllKmers(k)) {
            List<String> newMotifs = new ArrayList<>();
            newMotifs.add(kmer.getText());

            for (int i = 1; i < t; i++) {
                Profile profile = Profile.parse(newMotifs, applyLaplace);
                // System.err.println(profile.print());
                String mostProbable = profile.getMostProbableKmer(source.getGenome(i));
                newMotifs.add(mostProbable);
            }

            Motifs motifs = new Motifs(newMotifs);
            int scoreMotifs = motifs.getScore();
            if (scoreMotifs < scoreBest) {
                best = motifs;
                scoreBest = scoreMotifs;
            }
        }

        return best;
    }

}
