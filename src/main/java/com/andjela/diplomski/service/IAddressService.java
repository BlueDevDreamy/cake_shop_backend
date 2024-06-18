package com.andjela.diplomski.service;

import com.andjela.diplomski.dto.address.AddressCreateDto;
import com.andjela.diplomski.dto.address.AddressDto;

public interface IAddressService {
    AddressDto createAddress(AddressCreateDto addressDto);
}
