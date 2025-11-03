package lotto.domain;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class LottoResults {

    private final Map<LottoRank, Integer> results = new EnumMap<>(LottoRank.class);
    private long totalPrize;

    public LottoResults(List<Lotto> lottos, WinningNumbers winningNumbers) {
        validateLottos(lottos);
        validateLottoElements(lottos);
        validateWinningNumbers(winningNumbers);
        countResults(lottos, winningNumbers);
    }

    public int getCountOf(LottoRank rank) {
        return results.getOrDefault(rank, 0);
    }

    public long getTotalPrize() {
        return totalPrize;
    }

    public double calculateYield(long purchaseAmount) {
        if (purchaseAmount <= 0) {
            throw new IllegalArgumentException("[ERROR] 구입 금액은 0보다 커야 합니다.");
        }
        return (double) totalPrize / purchaseAmount;
    }

    private void validateLottos(List<Lotto> lottos) {
        if (lottos == null) {
            throw new IllegalArgumentException("[ERROR] 당첨 결과를 계산할 로또 목록은 null일 수 없습니다.");
        }
    }

    private void validateLottoElements(List<Lotto> lottos) {
        for (Lotto lotto : lottos) {
            if (lotto == null) {
                throw new IllegalArgumentException("[ERROR] 로또 목록에 null이 포함될 수 없습니다.");
            }
        }
    }

    private void validateWinningNumbers(WinningNumbers winningNumbers) {
        if (winningNumbers == null) {
            throw new IllegalArgumentException("[ERROR] 당첨 번호는 null일 수 없습니다.");
        }
    }

    private void countResults(List<Lotto> lottos, WinningNumbers winningNumbers) {
        for (Lotto lotto : lottos) {
            LottoRank rank = toRank(lotto, winningNumbers);
            incrementRankCount(rank);
            totalPrize += rank.getPrize();
        }
    }

    private LottoRank toRank(Lotto lotto, WinningNumbers winningNumbers) {
        int matchCount = winningNumbers.countMatchingNumbers(lotto);
        boolean bonusMatched = winningNumbers.containsBonusNumber(lotto);
        return LottoRank.from(matchCount, bonusMatched);
    }

    private void incrementRankCount(LottoRank rank) {
        results.put(rank, results.getOrDefault(rank, 0) + 1);
    }
}
