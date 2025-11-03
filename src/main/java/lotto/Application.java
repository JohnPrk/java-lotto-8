package lotto;

import lotto.controller.LottoController;
import lotto.domain.LottoFactory;
import lotto.domain.RandomLottoGenerator;

public class Application {

    public static void main(String[] args) {
        LottoFactory lottoFactory = new LottoFactory(new RandomLottoGenerator());
        LottoController controller = new LottoController(lottoFactory);
        controller.run();
    }
}
