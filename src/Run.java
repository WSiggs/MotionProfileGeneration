public class Run
{
    public static void main(String[] args)
    {
        MotionProfile robot1 = new MotionProfile(2, 5, -2);
        robot1.calculatePointsForDistance(20);
        robot1.getValuesAtTime(2);

        double[] arr;

        System.out.println("\nValue Chart\n" + "Time\t Pos\tVel\t\tAcc");

        for(double i = 0; i <= robot1.totalTime; i+=.5)
        {
            arr = robot1.getValuesAtTime(i);
            System.out.println(arr[0] + "\t\t " + arr[1] + "\t" + arr[2] + "\t\t" + arr[3]);
        }
    }
}
