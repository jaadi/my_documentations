package documentations.model;

public class EntryTopicJunction {

	private int jId;
	private int entryId;
	private int topicId;

	public EntryTopicJunction() {
	}

	public EntryTopicJunction(int jId, int entryId, int topicId) {
		
		this.jId = jId;
		this.entryId = entryId;
		this.topicId = topicId;
	}

	public int getjId() {
		return jId;
	}

	public void setjId(int jId) {
		this.jId = jId;
	}

	public int getEntryId() {
		return entryId;
	}

	public void setEntryId(int entryId) {
		this.entryId = entryId;
	}

	public int getTopicId() {
		return topicId;
	}

	public void setTopicId(int topicId) {
		this.topicId = topicId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + entryId;
		result = prime * result + jId;
		result = prime * result + topicId;
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
		EntryTopicJunction other = (EntryTopicJunction) obj;
		if (entryId != other.entryId)
			return false;
		if (jId != other.jId)
			return false;
		if (topicId != other.topicId)
			return false;
		return true;
	}
	
	

}
