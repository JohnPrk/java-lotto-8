package lotto.domain;

import java.util.Arrays;

public enum LottoRank {

    FIRST(6, false, 2_000_000_000L),
    SECOND(5, true, 30_000_000L),
    THIRD(5, false, 1_500_000L),
    FOURTH(4, false, 50_000L),
    FIFTH(3, false, 5_000L),
    NONE(0, false, 0L);

    private final int matchCount;
    private final boolean requiresBonus;
    private final long prize;

    LottoRank(int matchCount, boolean requiresBonus, long prize) {
        this.matchCount = matchCount;
        this.requiresBonus = requiresBonus;
        this.prize = prize;
    }

    public long getPrize() {
        return prize;
    }

    public static LottoRank from(int matchCount, boolean bonusMatched) {
        if (matchCount == 5) {
            return bonusMatched ? SECOND : THIRD;
        }
        return Arrays.stream(values())
                .filter(rank -> rank.matchCount == matchCount)
                .findFirst()
                .orElse(NONE);
    }
}
