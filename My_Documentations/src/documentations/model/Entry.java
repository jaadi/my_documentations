package documentations.model;

import java.util.ArrayList;

public class Entry {

	private int eid;
	private String edate;
	private String econtent;
	private String device;
	private boolean currentlyBeingEdited;
	private ArrayList<Topic> topics;

	public Entry() {}	
	
	public Entry(int eid, String edate, String econtent, String device) {		
		this.eid = eid;
		this.edate = edate;
		this.econtent = econtent;
		this.device = device;		
	}

	public Entry(int eid, String edate, String econtent, String device,ArrayList<Topic> topics,boolean currentlyBeingEdited) {		
		this.eid = eid;
		this.edate = edate;
		this.econtent = econtent;
		this.device = device;
		this.topics = topics;
		this.currentlyBeingEdited = currentlyBeingEdited;
	}

	public int getEid() {
		return eid;
	}

	public void setEid(int eid) {
		this.eid = eid;
	}

	public String getEdate() {
		return edate;
	}

	public void setEdate(String edate) {
		this.edate = edate;
	}

	public String getEcontent() {
		return econtent;
	}

	public void setEcontent(String econtent) {
		this.econtent = econtent;
	}

	public String getDevice() {
		return device;
	}

	public ArrayList<Topic> getTopics() {
		return topics;
	}
	public void setTopics(ArrayList<Topic> topics) {
		this.topics = topics;
	}
	public void setDevice(String device) {
		this.device = device;
	}

	public boolean isCurrentlyBeingEdited() {
		return currentlyBeingEdited;
	}

	public void setCurrentlyBeingEdited(boolean currentlyBeingEdited) {
		this.currentlyBeingEdited = currentlyBeingEdited;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (currentlyBeingEdited ? 1231 : 1237);
		result = prime * result + ((device == null) ? 0 : device.hashCode());
		result = prime * result
				+ ((econtent == null) ? 0 : econtent.hashCode());
		result = prime * result + ((edate == null) ? 0 : edate.hashCode());
		result = prime * result + eid;
		result = prime * result + ((topics == null) ? 0 : topics.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Entry other = (Entry) obj;
		if (currentlyBeingEdited != other.currentlyBeingEdited)
			return false;
		if (device == null) {
			if (other.device != null)
				return false;
		} else if (!device.equals(other.device))
			return false;
		if (econtent == null) {
			if (other.econtent != null)
				return false;
		} else if (!econtent.equals(other.econtent))
			return false;
		if (edate == null) {
			if (other.edate != null)
				return false;
		} else if (!edate.equals(other.edate))
			return false;
		if (eid != other.eid)
			return false;
		if (topics == null) {
			if (other.topics != null)
				return false;
		} else if (!topics.equals(other.topics))
			return false;
		return true;
	}
	
	
	
	

	
	

}
