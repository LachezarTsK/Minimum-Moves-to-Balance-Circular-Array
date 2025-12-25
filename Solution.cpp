
#include <span>
#include <cmath>
#include <vector>
#include <numeric>
#include <algorithm>

using namespace std;

class Solution {

    static const int NOT_FOUND = -2;
    static const int NOT_POSSIBLE = -1;
    static const int NO_MOVES_NEEDED = 0;

public:
    long long minMoves(const vector<int>& balance) const {
        int indexNegativeValue = findIndexNegativeValue(balance);
        if (indexNegativeValue == NOT_FOUND) {
            return NO_MOVES_NEEDED;
        }

        // Alternatively: if (accumulate(balance.begin(), balance.end(), 0LL) < 0)
        if (calculateTotalBalance(balance) < 0) {
            return NOT_POSSIBLE;
        }

        return findMinMovesToTurnNegativeValueIntoNonnegative(balance, indexNegativeValue);
    }

private:
    int findIndexNegativeValue(span<const int> balance) const {
        int indexNegativeValue = NOT_FOUND;
        for (int i = 0; i < balance.size(); ++i) {
            if (balance[i] < 0) {
                indexNegativeValue = i;
                break;
            }
        }
        return indexNegativeValue;
    }

    long long calculateTotalBalance(span<const int> balance) const {
        long long totalBalance = 0;
        for (const auto& current : balance) {
            totalBalance += current;
        }
        return totalBalance;
    }

    long long findMinMovesToTurnNegativeValueIntoNonnegative(span<const int> balance, int indexNegativeValue) const {
        long long minMoves = 0;
        int negativeAbsoluteValue = abs(balance[indexNegativeValue]);
        int indexIncrease = (indexNegativeValue + 1) % balance.size();
        int indexDecrease = (balance.size() + indexNegativeValue - 1) % balance.size();
        int distanceIndexIncrease = 1;
        int distanceIndexDecrease = 1;

        while (negativeAbsoluteValue > 0) {

            int balanceValue = min(negativeAbsoluteValue, balance[indexIncrease]);
            minMoves += (long)balanceValue * distanceIndexIncrease;
            negativeAbsoluteValue -= balanceValue;

            ++distanceIndexIncrease;
            indexIncrease = (indexIncrease + 1) % balance.size();

            balanceValue = min(negativeAbsoluteValue, balance[indexDecrease]);
            minMoves += static_cast<long long>(balanceValue) * distanceIndexDecrease;
            negativeAbsoluteValue -= balanceValue;

            ++distanceIndexDecrease;
            indexDecrease = (balance.size() + indexDecrease - 1) % balance.size();
        }

        return minMoves;
    }
};
