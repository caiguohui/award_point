package org.example.util;

public class RewardPointsCalculator {

    /**
     * Calculate reward points
     *
     * @param amount
     * @return reward points
     */
    public static int calculateRewardPoints(double amount) {
        int rewardPoints = 0;
        if (amount > 100) {
            rewardPoints += (int) (amount - 100) * 1;
        }
        if (amount > 50) {
            rewardPoints += (int) (amount - 50) * 1;
        }
        return rewardPoints;
    }

    public static void main(String[] args) {
        System.out.println(calculateRewardPoints(120));
    }
}
