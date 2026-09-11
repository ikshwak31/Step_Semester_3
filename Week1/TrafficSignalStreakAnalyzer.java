public class TrafficSignalStreakAnalyzer {

    static void findLongestStreak(String signalLog) {

        if (signalLog.length() == 0) {
            System.out.println("No Signal Data");
            return;
        }

        char longestSignal = signalLog.charAt(0);
        int longestLength = 1;

        char currentSignal = signalLog.charAt(0);
        int currentLength = 1;

        for (int i = 1; i < signalLog.length(); i++) {

            if (signalLog.charAt(i) == currentSignal) {
                currentLength++;
            } else {
                currentSignal = signalLog.charAt(i);
                currentLength = 1;
            }

            if (currentLength > longestLength) {
                longestLength = currentLength;
                longestSignal = currentSignal;
            }
        }

        System.out.println(
                "Longest Streak: '" + longestSignal +
                        "' repeated " + longestLength + " times"
        );
    }

    public static void main(String[] args) {

        String signalLog = "RRGGGYRR";

        findLongestStreak(signalLog);
    }
}
