package pricerange_cache.model.repo;

import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.concurrent.CompletableFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

import pricerange_cache.model.master.ResourceCatalogPriceRangeCache;
import ratings_cache.model.master.ResourceCatalogRatingsCache;

@Repository("resourceCatalogPriceRangeRepo")
public class ResourceCatalogPriceRangeCache_Repo implements IResourceCatalogPriceRangeCache_Repo  
{
	//private static final Logger logger = LoggerFactory.getLogger(ResourceCatalogReadMasterRepo.class);
	
	@Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Async("asyncExecutor")	
	public CompletableFuture<ArrayList<ResourceCatalogPriceRangeCache>> findResourceCatalogPriceRanges(Long resCatSeqNo)
	{		
		CompletableFuture<ArrayList<ResourceCatalogPriceRangeCache>> future = CompletableFuture.supplyAsync(() -> 
		{
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("resCatSeqNo", resCatSeqNo);
		String qryString = "select * from resource_catalog_pricerange a where a.resource_catalog_seq_no = :resCatSeqNo";
				
		ArrayList<ResourceCatalogPriceRangeCache> resourceCatalogPriceRange =  (ArrayList<ResourceCatalogPriceRangeCache>)namedParameterJdbcTemplate.query
				(
				 qryString,mapSqlParameterSource,
	                (rs, rowNum) ->
	                        new ResourceCatalogPriceRangeCache(	                                
	                                rs.getFloat("price_fr"),
	                                rs.getFloat("price_to"),
	                                rs.getLong("resource_catalog_seq_no")
	                                )
                        );                        
            			return resourceCatalogPriceRange;
            		});

            		return future;
            	}
	                                
}
