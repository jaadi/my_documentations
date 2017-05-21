package documentations.views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.jdesktop.swingx.JXDatePicker;

import documentations.model.Entry;
import documentations.model.Topic;

public class NewEntryPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private JTextField selectedTopicsField;
	private JTextArea textArea;
	private JTextField newTopicsField;
	private JXDatePicker datePicker;
	private JButton clearButton;
	private JButton saveNewEnrtyButton;
	private JButton cancelNewEntryButton;
	private JButton addNewTopicButton;
	private JLabel saveFailedWarningLabel;
	private JComboBox<String> deviceBox;
	private String[] deviceList;

	public NewEntryPanel(String[] deviceList) {

		this.deviceList = deviceList;

		setLayout(new BorderLayout());
		setBackground(new Color(159, 169, 160));

		JLabel formTitleLabel = new JLabel("new entry", JLabel.LEFT);
		formTitleLabel.setFont(new Font("Arial", Font.BOLD, 15));
		formTitleLabel.setBorder(new EmptyBorder(10, 10, 10, 10));
		add(formTitleLabel, BorderLayout.PAGE_START);

		textArea = new JTextArea();
		textArea.setWrapStyleWord(true);
		textArea.setLineWrap(true);
		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setBorder(BorderFactory.createTitledBorder("entry content"));
		add(scrollPane, BorderLayout.CENTER);

		JPanel commandsPanel = new JPanel(new GridLayout(0, 1));
		JLabel selecTopicLabel = new JLabel(
				"select topics from the topic list and hit the add button. new topics can be added through the textfield below.");
		selecTopicLabel.setBorder(new EmptyBorder(0, 5, 0, 0));
		commandsPanel.add(selecTopicLabel);

		JPanel selectedTopicsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel selectedTopicsLabel = new JLabel("selected topics");
		selectedTopicsField = new JTextField();
		selectedTopicsField.setPreferredSize(new Dimension(450, 30));
		selectedTopicsField.setEditable(false);
		clearButton = new JButton("clear");
		clearButton.setActionCommand("clearButton");
		selectedTopicsPanel.add(selectedTopicsLabel);
		selectedTopicsPanel.add(selectedTopicsField);
		selectedTopicsPanel.add(clearButton);

		commandsPanel.add(selectedTopicsPanel);

		JPanel newTopicPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel newTopicLabel1 = new JLabel("enter a new topic (one by one)",
				JLabel.LEFT);
		newTopicLabel1.setBorder(new EmptyBorder(0, 0, 0, 23));
		newTopicsField = new JTextField();
		newTopicsField.setPreferredSize(new Dimension(100, 30));
		addNewTopicButton = new JButton("add to the topics selection");
		addNewTopicButton.setActionCommand("addNewTopicButton");

		newTopicPanel.add(newTopicLabel1);
		newTopicPanel.add(newTopicsField);
		newTopicPanel.add(addNewTopicButton);

		commandsPanel.add(newTopicPanel);

		JPanel entrydatePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

		JLabel selecDateLabel = new JLabel("select a date");
		selecDateLabel.setBorder(new EmptyBorder(0, 0, 0, 117));
		datePicker = new JXDatePicker();
		datePicker.setDate(Calendar.getInstance().getTime());
		datePicker.setFormats(new SimpleDateFormat("dd.MM.yyyy"));
		entrydatePanel.add(selecDateLabel);
		entrydatePanel.add(datePicker);

		commandsPanel.add(entrydatePanel);

		JPanel devicePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

		JLabel selecDeviceLabel = new JLabel("select a device");
		selecDeviceLabel.setBorder(new EmptyBorder(0, 0, 0, 106));
		deviceBox = new JComboBox<String>(this.deviceList);
		deviceBox.setEditable(true);
		devicePanel.add(selecDeviceLabel);
		devicePanel.add(deviceBox);

		commandsPanel.add(devicePanel);

		JPanel saveEntryPanel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		saveFailedWarningLabel = new JLabel("");
		saveFailedWarningLabel.setForeground(new Color(250, 0, 0));
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 3;
		gbc.insets = new Insets(0, 5, 0, 0);
		gbc.weightx = 1.0;
		saveEntryPanel.add(saveFailedWarningLabel, gbc);

		saveNewEnrtyButton = new JButton("save");
		saveNewEnrtyButton.setActionCommand("saveNewEnrtyButton");
		gbc.gridx = 3;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.weightx = 0.0;
		gbc.insets = new Insets(5, 5, 5, 5);
		saveEntryPanel.add(saveNewEnrtyButton, gbc);

		cancelNewEntryButton = new JButton("cancel");
		cancelNewEntryButton.setActionCommand("cancelNewEntryButton");
		gbc.gridx = 4;
		gbc.gridy = 0;
		saveEntryPanel.add(cancelNewEntryButton, gbc);

		commandsPanel.add(saveEntryPanel);

		add(commandsPanel, BorderLayout.PAGE_END);
	}

	public void clearselectedTopicsTextField() {
		selectedTopicsField.setText("");
	}

	public JButton getSaveNewEnrtyButton() {
		return saveNewEnrtyButton;
	}

	public JButton getCancelNewEntryButton() {
		return cancelNewEntryButton;
	}

	public JButton getClearButton() {
		return clearButton;
	}	

	public void insertSelectedTopics(List<String> selectedValuesList) {
		for (String item : selectedValuesList) {
			String concat = selectedTopicsField.getText().concat(item)
					.concat(";");
			selectedTopicsField.setText(concat);
		}
	}

	public void resetFields() {

		textArea.setText("");
		selectedTopicsField.setText("");
		newTopicsField.setText("");
		datePicker.setDate(Calendar.getInstance().getTime());
	}

	public Entry getInputData() {		

		if (textArea.getText().trim().equals("")) {
			saveFailedWarningLabel.setText("please enter content bevor saving");

		} else if (selectedTopicsField.getText().equals("")
				&& newTopicsField.getText().trim().equals("")) {
			saveFailedWarningLabel
					.setText("please select topics or enter new one");

		} else if (selectedTopicsField.getText().equals("")
				&& newTopicsField.getText().trim().equals("")) {
			saveFailedWarningLabel
					.setText("please select topics or enter new one");

		} else {
			
			Date date = datePicker.getDate();
			SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yy");
			String edate = formatter.format(date);			
			String eContent = textArea.getText().trim();
			String eDevice = (String) deviceBox.getSelectedItem();
			ArrayList<Topic> topics = extractTopics(selectedTopicsField.getText().trim());
			
			return new Entry(0,edate, eContent, eDevice, topics,false);
		}
		
		return null;
	}

	private ArrayList<Topic> extractTopics(String unparsed) {
		
		//test
		

		ArrayList<Topic> topics = new ArrayList<Topic>();
		
		String[] split = unparsed.split(";");
		
		for (String item : split) {
			
			if (topics.contains(new Topic(0,item))) {
				continue;
			} else {
				topics.add(new Topic(0,item));
			}
		}		
		
		return topics;
	}

	public void addNewTopic() {
		selectedTopicsField.setText(selectedTopicsField.getText()
				+ newTopicsField.getText().trim() + ";");
		newTopicsField.setText("");
	}

	public JButton getAddNewTopicButton() {
		return addNewTopicButton;
	}

}
