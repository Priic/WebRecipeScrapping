package com.RecipeFoodCategory;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.io.IOException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import com.Utilities.*;
import com.driverFactory.InitClass;

public class Dinner extends InitClass {

	// returning list of accepted FoodCategory/Recipe category and Morbid condition
	private static List<String> acceptedFoodCategory() {

		return Arrays.asList("Vegan", "Vegetarian", "Jain", "Egg", "Non-veg");
	}

	private static List<String> acceptedRecipeCategory() {
		return Arrays.asList("Breakfast", "Lunch", "Snack", "Dinner");
	}

	private static List<String> targetMorbidCondition() {
		return Arrays.asList("Diabetes", "Hypothyroidism", "Hypertension", "PCOS");
	}

	@Test
	public void scrapeDinnerRecipe() throws InterruptedException, IOException {

		// list containing filters for accepted FoodCategory/Recipe category and Morbid
		// condition

		List<String> acceptedFoodCatList = acceptedFoodCategory();
		List<String> acceptedRecipeCatList = acceptedRecipeCategory();
		List<String> targetedMorbidCondList = targetMorbidCondition();

		ArrayList<String> recipeCatListPresent = new ArrayList<String>();
		ArrayList<String> foodCatListPresent = new ArrayList<String>();
		ArrayList<String> morbidCondListPresent = new ArrayList<String>();

		// xlsheet path
		String path = ".//src//test//resources//TestData//DinnerRecipeTestData.xlsx";
		ExcelReader excelReader = new ExcelReader(path);

		// write headers in xlsheet
		try {
			excelReader.setCellData("DinnerRecipes", 0, 0, "Recipe Id");
			excelReader.setCellData("DinnerRecipes", 0, 1, "Recipe Name");
			excelReader.setCellData("DinnerRecipes", 0, 2, "Recipe Category(Breakfast/lunch/snack/dinner)");
			excelReader.setCellData("DinnerRecipes", 0, 3, "Food Category(Veg/non-veg/vegan/Jain)");
			excelReader.setCellData("DinnerRecipes", 0, 4, "Ingredients");
			excelReader.setCellData("DinnerRecipes", 0, 5, "Preparation Time");
			excelReader.setCellData("DinnerRecipes", 0, 6, "Cooking Time");
			excelReader.setCellData("DinnerRecipes", 0, 7, "Preparation method");
			excelReader.setCellData("DinnerRecipes", 0, 8, "Nutrient values");
			excelReader.setCellData("DinnerRecipes", 0, 9,
					"Targetted morbid conditions (Diabeties/Hypertension/Hypothyroidism)");
			excelReader.setCellData("DinnerRecipes", 0, 10, "Recipe URL");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Thread.sleep(2);

		// Search dinner recipe from recipe search box on Home page
		WebElement recipeSearchBox = driver.findElement(By.id("ctl00_txtsearch"));
		WebElement searchButton = driver.findElement(By.id("ctl00_imgsearch"));

		recipeSearchBox.sendKeys("dinner");
		searchButton.click();
		Thread.sleep(1);

		// Search results for dinner recipe
		WebElement searchResult = driver.findElement(By.xpath("//a[@href='recipes-for-indian-dinner-939']"));
		searchResult.click();
		Thread.sleep(1);

		// Pagination- navigating through all recipe pages

		int cell = 1; // counter to prevent overwriting of data when moving to next page

		List<WebElement> paginationList = driver.findElements(By.xpath("//*[@id='pagination']/a"));
		int pageSize = paginationList.size();

		for (int page = 1; page <= pageSize; page++) {

			System.out.println("StartPAGE " + page);

			Thread.sleep(1000);
			WebElement pagination = driver.findElement(By.xpath("//*[@id='pagination']/a[" + page + "]"));
			pagination.click();
			Thread.sleep(1000);

			// get list of all dinner recipes cards in current page
			List<WebElement> allRecipeCards = driver.findElements(By.xpath("//article[@class='rcc_recipecard']"));
			int totalRecipeCard = allRecipeCards.size();
			System.out.println("Total recipe cards in page" + page + ":" + totalRecipeCard);

			// Iterate through the list to get single recipe information
			for (int i = 1; i <= 2; i++) {

				// Thread.sleep(1000);

				String recipeUrl = driver.findElement(By.xpath("(//span[@class='rcc_recipename']/a)[" + i + "]"))
						.getAttribute("href");

				String recipeName = driver.findElement(By.xpath("(//span[@class='rcc_recipename']/a)[" + i + "]"))
						.getText();
				String recipeId = recipeUrl.substring(recipeUrl.lastIndexOf("-") + 1, recipeUrl.lastIndexOf("r"));
				System.out.println("Recipe ID=" + recipeId);

				WebElement recipeLinks = driver.findElement(By.xpath("(//span[@class='rcc_recipename']/a)[" + i + "]"));
				recipeLinks.click();
				// Thread.sleep(1000);
				String ingredients = driver.findElement(By.id("rcpinglist")).getText();
				String prepartionTime = driver.findElement(By.xpath("//time[@itemprop='prepTime']")).getText();
				String cookingTime = driver.findElement(By.xpath("//time[@itemprop='cookTime']")).getText();
				String preparationMethod = driver.findElement(By.id("recipe_small_steps")).getText();
				String nutrient = driver.findElement(By.id("rcpnutrients")).getText();
				String tagstext = driver.findElement(By.id("recipe_tags")).getText();
				List<WebElement> tagsList = driver.findElements(By.xpath("(//div[@id='recipe_tags']/a)"));
				int tagsSize = tagsList.size();

				System.out.println("Number of tags present: " + tagsSize);
				System.out.println("--" + tagstext + "--");
				System.out.println("Recipe ID:------ " + recipeId);
				System.out.println("Recipe Name:----- " + recipeName);
				System.out.println("Recipe Category(Breakfast/lunch/snack/dinner):----- ");
				System.out.println("Food Category(Veg/non-veg/vegan/Jain):----- ");
				System.out.println("Ingredients :------ " + ingredients);
				System.out.println("Prepartion Time:------" + prepartionTime);
				System.out.println("Cooking Time:------ " + cookingTime);
				System.out.println("Preparation Method: ----- " + preparationMethod);
				System.out.println("Nutrient values: ----- " + nutrient);
				System.out.println("Targetted morbid conditions (Diabeties/Hypertension/Hypothyroidism): ----- ");
				System.out.println("Recipe URL:------" + recipeUrl);
				// Thread.sleep(1000);

//			// write recipe data in xlsheet
				excelReader.setCellData("DinnerRecipes", cell, 0, recipeId);
				excelReader.setCellData("DinnerRecipes", cell, 1, recipeName);

				// iterate through tags webElement to capture recipe category and write in excel
				for (int j = 0; j < acceptedRecipeCatList.size(); j++) {

					String RecipeCategory = acceptedRecipeCatList.get(j);
					if (tagstext.contains(RecipeCategory)) {
						System.out.println("Recipe category Present---------" + RecipeCategory);
						recipeCatListPresent.add(RecipeCategory);
					}
					excelReader.setCellData("DinnerRecipes", cell, 2, recipeCatListPresent.toString());

				}
				// iterate through tags webElement to capture food category and write in excel
				for (int j = 0; j < acceptedFoodCatList.size(); j++) {

					String foodCategory = acceptedFoodCatList.get(j);
					if (tagstext.contains(foodCategory)) {
						System.out.println("Food category Present---------" + foodCategory);
						foodCatListPresent.add(foodCategory);
					}
					excelReader.setCellData("DinnerRecipes", cell, 3, foodCatListPresent.toString());
				}
				// excelReader.setCellData("DinnerRecipes", i, 3, "Veg");
				excelReader.setCellData("DinnerRecipes", cell, 4, ingredients);
				excelReader.setCellData("DinnerRecipes", cell, 5, prepartionTime);
				excelReader.setCellData("DinnerRecipes", cell, 6, cookingTime);
				excelReader.setCellData("DinnerRecipes", cell, 7, preparationMethod);
				excelReader.setCellData("DinnerRecipes", cell, 8, nutrient);

				// iterate through tags webElement to capture morbid condition and write in
				// excel
				for (int j = 0; j < targetedMorbidCondList.size(); j++) {

					String tarMorbidCondition = targetedMorbidCondList.get(j);

					if (tagstext.contains(tarMorbidCondition)) {
						System.out.println("Morbid condition Present---------" + tarMorbidCondition);
						morbidCondListPresent.add(tarMorbidCondition);
					}
					excelReader.setCellData("DinnerRecipes", cell, 9, morbidCondListPresent.toString());
				}
				excelReader.setCellData("DinnerRecipes", cell, 10, recipeUrl);
				cell++;
				System.out.println("*****************************************************************");
				driver.navigate().to("https://www.tarladalal.com/recipes-for-indian-dinner-939?pageindex=" + page);
			}
		}
	}
}