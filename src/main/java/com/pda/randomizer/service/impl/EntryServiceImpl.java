package com.pda.randomizer.service.impl;

import com.pda.randomizer.service.EntryService;
import com.pda.randomizer.domain.Entry;
import com.pda.randomizer.repository.EntryRepository;
import com.pda.randomizer.repository.search.EntrySearchRepository;
import com.pda.randomizer.service.dto.EntryDTO;
import com.pda.randomizer.service.mapper.EntryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Entry}.
 */
@Service
@Transactional
public class EntryServiceImpl implements EntryService {

    private final Logger log = LoggerFactory.getLogger(EntryServiceImpl.class);

    private final EntryRepository entryRepository;

    private final EntryMapper entryMapper;

    private final EntrySearchRepository entrySearchRepository;

    public EntryServiceImpl(EntryRepository entryRepository, EntryMapper entryMapper, EntrySearchRepository entrySearchRepository) {
        this.entryRepository = entryRepository;
        this.entryMapper = entryMapper;
        this.entrySearchRepository = entrySearchRepository;
    }

    @Override
    public EntryDTO save(EntryDTO entryDTO) {
        log.debug("Request to save Entry : {}", entryDTO);
        Entry entry = entryMapper.toEntity(entryDTO);
        entry = entryRepository.save(entry);
        EntryDTO result = entryMapper.toDto(entry);
        entrySearchRepository.save(entry);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EntryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Entries");
        return entryRepository.findAll(pageable)
            .map(entryMapper::toDto);
    }


    public Page<EntryDTO> findAllWithEagerRelationships(Pageable pageable) {
        return entryRepository.findAllWithEagerRelationships(pageable).map(entryMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EntryDTO> findOne(Long id) {
        log.debug("Request to get Entry : {}", id);
        return entryRepository.findOneWithEagerRelationships(id)
            .map(entryMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Entry : {}", id);
        entryRepository.deleteById(id);
        entrySearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EntryDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Entries for query {}", query);
        return entrySearchRepository.search(queryStringQuery(query), pageable)
            .map(entryMapper::toDto);
    }
}
