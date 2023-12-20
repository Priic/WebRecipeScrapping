package com.Utilities;

import java.io.IOException;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class MyListeners implements ITestListener {

	public void onTestStart(ITestResult result) {
		System.out.println("Starting my test----");
	}

	public void onTestSuccess(ITestResult result) {
		System.out.println("Testcase Pass");
	}

	public void onTestFailure(ITestResult result) {
		System.out.println("Testcase fail ...So, take a screenshot");
		try {
			TakeScreenshotClass.takescreenshot();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void onTestSkipped(ITestResult result) {
		System.out.println("Testcase Skipped ");
	}

}
