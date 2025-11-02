package lotto.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;

class LottoTest {

    @Test
    void 로또_번호가_6개보다_많으면_예외가_발생한다() {

        // given
        List<Integer> numbers = List.of(1, 2, 3, 4, 5, 6, 7);

        // when & then
        assertThatThrownBy(() -> new Lotto(numbers))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 로또 번호는 6개여야 합니다.");
    }

    @Test
    void 로또_번호가_6개보다_적으면_예외가_발생한다() {

        // given
        List<Integer> numbers = List.of(1, 2, 3, 4, 5);

        // when & then
        assertThatThrownBy(() -> new Lotto(numbers))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 로또 번호는 6개여야 합니다.");
    }

    @Test
    void 로또_번호에_중복된_숫자가_있으면_예외가_발생한다() {

        // given
        List<Integer> numbers = List.of(1, 2, 3, 4, 5, 5);

        // when & then
        assertThatThrownBy(() -> new Lotto(numbers))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 로또 번호는 중복될 수 없습니다.");
    }

    @ParameterizedTest()
    @ValueSource(ints = {0, 46})
    void 로또_번호가_1부터_45_사이의_값이_아니면_예외가_발생한다(int invalidNumber) {

        // given
        List<Integer> numbers = List.of(1, 2, 3, 4, 5, invalidNumber);

        // when & then
        assertThatThrownBy(() -> new Lotto(numbers))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 로또 번호는 1부터 45 사이여야 합니다.");
    }

    @ParameterizedTest
    @NullSource
    void 로또_번호가_null이면_예외가_발생한다(List<Integer> numbers) {

        // when & then
        assertThatThrownBy(() -> new Lotto(numbers))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 로또 번호는 null일 수 없습니다.");
    }

    @Test
    void 로또_번호가_정상_범위에서_극_값인_1과_45를_포함해도_예외가_발생하지_않는다() {

        // given
        List<Integer> numbers = List.of(1, 2, 3, 4, 5, 45);

        // when & then
        assertThatNoException().isThrownBy(() -> new Lotto(numbers));
    }

    @Test
    void 정상_범위의_6개_값을_사용하면_객체_생성이_가능하다() {

        // given
        List<Integer> numbers = List.of(8, 1, 45, 3, 20, 10);

        // when
        Lotto lotto = new Lotto(numbers);

        // then
        assertThat(lotto).isNotNull();
        assertThat(lotto.getNumbers()).hasSize(6);
    }

    @Test
    void 로또_번호는_오름차순으로_정렬되어_저장된다() {

        // given
        List<Integer> numbers = List.of(8, 1, 45, 3, 20, 10);

        // when
        Lotto lotto = new Lotto(numbers);

        // then
        assertThat(lotto.getNumbers()).containsExactly(1, 3, 8, 10, 20, 45);
    }

    @Test
    void 로또가_특정_번호를_포함하면_true를_반환한다() {

        // given
        Lotto lotto = new Lotto(List.of(1, 3, 5, 10, 20, 45));

        // when & then
        assertThat(lotto.containsNumber(10)).isTrue();
        assertThat(lotto.containsNumber(7)).isFalse();
    }

    @ParameterizedTest
    @MethodSource("provideLottoMatchScenarios")
    void 다른_로또와_일치하는_번호_개수를_계산할_수_있다(Lotto priorLotto, Lotto nextLotto, int expectedMatchCount) {

        // when
        int matchCount = priorLotto.countMatchingNumbers(nextLotto);

        // then
        assertThat(matchCount).isEqualTo(expectedMatchCount);
    }

    @Test
    void 비교할_로또가_null이면_예외가_발생한다() {

        // given
        Lotto lotto = new Lotto(List.of(1, 2, 3, 4, 5, 6));

        // when & then
        assertThatThrownBy(() -> lotto.countMatchingNumbers(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 비교할 로또가 null일 수 없습니다.");
    }

    @Test
    void 로또_번호_목록은_외부에서_수정할_수_없다() {

        // given
        Lotto lotto = new Lotto(List.of(1, 2, 3, 4, 5, 6));

        // when & then
        assertThatThrownBy(() -> lotto.getNumbers().add(7))
                .isInstanceOf(UnsupportedOperationException.class);
    }

    private static Stream<Arguments> provideLottoMatchScenarios() {
        return Stream.of(
                Arguments.of(
                        new Lotto(List.of(1, 2, 3, 4, 5, 6)),
                        new Lotto(List.of(4, 5, 6, 40, 41, 42)),
                        3
                ),
                Arguments.of(
                        new Lotto(List.of(1, 2, 3, 4, 5, 6)),
                        new Lotto(List.of(7, 8, 9, 10, 11, 12)),
                        0
                ),
                Arguments.of(
                        new Lotto(List.of(1, 2, 3, 4, 5, 6)),
                        new Lotto(List.of(1, 2, 3, 4, 5, 6)),
                        6
                )
        );
    }
}
