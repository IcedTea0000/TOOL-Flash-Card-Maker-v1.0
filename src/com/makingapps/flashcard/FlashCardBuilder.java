package com.makingapps.flashcard;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.*;

public class FlashCardBuilder implements Serializable {

	private JTextArea question;
	private JTextArea answer;
	private ArrayList<FlashCard> cardList;
	private JFrame frame;

	public static void main(String[] args) {
		FlashCardBuilder builder = new FlashCardBuilder();
		builder.run();
	}

	public void run() {
		// build and display GUI
		System.out.println("FlashCardBuilder running");

		frame = new JFrame("Flash Card Builder");
		JPanel mainPanel = new JPanel();

		// create question jtextarea
		Font bigFont = new Font("sanserif", Font.BOLD, 24);
		question = new JTextArea(6, 20);
		question.setLineWrap(true);
		question.setWrapStyleWord(true);
		question.setFont(bigFont);

		// Create scroller and add question JTextArea to scroller
		JScrollPane qScroller = new JScrollPane(question);
		qScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		qScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		// create answer jtextarea
		answer = new JTextArea(6, 20);
		answer.setLineWrap(true);
		answer.setWrapStyleWord(true);
		answer.setFont(bigFont);

		// Create scroller and add answer JTextArea to scroller
		JScrollPane aScroller = new JScrollPane(answer);
		aScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		aScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		JButton nextButton = new JButton("Next Card");

		cardList = new ArrayList<FlashCard>();

		JLabel qLabel = new JLabel("Question: ");
		JLabel aLabel = new JLabel("Answer: ");

		// add components to main panel
		mainPanel.add(qLabel);
		mainPanel.add(qScroller);
		mainPanel.add(aLabel);
		mainPanel.add(aScroller);
		mainPanel.add(nextButton);
		nextButton.addActionListener(new NextCardListener());

		// create menubar, 3 menu items and add Listener to them
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		JMenuItem newMenuItem = new JMenuItem("New Deck");
		JMenuItem saveMenuItem = new JMenuItem("Save Deck");
		newMenuItem.addActionListener(new NewMenuListener());
		saveMenuItem.addActionListener(new SaveMenuListener());
		fileMenu.add(newMenuItem);
		fileMenu.add(saveMenuItem);
		menuBar.add(fileMenu);
		frame.setJMenuBar(menuBar);
		frame.getContentPane().add(BorderLayout.CENTER, mainPanel);
		frame.setSize(500,600);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	// add the current card to the list for saving and clear text areas
	private class NextCardListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			FlashCard card = new FlashCard(question.getText(), answer.getText());
			cardList.add(card);
			clearCard();
		}
	}

	// bring up a file dialog box
	// let the user name and save all the cards in current list as a set
	private class SaveMenuListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			FlashCard card = new FlashCard(question.getText(), answer.getText());
			cardList.add(card);

			JFileChooser fileSave = new JFileChooser();
			fileSave.showSaveDialog(frame);
			saveFile(fileSave.getSelectedFile());
			JOptionPane.showMessageDialog(frame, "Save deck "+fileSave.getSelectedFile().getName()+" success", "Save finish",JOptionPane.INFORMATION_MESSAGE);
		}
	}

	// start a new set, clear out the card list and clear out the text area
	private class NewMenuListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			cardList.clear();
			clearCard();
		}
	}

	// Called by SaveMenuListener, iterate through the list of cards and write each
	// one out to a text file
	private void saveFile(File file) {
		try {
			
			FileOutputStream fs=new FileOutputStream(file);
			ObjectOutputStream os=new ObjectOutputStream(fs);
			os.writeObject(cardList);
			os.close();	
		} catch (Exception ex) {
			System.out.println("Cant export cardList");
			ex.printStackTrace();
		}
	}

	private void clearCard() {
		question.setText("");
		answer.setText("");
		question.requestFocus();
	}
}
