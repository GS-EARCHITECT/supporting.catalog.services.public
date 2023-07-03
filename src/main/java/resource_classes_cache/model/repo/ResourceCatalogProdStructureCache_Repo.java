package resource_classes_cache.model.repo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import resource_classes_cache.model.master.ResourceCatalogProdStructureCache;

@Repository("resourceCatalogProdStructureRepo")
public class ResourceCatalogProdStructureCache_Repo implements IResourceCatalogProdStructureCache_Repo  
{
	//private static final Logger logger = LoggerFactory.getLogger(ResourceCatalogReadMasterRepo.class);
	
	@Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Autowired
	private Executor asyncExecutor;
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public CopyOnWriteArrayList<ResourceCatalogProdStructureCache> findResourceCatalogProdStructures(Long resCatSeqNo)
	{		
		
		CompletableFuture<CopyOnWriteArrayList<ResourceCatalogProdStructureCache>> future = CompletableFuture.supplyAsync(() -> 
		{
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("resCatSeqNo", resCatSeqNo);		
		String qryString = "select * from resource_catalog_prodstructure a where (a.resource_catalog_seq_no = :resCatSeqNo)";
		List<ResourceCatalogProdStructureCache> resourceCatalogProdStructures =  
				namedParameterJdbcTemplate.query
				(
				 qryString,mapSqlParameterSource,
	                (rs, rowNum) ->
	                        new ResourceCatalogProdStructureCache(	                                
	                                rs.getLong("resource_class_seq_no"),
	                                rs.getLong("par_resource_class_seq_no"),
	                                rs.getLong("resource_catalog_seq_no")
	                        )
	        );
		
		CopyOnWriteArrayList<ResourceCatalogProdStructureCache> cList= new CopyOnWriteArrayList<ResourceCatalogProdStructureCache>();
		cList.addAll(resourceCatalogProdStructures);
		return cList;
		},asyncExecutor);
		 
		CopyOnWriteArrayList<ResourceCatalogProdStructureCache> cList=null;
		try {
			cList = future.get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return cList;	
	}			
}