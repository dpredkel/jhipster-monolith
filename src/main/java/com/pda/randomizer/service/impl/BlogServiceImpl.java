package com.pda.randomizer.service.impl;

import com.pda.randomizer.service.BlogService;
import com.pda.randomizer.domain.Blog;
import com.pda.randomizer.repository.BlogRepository;
import com.pda.randomizer.repository.search.BlogSearchRepository;
import com.pda.randomizer.service.dto.BlogDTO;
import com.pda.randomizer.service.mapper.BlogMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Blog}.
 */
@Service
@Transactional
public class BlogServiceImpl implements BlogService {

    private final Logger log = LoggerFactory.getLogger(BlogServiceImpl.class);

    private final BlogRepository blogRepository;

    private final BlogMapper blogMapper;

    private final BlogSearchRepository blogSearchRepository;

    public BlogServiceImpl(BlogRepository blogRepository, BlogMapper blogMapper, BlogSearchRepository blogSearchRepository) {
        this.blogRepository = blogRepository;
        this.blogMapper = blogMapper;
        this.blogSearchRepository = blogSearchRepository;
    }

    @Override
    public BlogDTO save(BlogDTO blogDTO) {
        log.debug("Request to save Blog : {}", blogDTO);
        Blog blog = blogMapper.toEntity(blogDTO);
        blog = blogRepository.save(blog);
        BlogDTO result = blogMapper.toDto(blog);
        blogSearchRepository.save(blog);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public List<BlogDTO> findAll() {
        log.debug("Request to get all Blogs");
        return blogRepository.findAll().stream()
            .map(blogMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<BlogDTO> findOne(Long id) {
        log.debug("Request to get Blog : {}", id);
        return blogRepository.findById(id)
            .map(blogMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Blog : {}", id);
        blogRepository.deleteById(id);
        blogSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BlogDTO> search(String query) {
        log.debug("Request to search Blogs for query {}", query);
        return StreamSupport
            .stream(blogSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(blogMapper::toDto)
        .collect(Collectors.toList());
    }
}
