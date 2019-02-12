package com.makingapps.flashcard;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.swing.*;

public class FlashCardPlayer {

	private JTextArea display;
	private ArrayList<FlashCard> cardList = new ArrayList<FlashCard>();
	private FlashCard currentCard;
	private int currentCardIndex;
	private JFrame frame;
	private JButton nextButton;
	private boolean showAnswer;

	public static void main(String[] args) {
		FlashCardPlayer reader = new FlashCardPlayer();
		reader.run();
	}

	public void run() {
		// build and display gui
		frame = new JFrame("Flash Card Player");
		JPanel mainPanel = new JPanel();
		Font bigFont = new Font("sanserif", Font.BOLD, 24);

		// setup display question jtextarea
		display = new JTextArea(10, 20);
		display.setFont(bigFont);
		display.setLineWrap(true);
		display.setEditable(false);

		// add display to scroller
		JScrollPane qScroller = new JScrollPane(display);
		qScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		qScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		mainPanel.add(qScroller);

		// create show question button
		nextButton = new JButton("Show Question");
		mainPanel.add(nextButton);
		nextButton.addActionListener(new NextCardListener());

		// add components to menubar
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		JMenuItem loadMenuItem = new JMenuItem("Load card set");
		loadMenuItem.addActionListener(new OpenMenuListener());
		fileMenu.add(loadMenuItem);
		menuBar.add(fileMenu);

		frame.setJMenuBar(menuBar);
		frame.getContentPane().add(BorderLayout.CENTER, mainPanel);
		frame.setSize(500, 600);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	//check if user is showing question or answer
	class NextCardListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (cardList.isEmpty()) {
				display.setText("You didnt load a card deck yet.");
				nextButton.setEnabled(false);
			} else {
				if (showAnswer == false) {
					showQuestion();
				} else {
					display.setText(currentCard.getAnswer());
					nextButton.setText("Next Question");
					showAnswer = false;
				}
			}
		}
	}

	//show question and check if there are cards left in the deck
	private void showQuestion() {
		if (currentCardIndex >= cardList.size()) {
			display.setText("No more card to display");
			nextButton.setEnabled(false);
		} else {
			currentCard = cardList.get(currentCardIndex);
			currentCardIndex++;
			display.setText(currentCard.getQuestion());
			nextButton.setText("Show Answer");
			showAnswer = true;
			
			System.out.println("list size "+cardList.size());
			System.out.println("current index: "+currentCardIndex);
		}
	}

	//choose card deck from dialog
	class OpenMenuListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser fileOpen = new JFileChooser();
			fileOpen.showOpenDialog(frame);
			loadFile(fileOpen.getSelectedFile());
			nextButton.setEnabled(true);
		}
	}

	//load file card deck
	private void loadFile(File file) {
		try {
			FileInputStream fs = new FileInputStream(file);
			ObjectInputStream is = new ObjectInputStream(fs);
			cardList = (ArrayList<FlashCard>) is.readObject();
			is.close();
			System.out.println("Load deck success");
		} catch (Exception ex) {
			System.out.println("Cant read the deck file");
			ex.printStackTrace();
		}
	}

}
