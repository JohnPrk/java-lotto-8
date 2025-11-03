package lotto.domain.testDouble;

import lotto.domain.Lotto;
import lotto.domain.LottoGenerator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FakeLottoGenerator implements LottoGenerator {

    private final Iterator<List<Integer>> numbersIterator;

    public FakeLottoGenerator(List<List<Integer>> numbersSequences) {
        this.numbersIterator = numbersSequences.iterator();
    }

    @Override
    public List<Lotto> generate(int count) {
        List<Lotto> lottos = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            List<Integer> numbers = numbersIterator.next();
            lottos.add(new Lotto(numbers));
        }
        return lottos;
    }

    public static FakeLottoGenerator of(List<Integer> numbers) {
        return new FakeLottoGenerator(List.of(numbers));
    }
}
