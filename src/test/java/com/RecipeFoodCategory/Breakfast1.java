package com.RecipeFoodCategory;

import org.testng.annotations.Test;
import org.testng.annotations.Test;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import org.testng.annotations.Test;
import com.Utilities.*;

import com.driverFactory.InitClass;

public class Breakfast1 extends InitClass {

	@Test

	public void scrapeBreakfastRecipe() throws InterruptedException, IOException {

		// declaring variables
	
		String prep_Time = null;
		String ingredients = null;
		String cook_Time = null;
		String method = null;
		String nutrientValue;
		Instant timer_end;
		Instant timer_start = null;

		
		// searching snack in search textbox
		driver.findElement(By.id("ctl00_txtsearch")).sendKeys("Breakfast");
		driver.findElement(By.name("ctl00$imgsearch")).click();

		// ---------------creating excel -------------------------------------------
		
		String filePath = PropertyReader.getPropFromProperty("config", "excelFilePath")+"RecipeTestData.xlsx";
		ExcelReader excelReader = new ExcelReader(filePath);
		excelReader.createExcel("Breakfast");
		System.out.println("Excel created");
//		String filePath = "./src//test/resources/TestData/RecipeTestData.xlsx";
//		String path = "./src/test/resources/TestData/RecipeTestData.xlsx";
//		ExcelReader excelReader = new ExcelReader(path);
//		// creating first row of Excel
//		excelReader.setCellData("Breakfast", 0, 0, "RecipeID");
//		excelReader.setCellData("Breakfast", 0, 1, "RecipeName");
//		excelReader.setCellData("Breakfast", 0, 2, "Recipe Category(Breakfast/lunch/snack/dinner)");
//		excelReader.setCellData("Breakfast", 0, 3, "Food Category(Veg/non-veg/vegan/Jain)");
//		excelReader.setCellData("Breakfast", 0, 4, "Ingredients");
//		excelReader.setCellData("Breakfast", 0, 5, "Preparation Time");
//		excelReader.setCellData("Breakfast", 0, 6, "Cooking Time");
//		excelReader.setCellData("Breakfast", 0, 7, "Preparation method");
//		excelReader.setCellData("Breakfast", 0, 8, "Nutrient values");
//		excelReader.setCellData("Breakfast", 0, 9, "Targetted morbid conditions (Diabeties/Hypertension/Hypothyroidism)");
//		excelReader.setCellData("Breakfast", 0, 10, "Recipe URL");
//		System.out.println("Excel created");

		// fetching all urls in List
		java.util.List<WebElement> recipes_url = driver.findElements(By.className("rcc_recipename"));
		int total_cards = recipes_url.size();
		System.out.println("Total cards =" + total_cards);
		ArrayList<String> link = new ArrayList<String>(24);

		// Adding all recipe urls in arraylist
		for (WebElement e : recipes_url) {
			// .findElement -----> finds the tag <a> inside the current WebElement
			// .getAttribute ----> returns the href attribute of the <a> tag in the current
			// WebElement
			link.add(e.findElement(By.tagName("a")).getAttribute("href"));

		}

		// Clicking each recipe card and fetching all required information an writing in
		// excel
		int counter = 1; // using this counter to select the excel sheet row number to insert data
		for (Object each_recipe : link) {
			System.out.println("Counter =" + counter);
			Document doc = Jsoup.connect((String) each_recipe).timeout(1000 * 100).get(); // clicking each recipe
			// Fetching Recipe URL
			String URLString = each_recipe.toString();
			System.out.println("Recipe URL=" + URLString);
			excelReader.setCellData("Snacks", counter, 10, URLString);

			// Fetching Recipe ID
			String recipe_id = URLString.substring(URLString.lastIndexOf("-")+1, URLString.lastIndexOf("r"));
			System.out.println("Recipe ID=" + recipe_id);
			excelReader.setCellData("Breakfast", counter, 0, recipe_id);

			// fetching recipe name
			Elements recipe_nameElement = doc.selectXpath("//span[@id='ctl00_cntrightpanel_lblRecipeName']");
			String recipe_name = recipe_nameElement.text();
			System.out.println("Recipe name = " + recipe_name);
			excelReader.setCellData("Breakfast", counter, 1, recipe_name);

			// Hardcoding recipe category value = Snacks in all rows
			excelReader.setCellData("Breakfast", counter, 2, "Breakfast");

			// Hardcoding food category value = Veg
			excelReader.setCellData("Breakfast", counter, 3, "Veg");

			// Fetching ingredients list
			Elements ingredient_listElement = doc.selectXpath("//div[@id='rcpinglist']");
			ingredients = ingredient_listElement.text();
			System.out.println("Ingredients list=" + ingredients);
			excelReader.setCellData("Breakfast", counter, 4, ingredients);

			// fetching preparation time
			Elements prep_TimeElement = doc.selectXpath("//time[@itemprop='prepTime']");
			prep_Time = prep_TimeElement.text();
			System.out.println("Preparation time=" + prep_Time);
			excelReader.setCellData("Breakfast", counter, 5, prep_Time);

			// fetching cooking time
			Elements cook_TimeElement = doc.selectXpath("//time[@itemprop='cookTime']");
			cook_Time = cook_TimeElement.text();
			System.out.println("Cooking time =" + cook_Time);
			excelReader.setCellData("Breakfast", counter, 6, cook_Time);

			// fetching methos of the recipe
			Elements method_Element = doc.selectXpath("//div[@id='recipe_small_steps']");
			method = method_Element.text();
			System.out.println("Method =" + method);
			excelReader.setCellData("Breakfast", counter, 7, method);

			// Fetching nutrient vaues
			Elements nutrient_Element = doc.selectXpath("//div[@id='accompaniments']");
			nutrientValue = nutrient_Element.text();
			System.out.println("Nutrient Value=" + nutrientValue);
			excelReader.setCellData("Breakfast", counter, 8, nutrientValue);

			// Hardcoding recipe category value = Snacks in all rows
			excelReader.setCellData("Breakfast", counter, 9, "N/A");

			counter = counter + 1;
			System.out.println("Counter=" + counter);
		}

		System.out.println("Done!!!!");

		timer_end = Instant.now();
		System.out.println("Time taken to scrape this webpage = " + Duration.between(timer_start, timer_end).toSeconds()
				+ "Seconds");

	}
}
