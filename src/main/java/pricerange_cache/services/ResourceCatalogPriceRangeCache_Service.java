package pricerange_cache.services;

import org.slf4j.Logger;
import java.util.Optional;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pricerange_cache.model.master.ResourceCatalogPriceRangeCache;
import pricerange_cache.model.repo.IResourceCatalogPriceRangeCache_Repo;
import pricerange_cache.model.repo.ResourceCatalogPriceRangeCache_Repo;

@Service("resourceCatalogPriceRangeCacheServ")
@Scope(value = "prototype", proxyMode = ScopedProxyMode.TARGET_CLASS)
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
public class ResourceCatalogPriceRangeCache_Service implements IResourceCatalogPriceRangeCache_Service {
	// private static final Logger logger =
	// LoggerFactory.getLogger(ResourceCatalogMasterService.class);

	@Autowired
	private IResourceCatalogPriceRangeCache_Repo resourceCatalogPriceRangeRepo;

	@Autowired
	private Executor asyncExecutor;
	
	// abstract public ArrayList<Long> getAllResourcesForPriceRanges();
	
	@Override	
	@Cacheable(value="priceRangeCache",key = "new org.springframework.cache.interceptor.SimpleKey(#resCatSeqNo)")
	public ArrayList<ResourceCatalogPriceRangeCache> getAllResourceCatalogPriceRange(Long resCatSeqNo)
			throws InterruptedException, ExecutionException 
	{

		CompletableFuture<ArrayList<ResourceCatalogPriceRangeCache>> future = CompletableFuture.supplyAsync(() -> {			
			CompletableFuture<ArrayList<ResourceCatalogPriceRangeCache>> cresCatList = resourceCatalogPriceRangeRepo.findResourceCatalogPriceRanges(resCatSeqNo);
			ArrayList<ResourceCatalogPriceRangeCache> cresCatList2 = null;
			try {
				cresCatList2 = cresCatList.get();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return cresCatList2;
		},asyncExecutor);

		return future.get();

	}
}