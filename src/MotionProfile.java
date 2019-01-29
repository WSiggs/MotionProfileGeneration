/**
 * @author Siggy
 *         $
 */
public class MotionProfile
{
    private double acceleration;
    private double robot_max_velocity;
    private double deceleration;

    private double timeToCruise;
    private double cruiseTime;
    private double timeToStop;

    private double accelDistance;
    private double cruiseDistance;
    private double decelDistance;

    public double totalTime;

    private double v_cruise;

    public MotionProfile(double acceleration, double robot_max_velocity, double deceleration)
    {
        this.acceleration = acceleration;
        this.robot_max_velocity = robot_max_velocity;
        this.deceleration = deceleration;
    }


    public void calculatePointsForDistance(double distance)
    {
        v_cruise = Math.min(robot_max_velocity, calcTheoMax(distance));

        timeToCruise = v_cruise / acceleration;
        timeToStop = Math.abs(v_cruise / deceleration);

        accelDistance = (.5)*acceleration*(timeToCruise*timeToCruise);
        decelDistance = Math.abs((.5)*deceleration*(timeToStop*timeToStop));
        cruiseDistance = distance - (accelDistance+decelDistance);

        cruiseTime = cruiseDistance/v_cruise;


        System.out.println("ROBOT VARS: " +
                        "\nAcceleration:\t " + acceleration +
                        "\nRobot Max Vel.:\t " + robot_max_velocity);

        System.out.println("\nMOTION PROFILE VARS:" +
                "\nTime To Cruise:\t\t "  +  timeToCruise   +
                "\nCruise Time:\t\t "     +  cruiseTime     +
                "\nTime to Stop:\t\t "    +  timeToStop     +
                "\nAccel Distance:\t\t "  +  accelDistance  +
                "\nCruise Distance:\t "   +  cruiseDistance +
                "\nDecel Distance:\t\t "  +  decelDistance);

        totalTime = timeToCruise+cruiseTime+timeToStop;
    }

    public double calcTheoMax(double distance)
    {
        return 2*acceleration*(distance);
    }

    public double[] getValuesAtTime(double time)
    {
        /** Given time will output an array of 4 values in the following order
         *  arr[0] = time at setpoint
         *  arr[1] = position at time
         *  arr[2] = velocity at time
         *  arr[3] = acceleration
         */

        double[] arr = new double[4];

        arr[0] = time;

        if(time < timeToCruise && time >= 0)
        {
            arr[1] = (0.5)*acceleration*time*time;
            arr[2] = acceleration*time;
            arr[3] = acceleration;
        }
        else if(time >= timeToCruise && time <= (timeToCruise + cruiseTime))
        {
            //arr[1] = (0.5)*(acceleration*timeToCruise*timeToCruise) + v_cruise*(time-timeToCruise) + timeToCruise*(v_cruise/2);
            arr[1] = (time-timeToCruise) * v_cruise + (0.5)*(acceleration*timeToCruise*timeToCruise);
            arr[2] = v_cruise;
            arr[3] = 0;
        }
        else if(time > timeToCruise+cruiseTime && time <= timeToCruise+cruiseTime+timeToStop)
        {
            double decel_start_time = (timeToCruise+cruiseTime);
            //arr[1] = accelDistance+cruiseDistance+((time-(timeToCruise+cruiseTime))*(v_cruise/2));
            arr[1] = (0.5)*deceleration*(time-decel_start_time)*(time-decel_start_time)
                    + v_cruise*(time-decel_start_time)
                    + (cruiseTime) * v_cruise + ((0.5)*(acceleration*timeToCruise*timeToCruise));
            arr[2] = v_cruise+((time-(timeToCruise+cruiseTime))*deceleration);
            arr[3] = deceleration;
        }
        else
        {
            arr[1] = 0;
            arr[2] = 0;
            arr[3] = 0;
        }

        return arr;
    }
}
