package com.ms2.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ms2.dto.PokemonDTO;
import com.ms2.service.PokemonService;

@RestController
@RequestMapping("/ms2")
public class PokemonContoller {

	private final PokemonService service;

	public PokemonContoller(PokemonService service) {
		this.service = service;
	}
    
	@GetMapping("/pokemon/search")
    public List<PokemonDTO> searchPokemon(String koreanName) {
        return service.pokemonSelect(koreanName);
    }

}
