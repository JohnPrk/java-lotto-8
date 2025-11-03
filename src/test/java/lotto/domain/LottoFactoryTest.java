package lotto.domain;

import lotto.domain.testDouble.FakeLottoGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class LottoFactoryTest {

    @Test
    void 유효한_생성기와_개수로_로또_목록을_생성한다() {

        // given
        FakeLottoGenerator generator = new FakeLottoGenerator(
                List.of(
                        List.of(1, 2, 3, 4, 5, 6),
                        List.of(7, 8, 9, 10, 11, 12),
                        List.of(13, 14, 15, 16, 17, 18)
                )
        );
        LottoFactory factory = new LottoFactory(generator);
        int count = 3;

        // when
        List<Lotto> lottos = factory.generate(count);

        // then
        assertThat(lottos).hasSize(count);
        assertThat(lottos).allSatisfy(lotto -> assertThat(lotto).isNotNull());
    }

    @ParameterizedTest
    @NullSource
    void 생성기가_null이면_예외가_발생한다(LottoGenerator generator) {

        // when & then
        assertThatThrownBy(() -> new LottoFactory(generator))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 로또 생성기는 null일 수 없습니다.");
    }

    @ParameterizedTest
    @ValueSource(ints = {0, -1, -10})
    void 생성_개수가_0_이하이면_예외가_발생한다(int count) {

        // given
        LottoGenerator generator = FakeLottoGenerator.of(List.of(1, 2, 3, 4, 5, 6));
        LottoFactory factory = new LottoFactory(generator);

        // when & then
        assertThatThrownBy(() -> factory.generate(count))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 로또 생성 개수는 1개 이상이어야 합니다.");
    }
}
