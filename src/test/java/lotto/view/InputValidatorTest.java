package lotto.view;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class InputValidatorTest {

    @Test
    void 구입_금액_문자열을_정수로_파싱한다() {

        // given
        String input = "3000";

        // when
        int amount = InputValidator.parsePurchaseAmount(input);

        // then
        assertThat(amount).isEqualTo(3000);
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", " ", "   "})
    void 구입_금액_입력이_null_또는_공백이면_예외가_발생한다(String input) {

        // when & then
        assertThatThrownBy(() -> InputValidator.parsePurchaseAmount(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 입력은 비어 있을 수 없습니다.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"abc", "1000원", "1 000", "1,000"})
    void 구입_금액에_숫자가_아닌_값이_포함되면_예외가_발생한다(String input) {

        // when & then
        assertThatThrownBy(() -> InputValidator.parsePurchaseAmount(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 구입 금액은 숫자여야 합니다.");
    }

    @Test
    void 쉼표로_구분된_당첨_번호를_정수_리스트로_파싱한다() {

        // given
        String input = "1,2,3,4,5,6";

        // when
        List<Integer> numbers = InputValidator.parseWinningNumbers(input);

        // then
        assertThat(numbers).containsExactly(1, 2, 3, 4, 5, 6);
    }

    @Test
    void 당첨_번호_파싱시_공백을_무시한다() {

        // given
        String input = " 1,  2 ,3 , 4,5 ,6 ";

        // when
        List<Integer> numbers = InputValidator.parseWinningNumbers(input);

        // then
        assertThat(numbers).containsExactly(1, 2, 3, 4, 5, 6);
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", " ", "   "})
    void 당첨_번호_입력이_null_또는_공백이면_예외가_발생한다(String input) {

        // when & then
        assertThatThrownBy(() -> InputValidator.parseWinningNumbers(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 입력은 비어 있을 수 없습니다.");
    }

    @Test
    void 당첨_번호에_숫자가_아닌_값이_포함되면_예외가_발생한다() {

        // given
        String input = "1, 2, 세, 4, 5, 6";

        // when & then
        assertThatThrownBy(() -> InputValidator.parseWinningNumbers(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 당첨 번호는 숫자여야 합니다.");
    }

    @Test
    void 보너스_번호_문자열을_정수로_파싱한다() {

        // given
        String input = "7";

        // when
        int bonus = InputValidator.parseBonusNumber(input);

        // then
        assertThat(bonus).isEqualTo(7);
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", " ", "   "})
    void 보너스_번호_입력이_null_또는_공백이면_예외가_발생한다(String input) {

        // when & then
        assertThatThrownBy(() -> InputValidator.parseBonusNumber(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 입력은 비어 있을 수 없습니다.");
    }

    @Test
    void 보너스_번호에_숫자가_아닌_값이_들어오면_예외가_발생한다() {
        // given
        String input = "보너스";

        // when & then
        assertThatThrownBy(() -> InputValidator.parseBonusNumber(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 보너스 번호는 숫자여야 합니다.");
    }
}
