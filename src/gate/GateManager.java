package gate;

import java.io.File;
import java.io.IOException;

import gate.*;
import gate.persist.PersistenceException;
import gate.util.Err;
import gate.util.Out;
import gate.util.GateException;
import gate.util.persistence.PersistenceManager;
import gate.creole.ANNIEConstants;
import gate.creole.ResourceInstantiationException;
import gate.creole.SerialAnalyserController;
import gate.creole.ir.*;
import gate.creole.ir.lucene.*;

public class GateManager {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// initialise the GATE library

		// load ANNIE as an application from a gapp file
		try {
			
			//System.setProperty("gate.home", "/Applications/gate-7.1-build4485-BIN");
			//org.apache.log4j.BasicConfigurator.configure();
			Gate.init();

			SerialAnalyserController controller = (SerialAnalyserController) PersistenceManager
					.loadObjectFromFile(new File(new File(
							Gate.getPluginsHome(), ANNIEConstants.PLUGIN_DIR),
							ANNIEConstants.DEFAULT_FILE));
		} catch (PersistenceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ResourceInstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (GateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
