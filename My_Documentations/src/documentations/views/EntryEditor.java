package documentations.views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Label;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import org.jdesktop.swingx.JXDatePicker;

import documentations.model.Entry;
import documentations.model.Topic;

public class EntryEditor extends JFrame {

	private JButton entryDetailsChangesSaveButton;
	private JButton entryDetailsChangesDiscardButton;
	private JLabel entryDate;
	private JLabel entryChangedWarningLabel;
	private JXDatePicker datePicker;
	private JTextArea textArea;
	private JTextField deviceTextfield;
	private boolean contentChanged;
	private boolean dateChanged;
	private Entry entry;
	private String newContent = "";

	private static final long serialVersionUID = 1L;

	public EntryEditor(Entry entry) {

		super("entry id :" + String.valueOf(entry.getEid()));
		this.entry = entry;
		newContent = entry.getEcontent();
		JPanel pane = createPane();
		this.setContentPane(pane);
		setBounds(600, 200, 700, 500);
		setVisible(true);
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
	}

	private JPanel createPane() {

		JPanel entryDetailsPanel = new JPanel(new BorderLayout());
		entryDetailsPanel.setBackground(new Color(159, 169, 160));

		JLabel paneTitleLabel = new JLabel("Monitor/Editor", JLabel.CENTER);
		paneTitleLabel.setFont(new Font("Arial", Font.BOLD, 15));
		paneTitleLabel.setBorder(new EmptyBorder(10, 10, 10, 10));
		entryDetailsPanel.add(paneTitleLabel, BorderLayout.PAGE_START);

		JPanel contentWrapper = new JPanel(new BorderLayout());

		JPanel headerWrapper = new JPanel(new GridLayout(0, 1));

		JPanel datePanel = new JPanel(new GridLayout(0, 6));

		JLabel entryDateLabel = new JLabel("entry date : ", JLabel.LEFT);
		entryDateLabel.setBorder(new EmptyBorder(10, 10, 10, 0));
		datePanel.add(entryDateLabel);

		entryDate = new JLabel(entry.getEdate(), JLabel.LEFT);
		datePanel.add(entryDate);

		JLabel entryDateChangeLabel = new JLabel("change entry date : ");
		entryDateChangeLabel.setBorder(new EmptyBorder(10, 0, 10, 0));
		datePanel.add(entryDateChangeLabel);

		datePicker = new JXDatePicker();
		datePicker.setDate(Calendar.getInstance().getTime());
		datePicker.setFormats(new SimpleDateFormat("dd.MM.yyyy"));
		datePicker.setBorder(new EmptyBorder(10, 0, 10, 0));
		datePanel.add(datePicker);

		JLabel deviceLabel = new JLabel("device :", JLabel.RIGHT);
		deviceLabel.setBorder(new EmptyBorder(10, 0, 10, 5));
		datePanel.add(deviceLabel);

		JPanel textfieldWrapper = new JPanel(new BorderLayout());
		textfieldWrapper.setBorder(new EmptyBorder(10, 0, 10, 10));
		deviceTextfield = new JTextField(9);
		deviceTextfield.setText(entry.getDevice());
		textfieldWrapper.add(deviceTextfield, BorderLayout.LINE_START);
		datePanel.add(textfieldWrapper);

		headerWrapper.add(datePanel);

		JPanel topicsPanel = new JPanel();		
		topicsPanel.setLayout(new BoxLayout(topicsPanel, BoxLayout.X_AXIS));		
		
		JLabel lbl = new JLabel("Topics  :");
		lbl.setBorder(new EmptyBorder(0, 10, 0, 10));
		topicsPanel.add(lbl);
		for (Topic topic : entry.getTopics()) {			
			JLabel label = new JLabel(topic.getTname());
			label.setFont(new Font("Arial", Font.BOLD, 12));
			label.setBorder(new EmptyBorder(0, 10, 0, 10));
			topicsPanel.add(label);
			topicsPanel.add(new JLabel("-"));
		}

		headerWrapper.add(topicsPanel);

		contentWrapper.add(headerWrapper, BorderLayout.PAGE_START);

		textArea = new JTextArea();
		textArea.setWrapStyleWord(true);
		textArea.setLineWrap(true);
		textArea.setText(entry.getEcontent());
		addDocListener(textArea);
		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setBorder(BorderFactory
				.createTitledBorder("editable content"));

		contentWrapper.add(scrollPane, BorderLayout.CENTER);

		entryDetailsPanel.add(contentWrapper, BorderLayout.CENTER);

		JPanel saveDataPanel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		entryChangedWarningLabel = new JLabel("no changes");
		entryChangedWarningLabel.setForeground(new Color(3, 70, 3));

		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 3;
		gbc.insets = new Insets(0, 5, 0, 0);
		gbc.weightx = 1.0;
		saveDataPanel.add(entryChangedWarningLabel, gbc);

		entryDetailsChangesSaveButton = new JButton("save changes");
		String idAsString = String.valueOf(entry.getEid());
		entryDetailsChangesSaveButton
				.setActionCommand("entryDetailsChangesSaveButton" + "-"
						+ idAsString);
		gbc.gridx = 3;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.weightx = 0.0;
		gbc.insets = new Insets(5, 5, 5, 5);
		saveDataPanel.add(entryDetailsChangesSaveButton, gbc);

		entryDetailsChangesDiscardButton = new JButton("discard changes");
		entryDetailsChangesDiscardButton
				.setActionCommand("entryDetailsChangesDiscardButton" + "-"
						+ idAsString);
		gbc.gridx = 4;
		gbc.gridy = 0;
		saveDataPanel.add(entryDetailsChangesDiscardButton, gbc);

		entryDetailsPanel.add(saveDataPanel, BorderLayout.PAGE_END);

		return entryDetailsPanel;
	}

	private void addDocListener(final JTextArea ta) {

		ta.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent e) {
				upDateNewContent(e);
				if (isDataChanged()) {
					displayWarningLabel("note");
				} else {
					displayWarningLabel("no changes");
				}
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				upDateNewContent(e);
				if (isDataChanged()) {
					displayWarningLabel("note");
				} else {
					displayWarningLabel("no changes");
				}

			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				upDateNewContent(e);
				if (isDataChanged()) {
					displayWarningLabel("note");
				} else {
					displayWarningLabel("no changes");
				}
			}

			private void upDateNewContent(DocumentEvent e) {

				Document document = e.getDocument();
				try {
					newContent = document.getText(0, document.getLength());
				} catch (BadLocationException e1) {
					e1.printStackTrace();
				}
			}

		});
	}

	public void addListener(ActionListener alistener) {

		entryDetailsChangesSaveButton.addActionListener(alistener);
		entryDetailsChangesDiscardButton.addActionListener(alistener);

	}

	public boolean isDataChanged() {

		if (entry.getEdate().equals(entryDate.getText())
				&& entry.getEcontent().equals(newContent)) {
			return false;
		}
		return true;
	}

	public Entry getEntry() {
		return entry;
	}

	public boolean isContentChanged() {
		return contentChanged;
	}

	public void setContentChanged(boolean contentChanged) {
		this.contentChanged = contentChanged;
	}

	public boolean isDateChanged() {
		return dateChanged;
	}

	public void setDateChanged(boolean dateChanged) {
		this.dateChanged = dateChanged;
	}

	public void displayWarningLabel(String info) {

		if (info.equalsIgnoreCase("note")) {
			entryChangedWarningLabel.setText("Data was changed !");
			entryChangedWarningLabel.setForeground(new Color(250, 128, 0));
		} else if (info.equalsIgnoreCase("warning")) {
			entryChangedWarningLabel
					.setText("please save or discard changes befor closing window!");
			entryChangedWarningLabel.setForeground(new Color(250, 0, 0));
		} else if (info.equalsIgnoreCase("no changes")) {
			entryChangedWarningLabel.setText("no changes");
			entryChangedWarningLabel.setForeground(new Color(3, 70, 3));
		}

	}

}
