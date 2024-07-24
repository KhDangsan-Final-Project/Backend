package com.ms3.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.ms3.dto.LibraryDTO;
import com.ms3.mapper.LibraryMapper;

@Service
public class LibraryService {
    private final LibraryMapper mapper;

    public LibraryService(LibraryMapper mapper) {
        this.mapper = mapper;
    }

    public List<LibraryDTO> getPokemon(String userId) {
        return mapper.getPokemon(userId);
    }
}
