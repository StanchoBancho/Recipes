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
	private BrowseRecipesScreen browseRecipeScreen;
	private BrowseSearchedRecipesScreen browseSearchedRecipeScreen;
	private JMenuBar menuBar;
	private GateManager shareGateManager;

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
		browseRecipesMenuItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (browseRecipeScreen == null) {
					try {
						browseRecipeScreen = new BrowseRecipesScreen();
						browseRecipeScreen.setVisible(true);
						//
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
				else {
					browseRecipeScreen.setVisible(true);
				}
			}
		});
		
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

					//setup search screens
					SearchScreen searchScreen= new SearchScreen();
					searchScreen.setTitle("Search");
					recipeFinder.searchRecipeScreen = searchScreen;

					BrowseSearchedRecipesScreen browseSeachedRecipesScreen = new BrowseSearchedRecipesScreen(null);
					browseSeachedRecipesScreen.gateManager = recipeFinder.getShareGateManager();
					recipeFinder.browseSearchedRecipeScreen = browseSeachedRecipesScreen;
					searchScreen.addSaveRecipeListener(browseSeachedRecipesScreen);
					
					//setup add and browse recipe screens
					BrowseRecipesScreen browseRecipeScreen = new BrowseRecipesScreen();
					browseRecipeScreen.populateRecipeList();
					recipeFinder.browseRecipeScreen = browseRecipeScreen;
					
					AddRecipeScreen addRecipeScreen = new AddRecipeScreen(); 
					addRecipeScreen.setGateManager(recipeFinder.getShareGateManager());
					addRecipeScreen.addSaveRecipeListener(browseRecipeScreen);
					recipeFinder.addRecipeScreen = addRecipeScreen;

					//add window listener to all the screens
					final WindowListener listener = new WindowAdapter() {
						@Override
						public void windowActivated(WindowEvent e) {
							((JFrame) e.getWindow()).setJMenuBar(recipeFinder.menuBar);
						}
					};
					searchScreen.addWindowListener(listener);
					addRecipeScreen.addWindowListener(listener);
					browseRecipeScreen.addWindowListener(listener);
					browseSeachedRecipesScreen.addWindowListener(listener);
					
					//make visible some screen
					searchScreen.setVisible(true);
					addRecipeScreen.setVisible(true);
					browseRecipeScreen.setVisible(true);
					browseSeachedRecipesScreen.setVisible(false);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
