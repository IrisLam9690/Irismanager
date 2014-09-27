package edu.manager.bean.basic;



public class CVBean {

	public CVBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public CVBean(int id, String title, String content, Date time) {
		super();
		this.id = id;
		this.title = title;
		this.content = content;
		this.time = time;
	}

	@Id
	@GeneratedValue
	@Column(name = "id")
	private int id;
	
	@Column(name = "title")
	private String title;
	
	@Column(name = "content")
	private String content;
	
	@Column(name = "time")
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date time;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
}

