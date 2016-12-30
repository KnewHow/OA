package cn.itcast.oa.domain;

import java.util.Set;

public class Kynamic {

	private Long kid;//主键
	private Long pid;//父节点id
	private String name;//节点名称
	private Boolean isParent;//是否是父节点
	private Set<Version> versions;//所有版本
	public Long getKid() {
		return kid;
	}
	public void setKid(Long kid) {
		this.kid = kid;
	}
	public Long getPid() {
		return pid;
	}
	public void setPid(Long pid) {
		this.pid = pid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Boolean getIsParent() {
		return isParent;
	}
	public void setIsParent(Boolean isParent) {
		this.isParent = isParent;
	}
	public Set<Version> getVersions() {
		return versions;
	}
	public void setVersions(Set<Version> versions) {
		this.versions = versions;
	}
	
	
}
