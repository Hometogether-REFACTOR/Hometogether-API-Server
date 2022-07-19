package hometoogether.hometoogether.domain.post.service;

import hometoogether.hometoogether.domain.post.domain.Trial;

import java.util.Comparator;

public class MyComparator implements Comparator<Trial> {

    @Override
    public int compare(Trial o1, Trial o2) {
        double score1 = o1.getScore();
        double score2 = o2.getScore();

        if (score1 > score2) {
            return -1;
        } else if (score1 < score2) {
            return 1;
        } else {
            return 0;
        }
    }
}
