package lotto.domain;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class RandomLottoGeneratorTest {

    @Test
    void 로또_생성_요청_개수만큼_랜덤_로또를_생성한다() {

        // given
        LottoGenerator generator = new RandomLottoGenerator();
        int count = 5;

        // when
        List<Lotto> lottos = generator.generate(count);

        // then
        assertThat(lottos).hasSize(count)
                .doesNotContainNull();
    }
}
