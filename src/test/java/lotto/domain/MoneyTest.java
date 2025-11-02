package lotto.domain;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MoneyTest {

    private static final int UNIT_MONEY_FOR_BUY_LOTTO = 1000;

    @ParameterizedTest
    @ValueSource(ints = {1000, 5000, 1000000000})
    void 로또_구매_금액이_천_원으로_나눠지면_Money_객체가_생성된다(int amount) {
        // when
        Money money = new Money(amount);

        // then
        assertThat(money).isNotNull();
    }

    @ParameterizedTest
    @ValueSource(ints = {500, 1100, 2500})
    void 로또_구매_금액이_천_원으로_나눠지지_않으면_예외가_발생한다(int amount) {
        // when & then
        assertThatThrownBy(() -> new Money(amount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 1,000원 단위로 입력해 주세요.");
    }

    @ParameterizedTest
    @ValueSource(ints = {0, -1000, -5000})
    void 로또_구매_금액이_0원_이하면_예외가_발생한다(int amount) {
        // when & then
        assertThatThrownBy(() -> new Money(amount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 구입 금액은 0보다 커야 합니다.");
    }

    @ParameterizedTest
    @ValueSource(ints = {1000, 3000, 100000})
    void 로또_구매_금액을_입력하면_구매_가능한_로또_개수를_반환한다(int amount) {
        // given
        Money money = new Money(amount);

        // when
        int count = money.getLottoCount();

        // then
        assertThat(count).isEqualTo(amount / UNIT_MONEY_FOR_BUY_LOTTO);
    }
}
