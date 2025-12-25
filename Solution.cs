
using System;

public class Solution
{
    private static readonly int NOT_FOUND = -2;
    private static readonly int NOT_POSSIBLE = -1;
    private static readonly int NO_MOVES_NEEDED = 0;

    public long MinMoves(int[] balance)
    {
        int indexNegativeValue = FindIndexNegativeValue(balance);
        if (indexNegativeValue == NOT_FOUND)
        {
            return NO_MOVES_NEEDED;
        }

        // Alternatively: if (balance.Sum(x => (long)x) < 0)
        if (CalculateTotalBalance(balance) < 0)
        {
            return NOT_POSSIBLE;
        }

        return FindMinMovesToTurnNegativeValueIntoNonnegative(balance, indexNegativeValue);
    }

    private int FindIndexNegativeValue(int[] balance)
    {
        int indexNegativeValue = NOT_FOUND;
        for (int i = 0; i < balance.Length; ++i)
        {
            if (balance[i] < 0)
            {
                indexNegativeValue = i;
                break;
            }
        }
        return indexNegativeValue;
    }

    private long CalculateTotalBalance(int[] balance)
    {
        long totalBalance = 0;
        for (int i = 0; i < balance.Length; ++i)
        {
            totalBalance += balance[i];
        }
        return totalBalance;
    }

    private long FindMinMovesToTurnNegativeValueIntoNonnegative(int[] balance, int indexNegativeValue)
    {
        long minMoves = 0;
        int negativeAbsoluteValue = Math.Abs(balance[indexNegativeValue]);
        int indexIncrease = (indexNegativeValue + 1) % balance.Length;
        int indexDecrease = (balance.Length + indexNegativeValue - 1) % balance.Length;
        int distanceIndexIncrease = 1;
        int distanceIndexDecrease = 1;

        while (negativeAbsoluteValue > 0)
        {
            int balanceValue = Math.Min(negativeAbsoluteValue, balance[indexIncrease]);
            minMoves += (long)balanceValue * distanceIndexIncrease;
            negativeAbsoluteValue -= balanceValue;

            ++distanceIndexIncrease;
            indexIncrease = (indexIncrease + 1) % balance.Length;

            balanceValue = Math.Min(negativeAbsoluteValue, balance[indexDecrease]);
            minMoves += (long)balanceValue * distanceIndexDecrease;
            negativeAbsoluteValue -= balanceValue;

            ++distanceIndexDecrease;
            indexDecrease = (balance.Length + indexDecrease - 1) % balance.Length;
        }

        return minMoves;
    }
}
