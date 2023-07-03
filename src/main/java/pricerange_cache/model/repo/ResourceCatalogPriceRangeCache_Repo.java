package pricerange_cache.model.repo;

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
import pricerange_cache.model.master.ResourceCatalogPriceRangeCache;

@Repository("resourceCatalogPriceRangeRepo")
public class ResourceCatalogPriceRangeCache_Repo implements IResourceCatalogPriceRangeCache_Repo  
{
	private static final Logger logger = LoggerFactory.getLogger(ResourceCatalogPriceRangeCache_Repo.class);
	
	@Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Autowired
	private Executor asyncExecutor;
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)	
	public CopyOnWriteArrayList<ResourceCatalogPriceRangeCache> findResourceCatalogPriceRanges(Long resCatSeqNo)
	{		
		CompletableFuture<CopyOnWriteArrayList<ResourceCatalogPriceRangeCache>> future = CompletableFuture.supplyAsync(() -> 
		{
		CopyOnWriteArrayList<ResourceCatalogPriceRangeCache> cList = null;
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("resCatSeqNo", resCatSeqNo);
		String qryString = "select * from resource_catalog_pricerange a where (rownum=1 and a.resource_catalog_seq_no = :resCatSeqNo)";
		List<ResourceCatalogPriceRangeCache> resourceCatalogPriceRange =  namedParameterJdbcTemplate.query
				(
				 qryString,mapSqlParameterSource,
	                (rs, rowNum) ->
	                        new ResourceCatalogPriceRangeCache(	                                
	                                rs.getFloat("price_fr"),
	                                rs.getFloat("price_to"),
	                                rs.getLong("resource_catalog_seq_no")
	                                )
                        );
		
		if(resourceCatalogPriceRange!=null)
		{			
		cList = new CopyOnWriteArrayList<ResourceCatalogPriceRangeCache>();
		for (int i = 0; i < resourceCatalogPriceRange.size(); i++) 
		{
		cList.add(resourceCatalogPriceRange.get(i));	
		}		
		}
		   			return cList;
            		},asyncExecutor);

		CopyOnWriteArrayList<ResourceCatalogPriceRangeCache> cList = null;
            		
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
