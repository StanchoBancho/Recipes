package gate;

import java.io.IOException;
import java.util.ArrayList;

import gate.util.GateException;
import gate.util.Pair;
;

public class GateManager {
	private StandAloneAnnie annie;
	private ArrayList<Pair> annotationsToDocuments;
	
	public GateManager(){
		annie = new StandAloneAnnie(); 
	    try {
			annie.initAnnie();
			annotationsToDocuments = annie.getAllRecipesWihtIngredients();
		} catch (GateException | IOException e) {
			e.printStackTrace();
		}
	}
	
	public String processRecipe(String recipe){
		String result = null;
		if(annie != null){
	    	try {
				result = annie.processText(recipe);
			} catch (GateException | IOException e) {
				e.printStackTrace();
			}
	    	if(result != null){
	    		return result;
	    	}
	    }
		return null;
	}
	
	public ArrayList<String> getRecipesPathsThatContains(ArrayList<String> ingredients)
	{
		ArrayList<String> result = new ArrayList<String>();
		for(Pair p : annotationsToDocuments){
			for(String desiredIngredient : ingredients){
				if(p.first.equals(desiredIngredient)){
					if(!result.contains((String)p.second)){
						result.add((String)p.second);
						break;
					}
				}
			}
		}
		return result;
	}
}
