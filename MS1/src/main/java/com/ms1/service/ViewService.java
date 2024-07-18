package com.ms1.service;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.ms1.mapper.ViewMapper;

@Service
public class ViewService {
	private ViewMapper viewMapper;

	public ViewService(ViewMapper viewMapper) {
		this.viewMapper = viewMapper;
	}

	public int updateViewCount(String ipAddress) {
		LocalDate today = LocalDate.now();
		System.out.println(today);
        if (viewMapper.existsByIpAddressAndDate(ipAddress, today) == 0) {
            viewMapper.insertView(ipAddress);
        }
        return viewMapper.getViewCount();
	}

	public int getViewCount() {
		return viewMapper.getViewCount();
	}

	
	
}
