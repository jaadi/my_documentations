package documentations.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import documentations.controll.Controller;

public class Model {

	private Controller controller;
	private ArrayList<Topic> topicsList = new ArrayList<Topic>();
	private ArrayList<Entry> mainEntrysList = new ArrayList<Entry>();
	private ArrayList<Entry> entryList = new ArrayList<Entry>();
	private ArrayList<EntryTopicJunction> entryTopicJunctionList = new ArrayList<EntryTopicJunction>();

	Connection con = null;
	Statement stmt = null;

	public Model(Controller controller) {

		this.controller = controller;
		con = ConnectionClass.Connect();
		fetchLists();
		fillMainEntrysList();
	}

	private void fetchLists() {

		if (con == null) {
			con = ConnectionClass.Connect();
			fetchLists();
		} else {
			fetchTopicsList();
			fetchEntryList();
			fetchJunctionsList();
		}

	}

	private void fetchJunctionsList() {

		try {

			stmt = con.createStatement();
			String sql = "select* from entry_topic_junction ";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				int jid = rs.getInt(1);
				int entry_id = rs.getInt(2);
				int topic_id = rs.getInt(3);
				entryTopicJunctionList.add(new EntryTopicJunction(jid,
						entry_id, topic_id));
			}
			stmt.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e);
			e.printStackTrace();
		}

	}

	private void fetchTopicsList() {
		
		ArrayList<Topic> tmp= topicsList;
		
		try {
			stmt = con.createStatement();
			String sql = "select* from topics ";
			ResultSet rs = stmt.executeQuery(sql);
			topicsList.clear();
			while (rs.next()) {
				int tid = rs.getInt(1);
				String tname = rs.getString(2);
				topicsList.add(new Topic(tid, tname));
			}
			stmt.close();
		} catch (SQLException e) {			
			topicsList = tmp;
			e.printStackTrace();
		}
	}
	
	private void fillMainEntrysList(){	
		
		for(Entry entry: entryList){			
			ArrayList<Topic> topicsList = fetchTopicsList(entry);
			mainEntrysList.add(new Entry(entry.getEid(),entry.getEdate(), entry.getEcontent(), entry.getDevice(), topicsList,false));			
		}		
	}

 	public void fetchEntryList() {
 		
 		ArrayList<Entry> tmp= entryList;

		try {
			stmt = con.createStatement();
			String sql = "select* from entrys ";
			ResultSet rs = stmt.executeQuery(sql);
			entryList.clear();
			while (rs.next()) {
				int eid = rs.getInt(1);
				String edate = rs.getString(2);
				String econtent = rs.getString(3);
				String edevice = rs.getString(4);
				entryList.add(new Entry(eid, edate, econtent, edevice));
			}
			stmt.close();
		} catch (SQLException e) {
			entryList = tmp;
			e.printStackTrace();
		}			
	}
	
	public ArrayList<Topic> fetchTopicsList(Entry entry){
		
		int eid = entry.getEid();
		ArrayList<Topic> list = new ArrayList<Topic>();
		
		try {
			
			stmt = con.createStatement();			
			String sql = "SELECT t.tid, t.tname FROM topics t"
					+ "		inner join entry_topic_junction j on j.topic_id = t.tid"
					+ "		inner join entrys e on  j.entry_id =e.eid"
					+ "		where e.eid = "+eid+"; ";
			
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				int tid = rs.getInt(1);
				String tname = rs.getString(2);
				list.add(new Topic(tid, tname));
			}
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		
		return list;
	}

	public Entry getEntry(int id) {
		for (Entry entry : mainEntrysList) {
			if (entry.getEid() == id) {
				return entry;
			}
		}
		return null;
	}

	private Topic getTopic(String tname) {
		for (Topic topic : topicsList) {
			if (topic.getTname().equalsIgnoreCase(tname)) {
				return topic;
			}
		}
		return null;
	}

	public void addData(Entry entry) {

		int entryId = storeEntry(entry);
		entry.setEid(entryId);
		mainEntrysList.add(entry);
		controller.updateEntryList(entry);

		ArrayList<Topic> topics = entry.getTopics();

		for (Topic topic : topics) {

			Topic topc = getTopic(topic.getTname());

			if (topc == null) {

				int tid = storeTopic(topic);
				Topic tc = new Topic(tid, topic.getTname());
				topicsList.add(tc);
				controller.updateTopicsList(tc);

				storeEntryTopicsjunctions(entryId, tid);

			} else {

				storeEntryTopicsjunctions(entryId, topc.getTid());
			}
		}
	}

	private int storeTopic(Topic topic) {

		int tid = 0;
		String tname = topic.getTname();

		try {

			if (con == null) {
				con = ConnectionClass.Connect();
			}

			stmt = con.createStatement();
			String sql = "INSERT INTO topics (tname) VALUES ( '" + tname
					+ "');";
			stmt.executeUpdate(sql);

			sql = "SELECT last_insert_rowid();";
			ResultSet result = stmt.executeQuery(sql);
			tid = result.getInt(1);

			stmt.close();
			con.commit();

		} catch (SQLException e) {

			e.printStackTrace();
		}

		return tid;
	}

	private int storeEntry(Entry entry) {

		int eid = 0;
		String edate = entry.getEdate();
		String econtent = entry.getEcontent();
		String device = entry.getDevice();

		try {

			if (con == null) {
				con = ConnectionClass.Connect();
			}

			stmt = con.createStatement();
			String sql = "INSERT INTO entrys (edate,econtent,device) VALUES ( "
					+ " '" + edate + "', '" + econtent + "', '" + device
					+ "');";
			stmt.executeUpdate(sql);

			sql = "SELECT last_insert_rowid();";
			ResultSet result = stmt.executeQuery(sql);
			eid = result.getInt(1);

			stmt.close();
			con.commit();

		} catch (SQLException e) {

			e.printStackTrace();
		}

		return eid;
	}

	private void storeEntryTopicsjunctions(int entryId, int tid) {

		try {

			if (con == null) {
				con = ConnectionClass.Connect();
			}

			stmt = con.createStatement();
			String sql = "INSERT INTO entry_topic_junction ( entry_id,topic_id) VALUES ( "
					+ " '" + entryId + "', '" + tid + "');";
			stmt.executeUpdate(sql);

			stmt.close();
			con.commit();

		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

	public ArrayList<Topic> getTopicsList() {
		return topicsList;
	}

	public ArrayList<Entry> getMainEntrysList() {
		return mainEntrysList;
	}

	public ArrayList<EntryTopicJunction> getEntryTopicJunctionList() {
		return entryTopicJunctionList;
	}

	// SQLiteCommand cmd = new SQLiteCommand("PRAGMA foreign_keys = ON", conn);
	// cmd.ExecuteNonQuery();

}
