package com.TestData;

import java.util.ArrayList;
import java.util.List;
import com.driverFactory.InitClass;

public class categoryList extends InitClass {


	public static List<String> acceptedFoodCatList = new ArrayList<String>();
	public static List<String> acceptedRecipeCatList = new ArrayList<String>();
	public static List<String> targetMorbidConditionList = new ArrayList<String>();

	public static String RecipeCategory = "Dinner";
	public static String morbidCondition = "Diabetic";


	public static List<String> acceptedFoodCategory() {

		acceptedFoodCatList.add("Veg");
		acceptedFoodCatList.add("Vegetarian");
		acceptedFoodCatList.add("Jain");
		acceptedFoodCatList.add("Eggitarian");
		acceptedFoodCatList.add("Non-veg");

		return acceptedFoodCatList;
	}

	public static List<String> acceptedRecipeCategory() {

		acceptedRecipeCatList.add("Dinner");
		acceptedRecipeCatList.add("Lunch");
		acceptedRecipeCatList.add("Snacks");
		acceptedRecipeCatList.add("Breakfast");

		return acceptedRecipeCatList;
	}


	public static List<String> targetMorbidCondition() {

		targetMorbidConditionList.add("diabetic Recipe");
		targetMorbidConditionList.add("Diabetic");
		targetMorbidConditionList.add("Hyperthyroidism");
		targetMorbidConditionList.add("High blood pressure");
		targetMorbidConditionList.add("PCOS");

		return targetMorbidConditionList;
	}

}


