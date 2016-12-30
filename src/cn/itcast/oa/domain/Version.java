package cn.itcast.oa.domain;

import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

public class Version {

	private Long vid;//主键
	private Long version;//版本号
	private Date updateTime;//修改时间
	private String title;//主题
	private String content;//内容
	private Kynamic kynamic;//所属的知识管理类
	public Long getVid() {
		return vid;
	}
	public void setVid(Long vid) {
		this.vid = vid;
	}
	public Long getVersion() {
		return version;
	}
	public void setVersion(Long version) {
		this.version = version;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
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
	@JSON(serialize=false)
	public Kynamic getKynamic() {
		return kynamic;
	}
	public void setKynamic(Kynamic kynamic) {
		this.kynamic = kynamic;
	}
	
	
}
