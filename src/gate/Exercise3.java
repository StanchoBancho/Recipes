package gate;


import java.io.File;
import java.util.List;

import com.sun.org.apache.xerces.internal.dom.DocumentImpl;

import gate.*;
import gate.creole.ConditionalSerialAnalyserController;
import gate.gui.*;
import gate.util.persistence.PersistenceManager;

//import com.ivita.evtima.utils.Utils;

public class Exercise3 {

	public static void main(String[] args)
	 throws Exception{
		
		System.setProperty("gate.home", "/Applications/gate-7.1-build4485-BIN");

		// prepare the library and clean up the config files 	
		Gate.init(); 
		Gate.initConfigData();
		
		//make visible
		MainFrame.getInstance().setVisible(true);
		
		//make a corpus
		Corpus corpus = Factory.newCorpus("docs");

		//add documents to the corpus;
		//the documents are in folder "docs" which is in the root folder of the project 
		File inputDir = new File( "docs/");
		for(File file : inputDir.listFiles()){
			// create a new document
			Document doc = Factory.newDocument(file.toURI().toURL());
			corpus.add(doc);
		}
		
		//load an application from a xgapp file which is in the root folder of the project
		ConditionalSerialAnalyserController myapp = 
				(ConditionalSerialAnalyserController) 
		PersistenceManager.loadObjectFromFile
		(new File("application.xgapp"));
		  
		//set a corpus for the app
		myapp.setCorpus(corpus);
		
		//execute the application
		myapp.execute();
		
		//for each document in the corpus, print the string of each annotation 
		//of type Person which is in the default annotation set
		for(Document doc : corpus){
			for(Annotation ann : doc.getAnnotations().get("Person")){
				System.out.println(doc.getContent().getContent
						(ann.getStartNode().getOffset(), 
								ann.getEndNode().getOffset()));
			}
		}

	}
}
