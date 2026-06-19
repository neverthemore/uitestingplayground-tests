package com.uitesting.listeners;

import org.testng.IExecutionListener;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class AllureEnvironmentListener implements IExecutionListener {

    private static final String ALLURE_RESULTS_DIR = "target/allure-results";

    @Override
    public void onExecutionStart() {
        try {
            Path resultsDir = Paths.get(ALLURE_RESULTS_DIR);
            Files.createDirectories(resultsDir);

            Properties props = new Properties();
            props.setProperty("Target.URL", "http://www.uitestingplayground.com");
            props.setProperty("Browser", "Chrome");
            props.setProperty("Run.Mode",
                    Boolean.parseBoolean(System.getProperty("headless", "true"))
                            ? "headless" : "headed");
            props.setProperty("Java.Version", System.getProperty("java.version"));
            props.setProperty("OS", System.getProperty("os.name")
                    + " " + System.getProperty("os.version"));
            props.setProperty("Test.Framework", "TestNG + Selenium WebDriver");

            try (FileOutputStream out = new FileOutputStream(
                    resultsDir.resolve("environment.properties").toFile())) {
                props.store(out, "Allure environment info — generated automatically");
            }
        } catch (IOException e) {
            System.err.println("Could not write Allure environment.properties: "
                    + e.getMessage());
        }
    }

    @Override
    public void onExecutionFinish() {
        // No-op
    }
}
