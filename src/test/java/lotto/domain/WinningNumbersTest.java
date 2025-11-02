package lotto.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class WinningNumbersTest {

    @ParameterizedTest
    @ValueSource(ints = {1, 45})
    void 보너스_번호가_정상_범위에서_극_값인_1과_45_값이_들어가도_예외가_발생하지_않는다(int validBonusNumber) {

        // given
        List<Integer> numbers = List.of(2, 3, 4, 5, 6, 7);

        // when & then
        assertThatNoException()
                .isThrownBy(() -> new WinningNumbers(numbers, validBonusNumber));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 46})
    void 보너스_번호가_정상_범위를_벗어난_1부터_45_사이의_값이_아니면_예외가_발생한다(int invalidBonusNumber) {

        // given
        List<Integer> numbers = List.of(1, 2, 3, 4, 5, 6);

        // when & then
        assertThatThrownBy(() -> new WinningNumbers(numbers, invalidBonusNumber))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 보너스 번호는 1부터 45 사이여야 합니다.");
    }

    @Test
    void 보너스_번호가_당첨_번호와_중복되면_예외가_발생한다() {

        // given
        List<Integer> numbers = List.of(1, 2, 3, 4, 5, 6);

        // when & then
        assertThatThrownBy(() -> new WinningNumbers(numbers, 6))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 보너스 번호는 당첨 번호와 중복될 수 없습니다.");
    }

    @Test
    void 당첨_번호_로또의_생성자_검증이_보너스_번호_검증보다_먼저_수행된다() {

        // given
        List<Integer> invalidNumbers = List.of(1, 2, 3, 4, 5, 5);

        // when & then
        assertThatThrownBy(() -> new WinningNumbers(invalidNumbers, 7))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 로또 번호는 중복될 수 없습니다.");
    }

    @Test
    void 당첨_번호와_보너스_번호가_모두_유효하면_생성된다() {

        // given
        List<Integer> lottoNumbers = List.of(4, 36, 3, 28, 44, 11);
        int bonusNumber = 7;

        // when
        WinningNumbers winningNumbers = new WinningNumbers(lottoNumbers, bonusNumber);

        // then
        assertThat(winningNumbers).isNotNull();
        assertThat(winningNumbers.getBonusNumber()).isEqualTo(bonusNumber);

        Lotto testLotto = new Lotto(lottoNumbers);
        assertThat(winningNumbers.countMatchingNumbers(testLotto)).isEqualTo(6);
    }

    @Test
    void 당첨_번호_로또와_비교할_로또의_값_일치_개수를_구할_수_있다() {

        // given
        WinningNumbers winningNumbers = new WinningNumbers(List.of(1, 2, 3, 4, 5, 6), 7);
        Lotto lotto = new Lotto(List.of(1, 2, 10, 11, 12, 13));

        // when
        int matchCount = winningNumbers.countMatchingNumbers(lotto);

        // then
        assertThat(matchCount).isEqualTo(2);
    }

    @Test
    void 보너스_번호를_포함하는지_확인할_수_있다() {

        // given
        WinningNumbers winningNumbers = new WinningNumbers(List.of(1, 2, 3, 4, 5, 6), 7);
        Lotto containsBonus = new Lotto(List.of(7, 10, 20, 30, 40, 45));
        Lotto notContainsBonus = new Lotto(List.of(8, 10, 20, 30, 40, 45));

        // when & then
        assertThat(winningNumbers.containsBonusNumber(containsBonus)).isTrue();
        assertThat(winningNumbers.containsBonusNumber(notContainsBonus)).isFalse();
    }
}
