package lotto.domain;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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

    @Test
    void 로또_번호가_1보다_작으면_예외가_발생한다() {

        // given
        List<Integer> numbers = List.of(0, 2, 3, 4, 5, 6);

        // when & then
        assertThatThrownBy(() -> new Lotto(numbers))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 로또 번호는 1부터 45 사이여야 합니다.");
    }

    @Test
    void 로또_번호가_45보다_크면_예외가_발생한다() {

        // given
        List<Integer> numbers = List.of(1, 2, 3, 4, 5, 46);

        // when & then
        assertThatThrownBy(() -> new Lotto(numbers))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 로또 번호는 1부터 45 사이여야 합니다.");
    }

    @Test
    void 올바른_로또_번호_6개면_로또_객체가_생성된다() {

        // given
        List<Integer> numbers = List.of(8, 1, 45, 3, 20, 10);

        // when
        Lotto lotto = new Lotto(numbers);

        // then
        assertThat(lotto).isNotNull();
    }

    @Test
    void 로또_번호는_오름차순으로_정렬되어_저장된다() {

        // given
        List<Integer> numbers = List.of(8, 1, 45, 3, 20, 10);

        // when
        Lotto lotto = new Lotto(numbers);

        // then
        assertThat(lotto).isNotNull();
        assertThat(lotto.getNumbers())
                .containsExactly(1, 3, 8, 10, 20, 45);
    }
}
