package com.ms2.dto;

import java.util.List;

import org.apache.ibatis.type.Alias;

@Alias("user")
public class UserDTO {
    private String id;
    private String nickname;
    private Integer grantNo;
    private String profile;
    private Integer matchWin;
    private List<PokemonDTO> pokemonList;
    
    
    
    
	public UserDTO() { }




	public String getId() {
		return id;
	}




	public void setId(String id) {
		this.id = id;
	}




	public String getNickname() {
		return nickname;
	}




	public void setNickname(String nickname) {
		this.nickname = nickname;
	}




	public Integer getGrantNo() {
		return grantNo;
	}




	public void setGrantNo(Integer grantNo) {
		this.grantNo = grantNo;
	}




	public String getProfile() {
		return profile;
	}




	public void setProfile(String profile) {
		this.profile = profile;
	}




	public Integer getMatchWin() {
		return matchWin;
	}




	public void setMatchWin(Integer matchWin) {
		this.matchWin = matchWin;
	}




	public List<PokemonDTO> getPokemonList() {
		return pokemonList;
	}




	public void setPokemonList(List<PokemonDTO> pokemonList) {
		this.pokemonList = pokemonList;
	}




	@Override
	public String toString() {
		return "UserDTO [id=" + id + ", nickname=" + nickname + ", grantNo=" + grantNo + ", profile=" + profile
				+ ", matchWin=" + matchWin + ", pokemonList=" + pokemonList + "]";
	}








   
}
