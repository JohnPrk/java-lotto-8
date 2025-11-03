package lotto.view;

import lotto.domain.Lotto;
import lotto.domain.LottoRank;
import lotto.domain.LottoResults;

import java.util.List;

public class OutputView {

    public static void printLottos(List<Lotto> lottos) {
        System.out.printf("%d개를 구매했습니다.%n", lottos.size());
        for (Lotto lotto : lottos) {
            System.out.println(lotto.getNumbers());
        }
    }

    public static void printStatistics(LottoResults results) {
        System.out.println("당첨 통계");
        System.out.println("---");
        printRankResult(results, LottoRank.FIFTH, "3개 일치 (5,000원) - %d개");
        printRankResult(results, LottoRank.FOURTH, "4개 일치 (50,000원) - %d개");
        printRankResult(results, LottoRank.THIRD, "5개 일치 (1,500,000원) - %d개");
        printRankResult(results, LottoRank.SECOND, "5개 일치, 보너스 볼 일치 (30,000,000원) - %d개");
        printRankResult(results, LottoRank.FIRST, "6개 일치 (2,000,000,000원) - %d개");
    }

    private static void printRankResult(LottoResults results, LottoRank rank, String format) {
        int count = results.getCountOf(rank);
        System.out.printf(format + "%n", count);
    }

    public static void printYield(double yield) {
        double percentage = yield * 100;
        System.out.printf("총 수익률은 %.1f%%입니다.%n", percentage);
    }

    public static void printError(String message) {
        System.out.println(message);
    }
}
