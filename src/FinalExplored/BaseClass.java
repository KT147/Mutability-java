package FinalExplored;

public class BaseClass {

    public final void recommendedMethod() {
        System.out.println("[BaseClasse.recommendedMethod]: Best Way to do it");
        optionalMethod();
        mandatoryMethod();
    }

    protected void optionalMethod() {
        System.out.println("[BaseClasse.optionalMethod]: Customize Optional Method");
    }

    private final void mandatoryMethod() {
        System.out.println("[BaseClasse.mandatoryMethod]: NON-NEGOTIABLE!");
    }

    public static void recommendedStatic() {
        System.out.println("[BaseClasse.recommendedStatic]: Best Way to do it");
        optionalStatic();
        mandatoryStatic();
    }

    protected static void optionalStatic() {
        System.out.println("[BaseClasse.optionalStatic]: Optional");
    }

    private static void mandatoryStatic() {
        System.out.println("[BaseClasse.mandatoryStatic]: MANDATORY");
    }

}
