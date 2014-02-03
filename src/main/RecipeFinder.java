package main;

import gate.GateManager;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import appliacation.screens.*;

public class RecipeFinder {
	private AddRecipeScreen addRecipeScreen;
	private SearchScreen searchRecipeScreen;
	private JMenuBar menuBar;
	private GateManager shareGateManager;
	// TO:DO:
	// static private boolean PreviewRecipesScreen;

	public GateManager getShareGateManager() {
		return shareGateManager;
	}

	/**
	 * Launch the application.
	 */

	public RecipeFinder() {
		createMenuBarOfApplication();
		shareGateManager = new GateManager();
	}

	private void createMenuBarOfApplication() {
		menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		menuBar.add(fileMenu);
		JMenuItem addNewRecipeMenuItem = new JMenuItem("Add New Recipe");
		addNewRecipeMenuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (addRecipeScreen == null) {
					try {
						addRecipeScreen = new AddRecipeScreen();
						addRecipeScreen.setVisible(true);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				} else {
					addRecipeScreen.setVisible(true);
				}
			}
		});
		fileMenu.add(addNewRecipeMenuItem);

		JMenuItem searchRecipesMenuItem = new JMenuItem("Search");
		searchRecipesMenuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (searchRecipeScreen == null) {
					try {
						searchRecipeScreen = new SearchScreen();
						searchRecipeScreen.setVisible(true);
						//
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}

				else {
					searchRecipeScreen.setVisible(true);
				}

			}
		});
		fileMenu.add(searchRecipesMenuItem);

		JMenuItem browseRecipesMenuItem = new JMenuItem("Browse Recipes");
		fileMenu.add(browseRecipesMenuItem);
	}

	public static void main(String[] args) {
		// setup menu
		System.setProperty("apple.laf.useScreenMenuBar", "true");
		System.setProperty("com.apple.mrj.application.apple.menu.about.name",
				"Recipe Finder");

		EventQueue.invokeLater(new Runnable() {

			public void run() {
				try {
					final RecipeFinder recipeFinder = new RecipeFinder();

					recipeFinder.searchRecipeScreen = new SearchScreen();
					recipeFinder.searchRecipeScreen.setVisible(true);
					recipeFinder.searchRecipeScreen.setTitle("Search");
					// recipeFinder.searchRecipeScreen.delegate = recipeFinder;

					AddRecipeScreen addRecipeScreen = new AddRecipeScreen(); 
					addRecipeScreen.setVisible(true);
					addRecipeScreen.setGateManager(recipeFinder.getShareGateManager());
					recipeFinder.addRecipeScreen = addRecipeScreen;
					
					// recipeFinder.addRecipeScreen.delegate = recipeFinder;

					final WindowListener listener = new WindowAdapter() {
						@Override
						public void windowActivated(WindowEvent e) {
							((JFrame) e.getWindow())
									.setJMenuBar(recipeFinder.menuBar);
						}
					};
					recipeFinder.searchRecipeScreen.addWindowListener(listener);
					recipeFinder.addRecipeScreen.addWindowListener(listener);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
