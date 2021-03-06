package com.br.cooapi.combustivel;

import com.br.cooapi.abasteci.Abasteci;
import com.br.cooapi.abasteci.AbasteciDto;
import com.br.cooapi.abasteci.AbasteciForm;
import com.br.cooapi.abasteci.AbasteciRepositories;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CombustivelService {
    @Autowired
    private CombustivelRepositories repositories;

    public List<CombustivelDto> findAll () {
        List<Combustivel> result = repositories.findAll();
        return result.stream().map(CombustivelDto::from).collect(Collectors.toList());
    }

    public CombustivelDto create (CombustivelForm obj) {
        Combustivel combustivel = Combustivel.from(obj);
        return CombustivelDto.from(repositories.save(combustivel));
    }

    public CombustivelDto findById (Integer id) {
        Optional<Combustivel> obj = repositories.findById(id);
        return CombustivelDto.from(obj.get());
    }

    public CombustivelDto update (int id, CombustivelForm combustivel) {
        ModelMapper modelMapper = new ModelMapper();
        Combustivel combustivel1 = repositories.findById(id).orElseThrow(() -> {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        });
        modelMapper.map(combustivel, combustivel1);
        return CombustivelDto.from(repositories.save(combustivel1));
    }

    public void delete (Integer idCombustivel) {
        findById(idCombustivel);
        try {
            repositories.deleteById(idCombustivel);
        } catch (DataAccessException e) {
            throw new DataIntegrityViolationException("Objeto não pode ser deletado!!!");
        }

    }
}
