package gate;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import gate.util.GateException;


public class GateManager {
	private StandAloneAnnie annie;
	private ArrayList<Document> documents;

	public GateManager() {
		annie = new StandAloneAnnie();
		try {
			annie.initAnnie();
			documents = annie.getDocumentsWithIngredients();
		} catch (GateException | IOException e) {
			e.printStackTrace();
		}
	}

	public String processRecipe(String recipe) {
		String result = null;
		if (annie != null) {
			try {
				result = annie.processText(recipe);
			} catch (GateException | IOException e) {
				e.printStackTrace();
			}
			if (result != null) {
				return result;
			}
		}
		return null;
	}

	public void updateSearchCorpusWithNewRecipe(URL newRecipePath) {
		Document newAnnotationsToDocumentsPaths = annie.getDocumentWithIngredientForNewRecipe(newRecipePath);
		if (newAnnotationsToDocumentsPaths != null && !documents.contains(newAnnotationsToDocumentsPaths)) {
			documents.add(newAnnotationsToDocumentsPaths);
		}
	}

	public ArrayList<Document> getDocumentsThatContainsIngredients(ArrayList<String> ingredients) {
		ArrayList<Document> result = new ArrayList<Document>();
		for (Document p : documents) {
			for (String desiredIngredient : ingredients) {
				AnnotationSet documentAnnotations = p.getAnnotations();
				for (Annotation a : documentAnnotations) {
					FeatureMap f = a.getFeatures();
					String ingredientName = (String) f.get("ingredientName");
					if (ingredientName!= null && ingredientName.equals(desiredIngredient)) {
						// pair (annotation, path)
						if (!result.contains(p)) {
							result.add(p);
							break;
						}
					}
				}
			}
		}
		return result;
	}

}
