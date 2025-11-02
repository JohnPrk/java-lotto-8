package lotto.domain;

public class Money {

    private static final int UNIT_MONEY_FOR_BUY_LOTTO = 1000;
    private final int amount;

    public Money(int amount) {
        validatePositive(amount);
        validateUnit(amount);
        this.amount = amount;
    }

    private void validatePositive(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("[ERROR] 구입 금액은 0보다 커야 합니다.");
        }
    }

    private void validateUnit(int amount) {
        if (amount % UNIT_MONEY_FOR_BUY_LOTTO != 0) {
            throw new IllegalArgumentException("[ERROR] 1,000원 단위로 입력해 주세요.");
        }
    }

    public int getLottoCount() {
        return amount / UNIT_MONEY_FOR_BUY_LOTTO;
    }
}
