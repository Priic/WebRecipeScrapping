package com.TestData;

import java.util.ArrayList;
import com.driverFactory.InitClass;

public class Diabetes_IngredientsCheckList extends InitClass {

	public static ArrayList<String> diabetesEliminatedList = new ArrayList<String>();
	public static ArrayList<String> diabeticToAddList = new ArrayList<String>();
	public static ArrayList<String> allergyList = new ArrayList<String>();

	public static void main(String[] args) {
		
		toAddIngredients("milk");
	}
	public static boolean eliminateIngredient(String recieve_Ingredient) {
		diabetesEliminatedList.add("cream of rice");
		diabetesEliminatedList.add("rice flour");
		diabetesEliminatedList.add("rice rava");
		diabetesEliminatedList.add("corn");
		diabetesEliminatedList.add("sugar");
		diabetesEliminatedList.add("white rice");
		diabetesEliminatedList.add("white bread");
		diabetesEliminatedList.add("pasta");
		diabetesEliminatedList.add("soda");
		diabetesEliminatedList.add("flavoured water");
		diabetesEliminatedList.add("gatorade");
		diabetesEliminatedList.add("apple juice");
		diabetesEliminatedList.add("orange juice");
		diabetesEliminatedList.add("pomegranate juice");
		diabetesEliminatedList.add("flavoured curd");
		diabetesEliminatedList.add("flavoured yogurt");
		diabetesEliminatedList.add("cereals");
		diabetesEliminatedList.add("corn flakes");
		diabetesEliminatedList.add("puffed rice");
		diabetesEliminatedList.add("bran flakes");
		diabetesEliminatedList.add("instant oatmeal");
		diabetesEliminatedList.add("honey");
		diabetesEliminatedList.add("maple syrup");
		diabetesEliminatedList.add("jaggery");
		diabetesEliminatedList.add("sweets");
		diabetesEliminatedList.add("candies");
		diabetesEliminatedList.add("chocolates");
		diabetesEliminatedList.add("refined");
		diabetesEliminatedList.add("all purpose flour");
		diabetesEliminatedList.add("alcoholic beverages");
		diabetesEliminatedList.add("bacon");
		diabetesEliminatedList.add("sausages");
		diabetesEliminatedList.add("hot dog");
		diabetesEliminatedList.add("deli meats");
		diabetesEliminatedList.add("chicken nuggets");
		diabetesEliminatedList.add("chicken patties");
		diabetesEliminatedList.add("bacon");
		diabetesEliminatedList.add("jams");
		diabetesEliminatedList.add("jelly");
		diabetesEliminatedList.add("mango pickle");
		diabetesEliminatedList.add("pickles");
		diabetesEliminatedList.add("pickle cucumber");
		diabetesEliminatedList.add("pickle tomatoes");
		diabetesEliminatedList.add("canned pineapple");
		diabetesEliminatedList.add("canned peaches");
		diabetesEliminatedList.add("canned mangos ");
		diabetesEliminatedList.add("canned pear");
		diabetesEliminatedList.add("canned mixed fruit");
		diabetesEliminatedList.add("canned mandarine");
		diabetesEliminatedList.add("canned oranges");
		diabetesEliminatedList.add("canned cherries");
		diabetesEliminatedList.add("chips");
		diabetesEliminatedList.add("mayonnaise");
		diabetesEliminatedList.add("palmolein oil");
		diabetesEliminatedList.add("powdered milk");
		diabetesEliminatedList.add("dried beans");
		diabetesEliminatedList.add("dried peas");
		diabetesEliminatedList.add("dried corn");
		diabetesEliminatedList.add("doughnuts");
		diabetesEliminatedList.add("cakes");
		diabetesEliminatedList.add("pastries");
		diabetesEliminatedList.add("cookies");
		diabetesEliminatedList.add("croissants");
		diabetesEliminatedList.add("sweetened tea");
		diabetesEliminatedList.add("coffee");
		diabetesEliminatedList.add("packaged snacks");
		diabetesEliminatedList.add("soft drinks");
		diabetesEliminatedList.add("banana");
		diabetesEliminatedList.add("melon");
		diabetesEliminatedList.add("dairy milk");
		diabetesEliminatedList.add("butter");
		diabetesEliminatedList.add("cheese");

		boolean isIngrediantPresent = false;
		String ingredient = recieve_Ingredient.toLowerCase();
	//	System.out.println("Ingredient recieved: " + recieve_Ingredient);
		for (String i : diabetesEliminatedList) {
			if (ingredient.contains(i)) // p_Ingredient
			{
				System.out.println("Recipe skipped as " + i + " is present");
				isIngrediantPresent = true;
				break;
			}
		}
		return isIngrediantPresent;
	}

	public static String toAddIngredients(String recieve_Ingredient) {

		diabeticToAddList.add("broccoli");
		diabeticToAddList.add("pumpkin");
		diabeticToAddList.add("pumpkin seeds");
		diabeticToAddList.add("chia seeds ");
		diabeticToAddList.add("flaxseeds");
		diabeticToAddList.add("apples");
		diabeticToAddList.add("nuts");
		diabeticToAddList.add("lady finger");
		diabeticToAddList.add("okra");
		diabeticToAddList.add("beans");
		diabeticToAddList.add("raspberries");
		diabeticToAddList.add("strawberries");
		diabeticToAddList.add("blueberries");
		diabeticToAddList.add("blackberries");
		diabeticToAddList.add("egg");
		diabeticToAddList.add("yogurt");
		diabeticToAddList.add("bitter gaurd");
		diabeticToAddList.add("rolled oats");
		diabeticToAddList.add("steel cut oats");
		diabeticToAddList.add("chicken");
		diabeticToAddList.add("fish");
		diabeticToAddList.add("quinoa");
		diabeticToAddList.add("mushroom");

		String add_Ingredient = recieve_Ingredient.toLowerCase();
		String isAddIngrediant = "";

		for (String toAddIngredient : diabeticToAddList)

		{
			if (add_Ingredient.contains(toAddIngredient)) // p_Ingredient
			{
				isAddIngrediant = toAddIngredient;
				System.out.println("To add ingredient present is: " +isAddIngrediant);
				break;
			}
		}
		return isAddIngrediant;
	}

	public static boolean checkAllergyIngredients(String recieve_Ingredient)

	{
		allergyList.add("milk");
		allergyList.add("soy");
		allergyList.add("egg");
		allergyList.add("sesame");
		allergyList.add("peanuts");
		allergyList.add("walnuts");
		allergyList.add("almonds");
		allergyList.add("hazelnut");
		allergyList.add("pecan");
		allergyList.add("cashew");
		allergyList.add("pistachio");
		allergyList.add("Shell fish");
		allergyList.add("Seafood");

		boolean isIngredientAllergy = false;
		String allergy_Ingredient = recieve_Ingredient.toLowerCase();
		System.out.println("Recieved ingredients for allery check : " + allergy_Ingredient);
		for (String al : allergyList) {
			if (allergy_Ingredient.contains(al)) // p_Ingredient
			{
				System.out.println("Recipe skipped as an allergy ingredients: " + al+ " is present" );
				isIngredientAllergy = true;
				break;
			}
		}
		return isIngredientAllergy;
	}

}
