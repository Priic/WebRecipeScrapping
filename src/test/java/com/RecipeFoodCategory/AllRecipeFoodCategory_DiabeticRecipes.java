package com.RecipeFoodCategory;


import java.util.List;
import java.util.ArrayList;
import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import com.TestData.Diabetes_IngredientsCheckList;
import com.TestData.categoryList;
import com.Utilities.*;
import com.driverFactory.InitClass;

public class AllRecipeFoodCategory_DiabeticRecipes extends InitClass {

	@Test
	public void scrapeDinnerRecipe() throws InterruptedException, IOException {

		// declaring variables
		String prepartionTime = null;
		String ingredients = null;
		String cookingTime = null;
		String preparationMethod = null;
		String nutrient = "";

		/*
		 * list containing filters for accepted FoodCategory/Recipe category and Morbid
		 * condition
		 */

		List<String> acceptedFoodCatList = categoryList.acceptedFoodCategory();
		List<String> acceptedRecipeCatList = categoryList.acceptedRecipeCategory();
		List<String> targetedMorbidCondList = categoryList.targetMorbidCondition();

		// creating Excel
		String filePath = PropertyReader.getPropFromProperty("config", "excelFilePath") + "Diabetic_RecipeTestData.xlsx";
		ExcelReader excelReader = new ExcelReader(filePath);
		excelReader.createExcel("Diabetic_AllRecipeFoodCat");
		System.out.println("Excel created");

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

			ArrayList<String> morbidCondListPresent = new ArrayList<String>();
			ArrayList<String> foodCatListPresent = new ArrayList<String>();
			ArrayList<String> recipeCatListPresent = new ArrayList<String>();

			System.out.println("********************------------------------------**********");
			System.out.println("--------------RecipePAGE " + page + "------------------------");
			System.out.println("********************------------------------------**********");

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

				// fetching tag text
				String tagstext = doc.getElementById("recipe_tags").text();
				List<WebElement> tagsList = driver.findElements(By.xpath("(//div[@id='recipe_tags']/a)"));
				int tagsSize = tagsList.size();
				System.out.println("Tags =" + tagstext);
				System.out.println("***********************************************");

				// check for Diabetes ingredient eliminate list in the recipes
				boolean eliminateList = Diabetes_IngredientsCheckList.eliminateIngredient(ingredients);

				System.out.println(eliminateList);
				if (eliminateList) {

					System.out.println(
							"navigate back ---> Eliminated Ingredient present--*****************************************************************");
					// navigateBack(page, recipeUrl);
					// driver.navigate().to(recipeUrl + "?pageindex=" + page);
					driver.navigate().to("https://www.tarladalal.com/recipes-for-indian-dinner-939?pageindex=" + page);
					continue;
				}
				// Write in excel recipe details based on condition

				excelReader.setCellData("Diabetic_AllRecipeFoodCat", cell, 0, recipeId);
				excelReader.setCellData("Diabetic_AllRecipeFoodCat", cell, 1, recipeName);

				// Check the tags text for dinner recipe and write only in excel,if present

				for (int j = 0; j < acceptedRecipeCatList.size(); j++) {

					String recipeCategory = acceptedRecipeCatList.get(j);
					if (tagstext.contains(recipeCategory)) {
						System.out.println("Recipe category Present---------" + recipeCategory);
						recipeCatListPresent.add(recipeCategory);
					}
					excelReader.setCellData("Diabetic_AllRecipeFoodCat", cell, 2, recipeCatListPresent.toString());
				}

				// iterate through tags webElement to capture food category and write in excel

				for (int j = 0; j < acceptedFoodCatList.size(); j++) {

					String foodCategory = acceptedFoodCatList.get(j);
					if (tagstext.contains(foodCategory)) {
						System.out.println("Food category Present---------" + foodCategory);
						foodCatListPresent.add(foodCategory);
					}
					excelReader.setCellData("Diabetic_AllRecipeFoodCat", cell, 3, foodCatListPresent.toString());
				}
				excelReader.setCellData("Diabetic_AllRecipeFoodCat", cell, 4, ingredients);
				excelReader.setCellData("Diabetic_AllRecipeFoodCat", cell, 5, prepartionTime);
				excelReader.setCellData("Diabetic_AllRecipeFoodCat", cell, 6, cookingTime);
				excelReader.setCellData("Diabetic_AllRecipeFoodCat", cell, 7, preparationMethod);
				excelReader.setCellData("Diabetic_AllRecipeFoodCat", cell, 8, nutrient);

				// iterate through tags webElement to capture morbid condition and write in
				// excel

				for (int j = 0; j < targetedMorbidCondList.size(); j++) {

					String tarMorbidCondition = targetedMorbidCondList.get(j);
					if (tagstext.contains(tarMorbidCondition)) {
						System.out.println("Morbid condition Present---------" + tarMorbidCondition);
						morbidCondListPresent.add(tarMorbidCondition);
						excelReader.setCellData("Diabetic_AllRecipeFoodCat", cell, 9, morbidCondListPresent.toString());
					}
				}
				excelReader.setCellData("Diabetic_AllRecipeFoodCat", cell, 10, recipeUrl);

				// Printing recipe details on console

				System.out.println("Recipe ID:------ " + recipeId);
				System.out.println("Recipe Name:----- " + recipeName);
				System.out.println(
						"Recipe Category(Breakfast/lunch/snack/dinner):----- " + recipeCatListPresent.toString());
				System.out.println("Food Category(Veg/non-veg/vegan/Jain):----- " + foodCatListPresent.toString());
				System.out.println("Ingredients :------ " + ingredients);
				System.out.println("Prepartion Time:------" + prepartionTime);
				System.out.println("Cooking Time:------ " + cookingTime);
				System.out.println("Preparation Method: ----- " + preparationMethod);
				System.out.println("Nutrient values: ----- " + nutrient);
				System.out.println("Recipe URL:------" + recipeUrl);
				System.out.println("Targetted morbid conditions (Diabeties/Hypertension/Hypothyroidism): ----- "
						+ morbidCondListPresent.toString());
				// Clear the list for next iteration
				recipeCatListPresent.clear();
				foodCatListPresent.clear();
				morbidCondListPresent.clear();
				counter = counter + 1;
				System.out.println("Counter=" + counter);
				System.out.println("*****************************************************************");
				cell++;
				driver.navigate().to("https://www.tarladalal.com/recipes-for-indian-dinner-939?pageindex=" + page);
			}
		}

		System.out.println("Done!!!!");
	}

	public void navigateBack(int page, String recipeUrl) {

		driver.navigate().to(recipeUrl + "?pageindex=" + page);

	}
}
