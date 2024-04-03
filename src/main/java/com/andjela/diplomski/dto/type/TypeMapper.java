package com.andjela.diplomski.dto.type;

import com.andjela.diplomski.dto.cake.CakeDto;
import com.andjela.diplomski.dto.cake.CakeMapper;
import com.andjela.diplomski.entity.Cake;
import com.andjela.diplomski.entity.codebook.Type;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface TypeMapper {
    TypeMapper MAPPER = Mappers.getMapper(TypeMapper.class);
    TypeDto mapToTypeDto(Type type);
    Type mapToType(TypeDto typeDto);
    List<TypeDto> mapToListTypeDto(List<Type> types);
}
