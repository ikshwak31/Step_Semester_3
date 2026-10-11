
import java.util.HashSet;

public class PairWithTargetSumComparison {

    // Approach 1: Brute force
    public static boolean bruteForce(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] + nums[j] == target) {
                    return true;
                }
            }
        }

        return false;
    }

    // Approach 2: HashSet
    public static boolean optimized(int[] nums, int target) {
        HashSet<Integer> seen = new HashSet<>();

        for (int num : nums) {
            int complement = target - num;

            if (seen.contains(complement)) {
                return true;
            }

            seen.add(num);
        }

        return false;
    }

    public static void main(String[] args) {
        int[] nums1 = {2, 7, 11, 15};
        int[] nums2 = {3, 4, 6};

        int target1 = 9;
        int target2 = 20;

        System.out.println("Brute force:");
        System.out.println(bruteForce(nums1, target1));
        System.out.println(bruteForce(nums2, target2));

        System.out.println("Optimized:");
        System.out.println(optimized(nums1, target1));
        System.out.println(optimized(nums2, target2));
    }
}
