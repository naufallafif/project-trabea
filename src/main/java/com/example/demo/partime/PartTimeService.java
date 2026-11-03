package com.example.demo.partime;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import com.example.demo.partime.dto.PartTimeResponse;

@Service
public class PartTimeService {
    private final PartTimeRepository partTimeRepository;
    private final PartTimeMapper partTimeMapper;

    public PartTimeService(PartTimeRepository partTimeRepository, PartTimeMapper partTimeMapper) {
        this.partTimeRepository = partTimeRepository;
        this.partTimeMapper = partTimeMapper;
    }

    @Bean
    public List<PartTimeResponse> getAllPartTimeEmployees() {
        return partTimeRepository.findAll()
                .stream()
                .map(partTimeMapper::toResponse)
                .toList();
    }

}
