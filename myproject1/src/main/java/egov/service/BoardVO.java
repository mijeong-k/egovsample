package egov.service;

public class BoardVO {

	private int unq;
	private String title;
	private String pass;
	private String name;
	private String content;
	private int hits;
	private String rdate;
	
	private int page = 1;
	private int startno;
	private int endno;
	
	public int getStartno() {
		return startno;
	}
	public void setStartno(int startno) {
		this.startno = startno;
	}
	public int getEndno() {
		return endno;
	}
	public void setEndno(int endno) {
		this.endno = endno;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getUnq() {
		return unq;
	}
	public void setUnq(int unq) {
		this.unq = unq;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	public int getHits() {
		return hits;
	}
	public void setHits(int hits) {
		this.hits = hits;
	}
	
	public String getRdate() {
		return rdate;
	}
	public void setRdate(String rdate) {
		this.rdate = rdate;
	}
	
}
