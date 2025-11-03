package lotto.view;

import java.util.Arrays;
import java.util.List;

public class InputValidator {

    private static final String NUMBER_DELIMITER = ",";

    private InputValidator() {
    }

    public static int parsePurchaseAmount(String input) {
        validateNotBlank(input);
        String trimmed = input.trim();
        try {
            return Integer.parseInt(trimmed);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("[ERROR] 구입 금액은 숫자여야 합니다.");
        }
    }

    public static List<Integer> parseWinningNumbers(String input) {
        validateNotBlank(input);
        String[] parts = input.split(NUMBER_DELIMITER);
        return Arrays.stream(parts)
                .map(String::trim)
                .map(part -> parseNumber(part, "[ERROR] 당첨 번호는 숫자여야 합니다."))
                .toList();
    }

    public static int parseBonusNumber(String input) {
        validateNotBlank(input);
        String trimmed = input.trim();
        return parseNumber(trimmed, "[ERROR] 보너스 번호는 숫자여야 합니다.");
    }

    private static void validateNotBlank(String input) {
        if (input == null) {
            throw new IllegalArgumentException("[ERROR] 입력은 비어 있을 수 없습니다.");
        }
        if (input.trim().isEmpty()) {
            throw new IllegalArgumentException("[ERROR] 입력은 비어 있을 수 없습니다.");
        }
    }

    private static int parseNumber(String value, String errorMessage) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(errorMessage);
        }
    }
}
