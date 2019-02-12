package com.makingapps.flashcard;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

import com.makingapps.flashcard.FlashCardPlayer.NextCardListener;
import com.makingapps.flashcard.FlashCardPlayer.OpenMenuListener;

public class FlashCardRunner {
	private JTextArea display;
	private JButton createDeck;
	private JButton studyDeck;
	private JFrame frame;

	public static void main(String[] args) {
		FlashCardRunner fc = new FlashCardRunner();
		fc.run();
	}

	// build and display gui
	public void run() {
		frame = new JFrame("Flash Card Maker v1.0");
		JPanel mainPanel = new JPanel();
		Font bigFont = new Font("sanserif", Font.BOLD, 24);

		// setup display question jtextarea
		display = new JTextArea(10, 20);
		display.setFont(bigFont);
		display.setLineWrap(true);
		display.setWrapStyleWord(true);
		display.setEditable(false);
		display.setText("Welcome to Flash Card Maker v1.0. Choose your option to create new deck or study deck below.");
		mainPanel.add(display);

		// create show question button
		createDeck = new JButton("Create New Deck");
		mainPanel.add(createDeck);
		studyDeck = new JButton("Study Deck");
		mainPanel.add(studyDeck);
		createDeck.addActionListener(new createDeckListener());
		studyDeck.addActionListener(new studyDeckListener());
		
		//add components to frame
		frame.getContentPane().add(BorderLayout.CENTER, mainPanel);
		frame.setSize(500, 450);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	//invoke FlashCardBuilder class 
	private class createDeckListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			FlashCardBuilder builder = new FlashCardBuilder();
			builder.run();
			createDeck.setEnabled(false);
		}
	}

	//invoke FlashCardPlayer class 
	private class studyDeckListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			FlashCardPlayer reader = new FlashCardPlayer();
			reader.run();
			studyDeck.setEnabled(false);
		}
	}
}
