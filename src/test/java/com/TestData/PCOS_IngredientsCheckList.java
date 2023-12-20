package com.TestData;

import java.util.ArrayList;

import com.driverFactory.InitClass;

public class PCOS_IngredientsCheckList extends InitClass  {
	public static ArrayList<String> PCOSEliminateList = new ArrayList<String>();
	public static ArrayList<String> PCOSToAddList = new ArrayList<String>();
	public static ArrayList<String> AllergyList = new ArrayList<String>();
			
	public static boolean eliminateIngredient(String recieve_Ingredient) {
		
		PCOSEliminateList.add("Cakes");
		PCOSEliminateList.add("Pastries");
		PCOSEliminateList.add("White Bread");
		PCOSEliminateList.add("Fried Food");
		PCOSEliminateList.add("Pizza");
		PCOSEliminateList.add("Burger");
		PCOSEliminateList.add("Cakes");
		PCOSEliminateList.add("Carbonated beverages");
		PCOSEliminateList.add("Sweets");
		PCOSEliminateList.add("Ice creams");
		PCOSEliminateList.add("soda");
		PCOSEliminateList.add("Red Meat");
		PCOSEliminateList.add("Processed Meat");
		PCOSEliminateList.add("Dairy");
		PCOSEliminateList.add("Soy Products");
		PCOSEliminateList.add("Gluten");
		PCOSEliminateList.add("Pasta");
		PCOSEliminateList.add("White Rice");
		PCOSEliminateList.add("Doughnuts");
		PCOSEliminateList.add("Fries");
		PCOSEliminateList.add("Coffee");
		PCOSEliminateList.add("Vegetable oil");
		PCOSEliminateList.add("Soyabean Oil");
		PCOSEliminateList.add("Canola Oil");
		PCOSEliminateList.add("Rapeseed oil");
		PCOSEliminateList.add("Sunflower oil");
		PCOSEliminateList.add("Safflower oil");
		
		boolean isIngrediantPresent = false;
		String ingredient = recieve_Ingredient.toLowerCase();

		for (String i : PCOSEliminateList) {
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
		
		PCOSToAddList.add("High Fibre fruits");
		PCOSToAddList.add("Vegetables");
		
//		String add_Ingredient = recieve_Ingredient.toLowerCase();
//		System.out.println("Ingredient to be added in the list" + add_Ingredient);
//		String isAddIngrediant = "";
//
//		for (String toAddIngredient : PCOSToAddList)
//
//		{
//			if (add_Ingredient.contains(toAddIngredient)) // p_Ingredient
//			{
//				System.out.println("Added Ingredient::" + add_Ingredient + "because of " + toAddIngredient + "recepid" + "");
//				isAddIngrediant = toAddIngredient;
//				break;
//			}
//		}
//		return isAddIngrediant;	
//	}
		
		String add_Ingredient = recieve_Ingredient.toLowerCase();
		System.out.println("Ingredient to be added in the list" + add_Ingredient);
		String isAddIngrediant = "";

		for (String toAddIngredient : PCOSToAddList)

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
		AllergyList.add("milk");
		AllergyList.add("soy");
		AllergyList.add("egg");
		AllergyList.add("sesame");
		AllergyList.add("peanuts");
		AllergyList.add("walnuts");
		AllergyList.add("almonds");
		AllergyList.add("hazelnut");
		AllergyList.add("pecan");
		AllergyList.add("cashew");
		AllergyList.add("pistachio");
		AllergyList.add("Shell fish");
		AllergyList.add("Seafood");

		boolean isIngredientAllergy = false;
		String allergy_Ingredient = recieve_Ingredient.toLowerCase();
		System.out.println("Recieved ingredients for allery check" + allergy_Ingredient);
		for (String al : AllergyList) {
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
