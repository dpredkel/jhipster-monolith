package com.pda.randomizer.repository.search;

import com.pda.randomizer.domain.Entry;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Entry} entity.
 */
public interface EntrySearchRepository extends ElasticsearchRepository<Entry, Long> {
}
