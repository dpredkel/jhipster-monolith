package com.pda.randomizer.service;

import com.pda.randomizer.service.dto.BlogDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.pda.randomizer.domain.Blog}.
 */
public interface BlogService {

    /**
     * Save a blog.
     *
     * @param blogDTO the entity to save.
     * @return the persisted entity.
     */
    BlogDTO save(BlogDTO blogDTO);

    /**
     * Get all the blogs.
     *
     * @return the list of entities.
     */
    List<BlogDTO> findAll();


    /**
     * Get the "id" blog.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BlogDTO> findOne(Long id);

    /**
     * Delete the "id" blog.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the blog corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @return the list of entities.
     */
    List<BlogDTO> search(String query);
}
