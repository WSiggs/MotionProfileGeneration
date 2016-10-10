/**
 * @author Siggy
 *         $
 */
public class MotionProfile
{
    private double acceleration;
    private double robot_max_velocity;

    private double timeToCruise;
    private double cruiseTime;
    private double timeToStop;

    private double accelDistance;
    private double cruiseDistance;
    private double decelDistance;

    private double v_cruise;

    public MotionProfile(double acceleration, double robot_max_velocity)
    {
        this.acceleration = acceleration;
        this.robot_max_velocity = robot_max_velocity;
    }


    public void driveToDistance(double distance)
    {
        v_cruise = Math.min(robot_max_velocity, calcTheoMax(distance));

        timeToCruise = v_cruise / acceleration;
        timeToStop = v_cruise / acceleration;

        accelDistance = (.5)*acceleration*(timeToCruise*timeToCruise);
        decelDistance = (.5)*acceleration*(timeToStop*timeToStop);
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
                "\nCruise Distance:\t " +  cruiseDistance +
                "\nDecel Distance:\t\t "  +  decelDistance);
    }

    public double calcTheoMax(double distance)
    {
        return Math.sqrt(2*acceleration*(distance));
    }


    public double getTimeToCruise()
    {
        return timeToCruise;
    }

    public double getCruiseTime()
    {
        return cruiseTime;
    }

    public double getTimeToStop()
    {
        return timeToStop;
    }

    public double getAccelDistance()
    {
        return accelDistance;
    }

    public double getCruiseDistance()
    {
        return cruiseDistance;
    }

    public double getDecelDistance()
    {
        return decelDistance;
    }

    public double getV_cruise()
    {
        return v_cruise;
    }

}
