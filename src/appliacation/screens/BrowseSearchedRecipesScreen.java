package appliacation.screens;

import interfaces.RecipeSeekerListener;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import org.apache.commons.io.FilenameUtils;

import gate.Annotation;
import gate.AnnotationSet;
import gate.Document;
import gate.FeatureMap;
import gate.GateConstants;
import gate.GateManager;
import gate.StandAloneAnnie.SortedAnnotationList;
import gate.corpora.RepositioningInfo;
import gate.util.InvalidOffsetException;

public class BrowseSearchedRecipesScreen extends BrowseRecipesScreen implements RecipeSeekerListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public ArrayList<String> recipesPaths;
	public ArrayList<Document> searchedDocuments;
	public ArrayList<String> ingredients;

	public GateManager gateManager;

	/**
	 * Create the frame.
	 */
	public BrowseSearchedRecipesScreen(ArrayList<String> recipesPaths) {
		super();
		this.recipesPaths = recipesPaths;
		textPane.setContentType("text/html");
	}

	public void populateRecipeList() {
		Collections.sort(recipesPaths);
		recipesListModel.removeAllElements();
		for (String string : recipesPaths) {
			recipesListModel.addElement(string);
		}
		list.updateUI();
	}

	@Override
	public void searchRecipesWithIngredients(ArrayList<String> ingredients) {

		searchedDocuments = gateManager.getDocumentsThatContainsIngredients(ingredients);
		recipesPaths = new ArrayList<String>();
		this.ingredients = ingredients;

		for (Document doc : searchedDocuments) {
			String decodedDocPath = FilenameUtils.getName(doc.getSourceUrl().toString());
			String docPath;
			try {
				docPath = URLDecoder.decode(decodedDocPath, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				docPath = "Error in geting doc path";
			}
			if (!recipesPaths.contains(docPath)) {
				recipesPaths.add(docPath);
			}
		}

		setVisible(true);
		populateRecipeList();
		if(recipesPaths.size() > 0){
			list.setSelectedIndex(0);
		}
	}

	protected void presentSelectedRecipe(int recipeIndex) {
		if (0 <= recipeIndex && recipeIndex < recipesListModel.capacity()) {
			String fileName = recipesPaths.get(recipeIndex);
			lblRecipenamelabel.setText(fileName);
			Document selectedDocument = searchedDocuments.get(recipeIndex);
			ArrayList<Annotation> allAnnotationsForSelectedDocument = new ArrayList<Annotation>();

			AnnotationSet documentAnnotations = selectedDocument.getAnnotations();
			for (Annotation annotation : documentAnnotations) {
				for (String desiredIngredient : ingredients) {
					FeatureMap f = annotation.getFeatures();
					String ingredientName = (String) f.get("ingredientName");
					if (ingredientName != null && ingredientName.equals(desiredIngredient)) {
						allAnnotationsForSelectedDocument.add(annotation);
					}
				}
			}

			// draw annotations to the text
			String htmlText = getHTMLfromTextWithAnnotations(selectedDocument, allAnnotationsForSelectedDocument);
			textPane.setText(htmlText);
		}
	}

	private String getHTMLfromTextWithAnnotations(Document document, ArrayList<Annotation> annotations) {

		long insertPositionEnd;
		long insertPositionStart;

		String startTagPart_1 = "<span GateID=\"";
		String startTagPart_2 = "\" title=\"";
		String startTagPart_3_color_red = "\" style=\"background:#33CCFF;\">";
		String endTag = "</span>";

		FeatureMap features = document.getFeatures();
		String originalContent = (String) features.get(GateConstants.ORIGINAL_DOCUMENT_CONTENT_FEATURE_NAME);
		RepositioningInfo info = (RepositioningInfo) features.get(GateConstants.DOCUMENT_REPOSITIONING_INFO_FEATURE_NAME);

		//sort annotations
		SortedAnnotationList sortedAnnotations = new SortedAnnotationList();
		for (Annotation currAnnot : annotations) {
			sortedAnnotations.addSortedExclusive(currAnnot);
		}
		
		if (originalContent != null && info != null) {
			StringBuffer editableContent = new StringBuffer(originalContent);
			
			for (int i = sortedAnnotations.size() - 1; i >= 0; --i) {
				Annotation currAnnot = sortedAnnotations.get(i);
				if (currAnnot.getType().equals("Ingredient")) {
					insertPositionStart = currAnnot.getStartNode().getOffset().longValue();
					insertPositionStart = info.getOriginalPos(insertPositionStart);
					insertPositionEnd = currAnnot.getEndNode().getOffset().longValue();
					insertPositionEnd = info.getOriginalPos(insertPositionEnd, true);
					if (insertPositionEnd != -1 && insertPositionStart != -1) {
						editableContent.insert((int) insertPositionEnd, endTag);
						editableContent.insert((int) insertPositionStart, startTagPart_3_color_red);
						editableContent.insert((int) insertPositionStart, currAnnot.getType());
						editableContent.insert((int) insertPositionStart, startTagPart_2);
						editableContent.insert((int) insertPositionStart, currAnnot.getId().toString());
						editableContent.insert((int) insertPositionStart, startTagPart_1);
					}
				} // if
			}
			editableContent.insert(0, "<font face=\"Lucida Grande\" size=\"3.5\">");
			editableContent.insert(0, "<html>");
			editableContent.append("</font>");
			editableContent.append("</html>");
			String result = editableContent.toString();
			result = result.replaceAll("(\r\n|\n)", "<br/>");
			return result;
		} else if (originalContent != null) {
			StringBuffer editableContent = new StringBuffer(originalContent);
			for (int i = sortedAnnotations.size() - 1; i >= 0; --i) {
				Annotation currAnnot = sortedAnnotations.get(i);
				if (currAnnot.getType().equals("Ingredient")) {
					insertPositionStart = currAnnot.getStartNode().getOffset().longValue();
					insertPositionEnd = currAnnot.getEndNode().getOffset().longValue();
					if (insertPositionEnd != -1 && insertPositionStart != -1) {
					
						 try {
							String text = document.getContent().getContent(currAnnot.getStartNode().getOffset(),
							        		 currAnnot.getEndNode().getOffset()).toString();
							System.out.println(text);
						} catch (InvalidOffsetException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						editableContent.insert((int) insertPositionEnd, endTag);
						editableContent.insert((int) insertPositionStart, startTagPart_3_color_red);
						editableContent.insert((int) insertPositionStart, currAnnot.getType());
						editableContent.insert((int) insertPositionStart, startTagPart_2);
						editableContent.insert((int) insertPositionStart, currAnnot.getId().toString());
						editableContent.insert((int) insertPositionStart, startTagPart_1);
					}
				} // if
			} // for
			editableContent.insert(0, "<font face=\"Lucida Grande\" size=\"3.5\">");
			editableContent.insert(0, "<html>");
			editableContent.append("</font>");
			editableContent.append("</html>");
			String result = editableContent.toString();
			result = result.replaceAll("(\r\n|\n)", "<br/>");
			return result;

		}

		return "";
	}
}
