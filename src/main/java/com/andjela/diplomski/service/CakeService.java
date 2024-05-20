package com.andjela.diplomski.service;

import com.andjela.diplomski.dto.cake.CakeDto;
import com.andjela.diplomski.dto.cake.CakeMapper;
import com.andjela.diplomski.entity.Cake;
import com.andjela.diplomski.entity.codebook.Category;
import com.andjela.diplomski.exception.DataNotValidException;
import com.andjela.diplomski.exception.ResourceNotFoundException;
import com.andjela.diplomski.repository.CakeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CakeService implements ICakeService {
    private final CakeRepository cakeRepository;

    @Override
    public CakeDto createCake(CakeDto cakeDto) {
        if (cakeDto.getTitle().isEmpty() || cakeDto.getTitle().length() < 2) {
            throw new DataNotValidException("Title must have 2 or more characters");
        }
        if (cakeDto.getPricePerKilo() < 0) {
            throw new DataNotValidException("Price pre kilo must be positive number");
        }
        if (cakeDto.getMinWeight() < 2) {
            throw new DataNotValidException("Min weight must be greater than 2");
        }
        if (cakeDto.getMaxWeight() > 30) {
            throw new DataNotValidException("Max weight must be less than 30");
        }
        if (cakeDto.getMinTier() < 1) {
            throw new DataNotValidException("Min tier must be greater than 1");
        }
        if (cakeDto.getMaxTier() > 6) {
            throw new DataNotValidException("Max tier must be less than 6");
        }
        if (cakeDto.getImageUrl().isEmpty()) {
            throw new DataNotValidException("Image url must not be empty");
        }
        Cake cake = Cake.builder()
                .title(cakeDto.getTitle())
                .pricePerKilo(cakeDto.getPricePerKilo())
                .decorationPrice(cakeDto.getDecorationPrice())
                .minWeight(cakeDto.getMinWeight())
                .maxWeight(cakeDto.getMaxWeight())
                .minTier(cakeDto.getMinTier())
                .maxTier(cakeDto.getMaxTier())
                .imageUrl(cakeDto.getImageUrl())
                .category(cakeDto.getCategory())
                .createdAt(LocalDateTime.now())
                .build();

        cakeRepository.save(cake);

        CakeDto createdCakeDto = CakeMapper.MAPPER.mapToCakeDto(cake);
        return createdCakeDto;
    }

    @Override
    public CakeDto getCakeById(Long cakeId) {
        Cake cake = cakeRepository.findById(cakeId).orElseThrow(() -> new ResourceNotFoundException("Didn't find cake with id:" + cakeId));
        CakeDto cakeDto = CakeMapper.MAPPER.mapToCakeDto(cake);
        return cakeDto;
    }

    @Override
    public List<CakeDto> getCakes() {
        List<CakeDto> foundCakes = new ArrayList<>();
        List<Cake> cakes = cakeRepository.findAll();

        for (Cake cake : cakes) {
            CakeDto cakeDto = CakeMapper.MAPPER.mapToCakeDto(cake);
            foundCakes.add(cakeDto);
        }
        return foundCakes;
    }

    @Override
    public CakeDto updateCake(Long id, CakeDto cakeDto) {
        Cake foundCake = cakeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Entity whit id " + id + " could not be updated"));
        boolean isChanged = false;

        if (cakeDto.getTitle().isEmpty() || cakeDto.getTitle().length() < 2) {
            throw new DataNotValidException("Title must have 2 or more characters");
        } else {
            isChanged = true;
            foundCake.setTitle(cakeDto.getTitle());
        }
        if (cakeDto.getPricePerKilo() < 0) {
            throw new DataNotValidException("Price pre kilo must be positive number");
        } else {
            isChanged = true;
            foundCake.setPricePerKilo(cakeDto.getPricePerKilo());
        }
        if (cakeDto.getDecorationPrice() < 0) {
            throw new DataNotValidException("Decoration price must be positive number");
        } else {
            isChanged = true;
            foundCake.setDecorationPrice(cakeDto.getDecorationPrice());
        }
        if (cakeDto.getMinWeight() < 2) {
            throw new DataNotValidException("Min weight must be greater than 2");
        } else {
            isChanged = true;
            foundCake.setMinTier(cakeDto.getMinTier());
        }
        if (cakeDto.getMaxWeight() > 30) {
            throw new DataNotValidException("Max weight must be less than 30");
        } else {
            isChanged = true;
            foundCake.setMaxWeight(cakeDto.getMaxWeight());
        }
        if (cakeDto.getMinTier() < 1) {
            throw new DataNotValidException("Min tier must be greater than 1");
        } else {
            isChanged = true;
            foundCake.setMinTier(cakeDto.getMinTier());
        }
        if (cakeDto.getMaxTier() > 6) {
            throw new DataNotValidException("Max tier must be less than 6");
        } else {
            isChanged = true;
            foundCake.setMaxTier(cakeDto.getMaxTier());
        }
        if (cakeDto.getImageUrl().isEmpty()) {
            throw new DataNotValidException("Image url must not be empty");
        } else {
            isChanged = true;
            foundCake.setImageUrl(cakeDto.getImageUrl());
        }
        if (cakeDto.getCategory() == null) {
            throw new DataNotValidException("Category must not be empty");
        } else {
            isChanged = true;
            foundCake.setCategory(cakeDto.getCategory());
        }

        if (isChanged) {
            foundCake.setUpdatedAt(LocalDateTime.now());
            cakeRepository.save(foundCake);
        }
        CakeDto updatedCakeDto = CakeMapper.MAPPER.mapToCakeDto(foundCake);

        return updatedCakeDto;
    }

    @Override
    public void deleteCake(Long cakeId) {
        if (!cakeRepository.existsById(cakeId)) {
            throw new ResourceNotFoundException("Didn't find cake with id:" + cakeId);
        }
        cakeRepository.deleteById(cakeId);
    }

    @Override
    public List<CakeDto> findCakeByCategory(String category) {
        List<Cake> cakes = cakeRepository.findByCategory(category);
        return CakeMapper.MAPPER.mapToListCakeDto(cakes);
    }

    @Override
    public List<CakeDto> searchCakes(String query) {
        List<Cake> cakes = cakeRepository.searchCakes(query);
        System.out.println("cakes --- " + cakes);
        return CakeMapper.MAPPER.mapToListCakeDto(cakes);
    }

    @Override
    public Page<Cake> getAllCakesPageable(String category, int minWeight, int maxWeight, int minTier, int maxTier, String sort, Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        List<Cake> cakes = cakeRepository.filterCakes(category, minWeight, maxWeight, minTier, maxTier, sort);

        int startIndex = (int) pageable.getOffset();
        int endIndex = Math.min(startIndex + pageable.getPageSize(), cakes.size());

        List<Cake> pageContent = cakes.subList(startIndex, endIndex);
        Page<Cake> filteredCakes = new PageImpl<>(pageContent, pageable, cakes.size());
        return filteredCakes;
    }

}
