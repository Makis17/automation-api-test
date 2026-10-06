package com.example.bookstore.util;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.junit.jupiter.api.extension.*;

import java.nio.file.Files;
import java.nio.file.Path;

public class ReportExtension implements BeforeAllCallback, AfterAllCallback, BeforeEachCallback,
        AfterTestExecutionCallback, TestWatcher {
    private static final ThreadLocal<ExtentTest> CURRENT = new ThreadLocal<>();
    private static ExtentReports extent;

    @Override public synchronized void beforeAll(ExtensionContext context) throws Exception {
        if (extent != null) return;
        Files.createDirectories(Path.of("test-report"));
        ExtentSparkReporter spark = new ExtentSparkReporter("test-report/extent-report.html");
        extent = new ExtentReports();
        extent.attachReporter(spark);
        extent.setSystemInfo("Base URL", System.getProperty("baseUrl", "https://fakerestapi.azurewebsites.net"));
        extent.setSystemInfo("Framework", "JUnit 5 + RestAssured");
        extent.setSystemInfo("Java", System.getProperty("java.version"));
        Runtime.getRuntime().addShutdownHook(new Thread(() -> extent.flush()));
    }

    @Override public void beforeEach(ExtensionContext context) {
        String name = context.getRequiredTestClass().getSimpleName() + " :: " + context.getDisplayName();
        CURRENT.set(extent.createTest(name));
    }

    @Override public void testSuccessful(ExtensionContext context) { CURRENT.get().pass("Test passed"); }
    @Override public void testFailed(ExtensionContext context, Throwable cause) {
        CURRENT.get().fail(cause);
    }
    @Override public void testAborted(ExtensionContext context, Throwable cause) { CURRENT.get().skip("Test aborted"); }


    @Override public void afterTestExecution(ExtensionContext context) {
        CURRENT.get().info("Executed: " + context.getDisplayName());
    }

    @Override
    public void afterAll(ExtensionContext context) throws Exception {

    }
}
