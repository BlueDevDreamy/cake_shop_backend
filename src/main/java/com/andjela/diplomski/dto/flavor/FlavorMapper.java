package com.andjela.diplomski.dto.flavor;

import com.andjela.diplomski.entity.codebook.Flavor;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface FlavorMapper {
    FlavorMapper MAPPER = Mappers.getMapper(FlavorMapper.class);

    FlavorDto mapToFlavorDto(Flavor flavor);
    Flavor mapToFlavor(FlavorDto flavorDto);
}
