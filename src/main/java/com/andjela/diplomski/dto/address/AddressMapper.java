package com.andjela.diplomski.dto.address;

import com.andjela.diplomski.entity.Address;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
@Mapper
public interface AddressMapper {
    AddressMapper MAPPER = Mappers.getMapper(AddressMapper.class);

    AddressDto mapToAddressDto(Address address);
    Address mapToAddress(AddressDto addressDto);
}
