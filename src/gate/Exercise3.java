package gate;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import com.sun.org.apache.xerces.internal.dom.DocumentImpl;

import gate.*;
import gate.creole.ConditionalSerialAnalyserController;
import gate.creole.ExecutionException;
import gate.creole.ResourceInstantiationException;
import gate.gui.*;
import gate.persist.PersistenceException;
import gate.util.GateException;
import gate.util.InvalidOffsetException;
import gate.util.persistence.PersistenceManager;

//import com.ivita.evtima.utils.Utils;

public class Exercise3 {

	public static ArrayList<String> getCorpus() {
		// System.setProperty("gate.home",
		// "/Applications/gate-7.1-build4485-BIN");

		// prepare the library and clean up the config files
		try {
			Gate.init();
			Gate.initConfigData();
		} catch (GateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

		// make visible
		MainFrame.getInstance().setVisible(true);

		// make a corpus
		Corpus corpus;
		try {
			corpus = Factory.newCorpus("docs");
		} catch (ResourceInstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

		// add documents to the corpus;
		// the documents are in folder "docs" which is in the root folder of the
		// project
		File inputDir = new File("docs/");
		for (File file : inputDir.listFiles()) {
			// create a new document
			Document doc = null;
			try {
				doc = Factory.newDocument(file.toURI().toURL());
			} catch (ResourceInstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (doc != null) {
				corpus.add(doc);
			}
		}

		// load an application from a xgapp file which is in the root folder of
		// the project
		ConditionalSerialAnalyserController myapp = null;
		try {
			myapp = (ConditionalSerialAnalyserController) PersistenceManager
					.loadObjectFromFile(new File("app.xgapp"));
		} catch (PersistenceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (ResourceInstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

		// set a corpus for the app
		myapp.setCorpus(corpus);

		// execute the application
		System.out.println("zapochna execute-a");
		try {
			myapp.execute();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

		// for each document in the corpus, print the string of each annotation
		// of type Person which is in the default annotation set
		ArrayList<String> array = new ArrayList<String>();
		for (Document doc : corpus) {
			for (Annotation ann : doc.getAnnotations().get("Person")) {
				try {
					array.add(ann.toString());
					// array.add(doc.getContent().getContent(ann.getStartNode().getOffset(),ann.getEndNode().getOffset()));
					System.out.println(doc.getContent().getContent(
							ann.getStartNode().getOffset(),
							ann.getEndNode().getOffset()));
				} catch (InvalidOffsetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		System.out.println("svurshvam");
		return array;
	}
}