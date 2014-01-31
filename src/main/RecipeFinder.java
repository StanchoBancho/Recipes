package main;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import appliacation.screens.*;

public class RecipeFinder {
	static private AddRecipeScreen addRecipeScreen;
	static private SearchScreen searchRecipeScreen;
	
//	TO:DO:
//	static private boolean PreviewRecipesScreen;
		
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		//setup menu
		 System.setProperty("apple.laf.useScreenMenuBar", "true");
	     System.setProperty("com.apple.mrj.application.apple.menu.about.name", "Recipe Finder");
	      

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					searchRecipeScreen = new SearchScreen();
					searchRecipeScreen.frame.setVisible(true);
					searchRecipeScreen.frame.setTitle("Search");
					
					addRecipeScreen = new AddRecipeScreen();
					addRecipeScreen.setVisible(true);
					setupMenuButtonsOfMenuFrame(addRecipeScreen);

					
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private static void initAndPresentAddRecipeScreen(){
		if(addRecipeScreen != null){
			addRecipeScreen.setVisible(true);
		}
		else{
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						addRecipeScreen = new AddRecipeScreen();
						addRecipeScreen.setVisible(true);
						setupMenuButtonsOfMenuFrame(addRecipeScreen);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		}
	}
	
	private static void setupMenuButtonsOfMenuFrame(MenuFrame frame){
		//setup add recipe button
		frame.addNewRecipeMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				initAndPresentAddRecipeScreen();
			}
		});	
		
	}
	
}
