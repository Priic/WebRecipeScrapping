package com.RecipeFoodCategory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import com.Utilities.*;
import com.Utilities.PropertyReader;
import com.driverFactory.InitClass;

public class LunchRecipe_WithfoodCat extends InitClass {
	// returning list of accepted FoodCategory/Recipe category and Morbid condition
	private static List<String> acceptedFoodCategory() {

		return Arrays.asList("Vegan", "Vegetarian", "Jain", "Egg", "Non-veg", "Veg");
	}

	private static List<String> targetMorbidCondition() {
		return Arrays.asList("Diabetic", "Hyperthyroidism", "Hypertension", "PCOS");
	}

	@Test
	public void scrapelunchRecipe() throws InterruptedException, IOException {

		// list containing filters for accepted FoodCategory/Recipe category and Morbid
		// condition

		List<String> acceptedFoodCatList = acceptedFoodCategory();
		List<String> targetedMorbidCondList = targetMorbidCondition();

		// ArrayList<String> recipeCatListPresent = new ArrayList<String>();

		String RecipeCategory = "Lunch";

		// xlsheet path
		String filePath = PropertyReader.getPropFromProperty("config", "excelFilePath")+"RecipeTestData.xlsx";
		ExcelReader xlUtil = new ExcelReader(filePath);
		xlUtil.createExcel("PCOS_Add");
		System.out.println("Excel created");
		Thread.sleep(2);

		// Search lunch recipe from recipe search box on Home page
		WebElement recipeSearchBox = driver.findElement(By.id("ctl00_txtsearch"));
		WebElement searchButton = driver.findElement(By.id("ctl00_imgsearch"));

		recipeSearchBox.sendKeys("Lunch");
		searchButton.click();
		Thread.sleep(1);

		// Search results for lunch recipe
		WebElement searchResult = driver.findElement(By.xpath("//a[@href='recipes-for-indian-lunch-926']"));
		searchResult.click();
		Thread.sleep(1);

		// Pagination- navigating through all recipe pages

		int cell = 1; // counter to prevent overwriting of data when moving to next page

		List<WebElement> paginationList = driver.findElements(By.xpath("//*[@id='pagination']/a"));
		int pageSize = paginationList.size();

		for (int page = 1; page <= pageSize; page++) {

			ArrayList<String> morbidCondListPresent = new ArrayList<String>();
			ArrayList<String> foodCatListPresent = new ArrayList<String>();

			System.out.println("StartPAGE " + page);

			Thread.sleep(1000);
			WebElement pagination = driver.findElement(By.xpath("//*[@id='pagination']/a[" + page + "]"));
			pagination.click();
			Thread.sleep(1000);

			// get list of all lunch recipes cards in current page
			List<WebElement> allRecipeCards = driver.findElements(By.xpath("//article[@class='rcc_recipecard']"));
			int totalRecipeCard = allRecipeCards.size();
			System.out.println("Total recipe cards in page" + page + ":" + totalRecipeCard);

			// Iterate through the list to get single recipe information
			for (int i = 1; i <= 10; i++) {

				Thread.sleep(1000);

				String recipeUrl = driver.findElement(By.xpath("(//span[@class='rcc_recipename']/a)[" + i + "]"))
						.getAttribute("href");

				String recipeName = driver.findElement(By.xpath("(//span[@class='rcc_recipename']/a)[" + i + "]"))
						.getText();
				String recipeId = recipeUrl.substring(recipeUrl.lastIndexOf("-") + 1, recipeUrl.lastIndexOf("r"));
				System.out.println("Recipe ID=" + recipeId);

				WebElement recipeLinks = driver.findElement(By.xpath("(//span[@class='rcc_recipename']/a)[" + i + "]"));
				recipeLinks.click();
				Thread.sleep(1000);
				String ingredients = driver.findElement(By.id("rcpinglist")).getText();
				String prepartionTime = driver.findElement(By.xpath("//time[@itemprop='prepTime']")).getText();
				String cookingTime = driver.findElement(By.xpath("//time[@itemprop='cookTime']")).getText();
				String preparationMethod = driver.findElement(By.id("recipe_small_steps")).getText();
				Thread.sleep(1000);
				String nutrient = "";
				try {
					nutrient = driver.findElement(By.id("rcpnutrients")).getText();
				} catch (NoSuchElementException e) {
					System.out.println("Nutrient value not present:" + e);
					continue;
				}
				String tagstext = driver.findElement(By.id("recipe_tags")).getText();
				List<WebElement> tagsList = driver.findElements(By.xpath("(//div[@id='recipe_tags']/a)"));
				int tagsSize = tagsList.size();
				Thread.sleep(1000);

//					// write recipe data in xlsheet

				// Check the tags text for lunch recipes, write only in excel , if present

				if (tagstext.contains(RecipeCategory)) {

					System.out.println("Recipe category Present---------" + RecipeCategory);

					xlUtil.setCellData("LunchFoodCatRecipes", cell, 2, RecipeCategory);
					xlUtil.setCellData("LunchFoodCatRecipes", cell, 0, recipeId);
					xlUtil.setCellData("LunchFoodCatRecipes", cell, 1, recipeName);

					// iterate through tags webElement to capture food category and write in excel
					for (int j = 0; j < acceptedFoodCatList.size(); j++) {

						String foodCategory = acceptedFoodCatList.get(j);
						if (tagstext.contains(foodCategory)) {
							System.out.println("Food category Present---------" + foodCategory);
							foodCatListPresent.add(foodCategory);
						}
						xlUtil.setCellData("LunchFoodCatRecipes", cell, 3, foodCatListPresent.toString());

					}
					foodCatListPresent.clear();

					xlUtil.setCellData("LunchFoodCatRecipes", cell, 4, ingredients);
					xlUtil.setCellData("LunchFoodCatRecipes", cell, 5, prepartionTime);
					xlUtil.setCellData("LunchFoodCatRecipes", cell, 6, cookingTime);
					xlUtil.setCellData("LunchFoodCatRecipes", cell, 7, preparationMethod);
					xlUtil.setCellData("LunchFoodCatRecipes", cell, 8, nutrient);

					// iterate through tags webElement to capture morbid condition and write in
					// excel

					for (int j = 0; j < targetedMorbidCondList.size(); j++) {

						String tarMorbidCondition = targetedMorbidCondList.get(j);
						if (tagstext.contains(tarMorbidCondition)) {
							System.out.println("Morbid condition Present---------" + tarMorbidCondition);
							morbidCondListPresent.add(tarMorbidCondition);
							xlUtil.setCellData("LunchFoodCatRecipes", cell, 9, morbidCondListPresent.toString());
						}
					}
					xlUtil.setCellData("LunchFoodCatRecipes", cell, 10, recipeUrl);
					// Printing on console
					System.out.println("Number of tags present: " + tagsSize);
					// System.out.println("--" + tagstext + "--");
					System.out.println("Recipe ID:------ " + recipeId);
					System.out.println("Recipe Name:----- " + recipeName);
					System.out.println("Recipe Category(Breakfast/lunch/snack/lunch):----- ");
					System.out.println("Food Category(Veg/non-veg/vegan/Jain):----- ");
					System.out.println("Ingredients :------ " + ingredients);
					System.out.println("Prepartion Time:------" + prepartionTime);
					System.out.println("Cooking Time:------ " + cookingTime);
					System.out.println("Preparation Method: ----- " + preparationMethod);
					System.out.println("Nutrient values: ----- " + nutrient);
					System.out.println("Targetted morbid conditions (Diabeties/Hypertension/Hypothyroidism): ----- ");
					System.out.println("Recipe URL:------" + recipeUrl);
				}
				cell++;
				System.out.println("*****************************************************************");
				driver.navigate().to("https://www.tarladalal.com/recipes-for-indian-lunch-926?pageindex=" + page);
			}
		}
	}

}
