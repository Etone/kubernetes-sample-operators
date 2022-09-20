package nvtc.io.conferenceoperator;

import nvtc.io.conferenceoperator.operator.controller.DataServiceController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ConferenceOperatorFabric8Application implements CommandLineRunner {

    @Autowired
    private DataServiceController controller;

    public static void main(String[] args) {
        SpringApplication.run(ConferenceOperatorFabric8Application.class, args);
    }

    @Override
    public void run(String... args)  {
        controller.run();
    }
}
