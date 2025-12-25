
public class Solution {

    private static final int NOT_FOUND = -2;
    private static final int NOT_POSSIBLE = -1;
    private static final int NO_MOVES_NEEDED = 0;

    public long minMoves(int[] balance) {
        int indexNegativeValue = findIndexNegativeValue(balance);
        if (indexNegativeValue == NOT_FOUND) {
            return NO_MOVES_NEEDED;
        }

        // Alternatively: if (Arrays.stream(balance).asLongStream().sum() < 0)
        if (calculateTotalBalance(balance) < 0) {
            return NOT_POSSIBLE;
        }

        return findMinMovesToTurnNegativeValueIntoNonnegative(balance, indexNegativeValue);
    }

    private int findIndexNegativeValue(int[] balance) {
        int indexNegativeValue = NOT_FOUND;
        for (int i = 0; i < balance.length; ++i) {
            if (balance[i] < 0) {
                indexNegativeValue = i;
                break;
            }
        }
        return indexNegativeValue;
    }

    private long calculateTotalBalance(int[] balance) {
        long totalBalance = 0;
        for (int i = 0; i < balance.length; ++i) {
            totalBalance += balance[i];
        }
        return totalBalance;
    }

    private long findMinMovesToTurnNegativeValueIntoNonnegative(int[] balance, int indexNegativeValue) {
        long minMoves = 0;
        int negativeAbsoluteValue = Math.abs(balance[indexNegativeValue]);
        int indexIncrease = (indexNegativeValue + 1) % balance.length;
        int indexDecrease = (balance.length + indexNegativeValue - 1) % balance.length;
        int distanceIndexIncrease = 1;
        int distanceIndexDecrease = 1;

        while (negativeAbsoluteValue > 0) {

            int balanceValue = Math.min(negativeAbsoluteValue, balance[indexIncrease]);
            minMoves += (long) balanceValue * distanceIndexIncrease;
            negativeAbsoluteValue -= balanceValue;

            ++distanceIndexIncrease;
            indexIncrease = (indexIncrease + 1) % balance.length;

            balanceValue = Math.min(negativeAbsoluteValue, balance[indexDecrease]);
            minMoves += (long) balanceValue * distanceIndexDecrease;
            negativeAbsoluteValue -= balanceValue;

            ++distanceIndexDecrease;
            indexDecrease = (balance.length + indexDecrease - 1) % balance.length;
        }

        return minMoves;
    }
}
