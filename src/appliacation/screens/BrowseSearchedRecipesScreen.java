package appliacation.screens;

import interfaces.RecipeSeekerListener;

import java.util.ArrayList;
import java.util.Collections;

import gate.GateManager;
public class BrowseSearchedRecipesScreen extends BrowseRecipesScreen implements RecipeSeekerListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public ArrayList<String> recipesPaths;
	public GateManager gateManager;

	/**
	 * Create the frame.
	 */
	public BrowseSearchedRecipesScreen(ArrayList<String> recipesPaths) {
		super();
		this.recipesPaths = recipesPaths;
	}

	public void populateRecipeList(){
	
		Collections.sort(recipesPaths);
		recipesListModel.removeAllElements();
		for (String string : recipesPaths) {
			recipesListModel.addElement(string);
		}
		list.updateUI();
	}

	@Override
	public void searchRecipesWithIngredients(ArrayList<String> ingredients) {
		recipesPaths = gateManager.getRecipesPathsThatContains(ingredients);
		setVisible(true);
		populateRecipeList();
	}
}
