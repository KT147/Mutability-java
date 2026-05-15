import FinalExplored.BaseClass;
import external.Logger;

public class Main {

    public static void main(String[] args) {

        BaseClass parent = new BaseClass();
        ChildClass child = new ChildClass();
        BaseClass childReferredToAsBase = new BaseClass();

        parent.recommendedMethod();
        System.out.println("-------");
        childReferredToAsBase.recommendedMethod();
        System.out.println("-------");
        child.recommendedMethod();

        System.out.println("-------");

        parent.recommendedStatic();
        System.out.println("-------");
        childReferredToAsBase.recommendedStatic();
        System.out.println("-------");
        child.recommendedStatic();

        System.out.println("-------");
        BaseClass.recommendedStatic();
        ChildClass.recommendedStatic();

        String xArgument = "Bla ";
        StringBuilder zArgument = new StringBuilder("Only");
        doXYZ(xArgument, 16, zArgument);

        StringBuilder tracker = new StringBuilder("Step 1 abc");
        Logger.logToConsole(tracker.toString());
        tracker.append(", Step 2 bla");
        Logger.logToConsole(tracker.toString());
        System.out.println(tracker);
    }

    private static void doXYZ(String x, int y, final StringBuilder z) {

        final String c = x + y;
        System.out.println("c= " +c);
        x = c;
        z.append(y);
    }
}
