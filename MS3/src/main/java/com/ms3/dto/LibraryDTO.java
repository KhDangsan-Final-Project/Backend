package com.ms3.dto;

import org.apache.ibatis.type.Alias;

@Alias("library")
public class LibraryDTO {
	
	private String id;
	private int pokemonNum;
	
	public LibraryDTO(String id, int pokemonNum) {
		this.id = id;
		this.pokemonNum = pokemonNum;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getPokemonNum() {
		return pokemonNum;
	}

	public void setPokemonNum(int pokemonNum) {
		this.pokemonNum = pokemonNum;
	}

	@Override
	public String toString() {
		return "LibraryDTO [id=" + id + ", pokemonNum=" + pokemonNum + "]";
	}
	
	
	
}
