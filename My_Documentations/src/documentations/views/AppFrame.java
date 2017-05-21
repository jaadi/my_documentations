package documentations.views;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.JXDatePicker;

import documentations.model.Entry;
import documentations.model.Topic;

public class AppFrame extends JFrame {

	private JButton newEntryButton;
	private JButton topicsFilterButton;
	private JButton periodFilterButton;
	private EntrysTable table;
	private JLabel topicsSelectionLabel;
	private JPanel wrapper;
	private JPanel cardsPanel;
	private boolean editing = false;
	private ArrayList<Topic> topicsList;
	private ArrayList<Entry> entrysList;
	private JList<String> topicsItemList;
	private DefaultListModel<String> listModel;
	private NewEntryPanel newEntryPanel;
	private DefaultTableModel defaultTableModel ;

	final static String ENTRYLISTPANEL = "Card with entrys";
	final static String ENTRYINPUTPANEL = "Card with input form";

	private static final long serialVersionUID = 1L;

	public AppFrame(String name, ArrayList<Topic> tList, ArrayList<Entry> eList) {

		super(name);
		this.topicsList = tList;
		this.entrysList = eList;
		setBounds(10, 10, 1200, 750);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		wrapper = new JPanel(new BorderLayout(20, 20));
		wrapper.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		wrapper.setBackground(new Color(59, 88, 75));
		setContentPane(wrapper);
		addComponentsToPane(wrapper);
		setVisible(true);
	}

	private void addComponentsToPane(JPanel wrapper) {

		JPanel pnl_AppTitle = createAppTitlePanel();
		wrapper.add(pnl_AppTitle, BorderLayout.PAGE_START);

		JPanel cardPanel = createCardPanel();
		wrapper.add(cardPanel, BorderLayout.CENTER);

		JPanel newEntryButtonPannel = CreateNewEntryButtonPannel();
		wrapper.add(newEntryButtonPannel, BorderLayout.PAGE_END);

		JPanel topicsListPanel = createTopicsListPanel();
		wrapper.add(topicsListPanel, BorderLayout.LINE_START);

		JPanel periodSelectionPanel = createPeriodSelectionPanel();
		wrapper.add(periodSelectionPanel, BorderLayout.LINE_END);

	}

	private JPanel createAppTitlePanel() {

		JPanel pnl_AppTitle = new JPanel();
		pnl_AppTitle.setBackground(new Color(159, 169, 160));
		pnl_AppTitle.setBorder(BorderFactory.createLoweredBevelBorder());
		FlowLayout layout = (FlowLayout) pnl_AppTitle.getLayout();
		layout.setVgap(30);

		JLabel lblTitle = new JLabel("Protocols");
		lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
		lblTitle.setAlignmentX(CENTER_ALIGNMENT);
		lblTitle.setForeground(new Color(250, 253, 251));
		pnl_AppTitle.add(lblTitle);

		return pnl_AppTitle;
	}

	private JPanel createCardPanel() {

		// Create the panel that contains the "cards".
		cardsPanel = new JPanel(new CardLayout());
		cardsPanel.setBorder(BorderFactory.createLoweredBevelBorder());
		// create first card : panel with entry list
		JScrollPane entrysListPane = createEntrysListPanel();
		cardsPanel.add(entrysListPane, ENTRYLISTPANEL);

		// create second card : panel with input form
		newEntryPanel = new NewEntryPanel(getDeviceList());
		cardsPanel.add(newEntryPanel, ENTRYINPUTPANEL);

		return cardsPanel;
	}

	private JScrollPane createEntrysListPanel() {

		final String[] columnNames = { "id", "date", "content", "device" };

		defaultTableModel = new DefaultTableModel(entryListTo2DArray(),columnNames) ;

		table = new EntrysTable(defaultTableModel);	

		JScrollPane entrysListPane = new JScrollPane(table,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		entrysListPane.setBorder(BorderFactory
				.createTitledBorder("entrys list"));		
		table.adjustColumnWidth();
		return entrysListPane;
	}
	
	private JPanel CreateNewEntryButtonPannel() {

		JPanel singleButtonPanel = new JPanel();
		singleButtonPanel.setAlignmentX(CENTER_ALIGNMENT);
		singleButtonPanel.setBorder(BorderFactory.createLoweredBevelBorder());
		singleButtonPanel.setBackground(new Color(159, 169, 160));

		newEntryButton = new JButton("new entry");
		newEntryButton.setActionCommand("newEntryButton");
		singleButtonPanel.add(newEntryButton);

		return singleButtonPanel;
	}

	private JPanel createTopicsListPanel() {

		JPanel packPanel = new JPanel();
		packPanel.setBackground(new Color(159, 169, 160));
		packPanel.setBorder(BorderFactory.createLoweredBevelBorder());
		JPanel topicsListPanel = new JPanel(new GridBagLayout());
		topicsListPanel.setBackground(new Color(159, 169, 160));
		topicsListPanel.setBorder(BorderFactory.createLoweredBevelBorder());
		topicsListPanel
				.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

		topicsSelectionLabel = new JLabel(
				"<html>select one or multiple topics<br> to filter the entrys</html>");
		topicsSelectionLabel.setFont(new Font("Arial", Font.BOLD, 15));
		topicsSelectionLabel.setForeground(new Color(250, 253, 251));
		GridBagConstraints a = new GridBagConstraints();
		a.insets = new Insets(10, 10, 10, 10);
		a.gridx = 0;
		a.gridy = 0;
		a.gridwidth = 3;
		a.gridheight = 2;
		topicsListPanel.add(topicsSelectionLabel, a);

		listModel = new DefaultListModel<String>();
		topicsItemList = new JList<String>(listModel);
		topicsItemList.setSelectedIndex(0);
		updateTopicsItemList();
		JScrollPane scrollPane = new JScrollPane(topicsItemList);
		scrollPane.setPreferredSize(new Dimension(200, 380));
		GridBagConstraints b = new GridBagConstraints();
		b.anchor = GridBagConstraints.FIRST_LINE_START;
		b.insets = new Insets(10, 10, 10, 10);
		b.gridx = 0;
		b.gridy = 2;
		b.gridwidth = 3;
		b.gridheight = 5;
		topicsListPanel.add(scrollPane, b);

		topicsFilterButton = new JButton("execute filter");
		topicsFilterButton.setActionCommand("topicsFilterButton");
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 7;
		c.insets = new Insets(20, 10, 20, 20);
		topicsListPanel.add(topicsFilterButton, c);

		packPanel.add(topicsListPanel);

		return packPanel;
	}

	public void updateTopicsItemList() {

		for (Topic topic : topicsList) {
			listModel.addElement(topic.getTname());
		}
	}

	private JPanel createPeriodSelectionPanel() {

		JPanel packPanel = new JPanel();
		packPanel.setBackground(new Color(159, 169, 160));
		packPanel.setBorder(BorderFactory.createLoweredBevelBorder());
		JPanel periodSelectionPanel = new JPanel(new GridBagLayout());
		periodSelectionPanel
				.setBorder(BorderFactory.createLoweredBevelBorder());
		periodSelectionPanel.setBackground(new Color(159, 169, 160));
		periodSelectionPanel
				.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

		GridBagConstraints a = new GridBagConstraints();
		JLabel periodSelectionLabel = new JLabel(
				"select a period to filter the entrys");
		periodSelectionLabel.setFont(new Font("Arial", Font.BOLD, 15));
		periodSelectionLabel.setForeground(new Color(250, 253, 251));
		a.insets = new Insets(10, 10, 10, 10);
		a.gridx = 0;
		a.gridy = 0;
		a.gridwidth = 3;
		a.gridheight = 2;
		periodSelectionPanel.add(periodSelectionLabel, a);

		GridBagConstraints b = new GridBagConstraints();
		JLabel fromLabel = new JLabel("from :");
		b.anchor = GridBagConstraints.FIRST_LINE_START;
		b.insets = new Insets(10, 10, 10, 10);
		b.gridx = 0;
		b.gridy = 3;
		periodSelectionPanel.add(fromLabel, b);

		GridBagConstraints c = new GridBagConstraints();
		JXDatePicker startDatePicker = new JXDatePicker();
		startDatePicker.setDate(Calendar.getInstance().getTime());
		startDatePicker.setFormats(new SimpleDateFormat("dd.MM.yyyy"));
		c.gridx = 1;
		c.gridy = 3;
		periodSelectionPanel.add(startDatePicker, c);

		GridBagConstraints d = new GridBagConstraints();
		JLabel untilLabel = new JLabel("until :");
		d.anchor = GridBagConstraints.FIRST_LINE_START;
		d.insets = new Insets(10, 10, 10, 10);
		d.gridx = 0;
		d.gridy = 4;
		periodSelectionPanel.add(untilLabel, d);

		JXDatePicker endDatePicker = new JXDatePicker();
		endDatePicker.setDate(Calendar.getInstance().getTime());
		endDatePicker.setFormats(new SimpleDateFormat("dd.MM.yyyy"));
		GridBagConstraints e = new GridBagConstraints();
		e.gridx = 1;
		e.gridy = 4;
		periodSelectionPanel.add(endDatePicker, e);

		GridBagConstraints f = new GridBagConstraints();
		periodFilterButton = new JButton("execute filter");
		periodFilterButton.setActionCommand("periodFilterButton");
		f.insets = new Insets(20, 20, 20, 20);
		f.gridx = 0;
		f.gridy = 5;
		periodSelectionPanel.add(periodFilterButton, f);
		packPanel.add(periodSelectionPanel);

		return packPanel;
	}

	public void addListener(ActionListener alistener,
			ListSelectionListener llistener) {

		newEntryButton.addActionListener(alistener);
		newEntryPanel.getSaveNewEnrtyButton().addActionListener(alistener);
		newEntryPanel.getCancelNewEntryButton().addActionListener(alistener);
		topicsFilterButton.addActionListener(alistener);
		periodFilterButton.addActionListener(alistener);
		newEntryPanel.getClearButton().addActionListener(alistener);
		newEntryPanel.getAddNewTopicButton().addActionListener(alistener);

		table.getSelectionModel().addListSelectionListener(llistener);
	}

	public void switchViews(String command) {

		if (command.equals("newEntryButton")) {
			if (isEditing()) {
				return;
			} else {
				CardLayout c = (CardLayout) (cardsPanel.getLayout());
				c.show(cardsPanel, ENTRYINPUTPANEL);
				renameTopicsListPanelLabels("editing");
				setEditing(true);
			}
		} else if (command.equals("saveNewEnrtyButton")
				|| command.equals("cancelNewEntryButton")
				|| command.equals("entryDetailsChangesSaveButton")
				|| command.equals("entryDetailsChangesDiscardButton")) {
			CardLayout c = (CardLayout) (cardsPanel.getLayout());
			c.show(cardsPanel, ENTRYLISTPANEL);
			renameTopicsListPanelLabels("filtering");
			setEditing(false);
		}

	}

	public boolean isEditing() {
		return editing;
	}

	public void setEditing(boolean editing) {

		this.editing = editing;
	}

	private void renameTopicsListPanelLabels(String function) {

		if (function.equals("editing")) {
			topicsSelectionLabel
					.setText("<html>select one or multiple topics<br> and hit the add Button</html>");
			topicsFilterButton.setText("add");
			topicsFilterButton.setActionCommand("addTopics");
		} else if (function.equals("filtering")) {
			topicsSelectionLabel
					.setText("<html>select one or multiple topics<br> to filter the entrys</html>");
			topicsFilterButton.setText("execute filter");
			topicsFilterButton.setActionCommand("topicsFilterButton");
		}

	}

	public void clearselectedTopicsTextField() {
		newEntryPanel.clearselectedTopicsTextField();
	}

	public EntrysTable getTable() {
		return table;
	}

	private Object[][] entryListTo2DArray() {

		Object[][] entrysArray = new Object[this.entrysList.size()][4];
		
		for (int i = 0; i < this.entrysList.size(); i++) {
			entrysArray[i][0] = entrysList.get(i).getEid();
			entrysArray[i][1] = entrysList.get(i).getEdate();
			entrysArray[i][2] = entrysList.get(i).getEcontent();
			entrysArray[i][3] = entrysList.get(i).getDevice();
		}

		return entrysArray;
	}

	public void insertSelectedTopics() {

		newEntryPanel.insertSelectedTopics(topicsItemList
				.getSelectedValuesList());
	}

	public void resetNewEntryFields() {
		newEntryPanel.resetFields();

	}

	public Entry getInputData() {
		Entry entry = newEntryPanel.getInputData();
		return entry;
	}

	public void addNewTopic() {
		newEntryPanel.addNewTopic();
	}

	public String[] getDeviceList() {

		ArrayList<String> tmpList = new ArrayList<String>();

		for (int i = 0; i < this.entrysList.size(); i++) {
			if (tmpList.contains(entrysList.get(i).getDevice())) {
				continue;
			} else {
				tmpList.add(entrysList.get(i).getDevice());
			}
		}

		return tmpList.toArray(new String[tmpList.size()]);
	}

	public void updateTopicsList(Topic tc) {
		
		topicsList.add(tc);
		listModel.addElement(tc.getTname());
	}

	public void updateEntryList(Entry entry) {
		entrysList.add(entry);
		String[]arr ={String.valueOf(entry.getEid()), entry.getEdate(), entry.getEcontent(), entry.getDevice()};
		defaultTableModel.addRow(arr);		
	}
}
