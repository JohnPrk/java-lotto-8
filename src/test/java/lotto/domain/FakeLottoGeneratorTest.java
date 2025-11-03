package lotto.domain;

import lotto.domain.testDouble.FakeLottoGenerator;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class FakeLottoGeneratorTest {

    @Test
    void 준비된_번호_시퀀스를_사용해_요청한_개수만큼_로또를_생성한다() {

        // given
        List<List<Integer>> numbersSequences = List.of(
                List.of(1, 2, 3, 4, 5, 6),
                List.of(7, 8, 9, 10, 11, 12)
        );
        LottoGenerator generator = new FakeLottoGenerator(numbersSequences);

        // when
        List<Lotto> lottos = generator.generate(2);

        // then
        assertThat(lottos).hasSize(2);
        assertThat(lottos.get(0).getNumbers()).containsExactly(1, 2, 3, 4, 5, 6);
        assertThat(lottos.get(1).getNumbers()).containsExactly(7, 8, 9, 10, 11, 12);
    }

    @Test
    void 준비된_번호보다_많은_개수를_요청하면_NoSuchElementException이_발생한다() {

        // given
        List<List<Integer>> numbersSequences = List.of(
                List.of(1, 2, 3, 4, 5, 6)
        );
        LottoGenerator generator = new FakeLottoGenerator(numbersSequences);

        // when & then
        assertThatThrownBy(() -> generator.generate(2))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void of_정적_팩터리_메서드는_단일_번호_세트로_로또를_생성한다() {

        // given
        FakeLottoGenerator generator = FakeLottoGenerator.of(
                List.of(1, 2, 3, 4, 5, 6)
        );

        // when
        List<Lotto> lottos = generator.generate(1);

        // then
        assertThat(lottos).hasSize(1);
        assertThat(lottos.get(0).getNumbers()).containsExactly(1, 2, 3, 4, 5, 6);
    }
}
