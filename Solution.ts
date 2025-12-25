
function minMoves(balance: number[]): number {
    const indexNegativeValue = findIndexNegativeValue(balance);
    if (indexNegativeValue === Util.NOT_FOUND) {
        return Util.NO_MOVES_NEEDED;
    }

    // Alternatively: if (balance.reduce((x, y) => x + y) < 0)
    if (calculateTotalBalance(balance) < 0) {
        return Util.NOT_POSSIBLE;
    }

    return findMinMovesToTurnNegativeValueIntoNonnegative(balance, indexNegativeValue);
};

class Util {
    static NOT_FOUND = -2;
    static NOT_POSSIBLE = -1;
    static NO_MOVES_NEEDED = 0;
}

function findIndexNegativeValue(balance: number[]): number {
    let indexNegativeValue = Util.NOT_FOUND;
    for (let i = 0; i < balance.length; ++i) {
        if (balance[i] < 0) {
            indexNegativeValue = i;
            break;
        }
    }
    return indexNegativeValue;
}

function calculateTotalBalance(balance: number[]): number {
    let totalBalance = 0;
    for (let i = 0; i < balance.length; ++i) {
        totalBalance += balance[i];
    }
    return totalBalance;
}

function findMinMovesToTurnNegativeValueIntoNonnegative(balance: number[], indexNegativeValue: number): number {
    let minMoves = 0;
    let negativeAbsoluteValue = Math.abs(balance[indexNegativeValue]);
    let indexIncrease = (indexNegativeValue + 1) % balance.length;
    let indexDecrease = (balance.length + indexNegativeValue - 1) % balance.length;
    let distanceIndexIncrease = 1;
    let distanceIndexDecrease = 1;

    while (negativeAbsoluteValue > 0) {

        let balanceValue = Math.min(negativeAbsoluteValue, balance[indexIncrease]);
        minMoves += balanceValue * distanceIndexIncrease;
        negativeAbsoluteValue -= balanceValue;

        ++distanceIndexIncrease;
        indexIncrease = (indexIncrease + 1) % balance.length;

        balanceValue = Math.min(negativeAbsoluteValue, balance[indexDecrease]);
        minMoves += balanceValue * distanceIndexDecrease;
        negativeAbsoluteValue -= balanceValue;

        ++distanceIndexDecrease;
        indexDecrease = (balance.length + indexDecrease - 1) % balance.length;
    }

    return minMoves;
}
