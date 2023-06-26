package resource_classes_cache.model.repo;

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
import resource_classes_cache.model.master.ResourceCatalogProdStructureCache;

@Repository("resourceCatalogProdStructureRepo")
public class ResourceCatalogProdStructureCache_Repo implements IResourceCatalogProdStructureCache_Repo  
{
	//private static final Logger logger = LoggerFactory.getLogger(ResourceCatalogReadMasterRepo.class);
	
	@Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Autowired
	private Executor asyncExecutor;
	
	@Async
	public CompletableFuture<ArrayList<ResourceCatalogProdStructureCache>> findResourceCatalogProdStructures(Long resCatSeqNo)
	{		
		
		CompletableFuture<ArrayList<ResourceCatalogProdStructureCache>> future = CompletableFuture.supplyAsync(() -> 
		{
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("resCatSeqNo", resCatSeqNo);		
		String qryString = "select * from resource_catalog_prodstructure a where (a.resource_catalog_seq_no = :resCatSeqNo)";
		ArrayList<ResourceCatalogProdStructureCache> resourceCatalogProdStructures =  
				(ArrayList<ResourceCatalogProdStructureCache>)namedParameterJdbcTemplate.query
				(
				 qryString,mapSqlParameterSource,
	                (rs, rowNum) ->
	                        new ResourceCatalogProdStructureCache(
	                                rs.getLong("resource_catalog_seq_no"),
	                                rs.getLong("resource_class_seq_no"),
	                                rs.getLong("resource_class_seq_no")	                                
	                        )
	        );
		return resourceCatalogProdStructures;
		},asyncExecutor);
		 
		return future;	
	}			
}
