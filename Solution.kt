
import kotlin.math.abs
import kotlin.math.min

class Solution {

    private companion object {
        const val NOT_FOUND = -2
        const val NOT_POSSIBLE = -1
        const val NO_MOVES_NEEDED = 0
    }

    fun minMoves(balance: IntArray): Long {
        val indexNegativeValue = findIndexNegativeValue(balance)
        if (indexNegativeValue == NOT_FOUND) {
            return NO_MOVES_NEEDED.toLong()
        }

        // Alternatively: if (balance.sumOf() { x -> x.toLong() } < 0)
        if (calculateTotalBalance(balance) < 0) {
            return NOT_POSSIBLE.toLong()
        }

        return findMinMovesToTurnNegativeValueIntoNonnegative(balance, indexNegativeValue)
    }

    private fun findIndexNegativeValue(balance: IntArray): Int {
        var indexNegativeValue = NOT_FOUND
        for (i in balance.indices) {
            if (balance[i] < 0) {
                indexNegativeValue = i
                break
            }
        }
        return indexNegativeValue
    }

    private fun calculateTotalBalance(balance: IntArray): Long {
        var totalBalance: Long = 0
        for (current in balance) {
            totalBalance += current
        }
        return totalBalance
    }

    private fun findMinMovesToTurnNegativeValueIntoNonnegative(balance: IntArray, indexNegativeValue: Int): Long {
        var minMoves: Long = 0
        var negativeAbsoluteValue = abs(balance[indexNegativeValue])
        var indexIncrease = (indexNegativeValue + 1) % balance.size
        var indexDecrease = (balance.size + indexNegativeValue - 1) % balance.size
        var distanceIndexIncrease = 1
        var distanceIndexDecrease = 1

        while (negativeAbsoluteValue > 0) {

            var balanceValue = min(negativeAbsoluteValue, balance[indexIncrease])
            minMoves += balanceValue.toLong() * distanceIndexIncrease
            negativeAbsoluteValue -= balanceValue

            ++distanceIndexIncrease
            indexIncrease = (indexIncrease + 1) % balance.size

            balanceValue = min(negativeAbsoluteValue, balance[indexDecrease])
            minMoves += balanceValue.toLong() * distanceIndexDecrease
            negativeAbsoluteValue -= balanceValue

            ++distanceIndexDecrease
            indexDecrease = (balance.size + indexDecrease - 1) % balance.size
        }

        return minMoves
    }
}
