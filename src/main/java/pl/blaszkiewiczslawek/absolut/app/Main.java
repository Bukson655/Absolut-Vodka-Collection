package pl.blaszkiewiczslawek.absolut.app;

public class Main {

    private static final String APP_NAME = "Absolut Vodka Collection v1.8.2";

    public static void main(String[] args) {

        System.out.println(APP_NAME);
        AppControl control = new AppControl();
        control.controlLoop();

    }
}
