package com.ms3.dto;

import java.sql.Date;

import org.apache.ibatis.type.Alias;

@Alias("attendance")
public class AttendanceDTO {
	private String id;
	private Date attendanceDate;
	
	public AttendanceDTO(String id, Date attendanceDate) {
		this.id = id;
		this.attendanceDate = attendanceDate;
	}

	public AttendanceDTO() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getAttendanceDate() {
		return attendanceDate;
	}

	public void setAttendanceDate(Date attendanceDate) {
		this.attendanceDate = attendanceDate;
	}

	@Override
	public String toString() {
		return "AttendanceDTO [id=" + id + ", attendanceDate=" + attendanceDate + "]";
	}
	
	
}
