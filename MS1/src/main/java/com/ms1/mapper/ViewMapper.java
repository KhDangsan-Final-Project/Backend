package com.ms1.mapper;

import java.time.LocalDate;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ViewMapper {

	int getViewCount();

	int existsByIpAddressAndDate(String ipAddress, LocalDate today);

	void insertView(String ipAddress);

}
