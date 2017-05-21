package documentations.controll;

import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import documentations.model.Entry;
import documentations.model.EntryTopicJunction;
import documentations.model.Model;
import documentations.model.Topic;
import documentations.views.AppFrame;
import documentations.views.EntryEditor;

public class Controller implements ActionListener, ListSelectionListener {

	private AppFrame appFrame;
	private Model model;
	private Point editorPosition;
	ArrayList<Topic> topicsList;
	ArrayList<Entry> entryList;
	ArrayList<EntryTopicJunction> entryTopicJunctionList;
	ArrayList<EntryEditor> openedframes = new ArrayList<EntryEditor>();

	public Controller() {

		model = new Model(this);

		topicsList = model.getTopicsList();
		entryList = model.getMainEntrysList();
		entryTopicJunctionList = model.getEntryTopicJunctionList();

		appFrame = new AppFrame("Protocols", topicsList, entryList);
		appFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent evt) {

				if (openedframes.size() == 0) {
					appFrame.dispose();
					System.exit(0);
				} else {
					EntryEditor entryEditor = openedframes.get(0);
					entryEditor.toFront();
					Toolkit.getDefaultToolkit().beep();
				}
			}
		});

		appFrame.addListener(this, this);
		editorPosition = new Point(900, 50);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		String actionCommand = e.getActionCommand();
		if (actionCommand.contains("entryDetailsChangesSaveButton")) {

			int eid = Integer.valueOf(Integer.valueOf(actionCommand
					.substring(actionCommand.indexOf("-") + 1)));
			EntryEditor entryEditor = getWindowById(eid);
			// entryEditor.getTheNewValues();
			entryEditor.dispose();
			removeWindowfromList(entryEditor);

		} else if (actionCommand.contains("entryDetailsChangesDiscardButton")) {

			int eid = Integer.valueOf(actionCommand.substring(actionCommand
					.indexOf("-") + 1));
			EntryEditor entryEditor = getWindowById(eid);
			entryEditor.dispose();
			removeWindowfromList(entryEditor);

		} else {

			switch (actionCommand) {

			case "newEntryButton":
				appFrame.switchViews(actionCommand);
				break;
			case "saveNewEnrtyButton":
				Entry entry = appFrame.getInputData();
				
				if (entry == null) {
					return;
				} else {	
					model.addData(entry);
					appFrame.resetNewEntryFields();
					appFrame.switchViews(actionCommand);					
				}
				break;
			case "cancelNewEntryButton":
				appFrame.resetNewEntryFields();
				appFrame.switchViews(actionCommand);
				break;
			case "topicsFilterButton":
				break;
			case "periodFilterButton":
				break;
			case "clearButton":
				appFrame.clearselectedTopicsTextField();
				break;
			case "entryDetailsChangesSaveButton":
				break;
			case "entryDetailsChangesDiscardButton":
				appFrame.switchViews(actionCommand);
				break;
			case "addTopics":
				appFrame.insertSelectedTopics();
				break;
			case "addNewTopicButton":
				appFrame.addNewTopic();
				break;
			default:
				return;
			}
		}
	}

	private void removeWindowfromList(EntryEditor entryEditor) {

		int index = openedframes.indexOf(entryEditor);
		openedframes.remove(index);
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		if (e.getValueIsAdjusting()) {
			return;
		}

		int eid = (int) appFrame
				.getTable()
				.getModel()
				.getValueAt(
						appFrame.getTable().getSelectionModel()
								.getLeadSelectionIndex(), 0);

		if (getWindowById(eid) == null) {

			final EntryEditor entryEditor = new EntryEditor(model.getEntry(eid));
			entryEditor.addListener(this);
			entryEditor.addWindowListener(new WindowAdapter() {

				public void windowClosing(WindowEvent ev) {

					if (!entryEditor.isDataChanged()) {
						entryEditor.dispose();
						removeWindowfromList(entryEditor);
					} else {
						entryEditor.displayWarningLabel("warning");
					}
				}

			});

			openedframes.add(entryEditor);
		}
	}

	private EntryEditor getWindowById(int eid) {

		for (EntryEditor entryEditor : openedframes) {
			if (entryEditor.getEntry().getEid() == eid) {
				return entryEditor;
			}
		}
		return null;
	}

	public Point getEditorPosition() {
		return editorPosition;
	}

	public void updateTopicsList(Topic tc) {
		
		this.topicsList.add(tc);
		appFrame.updateTopicsList(tc);
		
	}

	public void updateEntryList(Entry entry) {
		this.entryList.add(entry);
		appFrame.updateEntryList(entry);
		
	}

	// private void schiftEditorPosition() {
	//
	// double x = getEditorPosition().getX();
	// double y = getEditorPosition().getY();
	// this.editorPosition = new Point((int) x + 10, (int) y + 10);
	// }

}
