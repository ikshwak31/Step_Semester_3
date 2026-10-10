
public class BMICalculator {

    static String getBmiStatus(double bmi) {

        if (bmi < 18.5) {
            return "Underweight";
        } else if (bmi < 25) {
            return "Normal";
        } else if (bmi < 30) {
            return "Overweight";
        } else {
            return "Obese";
        }
    }

    static void printWellnessReport(double[] heights, double[] weights) {

        System.out.printf("%-10s %-12s %-12s %-10s %-15s%n",
                "Person", "Height(m)", "Weight(kg)", "BMI", "Status");

        for (int i = 0; i < heights.length; i++) {

            double bmi = weights[i] / (heights[i] * heights[i]);

            System.out.printf("%-10d %-12.2f %-12.2f %-10.2f %-15s%n",
                    i + 1,
                    heights[i],
                    weights[i],
                    bmi,
                    getBmiStatus(bmi));
        }
    }

    public static void main(String[] args) {

        double[] heights = {
                1.75, 1.60, 1.82, 1.68, 1.72,
                1.55, 1.80, 1.65, 1.77, 1.70
        };

        double[] weights = {
                70, 90, 85, 60, 78,
                48, 95, 72, 68, 55
        };

        printWellnessReport(heights, weights);
    }
}
