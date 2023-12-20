package com.Utilities;

import org.testng.annotations.Test;
import org.testng.annotations.Test;
import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.annotations.Test;
import com.driverFactory.InitClass;


public class TakeScreenshotClass extends InitClass{
	@Test
	public static void takescreenshot() throws IOException {
		// To take the screenshot

		// 1. Add commons-io dependency in your pom.xml

		// 2. TakeScreenshot Interface which has the function getScreenshotAs

		TakesScreenshot tk = (TakesScreenshot)InitClass.driver;
		File src = tk.getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(src, new File("src\\test\\resources\\screenShots\\recipeFailed.jpg"));

	}
}
