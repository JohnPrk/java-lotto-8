package lotto.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class LottoRankTest {

    @Test
    void 여섯_개가_모두_일치하면_1등이다() {

        // given
        int matchCount = 6;
        boolean bonusMatched = false;

        // when
        LottoRank rank = LottoRank.from(matchCount, bonusMatched);

        // then
        assertThat(rank).isEqualTo(LottoRank.FIRST);
    }

    @Test
    void 다섯_개와_보너스까지_일치하면_2등이다() {

        // given
        int matchCount = 5;
        boolean bonusMatched = true;

        // when
        LottoRank rank = LottoRank.from(matchCount, bonusMatched);

        // then
        assertThat(rank).isEqualTo(LottoRank.SECOND);
    }

    @Test
    void 다섯_개만_일치하고_보너스는_다르면_3등이다() {

        // given
        int matchCount = 5;
        boolean bonusMatched = false;

        // when
        LottoRank rank = LottoRank.from(matchCount, bonusMatched);

        // then
        assertThat(rank).isEqualTo(LottoRank.THIRD);
    }

    @Test
    void 네_개가_일치하면_4등이다() {

        // given
        int matchCount = 4;

        // when
        LottoRank rank = LottoRank.from(matchCount, false);

        // then
        assertThat(rank).isEqualTo(LottoRank.FOURTH);
    }

    @Test
    void 세_개가_일치하면_5등이다() {

        // given
        int matchCount = 3;

        // when
        LottoRank rank = LottoRank.from(matchCount, false);

        // then
        assertThat(rank).isEqualTo(LottoRank.FIFTH);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2})
    void 두_개_이하로_일치하면_꽝이다(int matchCount) {

        // when
        LottoRank rank = LottoRank.from(matchCount, false);

        // then
        assertThat(rank).isEqualTo(LottoRank.NONE);
    }

    @ParameterizedTest
    @MethodSource("providePrizePerRank")
    void 등수별_당첨금을_반환한다(LottoRank rank, long expectedPrize) {

        //when
        long prize = rank.getPrize();

        // then
        assertThat(prize).isEqualTo(expectedPrize);
    }


    @ParameterizedTest
    @MethodSource("bonusShouldBeIgnoredCases")
    void 보너스는_다섯_개가_맞을_때를_제외하고는_영향을_주지_않는다(int matchCount) {

        // when
        LottoRank withBonus = LottoRank.from(matchCount, true);
        LottoRank withoutBonus = LottoRank.from(matchCount, false);

        // then
        assertThat(withBonus).isEqualTo(withoutBonus);
    }

    @Test
    void 다섯_개_일치_시_보너스_여부에_따라_등수가_바뀐다() {

        // when
        LottoRank withBonus = LottoRank.from(5, true);
        LottoRank withoutBonus = LottoRank.from(5, false);

        // then
        assertThat(withBonus).isEqualTo(LottoRank.SECOND);
        assertThat(withoutBonus).isEqualTo(LottoRank.THIRD);
        assertThat(withBonus).isNotEqualTo(withoutBonus);
    }

    private static Stream<Arguments> bonusShouldBeIgnoredCases() {
        return Stream.of(
                Arguments.of(0),
                Arguments.of(1),
                Arguments.of(2),
                Arguments.of(3),
                Arguments.of(4),
                Arguments.of(6)
        );
    }

    private static Stream<Arguments> providePrizePerRank() {
        return Stream.of(
                Arguments.of(LottoRank.FIRST, 2_000_000_000L),
                Arguments.of(LottoRank.SECOND, 30_000_000L),
                Arguments.of(LottoRank.THIRD, 1_500_000L),
                Arguments.of(LottoRank.FOURTH, 50_000L),
                Arguments.of(LottoRank.FIFTH, 5_000L),
                Arguments.of(LottoRank.NONE, 0L)
        );
    }
}
