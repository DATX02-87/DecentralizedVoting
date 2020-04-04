package se.chalmers.datx02.api;

import se.chalmers.datx02.model.Model;

public class App {
    public static void main(String[] args) {
        Model m = new Model();
        m.setName("test");
        System.out.println(m.getName());
    }
}
