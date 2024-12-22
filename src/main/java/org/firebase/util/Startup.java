package org.firebase.util;


import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;



@QuarkusMain
public class Startup {

    public static void main(String... args) {
        Quarkus.run(MyApp.class, args);
    }

    public static class MyApp implements QuarkusApplication {

        // Logger logger = Logger.getLogger(MyApp.class);

        @Override
        public int run(String... args) throws Exception {
            int exitcode = 0;
            // startUpCode();
            Quarkus.waitForExit();
            return exitcode;
        }

        public void startUpCode(){
            try {
                Class.forName("com.google.auth.ApiKeyCredentials");
                System.out.println("ApiKeyCredentials loaded successfully!");
            } catch (ClassNotFoundException e) {
                System.err.println("ApiKeyCredentials not found in classpath!");
            }
            
        }

    }
}
