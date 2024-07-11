package com.andjela.diplomski.dto.cake;

import com.andjela.diplomski.entity.Cake;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface CakeMapper {
    CakeMapper MAPPER = Mappers.getMapper(CakeMapper.class);

    CakeDto mapToCakeDto(Cake cake);

    Cake mapToCake(CakeDto cakeDto);

    List<CakeDto> mapToListCakeDto(List<Cake> cakes);

}
