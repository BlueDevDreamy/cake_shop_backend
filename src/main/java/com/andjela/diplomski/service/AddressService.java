package com.andjela.diplomski.service;

import com.andjela.diplomski.dto.address.AddressCreateDto;
import com.andjela.diplomski.dto.address.AddressDto;
import com.andjela.diplomski.dto.address.AddressMapper;
import com.andjela.diplomski.entity.Address;
import com.andjela.diplomski.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
@Service
public class AddressService implements IAddressService {

    private final AddressRepository addressRepository;

    @Override
    public AddressDto createAddress(AddressCreateDto addressCreateDto) {
        Address address = Address.builder()
                .streetAddress(addressCreateDto.getStreetAddress())
                .city(addressCreateDto.getCity())
                .zipCode(addressCreateDto.getZipCode())
                .createdAt(LocalDateTime.now())
                .build();

        addressRepository.save(address);
        AddressDto addressDto = AddressMapper.MAPPER.mapToAddressDto(address);
        return addressDto;
    }
}
