package gate;

import java.io.IOException;
import gate.util.GateException;
;

public class GateManager {
	private StandAloneAnnie annie;
	
	
	public GateManager(){
		annie = new StandAloneAnnie(); 
	    try {
			annie.initAnnie();
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
}
