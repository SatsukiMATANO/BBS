package bbs.beans;

public class Department {
	private int id;
	private int branch_id;
	private String departmentname;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getBranch_id(){
		return branch_id;
	}
	public void setBranch_id(int branch_id){
		this.branch_id = branch_id;
	}
	
	public String getDepartmentname() {
		return departmentname;
	}
	public void setDepartmentname(String departmentname) {
		this.departmentname = departmentname;
	}
	
}
