package com.manager.rss.test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;

import java.util.Optional;

public class HTMLReporter implements TestWatcher {

    private static ExtentHtmlReporter htmlReporter;
    private static ExtentReports extentReports;

    public HTMLReporter() {
        if (htmlReporter == null) {
            htmlReporter = new ExtentHtmlReporter("test-results.html");
            extentReports = new ExtentReports();
            extentReports.attachReporter(htmlReporter);
        }
    }

    @Override
    public void testDisabled(ExtensionContext context, Optional<String> reason) {

    }

    @Override
    public void testSuccessful(ExtensionContext context) {
        ExtentTest test = extentReports.createTest(context.getRequiredTestMethod().getName());
        test.pass("Test passed");
    }

    @Override
    public void testAborted(ExtensionContext context, Throwable cause) {
        ExtentTest test = extentReports.createTest(context.getRequiredTestMethod().getName());
        test.fail("Test aborted");
    }

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        ExtentTest test = extentReports.createTest(context.getRequiredTestMethod().getName());
        test.fail("Test failed");
    }
}
