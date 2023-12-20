package com.MorbidConditions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import com.TestData.Diabetes_IngredientsCheckList;
import com.TestData.categoryList;
import com.Utilities.ExcelReader;
import com.Utilities.PropertyReader;
import com.driverFactory.InitClass;

public class DiabetiesDinnerRecipes extends InitClass {

	@Test
	public void scrapeDiabeticRecipe() throws InterruptedException, IOException {

		// list containing filters for accepted FoodCategory/Recipe category and Morbid
		// condition
		List<String> acceptedFoodCatList = categoryList.acceptedFoodCategory();
		List<String> targetedMorbidCondList = categoryList.targetMorbidCondition();

		// ArrayList<String> recipeCatListPresent = new ArrayList<String>();

		String RecipeCategory = "Dinner";
		// creating Excel
				String filePath = PropertyReader.getPropFromProperty("config", "excelFilePath") + "Diabetic_MorbidTestData.xlsx";
				ExcelReader excelReader = new ExcelReader(filePath);
				excelReader.createExcel("Diabetic_DinnerRecipes");
				System.out.println("Excel created");
				Thread.sleep(2);
				
		
		// Click on recipe search tab on Home page
		WebElement recipeTag = driver.findElement(By.className("sel"));
		recipeTag.click();
		Thread.sleep(1);

		// Diabetic recipe linktab
		WebElement diabeticRecipeList = driver.findElement(By.id("ctl00_cntleftpanel_ttlhealthtree_tvTtlHealtht46"));
		diabeticRecipeList.click();
		Thread.sleep(1);

		// Pagination- navigating through all recipe pages

		int cell = 1; // counter to prevent overwriting of data when moving to next page

		List<WebElement> paginationList = driver.findElements(By.xpath("//*[@id='pagination']/a"));
		int pageSize = paginationList.size();

		for (int page = 1; page <= pageSize; page++) {

			ArrayList<String> morbidCondListPresent = new ArrayList<String>();
			ArrayList<String> foodCatListPresent = new ArrayList<String>();
			ArrayList<String> recipeCatListPresent = new ArrayList<String>();

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

				// if(ingredients.contains(ingredients))

				String prepartionTime = driver.findElement(By.xpath("//time[@itemprop='prepTime']")).getText();
				String cookingTime = driver.findElement(By.xpath("//time[@itemprop='cookTime']")).getText();
				String preparationMethod = driver.findElement(By.id("recipe_small_steps")).getText();
				Thread.sleep(1000);
				String nutrient = driver.findElement(By.id("rcpnutrients")).getText();
				String tagstext = driver.findElement(By.id("recipe_tags")).getText();
				List<WebElement> tagsList = driver.findElements(By.xpath("(//div[@id='recipe_tags']/a)"));
				int tagsSize = tagsList.size();
				Thread.sleep(1000);

//				// write recipe data in xlsheet

				// Check the tags text for dinner recipes, write only in excel , if present

				if (tagstext.contains(RecipeCategory)) {

					System.out.println("Recipe category Present---------" + RecipeCategory);

					// check for Diabetes ingredient eliminate list in the recipes
					boolean eliminateList = Diabetes_IngredientsCheckList.eliminateIngredient(ingredients);

					System.out.println(eliminateList);
					if (eliminateList) {

						System.out.println(
								"navigate back ---> Eliminated Ingredient present--*****************************************************************");
						driver.navigate().to(
								"https://www.tarladalal.com/recipes-for-indian-diabetic-recipes-370?pageindex=" + page);
						continue;
					}

					excelReader.setCellData("Diabetic_DinnerRecipes", cell, 2, RecipeCategory);
					excelReader.setCellData("Diabetic_DinnerRecipes", cell, 0, recipeId);
					excelReader.setCellData("Diabetic_DinnerRecipes", cell, 1, recipeName);

					// iterate through tags webElement to capture food category and write in excel
					for (int j = 0; j < acceptedFoodCatList.size(); j++) {

						String foodCategory = acceptedFoodCatList.get(j);
						if (tagstext.contains(foodCategory)) {
							System.out.println("Food category Present---------" + foodCategory);
							foodCatListPresent.add(foodCategory);
						}
						excelReader.setCellData("DiabeticRecipes_Eliminate", cell, 3, foodCatListPresent.toString());

					}
					

					excelReader.setCellData("Diabetic_DinnerRecipes", cell, 4, ingredients);
					excelReader.setCellData("Diabetic_DinnerRecipes", cell, 5, prepartionTime);
					excelReader.setCellData("Diabetic_DinnerRecipes", cell, 6, cookingTime);
					excelReader.setCellData("Diabetic_DinnerRecipes", cell, 7, preparationMethod);
					excelReader.setCellData("Diabetic_DinnerRecipes", cell, 8, nutrient);

					// iterate through tags webElement to capture morbid condition and write in
					// excel

					for (int j = 0; j < targetedMorbidCondList.size(); j++) {

						String tarMorbidCondition = targetedMorbidCondList.get(j);
						if (tagstext.contains(tarMorbidCondition)) {
							System.out.println("Morbid condition Present---------" + tarMorbidCondition);
							morbidCondListPresent.add(tarMorbidCondition);
							excelReader.setCellData("Diabetic_DinnerRecipes", cell, 9,
									morbidCondListPresent.toString());
						}
					}
					excelReader.setCellData("Diabetic_DinnerRecipes", cell, 10, recipeUrl);
					
					// Printing on console
					System.out.println("Number of tags present: " + tagsSize);
					// System.out.println("--" + tagstext + "--");
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
					System.out.println("Targetted morbid conditions (Diabeties/Hypertension/Hypothyroidism): ----- "
							+ morbidCondListPresent.toString());
					System.out.println("Recipe URL:------" + recipeUrl);
					
					morbidCondListPresent.clear();
					recipeCatListPresent.clear();
					foodCatListPresent.clear();

				}
				cell++;
				System.out.println("*****************************************************************");
				driver.navigate().to("https://www.tarladalal.com/recipes-for-indian-diabetic-recipes-370?pageindex=" + page);
			}
		}
	}

}