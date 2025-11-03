package lotto.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class LottoResultsTest {

    @Test
    void 주어진_로또들과_당첨_번호_로또를_비교하여_순위를_올리고_집계할_수_있다() {

        // given
        WinningNumbers winningNumbers = new WinningNumbers(List.of(1, 2, 3, 4, 5, 6), 7);
        Lotto first = new Lotto(List.of(1, 2, 3, 4, 5, 6));
        Lotto second = new Lotto(List.of(1, 2, 3, 4, 5, 7));
        Lotto third = new Lotto(List.of(1, 2, 3, 4, 5, 45));
        Lotto fourth = new Lotto(List.of(1, 2, 3, 4, 11, 45));
        Lotto fifth = new Lotto(List.of(1, 2, 3, 7, 11, 45));
        Lotto none = new Lotto(List.of(10, 11, 12, 13, 14, 15));
        List<Lotto> lottos = List.of(first, second, third, fourth, fifth, none);

        // when
        LottoResults results = new LottoResults(lottos, winningNumbers);

        // then
        assertThat(results.getCountOf(LottoRank.FIRST)).isEqualTo(1);
        assertThat(results.getCountOf(LottoRank.SECOND)).isEqualTo(1);
        assertThat(results.getCountOf(LottoRank.THIRD)).isEqualTo(1);
        assertThat(results.getCountOf(LottoRank.FOURTH)).isEqualTo(1);
        assertThat(results.getCountOf(LottoRank.FIFTH)).isEqualTo(1);
        assertThat(results.getCountOf(LottoRank.NONE)).isEqualTo(1);
    }

    @Test
    void 주어진_로또들과_당첨_번호_로또의_일치_개수가_2_이하라면_NONE만_증가한다() {

        // given
        WinningNumbers winningNumbers = new WinningNumbers(List.of(1, 2, 3, 4, 5, 6), 7);
        List<Lotto> lottos = List.of(
                new Lotto(List.of(10, 11, 12, 13, 14, 15)),
                new Lotto(List.of(20, 21, 22, 23, 24, 25)),
                new Lotto(List.of(1, 2, 22, 23, 24, 25)),
                new Lotto(List.of(20, 21, 3, 23, 24, 25)),
                new Lotto(List.of(20, 21, 3, 4, 24, 25)),
                new Lotto(List.of(20, 21, 10, 11, 5, 25)),
                new Lotto(List.of(20, 21, 10, 11, 5, 6))
        );

        // when
        LottoResults results = new LottoResults(lottos, winningNumbers);

        // then
        for (LottoRank rank : LottoRank.values()) {
            if (rank == LottoRank.NONE) {
                assertThat(results.getCountOf(rank)).isEqualTo(7);
                continue;
            }
            assertThat(results.getCountOf(rank)).isEqualTo(0);
        }
    }

    @Test
    void 당첨된_로또들의_총_당첨금을_합산한다() {

        // given
        WinningNumbers winningNumbers = new WinningNumbers(List.of(1, 2, 3, 4, 5, 6), 7);
        Lotto first = new Lotto(List.of(1, 2, 3, 4, 5, 6));
        Lotto fifth = new Lotto(List.of(1, 2, 3, 40, 41, 42));
        List<Lotto> lottos = List.of(first, fifth);

        // when
        LottoResults results = new LottoResults(lottos, winningNumbers);

        // then
        assertThat(results.getTotalPrize()).isEqualTo(2_000_005_000L);
    }

    @Test
    void 수익률을_구입금액으로_계산한다() {

        // given
        WinningNumbers winningNumbers = new WinningNumbers(List.of(1, 2, 3, 4, 5, 6), 7);
        Lotto first = new Lotto(List.of(1, 2, 3, 4, 5, 6));
        Lotto third = new Lotto(List.of(1, 2, 3, 4, 17, 6));
        List<Lotto> lottos = List.of(first, third);
        LottoResults results = new LottoResults(lottos, winningNumbers);

        // when
        double yield = results.calculateYield(lottos.size() * 1000);

        // then
        assertThat(yield).isEqualTo(2_001_500_000D / 2_000D);
    }

    @ParameterizedTest
    @NullSource
    void 로또_목록이_null이면_예외가_발생한다(List<Lotto> lottos) {

        // given
        WinningNumbers winningNumbers = new WinningNumbers(List.of(1, 2, 3, 4, 5, 6), 7);

        // when & then
        assertThatThrownBy(() -> new LottoResults(lottos, winningNumbers))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 당첨 결과를 계산할 로또 목록은 null일 수 없습니다.");
    }

    @Test
    void 로또_목록에_null이_포함되면_예외가_발생한다() {

        // given
        WinningNumbers winningNumbers = new WinningNumbers(List.of(1, 2, 3, 4, 5, 6), 7);
        List<Lotto> lottos = new ArrayList<>();
        lottos.add(new Lotto(List.of(1, 2, 3, 4, 5, 6)));
        lottos.add(null);

        // when & then
        assertThatThrownBy(() -> new LottoResults(lottos, winningNumbers))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 로또 목록에 null이 포함될 수 없습니다.");
    }

    @Test
    void 당첨번호가_null이면_예외가_발생한다() {
        // given
        List<Lotto> lottos = List.of(new Lotto(List.of(1, 2, 3, 4, 5, 6)));

        // when & then
        assertThatThrownBy(() -> new LottoResults(lottos, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 당첨 번호는 null일 수 없습니다.");
    }

    @ParameterizedTest
    @ValueSource(ints = {0, -10})
    void 수익률_계산시_구입금액이_0_이하이면_예외가_발생한다(int purchaseAmount) {

        // given
        WinningNumbers winningNumbers = new WinningNumbers(List.of(1, 2, 3, 4, 5, 6), 7);
        LottoResults results = new LottoResults(
                List.of(new Lotto(List.of(1, 2, 3, 4, 5, 6))),
                winningNumbers
        );

        // when & then
        assertThatThrownBy(() -> results.calculateYield(purchaseAmount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 구입 금액은 0보다 커야 합니다.");
    }
}
