package com.MorbidConditions;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import com.TestData.Hypertension_IngredientsCheckList;
import com.TestData.categoryList;
import com.Utilities.*;
import com.driverFactory.InitClass;

public class HypertensionAdd extends InitClass {

	@Test
	public void scrapeHypertensionToAddRecipe() throws InterruptedException, IOException {

		// declaring variables
		String prepartionTime = null;
		String ingredients = null;
		String cookingTime = null;
		String preparationMethod = null;
		String nutrient = "";
		Instant timer_end;
		Instant timer_start = null;

		/*
		 * list containing filters for accepted FoodCategory/Recipe category and Morbid
		 * condition
		 */

		List<String> acceptedFoodCatList = categoryList.acceptedFoodCategory();
		List<String> acceptedRecipeCatList = categoryList.acceptedRecipeCategory();
		List<String> targetedMorbidCondList = categoryList.targetMorbidCondition();
		// write headers for in xlsheet
			
			String filePath = PropertyReader.getPropFromProperty("config", "excelFilePath")+"MorbidTestData.xlsx";
			ExcelReader excelReader = new ExcelReader(filePath);
			excelReader.createExcel("Hypertension_ToAddList");
			System.out.println("Excel created");

		Thread.sleep(2);

		// Click on recipe tab on Home page
		WebElement recipeTag = driver.findElement(By.className("sel"));
		recipeTag.click();
		Thread.sleep(1);

		// Hypertension recipe linktab
		WebElement toAddHypertensionRecipeList = driver
				.findElement(By.id("ctl00_cntleftpanel_ttlhealthtree_tvTtlHealtht152"));
		toAddHypertensionRecipeList.click();
		Thread.sleep(1);

		// Pagination- navigating through all recipe pages
		int cell = 1; // counter to prevent overwriting of data when moving to next page

		List<WebElement> paginationList = driver.findElements(By.xpath("//*[@id='pagination']/a"));
		int pageSize = paginationList.size();

		for (int page = 1; page <= pageSize; page++) {

			// ArrayList<String> morbidCondListPresent = new ArrayList<String>();
			ArrayList<String> foodCatListPresent = new ArrayList<String>();
			ArrayList<String> recipeCatListPresent = new ArrayList<String>();
			ArrayList<String> morbidCondListPresent = new ArrayList<String>();

			System.out.println("RecipePAGE " + page);

			WebElement pagination = driver.findElement(By.xpath("//*[@id='pagination']/a[" + page + "]"));
			pagination.click();
			Thread.sleep(1000);

			// get list of all dinner recipes cards in current page

			List<WebElement> recipesUrl = driver.findElements(By.className("rcc_recipename"));
			int totalRecipeUrl = recipesUrl.size();
			ArrayList<String> link = new ArrayList<>();
			System.out.println("Total recipe in page" + ":" + totalRecipeUrl);

			// iterate to add all the recipes link in a List

			for (WebElement e : recipesUrl) {
				// .findElement -----> finds the tag <a> inside the current WebElement
				// .getAttribute ----> returns the href attribute of the <a> tag in the current
				// WebElement
				link.add(e.findElement(By.tagName("a")).getAttribute("href"));

			}

			// Iterate through the list to get single recipe information
			int counter = 1; // using this counter for recipe count
			for (Object eachRecipe : link) {

				System.out.println("RecipeNumber =" + counter);
				// Using Jsoup clicking on each recipe to get details
				Document doc = Jsoup.connect((String) eachRecipe).timeout(1000 * 100).get();

				// Fetching Recipe URL
				String recipeUrl = eachRecipe.toString();

				// Fetching Recipe ID
				String recipeId = recipeUrl.substring(recipeUrl.lastIndexOf("-") + 1, recipeUrl.lastIndexOf("r"));
				System.out.println("Recipe ID:------ " + recipeId);
				// fetching recipe name
				Elements recipe_nameElement = doc.selectXpath("//span[@id='ctl00_cntrightpanel_lblRecipeName']");
				String recipeName = recipe_nameElement.text();
				System.out.println("Recipe Name:----- " + recipeName);

				// Fetching ingredients list
				ingredients = doc.getElementById("rcpinglist").text();

				// fetching tag text
				String tagstext = doc.getElementById("recipe_tags").text();
				List<WebElement> tagsList = driver.findElements(By.xpath("(//div[@id='recipe_tags']/a)"));
				int tagsSize = tagsList.size();
				System.out.println("Tags =" + tagstext);
				System.out.println("***********************************************");

				// check1 for Hypertension ingredient eliminate list in the recipes
				boolean eliminateList = Hypertension_IngredientsCheckList.eliminateIngredient(ingredients);

				System.out.println(eliminateList);
				if (eliminateList) {

					System.out.println(
							"navigate back--> Eliminated Ingredient present--*****************************************************************");
					driver.navigate()
							.to("https://www.tarladalal.com/recipes-for-indian-diabetic-recipes-370?pageindex=" + page);
					continue;
				}

				// check2 for 2 add ingredient list in the recipes
				String toAddIngredientName = Hypertension_IngredientsCheckList.toAddIngredient(ingredients);

				if (toAddIngredientName == "")

				{

					System.out.println(
							"navigate back--> To add Ingredient not present--*****************************************************************");
					driver.navigate()
							.to("https://www.tarladalal.com/recipes-for-indian-diabetic-recipes-370?pageindex=" + page);
					continue;
				}
				
				// ArrayList<String> toAddIngredientList = new ArrayList<>();
				// toAddIngredientList.add(toAddIngredientName);

				System.out.println("To Add ingredient present is:" + toAddIngredientName);
				excelReader.setCellData("Hypertension_ToAddList", cell, 11, toAddIngredientName);
				// toAddIngredientList.clear();

				// fetching preparation time
				Elements prep_TimeElement = doc.selectXpath("//time[@itemprop='prepTime']");
				prepartionTime = prep_TimeElement.text();

				// fetching cooking time
				Elements cook_TimeElement = doc.selectXpath("//time[@itemprop='cookTime']");
				cookingTime = cook_TimeElement.text();

				// fetching Preparation methods of the recipe
				preparationMethod = doc.getElementById("recipe_small_steps").text();

				// Fetching nutrient values
				try {
					nutrient = doc.getElementById("rcpnutrients").text();

				} catch (Exception e) {
					System.out.println("Nutrient value not present:" + e);
					continue;
				}

				// Write in excel recipe details based on condition

				excelReader.setCellData("Hypertension_ToAddList", cell, 0, recipeId);
				excelReader.setCellData("Hypertension_ToAddList", cell, 1, recipeName);
				// iterate through tags webElement to capture recipe category and write in excel

				for (int j = 0; j < acceptedRecipeCatList.size(); j++) {

					String recipeCategory = acceptedRecipeCatList.get(j);
					if (tagstext.contains(recipeCategory)) {
						System.out.println("Recipe category Present---------" + recipeCategory);
						recipeCatListPresent.add(recipeCategory);
					}
					excelReader.setCellData("Hypertension_ToAddList", cell, 2, recipeCatListPresent.toString());
				}
				recipeCatListPresent.clear();

				// iterate through tags webElement to capture food category and write in excel

				for (int j = 0; j < acceptedFoodCatList.size(); j++) {

					String foodCategory = acceptedFoodCatList.get(j);
					if (tagstext.contains(foodCategory)) {
						System.out.println("Food category Present---------" + foodCategory);
						foodCatListPresent.add(foodCategory);
					}
					excelReader.setCellData("Hypertension_ToAddList", cell, 3, foodCatListPresent.toString());
				}
				foodCatListPresent.clear();

				excelReader.setCellData("Hypertension_ToAddList", cell, 4, ingredients);
				excelReader.setCellData("Hypertension_ToAddList", cell, 5, prepartionTime);
				excelReader.setCellData("Hypertension_ToAddList", cell, 6, cookingTime);
				excelReader.setCellData("Hypertension_ToAddList", cell, 7, preparationMethod);
				excelReader.setCellData("Hypertension_ToAddList", cell, 8, nutrient);
				excelReader.setCellData("Hypertension_ToAddList", cell, 10, recipeUrl);
				
				for (int j = 0; j < targetedMorbidCondList.size(); j++) {

					String tarMorbidCondition = targetedMorbidCondList.get(j);
					if (tagstext.contains(tarMorbidCondition)) {
						System.out.println("Morbid condition Present---------" + tarMorbidCondition);
						morbidCondListPresent.add(tarMorbidCondition);
						excelReader.setCellData("Hypertension_ToAddList", cell, 9, morbidCondListPresent.toString());
					}
				}
	
				// Printing recipe details on console

				System.out.println("Recipe ID:------ " + recipeId);
				System.out.println("Recipe Name:----- " + recipeName);
				System.out.println("Recipe Category(Breakfast/lunch/snack/dinner):----- " + recipeCatListPresent.toString());
				System.out.println("Food Category(Veg/non-veg/vegan/Jain):----- " + foodCatListPresent.toString());
				System.out.println("Ingredients :------ " + ingredients);
				System.out.println("Prepartion Time:------" + prepartionTime);
				System.out.println("Cooking Time:------ " + cookingTime);
				System.out.println("Preparation Method: ----- " + preparationMethod);
				System.out.println("Nutrient values: ----- " + nutrient);
				System.out.println("Targetted morbid conditions (Diabeties/Hypertension/Hypothyroidism): ----- "
						+ morbidCondListPresent.toString());
				System.out.println("Recipe URL:------" + recipeUrl);

				// Clear the list for next iteration
				recipeCatListPresent.clear();
				foodCatListPresent.clear();
				morbidCondListPresent.clear();
				counter = counter + 1;
				System.out.println("Counter=" + counter);
				System.out.println("*****************************************************************");
				cell++;
				driver.navigate()
						.to("https://www.tarladalal.com/recipes-for-high-blood-pressure-644?pageindex=" + page);

			}

		}
	System.out.println("Done!!!!");
	/*	timer_end = Instant.now();
		System.out.println("Time taken to scrape this webpage = " + Duration.between(timer_start, timer_end).toSeconds()
				+ "Seconds");
				*/
	}
}