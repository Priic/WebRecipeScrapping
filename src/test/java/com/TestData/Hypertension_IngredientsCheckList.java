package com.TestData;

import java.util.ArrayList;
import java.io.IOException;
import java.util.ArrayList;
import com.driverFactory.InitClass;

public class Hypertension_IngredientsCheckList {

	public static ArrayList<String> hypertensionEliminatedList = new ArrayList<String>();
	public static ArrayList<String> hypertensionAddList = new ArrayList<String>();
	public static ArrayList<String> allergyList = new ArrayList<String>();

	public static boolean eliminateIngredient(String recieve_Ingredient) {
		
		hypertensionEliminatedList.add("chips");
		hypertensionEliminatedList.add("pretzels");
		hypertensionEliminatedList.add("crackers");
		hypertensionEliminatedList.add("coffee");
		hypertensionEliminatedList.add("tea");
		hypertensionEliminatedList.add("soft drinks");
		hypertensionEliminatedList.add("alcohol");
		hypertensionEliminatedList.add("frozen food");
		hypertensionEliminatedList.add("soda");
		hypertensionEliminatedList.add("meat");
		hypertensionEliminatedList.add("bacon");
		hypertensionEliminatedList.add("ham");
		hypertensionEliminatedList.add("pickles");
		hypertensionEliminatedList.add("pomegranate juice");
		hypertensionEliminatedList.add("processed food");
		hypertensionEliminatedList.add("canned food");
		hypertensionEliminatedList.add("fried food");
		hypertensionEliminatedList.add("sauces");
		hypertensionEliminatedList.add("mayonnaise");
		hypertensionEliminatedList.add("bacon");
		hypertensionEliminatedList.add("sausages");
		hypertensionEliminatedList.add("deli meats");
		hypertensionEliminatedList.add("white rice");
		hypertensionEliminatedList.add("white bread");
		
		
		boolean isIngrediantPresent = false;
		String ingredient = recieve_Ingredient.toLowerCase();

		for (String i : hypertensionEliminatedList) {
			if (ingredient.contains(i)) // p_Ingredient
			{
				System.out.println("Recipe skipped as " + i + " is present");
				isIngrediantPresent = true;
				break;
			}
		}

		return isIngrediantPresent;

	}
	
	public static String toAddIngredient (String recieve_Ingredient) {
		
		hypertensionAddList.add("Beetroot");
		hypertensionAddList.add("blueberries");
		hypertensionAddList.add("strawberries");
		hypertensionAddList.add("Bananas");	
		hypertensionAddList.add("Avocado");
		hypertensionAddList.add("Tomato");
		hypertensionAddList.add("Sweet potato");
		hypertensionAddList.add("Mushroom");
		hypertensionAddList.add("Celery");
		hypertensionAddList.add("Kiwi");
		hypertensionAddList.add("Dark chocolate ");
		hypertensionAddList.add("Watermelon");
		hypertensionAddList.add("Spinach");
		hypertensionAddList.add("cabbage");
		hypertensionAddList.add("romaine lettuce");
		hypertensionAddList.add("mustard greens");
		hypertensionAddList.add("broccoli");
		hypertensionAddList.add("argula");
		hypertensionAddList.add("fennel");
		hypertensionAddList.add("kale");
		hypertensionAddList.add("Garlic");
		hypertensionAddList.add("Pomegranate");
		hypertensionAddList.add("Cinnamon");
		hypertensionAddList.add("Pistachios");
		hypertensionAddList.add("Chia seeds");
		hypertensionAddList.add("Yogurt");
		hypertensionAddList.add("unsalted nuts");
		hypertensionAddList.add("fish");
		hypertensionAddList.add("Low-fat yogurt");
		hypertensionAddList.add("quinoa");
			

		String add_Ingredient = recieve_Ingredient.toLowerCase();
		//System.out.println("Ingredient to be added in the list" + add_Ingredient);
		String isAddIngrediant = "";

		for (String toAddIngredient : hypertensionAddList)

		{
			if (add_Ingredient.contains(toAddIngredient)) // p_Ingredient
			{
				isAddIngrediant = toAddIngredient;
				System.out.println(
						"Added Ingredient::" + add_Ingredient + "because of " + toAddIngredient + "recepid" + "");
				
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
		System.out.println("Recieved ingredients for allery check" + allergy_Ingredient);
		for (String al : allergyList) {
			if (allergy_Ingredient.contains(al)) // p_Ingredient
			{
				System.out.println("Recipe skipped as an allergy ingredients: " + al + "is present");
				isIngredientAllergy = true;
				break;
			}
		}
		return isIngredientAllergy;
	}

	public static String toAddIngredients(String ingredients) {
		// TODO Auto-generated method stub
		return null;
	}

}