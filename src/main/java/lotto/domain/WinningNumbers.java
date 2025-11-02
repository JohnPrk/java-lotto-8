package lotto.domain;

import java.util.List;

public class WinningNumbers {

    private final Lotto winningLotto;
    private final int bonusNumber;

    public WinningNumbers(List<Integer> winningNumbers, int bonusNumber) {
        Lotto lotto = new Lotto(winningNumbers);
        validateBonusNumber(lotto, bonusNumber);
        this.winningLotto = lotto;
        this.bonusNumber = bonusNumber;
    }

    public int getBonusNumber() {
        return bonusNumber;
    }

    public int countMatchingNumbers(Lotto lotto) {
        return winningLotto.countMatchingNumbers(lotto);
    }

    public boolean containsBonusNumber(Lotto lotto) {
        return lotto.containsNumber(bonusNumber);
    }

    private void validateBonusNumber(Lotto winningLotto, int bonusNumber) {
        validateBonusRange(bonusNumber);
        validateBonusDuplicate(winningLotto, bonusNumber);
    }

    private void validateBonusRange(int bonusNumber) {
        if (bonusNumber < 1 || bonusNumber > 45) {
            throw new IllegalArgumentException("[ERROR] 보너스 번호는 1부터 45 사이여야 합니다.");
        }
    }

    private void validateBonusDuplicate(Lotto winningLotto, int bonusNumber) {
        if (winningLotto.containsNumber(bonusNumber)) {
            throw new IllegalArgumentException("[ERROR] 보너스 번호는 당첨 번호와 중복될 수 없습니다.");
        }
    }
}
