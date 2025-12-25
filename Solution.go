
package main
import "math"

const NOT_FOUND = -2
const NOT_POSSIBLE = -1
const NO_MOVES_NEEDED = 0

func minMoves(balance []int) int64 {
    indexNegativeValue := findIndexNegativeValue(balance)
    if indexNegativeValue == NOT_FOUND {
        return int64(NO_MOVES_NEEDED)
    }

    if calculateTotalBalance(balance) < 0 {
        return int64(NOT_POSSIBLE)
    }

    return findMinMovesToTurnNegativeValueIntoNonnegative(balance, indexNegativeValue)
}

func calculateTotalBalance(balance []int) int64 {
    var totalBalance int64 = 0
    for _, current := range balance {
        totalBalance += int64(current)
    }
    return totalBalance
}

func findIndexNegativeValue(balance []int) int {
    indexNegativeValue := NOT_FOUND
    for i := range balance {
        if balance[i] < 0 {
            indexNegativeValue = i
            break
        }
    }
    return indexNegativeValue
}

func findMinMovesToTurnNegativeValueIntoNonnegative(balance []int, indexNegativeValue int) int64 {
    var minMoves int64 = 0
    negativeAbsoluteValue := int(math.Abs(float64(balance[indexNegativeValue])))
    indexIncrease := (indexNegativeValue + 1) % len(balance)
    indexDecrease := (len(balance) + indexNegativeValue - 1) % len(balance)
    distanceIndexIncrease := 1
    distanceIndexDecrease := 1

    for negativeAbsoluteValue > 0 {

        var balanceValue = min(negativeAbsoluteValue, balance[indexIncrease])
        minMoves += int64(balanceValue) * int64(distanceIndexIncrease)
        negativeAbsoluteValue -= balanceValue

        distanceIndexIncrease++
        indexIncrease = (indexIncrease + 1) % len(balance)

        balanceValue = min(negativeAbsoluteValue, balance[indexDecrease])
        minMoves += int64(balanceValue) * int64(distanceIndexDecrease)
        negativeAbsoluteValue -= balanceValue

        distanceIndexDecrease++
        indexDecrease = (len(balance) + indexDecrease - 1) % len(balance)
    }

    return minMoves
}
