package finals;
public class MusicObject {
	
	private String id;
	private String title;
	private String albumTitle;
	private String albumArtist;
	private String year;
	private String genre;
	private String artist;
	
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
	public String getAlbumTitle() {
		return albumTitle;
	}
	public void setAlbumTitle(String albumTitle) {
		this.albumTitle = albumTitle;
	}
	public String getAlbumArtist() {
		return albumArtist;
	}
	public void setAlbumArtist(String albumArtist) {
		this.albumArtist = albumArtist;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	public String getArtist() {
		return artist;
	}
	public void setArtist(String artist) {
		this.artist = artist;
	}
	
	public String getTitle(Object valueAt) {
		return title;
	}
	
}
