package com.MorbidConditions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import com.TestData.Hypertension_EliminateIngredientsCheckList;
import com.TestData.categoryList;
import com.Utilities.ExcelReader;
import com.Utilities.PropertyReader;
import com.driverFactory.InitClass;

	@SuppressWarnings("unused")
	public class HypertensionEliminiate1 extends InitClass {	

		@Test
		public void scrapeHypertensionRecipes() throws InterruptedException, IOException {

			String RecipeCategory = "Dinner";	

			// write headers for in xlsheet
			
						String filePath = PropertyReader.getPropFromProperty("config", "excelFilePath")+"MorbidTestData.xlsx";
						ExcelReader excelReader = new ExcelReader(filePath);
						excelReader.createExcel("HypertensionRecipes");
						System.out.println("Excel created");

					Thread.sleep(2);

			// Search High Blood pressure recipes from recipe search box on Home page
			WebElement recipeSearchBox = driver.findElement(By.id("ctl00_txtsearch"));
			WebElement searchButton = driver.findElement(By.id("ctl00_imgsearch"));

			recipeSearchBox.sendKeys("high blood pressure");
			searchButton.click();
			Thread.sleep(1000);

			// Search results for High Blood pressure recipes
			WebElement searchResult = driver.findElement(By.xpath("//a[@class='rcpsrch_suggest'][1]"));
			
			//WebElement searchResult = driver.findElement(By.xpath("//a[@href='recipes-for-high-blood-pressure-644']"));
			Thread.sleep(1000);
			searchResult.click();
			Thread.sleep(1000);

			// Pagination- navigating through all recipe pages

			int cell = 1; // counter to prevent overwriting of data when moving to next page

			List<WebElement> paginationList = driver.findElements(By.xpath("//*[@id='pagination']/a"));
			int pageSize = paginationList.size();

			for (int page = 1; page <= pageSize; page++) {

				@SuppressWarnings("unused")
				ArrayList<String> morbidCondListPresent = new ArrayList<String>();
				ArrayList<String> foodCatListPresent = new ArrayList<String>();
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
				for (int i = 1; i<= 10; i++) {

					Thread.sleep(2000);

					String recipeUrl = driver.findElement(By.xpath("(//span[@class='rcc_recipename']/a)[" + i + "]"))
							.getAttribute("href");
					Thread.sleep(2000);

					String recipeName = driver.findElement(By.xpath("(//span[@class='rcc_recipename']/a)[" + i + "]"))
							.getText();
					Thread.sleep(2000);
					
					String recipeId = recipeUrl.substring(recipeUrl.lastIndexOf("-") + 1, recipeUrl.lastIndexOf("r"));
					System.out.println("Recipe ID=" + recipeId);
					Thread.sleep(2000);
					WebElement recipeLinks = driver.findElement(By.xpath("(//span[@class='rcc_recipename']/a)[" + i + "]"));
					recipeLinks.click();
					Thread.sleep(1000);
					String ingredients = driver.findElement(By.id("rcpinglist")).getText();
					String prepartionTime = driver.findElement(By.xpath("//time[@itemprop='prepTime']")).getText();
					String cookingTime = driver.findElement(By.xpath("//time[@itemprop='cookTime']")).getText();
					String preparationMethod = driver.findElement(By.id("recipe_small_steps")).getText();
					Thread.sleep(1000);
					String nutrient = driver.findElement(By.id("rcpnutrients")).getText();
					String tagstext = driver.findElement(By.id("recipe_tags")).getText();
					List<WebElement> tagsList = driver.findElements(By.xpath("(//div[@id='recipe_tags']/a)"));
					int tagsSize = tagsList.size();
					Thread.sleep(3000);
					
					//check for Hypertension ingredient eliminate list in the recipes
					boolean eliminateList = Hypertension_EliminateIngredientsCheckList.eliminateIngredient(ingredients);

					System.out.println(eliminateList);
					System.out
					.println("navigate back*****************************************************************");
			driver.navigate().to("https://www.tarladalal.com/recipes-for-high-blood-pressure-644?pageindex=" + page);
					/*if (eliminateList) {

						System.out
								.println("navigate back*****************************************************************");
						driver.navigate().to("https://www.tarladalal.com/recipes-for-high-blood-pressure-644?pageindex=" + page);
						continue;
					}*/

					excelReader.setCellData("HypertensionRecipes", cell, 2, RecipeCategory);
					excelReader.setCellData("HypertensionRecipes", cell, 0, recipeId);
					excelReader.setCellData("HypertensionRecipes", cell, 1, recipeName);

					List<String> FoodCatList = categoryList.acceptedFoodCategory();

					for (int j = 0; j < FoodCatList.size(); j++) {

						String foodCategory = FoodCatList.get(j);
						if (tagstext.contains(foodCategory)) {
							System.out.println("Food category Present---------" + foodCategory);
							foodCatListPresent.add(foodCategory);
						}
						excelReader.setCellData("HypertensionRecipes", cell, 3, foodCatListPresent.toString());

					}
					foodCatListPresent.clear();
					excelReader.setCellData("HypertensionRecipes", cell, 4, ingredients);
					excelReader.setCellData("HypertensionRecipes", cell, 5, prepartionTime);
					excelReader.setCellData("HypertensionRecipes", cell, 6, cookingTime);
					excelReader.setCellData("HypertensionRecipes", cell, 7, preparationMethod);
					excelReader.setCellData("HypertensionRecipes", cell, 8, nutrient);
					excelReader.setCellData("HypertensionRecipes", cell, 9, "Hypertension");
					excelReader.setCellData("HypertensionRecipes", cell, 10, recipeUrl);

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
				driver.navigate().to("https://www.tarladalal.com/recipes-for-high-blood-pressure-644?pageindex" + page);
			}

		}
	}

