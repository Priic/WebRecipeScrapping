package com.RecipeFoodCategory;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import org.testng.annotations.Test;

import com.TestData.categoryList;
import com.Utilities.*;

import com.driverFactory.InitClass;

public class Snack extends InitClass {
		

	@Test

	public void scrapeSnacksRecipe() throws InterruptedException, IOException {

				
				List<String> acceptedFoodCatList = categoryList.acceptedFoodCategory();
			//	List<String> acceptedRecipeCatList = acceptedRecipeCategory();
				List<String> targetedMorbidCondList = categoryList.targetMorbidCondition();

			//	ArrayList<String> recipeCatListPresent = new ArrayList<String>();
				ArrayList<String> foodCatListPresent = new ArrayList<String>();
				ArrayList<String> morbidCondListPresent = new ArrayList<String>();
				
				String RecipeCategory = "Snacks";

		// declaring variables
	
		String prep_Time = null;
		String ingredients = null;
		String cook_Time = null;
		String method = null;
		String nutrientValue;
		Instant timer_end;
		Instant timer_start = null;
		
		//starting the timer
		timer_start = Instant.now();
		
		// searching snack in search textbox
		driver.findElement(By.id("ctl00_txtsearch")).sendKeys("Snacks");
		driver.findElement(By.name("ctl00$imgsearch")).click();
		driver.findElement(By.xpath("//a[contains(text(),'Course > Starters / Snacks > Indian snacks with li')]")).click();
		System.out.println("Clicking fourth link on snacks search results");
		

		// ---------------creating excel -------------------------------------------
		String filePath = PropertyReader.getPropFromProperty("config", "excelFilePath")+"RecipeTestData.xlsx";
		ExcelReader xlUtil = new ExcelReader(filePath);
		xlUtil.createExcel("Snacks");
		System.out.println("Excel created");
		
		// Pagination- navigating through all recipe pages
		int PageNumber=1;// counter to prevent overwriting of data when moving to next page
		List<WebElement> paginationList = driver.findElements(By.xpath("//*[@id='pagination']/a"));
		int pageSize = paginationList.size();
		System.out.println("Total Pages="+pageSize);
		
		for(int page = 1; page <= pageSize; page++ ) {	
			System.out.println("StartPAGE " + page);
			Thread.sleep(1000);
			WebElement pagination = driver.findElement(By.xpath("//*[@id='pagination']/a[" + page + "]"));
			pagination.click();
			Thread.sleep(1000);
			
		// fetching all urls in List
		java.util.List<WebElement> recipes_url = driver.findElements(By.className("rcc_recipename"));
		int total_cards = recipes_url.size();
		System.out.println("Total cards =" + total_cards);
		ArrayList<String> link = new ArrayList<>(24);

		// Adding all recipe urls in arraylist
		for (WebElement e : recipes_url) {
			// .findElement -----> finds the tag <a> inside the current WebElement
			// .getAttribute ----> returns the href attribute of the <a> tag in the current
			// WebElement
			link.add(e.findElement(By.tagName("a")).getAttribute("href"));
		}

		// Clicking each recipe card and fetching all required information an writing in excel
		int counter = 1; // using this counter to for my reference
		for (Object each_recipe : link) {
			System.out.println("Counter =" + counter);
			Document doc = Jsoup.connect((String) each_recipe).timeout(1000 * 100).get(); // clicking each recipe
			// Fetching Recipe URL
			String URLString = each_recipe.toString();
			System.out.println("Recipe URL=" + URLString);
			xlUtil.setCellData("Snacks", PageNumber, 10, URLString);

			// Fetching Recipe ID
			String recipe_id = URLString.substring(URLString.lastIndexOf("-")+1, URLString.lastIndexOf("r"));
			System.out.println("Recipe ID=" + recipe_id);
			xlUtil.setCellData("Snacks", PageNumber, 0, recipe_id);

			// fetching recipe name
			Elements recipe_nameElement = doc.selectXpath("//span[@id='ctl00_cntrightpanel_lblRecipeName']");
			String recipe_name = recipe_nameElement.text();
			System.out.println("Recipe name = " + recipe_name);
			xlUtil.setCellData("Snacks", PageNumber, 1, recipe_name);
			
			// Fetching ingredients list
			Elements ingredient_listElement = doc.selectXpath("//div[@id='rcpinglist']");
			ingredients = ingredient_listElement.text();
			System.out.println("Ingredients list=" + ingredients);
			xlUtil.setCellData("Snacks", PageNumber, 4, ingredients);

			// fetching preparation time
			Elements prep_TimeElement = doc.selectXpath("//time[@itemprop='prepTime']");
			prep_Time = prep_TimeElement.text();
			System.out.println("Preparation time=" + prep_Time);
			xlUtil.setCellData("Snacks", PageNumber, 5, prep_Time);

			// fetching cooking time
			Elements cook_TimeElement = doc.selectXpath("//time[@itemprop='cookTime']");
			cook_Time = cook_TimeElement.text();
			System.out.println("Cooking time =" + cook_Time);
			xlUtil.setCellData("Snacks", PageNumber, 6, cook_Time);

			// fetching method of the recipe
			Elements method_Element = doc.selectXpath("//div[@id='recipe_small_steps']");
			method = method_Element.text();
			System.out.println("Method =" + method);
			xlUtil.setCellData("Snacks", PageNumber, 7, method);

			// Fetching nutrient vaues
			Elements nutrient_Element = doc.selectXpath("//div[@id='accompaniments']");
			nutrientValue = nutrient_Element.text();
			System.out.println("Nutrient Value=" + nutrientValue);
			xlUtil.setCellData("Snacks", PageNumber, 8, nutrientValue);

			// iterate through tags webElement to capture recipe category and write in excel
			//Fetching the tags
			Element tagstextElement = doc.getElementById("recipe_tags");
			String tagsText = tagstextElement.text();
			System.out.println("Tags ="+tagsText);
			if(tagsText.contains(RecipeCategory)) {
				System.out.println("Recipe Category Present---"+RecipeCategory);
				xlUtil.setCellData("Snacks", PageNumber, 2, RecipeCategory);
			}
			
			
			// iterate through tags webElement to capture food category and write in excel
			String FinalFoodCategory="";
			for (int j = 0; j < acceptedFoodCatList.size(); j++) {

				String foodCategory = acceptedFoodCatList.get(j);
				if (tagsText.contains(foodCategory)) {
					System.out.println("Food category Present---------" + foodCategory);
					foodCatListPresent.add(foodCategory);
					FinalFoodCategory=foodCategory.concat(" ").concat(FinalFoodCategory);
				}
				xlUtil.setCellData("Snacks", PageNumber, 3, FinalFoodCategory);
			}
			// iterate through tags webElement to capture morbid condition and write in excel
			String MorbidValue="";
			for (int j = 0; j < targetedMorbidCondList.size(); j++) {

				String tarMorbidCondition = targetedMorbidCondList.get(j);

				if (tagsText.contains(tarMorbidCondition)) {
					System.out.println("Morbid condition Present---------" + tarMorbidCondition);
					morbidCondListPresent.add(tarMorbidCondition);
					MorbidValue = tarMorbidCondition.concat(" ").concat(MorbidValue);
				}
				xlUtil.setCellData("Snacks", PageNumber, 9, MorbidValue);
			}
			counter = counter + 1;
			System.out.println("Counter=" + counter);
			PageNumber++;
			System.out.println("*****************************************************************");
			driver.navigate().to("https://www.tarladalal.com/recipes-for-indian-snacks-with-little-planning-180?pageindex=" + page);
		}
		}
		System.out.println("Done!!!!");
		
		//Stopping the timer
		timer_end = Instant.now();
		System.out.println("Time taken to scrape this webpage = " + Duration.between(timer_start, timer_end).toSeconds()+ "Seconds");

	}
}
