package com.MorbidConditions;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Test;

import com.Utilities.ExcelReader;
import com.Utilities.PropertyReader;
import com.driverFactory.InitClass;

import com.TestData.PCOS_IngredientsCheckList;
import com.TestData.categoryList;

public class PCOSEliminate extends InitClass {
	@Test
	public void PCOSRecipe() throws InterruptedException, IOException {
	
	// declaring variables

	String prep_Time = null;
	String ingredients = null;
	String cook_Time = null;
	String method = null;
	String nutrientValue="";
	Instant timer_end;
	Instant timer_start = null;
	String PCOS_Morbidity = "PCOS";
	//starting the timer
	timer_start = Instant.now();
	
	

				// list containing filters for accepted FoodCategory/Recipe category and Morbid Conditions
						
						List<String> acceptedFoodCatList = categoryList.acceptedFoodCategory();
						List<String> acceptedRecipeCatList = categoryList.acceptedRecipeCategory();
						List<String> targetedMorbidCondList = categoryList.targetMorbidCondition();
						
				
				
				//Navigating to PCOS recipes section
				WebElement Recipes = driver.findElement(By.xpath("//div[normalize-space()='RECIPES']"));
				Actions mouseHover = new Actions(driver);
				mouseHover.moveToElement(Recipes).build().perform();
				driver.findElement(By.xpath("//a[normalize-space()='Health']")).click();
				WebElement pcos = driver.findElement(By.id("ctl00_cntleftpanel_ttlhealthtree_tvTtlHealtht335"));
				mouseHover.moveToElement(pcos).build().perform();
				pcos.click();
				System.out.println("On PCOS recipes Section");
				
				//creating Excel
				String filePath = PropertyReader.getPropFromProperty("config", "excelFilePath")+"MorbidTestData.xlsx";
				ExcelReader xlUtil = new ExcelReader(filePath);
				xlUtil.createExcel("PCOS");
				
				// Pagination- navigating through all recipe pages
				int PageNumber=1;// counter to prevent overwriting of data when moving to next page
				List<WebElement> paginationList = driver.findElements(By.xpath("//*[@id='pagination']/a"));
				int pageSize = paginationList.size();
				System.out.println("Total Pages="+pageSize);
				
				for(int page = 1; page <= pageSize; page++ ) {	
					
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
					ArrayList<String> link = new ArrayList<>(24);

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

						// Fetching Recipe ID
						String recipe_id = URLString.substring(URLString.lastIndexOf("-")+1, URLString.lastIndexOf("r"));
						System.out.println("Recipe ID=" + recipe_id);
						

						// fetching recipe name
						Elements recipe_nameElement = doc.selectXpath("//span[@id='ctl00_cntrightpanel_lblRecipeName']");
						String recipe_name = recipe_nameElement.text();
						System.out.println("Recipe name = " + recipe_name);
						
						
						// Fetching ingredients list
						Elements ingredient_listElement = doc.selectXpath("//div[@id='rcpinglist']");
						ingredients = ingredient_listElement.text();
						String eliminationItems ="";
						boolean eliminateList = PCOS_IngredientsCheckList.eliminateIngredient(ingredients);

						System.out.println(eliminateList);
						if (eliminateList) {

							System.out.println("navigate back--> Eliminated Ingredient present--*****************************************************************");
							driver.navigate().to("https://www.tarladalal.com/recipes-for-pcos-1040?pageindex=" + page);
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
							System.out.println("Nutrient value not present:"+e);
							continue;
						}

						// iterate through tags webElement to capture recipe category and write in excel
						//Fetching the tags
						Element tagstextElement = doc.getElementById("recipe_tags");
						String tagsText = tagstextElement.text();
						System.out.println("Tags ="+tagsText);
						for (int j = 0; j < acceptedRecipeCatList.size(); j++) {

							String recipeCategory = acceptedRecipeCatList.get(j);
							if (tagsText.contains(recipeCategory)) {
								System.out.println("Recipe category Present---------" + recipeCategory);
								recipeCatListPresent.add(recipeCategory);
							}
							xlUtil.setCellData("PCOS", PageNumber, 2, recipeCatListPresent.toString());
						}
						recipeCatListPresent.clear();
						// iterate through tags webElement to capture food category and write in excel
						String FinalFoodCategory="";
						for (int j = 0; j < acceptedFoodCatList.size(); j++) {

							String foodCategory = acceptedFoodCatList.get(j);
							if (tagsText.contains(foodCategory)) {
								System.out.println("Food category Present---------" + foodCategory);
								foodCatListPresent.add(foodCategory);
								FinalFoodCategory=foodCategory.concat(" ").concat(FinalFoodCategory);
							}
							xlUtil.setCellData("PCOS", PageNumber, 3, FinalFoodCategory);
						}
						// iterate through tags webElement to capture morbid condition and write in excel
						
						//check for PCOS ingredient eliminate list in the recipes
						for (int j = 0; j < targetedMorbidCondList.size(); j++) {

							String tarMorbidCondition = targetedMorbidCondList.get(j);
							if (tagsText.contains(tarMorbidCondition)) {
								System.out.println("Morbid condition Present---------" + tarMorbidCondition);
								morbidCondListPresent.add(tarMorbidCondition);
								xlUtil.setCellData("PCOS", PageNumber, 9,
										morbidCondListPresent.toString());
							}
						}
						morbidCondListPresent.clear();
						
						//Printing all data in excel sheet
						xlUtil.setCellData("PCOS", PageNumber, 10, URLString);
						xlUtil.setCellData("PCOS", PageNumber, 0, recipe_id);
						xlUtil.setCellData("PCOS", PageNumber, 1, recipe_name);
						xlUtil.setCellData("PCOS", PageNumber, 4, ingredients);
						xlUtil.setCellData("PCOS", PageNumber, 5, prep_Time);
						xlUtil.setCellData("PCOS", PageNumber, 6, cook_Time);
						xlUtil.setCellData("PCOS", PageNumber, 7, method);
						//xlUtil.setCellData("PCOS", PageNumber, 3, FinalFoodCategory);
						
						counter++;
						System.out.println("Counter="+counter);
						PageNumber++;
						System.out.println("*****************************************************************");
						driver.navigate().to("https://www.tarladalal.com/recipes-for-pcos-1040?pageindex=" + page);
					}
					}
					System.out.println("Done!!!!");
					
					//Stopping the timer
					timer_end = Instant.now();
					System.out.println("Time taken to scrape this webpage = " + Duration.between(timer_start, timer_end).toSeconds()+ "Seconds");
					

}
}






