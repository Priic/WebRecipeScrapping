package com.MorbidConditions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import com.TestData.Hypothyroidism_IngredientsCheckList;
import com.TestData.categoryList;
import com.Utilities.ExcelReader;
import com.Utilities.PropertyReader;
import com.driverFactory.InitClass;

public class LunchHyperthyroidism_Eliminate extends InitClass {
	@Test
	public void scrapeLunchRecipe() throws InterruptedException, IOException {

		String RecipeCategory = "Lunch";

		// xlsheet path
		String filePath = PropertyReader.getPropFromProperty("config", "excelFilePath")+"MorbidTestData.xlsx";
		ExcelReader xlUtil = new ExcelReader(filePath);
		xlUtil.createExcel("LunchHypothyroidismElimination");
		System.out.println("Excel created");
		Thread.sleep(2);

		// Search lunch recipe from recipe search box on Home page
		WebElement recipeSearchBox = driver.findElement(By.id("ctl00_txtsearch"));
		WebElement searchButton = driver.findElement(By.id("ctl00_imgsearch"));

		recipeSearchBox.sendKeys("Lunch");
		searchButton.click();
		Thread.sleep(1);

		// Search results for Lunch recipe
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

			// get list of all Lunch recipes cards in current page
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

				// check for Hypothyroidism ingredient eliminate list in the recipes
				boolean eliminateList = Hypothyroidism_IngredientsCheckList.eliminateIngredient(ingredients);
				System.out.println(eliminateList);

				System.out.println(eliminateList);
				if (eliminateList) {

					System.out
							.println("navigate back*****************************************************************");
					driver.navigate().to("https://www.tarladalal.com/recipes-for-indian-lunch-926?pageindex=" + page);
					continue;
				}

				xlUtil.setCellData("LunchHypothyroidismElimination", cell, 2, RecipeCategory);
				xlUtil.setCellData("LunchHypothyroidismElimination", cell, 0, recipeId);
				xlUtil.setCellData("LunchHypothyroidismElimination", cell, 1, recipeName);

				List<String> FoodCatList = categoryList.acceptedFoodCategory();

				for (int j = 0; j < FoodCatList.size(); j++) {

					String foodCategory = FoodCatList.get(j);
					if (tagstext.contains(foodCategory)) {
						System.out.println("Food category Present---------" + foodCategory);
						foodCatListPresent.add(foodCategory);
					}
					xlUtil.setCellData("LunchHypothyroidismElimination", cell, 3, foodCatListPresent.toString());

				}
				foodCatListPresent.clear();
				xlUtil.setCellData("LunchHypothyroidismElimination", cell, 4, ingredients);
				xlUtil.setCellData("LunchHypothyroidismElimination", cell, 5, prepartionTime);
				xlUtil.setCellData("LunchHypothyroidismElimination", cell, 6, cookingTime);
				xlUtil.setCellData("LunchHypothyroidismElimination", cell, 7, preparationMethod);
				xlUtil.setCellData("LunchHypothyroidismElimination", cell, 8, nutrient);
				xlUtil.setCellData("LunchHypothyroidismElimination", cell, 9, "Hypothyroidism");
				xlUtil.setCellData("LunchHypothyroidismElimination", cell, 10, recipeUrl);

				// Printing on console
				System.out.println("Number of tags present: " + tagsSize);
				// System.out.println("--" + tagstext + "--");
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

			}
			cell++;
			System.out.println("*****************************************************************");
			driver.navigate().to("https://www.tarladalal.com/recipes-for-indian-lunch-926?pageindex=" + page);
		}

	}

}
