package lotto.domain;

import java.util.List;

public class LottoFactory {

    private final LottoGenerator generator;

    public LottoFactory(LottoGenerator generator) {
        validateGenerator(generator);
        this.generator = generator;
    }

    public List<Lotto> generate(int count) {
        validateCount(count);
        return generator.generate(count);
    }

    private void validateGenerator(LottoGenerator generator) {
        if (generator == null) {
            throw new IllegalArgumentException("[ERROR] 로또 생성기는 null일 수 없습니다.");
        }
    }

    private void validateCount(int count) {
        if (count <= 0) {
            throw new IllegalArgumentException("[ERROR] 로또 생성 개수는 1개 이상이어야 합니다.");
        }
    }
}
