package gate;

/*
 *  StandAloneAnnie.java
 *
 *
 * Copyright (c) 2000-2001, The University of Sheffield.
 *
 * This file is part of GATE (see http://gate.ac.uk/), and is free
 * software, licenced under the GNU Library General Public License,
 * Version 2, June1991.
 *
 * A copy of this licence is included in the distribution in the file
 * licence.html, and is also available at http://gate.ac.uk/gate/licence.html.
 *
 *  hamish, 29/1/2002
 *
 *  $Id: StandAloneAnnie.java,v 1.6 2006/01/09 16:43:22 ian Exp $
 */

import java.util.*;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;

import org.apache.commons.io.FilenameUtils;

import gate.util.*;
import gate.util.persistence.PersistenceManager;
import gate.corpora.RepositioningInfo;
import gate.creole.ResourceInstantiationException;

/**
 * This class illustrates how to use ANNIE as a sausage machine in another
 * application - put ingredients in one end (URLs pointing to documents) and get
 * sausages (e.g. Named Entities) out the other end.
 * <P>
 * <B>NOTE:</B><BR>
 * For simplicity's sake, we don't do any exception handling.
 */
public class StandAloneAnnie {

	/** The Corpus Pipeline application to contain ANNIE */
	private CorpusController annieController;

	/**
	 * Initialise the ANNIE system. This creates a "corpus pipeline" application
	 * that can be used to run sets of documents through the extraction system.
	 */
	public void initAnnie() throws GateException, IOException {
		Out.prln("Initialising ANNIE...");

		try {
			Gate.init();
			Gate.initConfigData();
		} catch (GateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// load the ANNIE application from the saved state
		File annieGapp = new File(System.getProperty("user.dir"), "gate-state.xgapp");
		annieController = (CorpusController) PersistenceManager.loadObjectFromFile(annieGapp);
		
		Out.prln("...ANNIE loaded");
	} // initAnnie()

	/** Tell ANNIE's controller about the corpus you want to run on */
	public void setCorpus(Corpus corpus) {
		annieController.setCorpus(corpus);
	} // setCorpus

	/** Run ANNIE */
	public void execute() throws GateException {
		Out.prln("Running ANNIE...");
		annieController.execute();
		Out.prln("...ANNIE complete");
	} // execute()

	private ArrayList<Document> getIngredientsAnnotationToTextFileFromCorpus(Corpus corpus) throws GateException, UnsupportedEncodingException{
		setCorpus(corpus);
		execute();
		ArrayList<Document> result = new ArrayList<Document>();
	    Iterator<Document> iter = corpus.iterator();
	  
	    while(iter.hasNext()) {
	        Document doc = (Document) iter.next();
	        AnnotationSet defaultAnnotSet = doc.getAnnotations();
	        Set<String> annotTypesRequired = new HashSet<String>();
	        annotTypesRequired.add("Ingredient");
	        Set<Annotation> annotations = new HashSet<Annotation>(defaultAnnotSet.get(annotTypesRequired));	        
	        if(annotations.size() > 0){
	        	result.add(doc);
	        }
	    }
		return result;
	}
	
	public Document getDocumentWithIngredientForNewRecipe(URL recipePath) {
		Corpus corpus;
		try {
			corpus = Factory.newCorpus("Corpus with all existing recipes");
		} catch (ResourceInstantiationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return null;
		}
		 
	      FeatureMap params = Factory.newFeatureMap();
	      params.put("sourceUrl", recipePath);
	      params.put("preserveOriginalContent", new Boolean(true));
	      params.put("collectRepositioningInfo", new Boolean(true));
	      Out.prln("Creating doc for " + recipePath);
	      Document doc;
		try {
			doc = (Document)Factory.createResource("gate.corpora.DocumentImpl", params);
		} catch (ResourceInstantiationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return null;
		}
	    corpus.add(doc);
	     
		
	   Document result = null;
		try {
			ArrayList<Document> documents = getIngredientsAnnotationToTextFileFromCorpus(corpus); 
			if(documents.size() > 0){
				result = documents.get(0);	
			}
		} catch (UnsupportedEncodingException | GateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = null;
		}
		return result;
	}
	
	public ArrayList<Document> getDocumentsWithIngredients()
	{
		//add all recipes we have to new corpus
		Corpus corpus;
		try {
			corpus = Factory.newCorpus("Corpus with all existing recipes");
		} catch (ResourceInstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	  
		File folder = new File(System.getProperty("user.dir"), "recipes-list"); 
		File[] listOfFiles = folder.listFiles();
		for(int i = 0; i < listOfFiles.length; i++) {
	      URL u;
		try {
			u = listOfFiles[i].toURI().toURL();
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			continue;
		}
	      FeatureMap params = Factory.newFeatureMap();
	      params.put("sourceUrl", u);
	      params.put("preserveOriginalContent", new Boolean(true));
	      params.put("collectRepositioningInfo", new Boolean(true));
	      Out.prln("Creating doc for " + u);
	      Document doc;
		try {
			doc = (Document)Factory.createResource("gate.corpora.DocumentImpl", params);
		} catch (ResourceInstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			continue;
		}
	      corpus.add(doc);
	    } 

		// tell the pipeline about the corpus and run it
		ArrayList<Document> result = null;
		try {
			result = getIngredientsAnnotationToTextFileFromCorpus(corpus);
		} catch (UnsupportedEncodingException | GateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = null;
		}
		return result;
		}
	
	/**
	 * Run from the command-line, with a list of URLs as argument.
	 * <P>
	 * <B>NOTE:</B><BR>
	 * This code will run with all the documents in memory - if you want to
	 * unload each from memory after use, add code to store the corpus in a
	 * DataStore.
	 * 
	 * @return
	 */
	public String processText(String text) throws GateException, IOException {
		// initialise the GATE library
		Out.prln("Initialising GATE...");
		Out.prln("...GATE initialised");
		
		// initialise ANNIE (this may take several minutes)
		// create a GATE corpus and add the document 
		Corpus corpus = Factory.newCorpus("StandAloneAnnie corpus");
		FeatureMap params = Factory.newFeatureMap();
		params.put("stringContent", text);
		params.put("preserveOriginalContent", new Boolean(true));
		params.put("collectRepositioningInfo", new Boolean(true));
		Document initialDoc = (Document) Factory.createResource("gate.corpora.DocumentImpl", params);
		corpus.add(initialDoc);

		// tell the pipeline about the corpus and run it
		setCorpus(corpus);
		execute();

		Iterator<Document> iter = corpus.iterator();
		Document doc = null;
		if (iter.hasNext()) {
			doc = (Document) iter.next();
		}
		if (doc == null) {
			return null;
		}
		AnnotationSet defaultAnnotSet = doc.getAnnotations();
		Set<String> annotTypesRequired = new HashSet<String>();
		annotTypesRequired.add("AmountNumber");
		annotTypesRequired.add("Measure");
		annotTypesRequired.add("Ingredient");
		Set<Annotation> annotations = new HashSet<Annotation>(defaultAnnotSet.get(annotTypesRequired));

		FeatureMap features = doc.getFeatures();
		String originalContent = (String) features.get(GateConstants.ORIGINAL_DOCUMENT_CONTENT_FEATURE_NAME);
		RepositioningInfo info = (RepositioningInfo) features.get(GateConstants.DOCUMENT_REPOSITIONING_INFO_FEATURE_NAME);


		String startTagPart_1 = "<span GateID=\"";
		String startTagPart_2 = "\" title=\"";
		String startTagPart_3_color_red = "\" style=\"background:Red;\">";
		String startTagPart_3_color_cyan = "\" style=\"background:Blue;\">";
		String startTagPart_3_color_magenda = "\" style=\"background:Yellow;\">";
		String endTag = "</span>";

		
		if (originalContent != null && info != null) {

			Out.prln("OrigContent and reposInfo existing. Generate file...");
			
			Iterator<Annotation> it = annotations.iterator();
			Annotation currAnnot;
			SortedAnnotationList sortedAnnotations = new SortedAnnotationList();

			while (it.hasNext()) {
				currAnnot = (Annotation) it.next();
				sortedAnnotations.addSortedExclusive(currAnnot);
			} // while

			StringBuffer editableContent = new StringBuffer(originalContent);
			long insertPositionEnd;
			long insertPositionStart;
			// insert anotation tags backward
			Out.prln("Unsorted annotations count: "+ annotations.size());
			Out.prln("Sorted annotations count: " + sortedAnnotations.size());
			
			for (int i = sortedAnnotations.size() - 1; i >= 0; --i) {
				currAnnot = (Annotation) sortedAnnotations.get(i);
				insertPositionStart = currAnnot.getStartNode().getOffset().longValue();
				insertPositionStart = info.getOriginalPos(insertPositionStart);
				insertPositionEnd = currAnnot.getEndNode().getOffset().longValue();
				insertPositionEnd = info.getOriginalPos(insertPositionEnd, true);
				
				if (insertPositionEnd != -1 && insertPositionStart != -1) {
					editableContent.insert((int) insertPositionEnd, endTag);
					if(currAnnot.getType().equals("AmountNumber")){
						editableContent.insert((int) insertPositionStart, startTagPart_3_color_red);
					}
					else if(currAnnot.getType().equals("Measure")){
						editableContent.insert((int) insertPositionStart, startTagPart_3_color_cyan);
					}
					else {
						editableContent.insert((int) insertPositionStart, startTagPart_3_color_magenda);
					}
					editableContent.insert((int) insertPositionStart, currAnnot.getType());
					editableContent.insert((int) insertPositionStart, startTagPart_2);
					editableContent.insert((int) insertPositionStart, currAnnot.getId().toString());
					editableContent.insert((int) insertPositionStart, startTagPart_1);
				} // if
			} // for
			editableContent.insert(0,"<font face=\"Lucida Grande\" size=\"13\">");
			editableContent.insert(0, "<html>");
			editableContent.append("</font>");
			editableContent.append("</html>");
			String result = editableContent.toString(); 
			result = result.replaceAll("(\r\n|\n)", "<br />");
			return result;

		} // if - should generate
		else if (originalContent != null) {
			Out.prln("OrigContent existing. Generate file...");

			Iterator<Annotation> it = annotations.iterator();
			Annotation currAnnot;
			SortedAnnotationList sortedAnnotations = new SortedAnnotationList();

			while (it.hasNext()) {
				currAnnot = (Annotation) it.next();
				sortedAnnotations.addSortedExclusive(currAnnot);
			} // while

			StringBuffer editableContent = new StringBuffer(originalContent);
			long insertPositionEnd;
			long insertPositionStart;
			// insert anotation tags backward
			Out.prln("Unsorted annotations count: " + annotations.size());
			Out.prln("Sorted annotations count: " + sortedAnnotations.size());
			for (int i = sortedAnnotations.size() - 1; i >= 0; --i) {
				currAnnot = (Annotation) sortedAnnotations.get(i);
				insertPositionStart = currAnnot.getStartNode().getOffset().longValue();
				insertPositionEnd = currAnnot.getEndNode().getOffset().longValue();
				if (insertPositionEnd != -1 && insertPositionStart != -1) {
					editableContent.insert((int) insertPositionEnd, endTag);
					
					if(currAnnot.getType().equals("AmountNumber")){
						editableContent.insert((int) insertPositionStart, startTagPart_3_color_red);
					}
					else if(currAnnot.getType().equals("Measure")){
						editableContent.insert((int) insertPositionStart, startTagPart_3_color_cyan);
					}
					else {
						editableContent.insert((int) insertPositionStart, startTagPart_3_color_magenda);
					}					
					
					editableContent.insert((int) insertPositionStart, currAnnot.getType());
					editableContent.insert((int) insertPositionStart, startTagPart_2);
					editableContent.insert((int) insertPositionStart, currAnnot.getId().toString());
					editableContent.insert((int) insertPositionStart, startTagPart_1);
				} // if
			} // for
			editableContent.insert(0,"<font face=\"Lucida Grande\" size=\"13\">");
			editableContent.insert(0, "<html>");
			editableContent.append("</font>");
			editableContent.append("</html>");
			String result = editableContent.toString(); 
			result = result.replaceAll("(\r\n|\n)", "<br />");
			return result;
		} else {
			Out.prln("Content : " + originalContent);
			Out.prln("Repositioning: " + info);
		}

		return null;
	}

	/**
   *
   */
	public static class SortedAnnotationList extends Vector<Annotation> {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public SortedAnnotationList() {
			super();
		} // SortedAnnotationList

		public boolean addSortedExclusive(Annotation annot) {
			Annotation currAnot = null;

			// overlapping check
			for (int i = 0; i < size(); ++i) {
				currAnot = (Annotation) get(i);
				if (annot.overlaps(currAnot)) {
					return false;
				} // if
			} // for

			long annotStart = annot.getStartNode().getOffset().longValue();
			long currStart;
			// insert
			for (int i = 0; i < size(); ++i) {
				currAnot = (Annotation) get(i);
				currStart = currAnot.getStartNode().getOffset().longValue();
				if (annotStart < currStart) {
					insertElementAt(annot, i);
					/*
					 * Out.prln("Insert start: "+annotStart+" at position: "+i+
					 * " size="+size()); Out.prln("Current start: "+currStart);
					 */
					return true;
				} // if
			} // for

			int size = size();
			insertElementAt(annot, size);
			// Out.prln("Insert start: "+annotStart+" at size position: "+size);
			return true;
		} // addSorted
	} // SortedAnnotationList
} // class StandAloneAnnie