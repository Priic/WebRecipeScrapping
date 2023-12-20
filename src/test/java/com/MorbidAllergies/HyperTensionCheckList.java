package com.MorbidAllergies;


import java.io.IOException;
import java.util.ArrayList;

import com.Utilities.ExcelReader;
import com.driverFactory.InitClass;

public class HyperTensionCheckList extends InitClass{
	
	  public static ArrayList<String> eliminatedList = new ArrayList<String>();
	  
	static boolean checkIngrediant(String i_Ingredient) 
	{
		eliminatedList.add("salt");
		eliminatedList.add("chips");
		eliminatedList.add("pretzel");
		eliminatedList.add("crackers");
		eliminatedList.add("coffee");
		eliminatedList.add("tea");
		eliminatedList.add("caffeine");
		eliminatedList.add("alcohol");
		eliminatedList.add("soft Drink");
		eliminatedList.add("frozen food");
		eliminatedList.add("pickles");
		eliminatedList.add("processed food");
		eliminatedList.add("canned food");

		String p_Ingredient = i_Ingredient.toLowerCase();
		System.out.println("igredeintes coming" + p_Ingredient);

		boolean isIngrediant = true;
		for (String v : eliminatedList) {
			if (p_Ingredient.contains(v)) // p_Ingredient
			{
				System.out.println("Eliminated::" + p_Ingredient + "because of " + v + "recepid" + "");
				isIngrediant = false;
				break;
			}
		}

		return isIngrediant;

	}
	
	public static ArrayList<String> addOnList=new  ArrayList<String>();
	
	 static boolean checkAddOns(String b_Ingredient) 
	
	{
		addOnList.add("banana");
		addOnList.add("choclate");
		addOnList.add("cinnamon");
		addOnList.add("yogurt");
		addOnList.add("nuts");
		addOnList.add("pista");
		addOnList.add("beetroot");
		addOnList.add("watermelon");
		addOnList.add("kiwi");
		addOnList.add("garlic");
		
		
		
		
		boolean isIngredientAdds=true;
		//String isIngrediant="";
		String a_Ingredient = b_Ingredient.toLowerCase();
		System.out.println("igredeintes coming" + a_Ingredient);

		
		for (String adl : addOnList) 
		{
			if (a_Ingredient.contains(adl)) // p_Ingredient
			{
				System.out.println("Add ons" + a_Ingredient + "because of " + adl + "recepid" + "");
				
			 return isIngredientAdds;
			}
		}
		return false;
		

		

	}
	 
	 public static ArrayList<String> allergyList=new  ArrayList<String>();
		
	 static boolean allergy(String allg_Ingredient) 
	
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
		allergyList.add("pistachio");
		
		
		
		boolean isIngredientAllergy=true;
		//String isIngrediant="";
		String allergy_Ingredient = allg_Ingredient.toLowerCase();
		System.out.println("igredeintes coming" + allergy_Ingredient);

		
		for (String al: allergyList) 
		{
			if (allergy_Ingredient.contains(al)) // p_Ingredient
			{
				System.out.println("Allergy" + allg_Ingredient + "because of " + al + "recepid" + "");
				
				isIngredientAllergy=false;
			}
		}
		return isIngredientAllergy;
		
	}
}
	
	


