package com.MorbidAllergies;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.ArrayList;

import com.TestData.Hypothyroidism_IngredientsCheckList;
import com.TestData.categoryList;
import com.Utilities.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import com.driverFactory.InitClass;

public class Hypothyroidism_Allergy extends InitClass {

	@Test
	public void scrapeRecipe() throws InterruptedException, IOException {

// declaring variables
		String prep_Time = null;
		String ingredients = null;
		String cook_Time = null;
		String method = null;
		String nutrientValue = "";
		Instant timer_end;
		Instant timer_start = null;
		String hypothyroidism_Morbidity = "hypothyroidism";
//starting the timer
		timer_start = Instant.now();

		// list containing filters for accepted FoodCategory/Recipe category and Morbid
		// Conditions
		List<String> acceptedFoodCatList = categoryList.acceptedFoodCategory();
		List<String> acceptedRecipeCatList = categoryList.acceptedRecipeCategory();
		List<String> targetedMorbidCondList = categoryList.targetMorbidCondition();

		// Navigating to PCOS recipes section
		WebElement Recipes = driver.findElement(By.xpath("//div[normalize-space()='RECIPES']"));
		Recipes.click();
		//driver.findElement(By.xpath("//a[normalize-space()='Health']")).click();
		WebElement hypothyroidism = driver.findElement(By.id("ctl00_cntleftpanel_ttlhealthtree_tvTtlHealtht211"));
		hypothyroidism.click();
		System.out.println("On hypothyroidism recipes Section");

		// creating Excel
		String filePath = ".\\src\\test\\resources\\TestData\\MorbidTestData.xlsx";
		ExcelReader xlUtil = new ExcelReader(filePath);
		// creating first row of Excel
		xlUtil.setCellData("Hypothyroidism_Allergy", 0, 0, "RecipeID");
		xlUtil.setCellData("Hypothyroidism_Allergy", 0, 1, "RecipeName");
		xlUtil.setCellData("Hypothyroidism_Allergy", 0, 2, "Recipe Category(Breakfast/lunch/snack/dinner)");
		xlUtil.setCellData("Hypothyroidism_Allergy", 0, 3, "Food Category(Veg/non-veg/vegan/Jain)");
		xlUtil.setCellData("Hypothyroidism_Allergy", 0, 4, "Ingredients");
		xlUtil.setCellData("Hypothyroidism_Allergy", 0, 5, "Preparation Time");
		xlUtil.setCellData("Hypothyroidism_Allergy", 0, 6, "Cooking Time");
		xlUtil.setCellData("Hypothyroidism_Allergy", 0, 7, "Preparation method");
		xlUtil.setCellData("Hypothyroidism_Allergy", 0, 8, "Nutrient values");
		xlUtil.setCellData("Hypothyroidism_Allergy", 0, 9,
				"Targetted morbid conditions (Diabeties/Hypertension/Hypothyroidism)");
		xlUtil.setCellData("Hypothyroidism_Allergy", 0, 10, "Recipe URL");
		System.out.println("Excel created");

		// Pagination- navigating through all recipe pages
		int PageNumber = 1;// counter to prevent overwriting of data when moving to next page
		List<WebElement> paginationList = driver.findElements(By.xpath("//*[@id='pagination']/a"));
		int pageSize = paginationList.size();
		System.out.println("Total Pages=" + pageSize);

		for (int page = 1; page <= pageSize; page++) {

			ArrayList<String> recipeCatListPresent = new ArrayList<String>();
			ArrayList<String> foodCatListPresent = new ArrayList<String>();
			ArrayList<String> morbidCondListPresent = new ArrayList<String>();

			System.out.println("StartPAGE " + page);
			Thread.sleep(1000);
			WebElement pagination = driver.findElement(By.xpath("//*[@id='pagination']/a[" + page + "]"));
			pagination.click();
			Thread.sleep(1000);

			// fetching all urls in List
			java.util.List<WebElement> recipes_url = driver.findElements(By.className("rcc_recipename"));
			int total_cards = recipes_url.size();
			System.out.println("Total cards =" + total_cards);
			ArrayList<String> link = new ArrayList<String>();

			// Adding all recipe urls in arraylist
			for (WebElement e : recipes_url) {
				link.add(e.findElement(By.tagName("a")).getAttribute("href"));
			}

			// Clicking each recipe card and fetching all required information an writing in
			// excel
			int counter = 1; // using this counter to select the excel sheet row number to insert data
			for (Object each_recipe : link) {
				System.out.println("Counter =" + counter);
				Document doc = Jsoup.connect((String) each_recipe).get(); // clicking each recipe
				// Fetching Recipe URL
				String URLString = each_recipe.toString();
				System.out.println("Recipe URL=" + URLString);

				// Fetching Recipe ID
				String recipe_id = URLString.substring(URLString.lastIndexOf("-") + 1, URLString.lastIndexOf("r"));
				System.out.println("Recipe ID=" + recipe_id);

				// fetching recipe name
				Elements recipe_nameElement = doc.selectXpath("//span[@id='ctl00_cntrightpanel_lblRecipeName']");
				String recipe_name = recipe_nameElement.text();
				System.out.println("Recipe name = " + recipe_name);

				// Fetching ingredients list
				Elements ingredient_listElement = doc.selectXpath("//div[@id='rcpinglist']");
				ingredients = ingredient_listElement.text();
				String eliminationItems = "";
				boolean eliminateList = Hypothyroidism_IngredientsCheckList.eliminateIngredient(ingredients);

				System.out.println(eliminateList);
				if (eliminateList) {

					System.out.println(
							"navigate back--> Eliminated Ingredient present--*****************************************************************");
					driver.navigate().to(
							"https://www.tarladalal.com/recipes-for-hypothyroidism-veg-diet-indian-recipes-849?pageindex="
									+ page);
					continue;
				}
				// Allergy List Validation
				boolean allergyList = Hypothyroidism_IngredientsCheckList.checkAllergyIngredients(ingredients);
				if (allergyList) {
					System.out.println(
							"navigate back--> Allergy present*****************************************************************");
					driver.navigate().to(
							"https://www.tarladalal.com/recipes-for-hypothyroidism-veg-diet-indian-recipes-849?pageindex="
									+ page);
					continue;
				}
				System.out.println("Ingredients list=" + ingredients);

				// fetching preparation time
				Elements prep_TimeElement = doc.selectXpath("//time[@itemprop='prepTime']");
				prep_Time = prep_TimeElement.text();
				System.out.println("Preparation time=" + prep_Time);

				// fetching cooking time
				Elements cook_TimeElement = doc.selectXpath("//time[@itemprop='cookTime']");
				cook_Time = cook_TimeElement.text();
				System.out.println("Cooking time =" + cook_Time);

				// fetching method of the recipe
				Elements method_Element = doc.selectXpath("//div[@id='recipe_small_steps']");
				method = method_Element.text();
				System.out.println("Method =" + method);

				// Fetching nutrient vaues
				try {
					nutrientValue = doc.getElementById("rcpnutrients").text();

				} catch (Exception e) {
					System.out.println("Nutrient value not present:" + e);
					continue;
				}

				// iterate through tags webElement to capture recipe category and write in excel
				// Fetching the tags
				Element tagstextElement = doc.getElementById("recipe_tags");
				String tagsText = tagstextElement.text();
				System.out.println("Tags =" + tagsText);
				for (int j = 0; j < acceptedRecipeCatList.size(); j++) {

					String recipeCategory = acceptedRecipeCatList.get(j);
					if (tagsText.contains(recipeCategory)) {
						System.out.println("Recipe category Present---------" + recipeCategory);
						recipeCatListPresent.add(recipeCategory);
					}
					xlUtil.setCellData("Hypothyroidism_Allergy", PageNumber, 2, recipeCatListPresent.toString());
				}
				recipeCatListPresent.clear();
				// iterate through tags webElement to capture food category and write in excel
				String FinalFoodCategory = "";
				for (int j = 0; j < acceptedFoodCatList.size(); j++) {

					String foodCategory = acceptedFoodCatList.get(j);
					if (tagsText.contains(foodCategory)) {
						System.out.println("Food category Present---------" + foodCategory);
						foodCatListPresent.add(foodCategory);
						FinalFoodCategory = foodCategory.concat(" ").concat(FinalFoodCategory);
					}
					xlUtil.setCellData("Hypothyroidism_Allergy", PageNumber, 3, FinalFoodCategory);
				}
				// iterate through tags webElement to capture morbid condition and write in
				// excel

				// check for PCOS ingredient eliminate list in the recipes
				for (int j = 0; j < targetedMorbidCondList.size(); j++) {

					String tarMorbidCondition = targetedMorbidCondList.get(j);
					if (tagsText.contains(tarMorbidCondition)) {
						System.out.println("Morbid condition Present---------" + tarMorbidCondition);
						morbidCondListPresent.add(tarMorbidCondition);
						xlUtil.setCellData("Hypothyroidism_Allergy", PageNumber, 9, morbidCondListPresent.toString());
					}
				}
				morbidCondListPresent.clear();

				// Printing all data in excel sheet
				xlUtil.setCellData("Hypothyroidism_Allergy", PageNumber, 10, URLString);
				xlUtil.setCellData("Hypothyroidism_Allergy", PageNumber, 0, recipe_id);
				xlUtil.setCellData("Hypothyroidism_Allergy", PageNumber, 1, recipe_name);
				xlUtil.setCellData("Hypothyroidism_Allergy", PageNumber, 4, ingredients);
				xlUtil.setCellData("Hypothyroidism_Allergy", PageNumber, 5, prep_Time);
				xlUtil.setCellData("Hypothyroidism_Allergy", PageNumber, 6, cook_Time);
				xlUtil.setCellData("Hypothyroidism_Allergy", PageNumber, 7, method);
				xlUtil.setCellData("Hypothyroidism_Allergy", PageNumber, 8, nutrientValue);
				// xlUtil.setCellData("Hypothyroidism_Allergy", PageNumber, 3,
				// FinalFoodCategory);

				counter++;
				System.out.println("Counter=" + counter);
				PageNumber++;
				System.out.println("*****************************************************************");
				driver.navigate().to(
						"https://www.tarladalal.com/recipes-for-hypothyroidism-veg-diet-indian-recipes-849?pageindex="
								+ page);

			}
		}
		System.out.println("Done!!!!");

		// Stopping the timer
		timer_end = Instant.now();
		System.out.println("Time taken to scrape this webpage = " + Duration.between(timer_start, timer_end).toSeconds()
				+ "Seconds");

	}
}