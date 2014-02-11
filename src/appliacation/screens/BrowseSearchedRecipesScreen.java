package appliacation.screens;

import interfaces.RecipeSeekerListener;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;

import gate.Annotation;
import gate.GateManager;
import gate.util.Pair;
public class BrowseSearchedRecipesScreen extends BrowseRecipesScreen implements RecipeSeekerListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public ArrayList<String> recipesPaths;
	public ArrayList<Pair> recipesPathsAndAnnotations;

	public GateManager gateManager;

	/**
	 * Create the frame.
	 */
	public BrowseSearchedRecipesScreen(ArrayList<String> recipesPaths) {
		super();
		this.recipesPaths = recipesPaths;
		textPane.setContentType("text/html");
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
		
		
		recipesPathsAndAnnotations = gateManager.getRecipesPathsThatContains(ingredients);
		recipesPaths = new ArrayList<String>();
		
		for (Pair p : recipesPathsAndAnnotations){
			recipesPaths.add((String)p.first);
		}
		
		setVisible(true);
		populateRecipeList();
	}
	
	protected void presentSelectedRecipe(int recipeIndex){
		if(0 <= recipeIndex  && recipeIndex < recipesListModel.capacity()){
			File folder = new File(System.getProperty("user.dir"), "recipes-list"); 
			String fileName = recipesListModel.elementAt(recipeIndex);
			if(fileName != null){
			File recipeFile = new File(folder, fileName);
			if(recipeFile != null){
				String content = null;
				try {
					content = readFile(recipeFile.toString(), StandardCharsets.UTF_8);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(content != null){
					//set html content
					
					ArrayList<Annotation> allAnnotationsForSelectedDocument = new ArrayList<Annotation>();
					for(Pair p: recipesPathsAndAnnotations){
						if(fileName.equals(p.first)){
							allAnnotationsForSelectedDocument.add((Annotation)p.second);
						}
					}
					//draw annotations to the text
					
					
					textPane.setText(content);
				}
			}
			}
		}
	}
}
