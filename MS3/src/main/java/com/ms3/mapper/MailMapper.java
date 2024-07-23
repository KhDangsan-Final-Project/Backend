package com.ms3.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ms3.dto.MailDTO;

@Mapper
public interface MailMapper {

	List<MailDTO> selectMail(String userId);

	int sendMail(MailDTO mailDTO);

	int deleteMail(int mailNo);
	
	MailDTO selectMailDetail(@Param("mailNo") int mailNo, @Param("userId") String userId);
	
}
