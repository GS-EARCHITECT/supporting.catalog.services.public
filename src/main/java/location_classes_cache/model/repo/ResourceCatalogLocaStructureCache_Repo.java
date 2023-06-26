package location_classes_cache.model.repo;

import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;
import location_classes_cache.model.master.ResourceCatalogLocaStructureCache;

@Repository("resourceCatalogLocaStructureRepo")
public class ResourceCatalogLocaStructureCache_Repo implements IResourceCatalogLocaStructureCache_Repo  
{
	//private static final Logger logger = LoggerFactory.getLogger(ResourceCatalogReadMasterRepo.class);
	
	@Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Async("asyncExecutor")	
	public CompletableFuture<ArrayList<ResourceCatalogLocaStructureCache>> findResourceCatalogLocaStructures(Long resCatSeqNo)
	{		
	
		CompletableFuture<ArrayList<ResourceCatalogLocaStructureCache>> future = CompletableFuture.supplyAsync(() -> 
		{
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("resCatSeqNo", resCatSeqNo);
		String qryString = "select * from resource_catalog_locstructure a where (a.resource_catalog_seq_no = :resCatSeqNo)";				
		ArrayList<ResourceCatalogLocaStructureCache> resourceCatalogLocaStructures =  (ArrayList<ResourceCatalogLocaStructureCache>)namedParameterJdbcTemplate.query
				(
				 qryString,mapSqlParameterSource,
	                (rs, rowNum) ->
	                        new ResourceCatalogLocaStructureCache
	                        (	                                
	                                rs.getLong("place_class_seq_no"),
	                                rs.getLong("par_place_class_seq_no"),
	                                rs.getLong("resource_catalog_seq_no")
	                                )
	                        );
	                        
	            			return resourceCatalogLocaStructures;
	            		});

	            		return future;
	            	}
			
}