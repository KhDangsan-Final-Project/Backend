package com.ms2.dto;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Alias("pokemon")
public class PokemonDTO {
	private int id;
	private String englishName;
	private String koreanName;
	
	public PokemonDTO() {
	}
	
	public PokemonDTO(int id, String englishName, String koreanName) {
		super();
		this.id = id;
		this.englishName = englishName;
		this.koreanName = koreanName;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getEnglishName() {
		return englishName;
	}
	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}
	public String getKoreanName() {
		return koreanName;
	}
	public void setKoreanName(String koreanName) {
		this.koreanName = koreanName;
	}
	@Override
	public String toString() {
		return "PokemonDTO [id=" + id + ", englishName=" + englishName + ", koreanName=" + koreanName + "]";
	}
	
	
}
