package com.br.cooapi.brand;

import lombok.Data;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import javax.persistence.*;

@Entity
@Data
public class Brand {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String marca;

    public static Brand from (BrandForm brandForm) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper.map(brandForm, Brand.class);
    }
}
