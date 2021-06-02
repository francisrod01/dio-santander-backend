package com.project.bootcamp.service;

import java.util.List;
import java.util.Optional;

import com.project.bootcamp.dto.StockDTO;
import com.project.bootcamp.exceptions.BusinessException;
import com.project.bootcamp.mapper.StockMapper;
import com.project.bootcamp.model.Stock;
import com.project.bootcamp.repository.StockRepository;
import com.project.bootcamp.util.MessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StockService {

    @Autowired
    private StockRepository repository;

    @Autowired
    private StockMapper mapper;

    @Transactional
    public StockDTO save(StockDTO dto) {
        Optional<Stock> optional = repository.findByNameAndDate(dto.getName(), dto.getDate());
        if (optional.isPresent()) {
            throw new BusinessException(MessageUtils.STOCK_ALREADY_EXISTS);
        }

        Stock stock = mapper.toEntity(dto);
        repository.save(stock);
        return mapper.toDto(stock);
    }

    @Transactional
    public StockDTO update(StockDTO dto) {
        Optional<Stock> optional = repository.findByStockUpdate(dto.getName(), dto.getDate(), dto.getId());
        if (optional.isPresent()) {
            throw new BusinessException(MessageUtils.STOCK_ALREADY_EXISTS);
        }

        Stock stock = mapper.toEntity(dto);
        repository.save(stock);
        return mapper.toDto(stock);
    }

    @Transactional(readOnly = true)
    public List<StockDTO> findAll() {
        return mapper.toDto(repository.findAll());
    }
}
