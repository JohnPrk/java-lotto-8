package lotto.controller;

import lotto.domain.*;
import lotto.view.InputView;
import lotto.view.OutputView;

import java.util.List;

public class LottoController {

    private final LottoFactory lottoFactory;

    public LottoController(LottoFactory lottoFactory) {
        this.lottoFactory = lottoFactory;
    }

    public void run() {
        Money money = readMoney();
        int lottoCount = money.getLottoCount();
        List<Lotto> lottos = lottoFactory.generate(lottoCount);
        OutputView.printLottos(lottos);
        WinningNumbers winningNumbers = readWinningNumbers();
        LottoResults results = new LottoResults(lottos, winningNumbers);
        OutputView.printStatistics(results);
        double yield = results.calculateYield(money.getAmount());
        OutputView.printYield(yield);
    }

    private Money readMoney() {
        while (true) {
            try {
                int purchaseAmount = InputView.readPurchaseAmount();
                return new Money(purchaseAmount);
            } catch (IllegalArgumentException e) {
                OutputView.printError(e.getMessage());
            }
        }
    }

    private WinningNumbers readWinningNumbers() {
        while (true) {
            try {
                List<Integer> numbers = readWinningNumberList();
                int bonus = readBonusNumber();
                return new WinningNumbers(numbers, bonus);
            } catch (IllegalArgumentException e) {
                OutputView.printError(e.getMessage());
            }
        }
    }

    private List<Integer> readWinningNumberList() {
        while (true) {
            try {
                return InputView.readWinningNumbers();
            } catch (IllegalArgumentException e) {
                OutputView.printError(e.getMessage());
            }
        }
    }

    private int readBonusNumber() {
        while (true) {
            try {
                return InputView.readBonusNumber();
            } catch (IllegalArgumentException e) {
                OutputView.printError(e.getMessage());
            }
        }
    }
}
