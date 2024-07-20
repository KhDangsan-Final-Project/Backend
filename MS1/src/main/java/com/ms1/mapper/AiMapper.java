package com.ms1.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AiMapper {

	int requestImg();

	int insertRequest();

	int insertDetected(String detectedText, String id);

	List<String> selectDetected(String id);


}
