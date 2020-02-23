public class BookObject {
	
	private String id;
	private String title;
	private String author;
	private String pubYear;
	private String isbm;
	private String status;
//	private String artist;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getPubYear() {
		return pubYear;
	}
	public void setPubYear(String pubYear) {
		this.pubYear = pubYear;
	}
	public String getIsbm() {
		return isbm;
	}
	public void setIsbm(String isbm) {
		this.isbm = isbm;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	/*public String getArtist() {
		return artist;
	}
	public void setArtist(String artist) {
		this.artist = artist;
	}*/
	
	public String getTitle(Object valueAt) {
		return title;
	}
	
}
