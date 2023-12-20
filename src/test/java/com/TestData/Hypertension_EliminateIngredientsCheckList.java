package com.TestData;

import java.io.IOException;
import java.util.ArrayList;
import com.driverFactory.InitClass;

public class Hypertension_EliminateIngredientsCheckList extends InitClass {

	public static ArrayList<String> hypertensionEliminatedList = new ArrayList<String>();
	

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

}
