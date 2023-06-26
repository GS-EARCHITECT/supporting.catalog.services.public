package ratings_cache.model.repo;

import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

import location_classes_cache.model.master.ResourceCatalogLocaStructureCache;
import ratings_cache.model.master.ResourceCatalogRatingsCache;

@Repository("resourceCatalogRatingsRepo")
public class ResourceCatalogRatingsCache_Repo implements IResourceCatalogRatingsCache_Repo  
{
	//private static final Logger logger = LoggerFactory.getLogger(ResourceCatalogReadMasterRepo.class);
	
	@Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Async("asyncExecutor")	
	public CompletableFuture<ArrayList<ResourceCatalogRatingsCache>> findResourceCatalogRatingss(Long resCatSeqNo)
	{		
	
		CompletableFuture<ArrayList<ResourceCatalogRatingsCache>> future = CompletableFuture.supplyAsync(() -> 
		{
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("resCatSeqNo", resCatSeqNo);
		String qryString = "select * from resource_catalog_ratings a where a.resource_catalog_seq_no = :resCatSeqNo";
				
		ArrayList<ResourceCatalogRatingsCache> resourceCatalogRatings =  (ArrayList<ResourceCatalogRatingsCache>)namedParameterJdbcTemplate.query
				(
				 qryString,mapSqlParameterSource,
	                (rs, rowNum) ->
	                        new ResourceCatalogRatingsCache(	                                
	                                rs.getFloat("rating"),	                                
	                                rs.getLong("resource_catalog_seq_no")
	                                )
                        );                        
            			return resourceCatalogRatings;
            		});

            		return future;
            	}

			
}
