import FinalExplored.BaseClass;

public class ChildClass extends BaseClass {

    @Override
    protected void optionalMethod() {
        System.out.println("Extra stuff happened");
        super.optionalMethod();
    }


    //    @Override
//    public void recommendedMethod() {
//        System.out.println("I'll do things my way");
//        optionalMethod();
//
//    }
    public static void recommendedStatic() {
        System.out.println("[ChildClasse.recommendedStatic]: Best Way to do it");
        optionalStatic();
    }
}
