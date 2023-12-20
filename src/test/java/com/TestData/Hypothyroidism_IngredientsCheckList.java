package com.TestData;

import java.util.ArrayList;

public class Hypothyroidism_IngredientsCheckList {

	public static ArrayList<String> hypothyroidismEliminatedList = new ArrayList<String>();
	public static ArrayList<String> hypothyroidismAddList = new ArrayList<String>();
	public static ArrayList<String> allergyList = new ArrayList<String>();

	public static boolean eliminateIngredient(String recieve_Ingredient) {
		hypothyroidismEliminatedList.add("Tofu");
		hypothyroidismEliminatedList.add("Edamame");
		hypothyroidismEliminatedList.add("Tempeh");
		hypothyroidismEliminatedList.add("Cauliflower");
		hypothyroidismEliminatedList.add("Cabbage");
		hypothyroidismEliminatedList.add("Broccoli");
		hypothyroidismEliminatedList.add("Kale");
		hypothyroidismEliminatedList.add("Spinach");
		hypothyroidismEliminatedList.add("Sweet potatoes");
		hypothyroidismEliminatedList.add("Pine nuts");
		hypothyroidismEliminatedList.add("Peanuts");
		hypothyroidismEliminatedList.add("Peaches");
		hypothyroidismEliminatedList.add("Green tea");
		hypothyroidismEliminatedList.add("Coffee");
		hypothyroidismEliminatedList.add("Alcohol");
		hypothyroidismEliminatedList.add("Soy milk");
		hypothyroidismEliminatedList.add("White bread");
		hypothyroidismEliminatedList.add("Cakes");
		hypothyroidismEliminatedList.add("pastries");
		hypothyroidismEliminatedList.add("Fried food");
		hypothyroidismEliminatedList.add("Sugar");
		hypothyroidismEliminatedList.add("ham");
		hypothyroidismEliminatedList.add("bacon");
		hypothyroidismEliminatedList.add("salami");
		hypothyroidismEliminatedList.add("sausages");
		hypothyroidismEliminatedList.add("Frozen food");
		hypothyroidismEliminatedList.add("Sodas");
		hypothyroidismEliminatedList.add("Energy drinks with Caffeine");
		hypothyroidismEliminatedList.add("noodles");
		hypothyroidismEliminatedList.add("soups");
		hypothyroidismEliminatedList.add("salad dressings");
		hypothyroidismEliminatedList.add("sauces");
		hypothyroidismEliminatedList.add("Candies");

		boolean isIngrediantPresent = false;
		String ingredient = recieve_Ingredient.toLowerCase();

		for (String i : hypothyroidismEliminatedList) {
			if (ingredient.contains(i)) // p_Ingredient
			{
				System.out.println("Recipe skipped as " + i + " is present");
				isIngrediantPresent = true;
				break;
			}
		}

		return isIngrediantPresent;

	}
	
	public static String AddIngredient(String recieve_Ingredient) {
		hypothyroidismAddList.add("Saltwater fish");
		hypothyroidismAddList.add("oysters");
		hypothyroidismAddList.add("shellfish");
		hypothyroidismAddList.add("Eggs");
		hypothyroidismAddList.add("Dairy");
		hypothyroidismAddList.add("Nuts");
		hypothyroidismAddList.add("Chicken");
		hypothyroidismAddList.add("Pumpkinseeds");
		hypothyroidismAddList.add("Seaweed");
		hypothyroidismAddList.add("Iodized salt");
		hypothyroidismAddList.add("Brazil nuts");
		hypothyroidismAddList.add("Blue berries");
		hypothyroidismAddList.add("Low-fat Yogurt");
		hypothyroidismAddList.add("Brown rice");
		hypothyroidismAddList.add("quinoa");
		hypothyroidismAddList.add("Mushroom");
			

		String add_Ingredient = recieve_Ingredient.toLowerCase();
		System.out.println("Ingredient to be added in the list" + add_Ingredient);
		String isAddIngrediant = "";

		for (String toAddIngredient : hypothyroidismAddList)

		{
			if (add_Ingredient.contains(toAddIngredient)) // p_Ingredient
			{
				System.out.println(
						"Added Ingredient::" + add_Ingredient + "because of " + toAddIngredient + "recepid" + "");
				isAddIngrediant = toAddIngredient;
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

}
