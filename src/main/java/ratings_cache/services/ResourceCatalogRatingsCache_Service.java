package ratings_cache.services;

import org.slf4j.Logger;
import java.util.Optional;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;
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

import ratings_cache.model.master.ResourceCatalogRatingsCache;
import ratings_cache.model.repo.IResourceCatalogRatingsCache_Repo;
import ratings_cache.model.repo.ResourceCatalogRatingsCache_Repo;

@Service("resourceCatalogRatingsCacheServ")
@Scope(value = "prototype", proxyMode = ScopedProxyMode.TARGET_CLASS)
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
public class ResourceCatalogRatingsCache_Service implements IResourceCatalogRatingsCache_Service 
{
	// private static final Logger logger =
	// LoggerFactory.getLogger(ResourceCatalogMasterService.class);

	@Autowired
	private IResourceCatalogRatingsCache_Repo resourceCatalogRatingsRepo;
	
	@Autowired
	private Executor asyncExecutor;
	
	@Override	
	@Cacheable(value="ratingsCache",key = "new org.springframework.cache.interceptor.SimpleKey(#resCatSeqNo)")
	public CopyOnWriteArrayList<ResourceCatalogRatingsCache> getAllResourceCatalogRatings(Long resCatSeqNo)
			throws InterruptedException, ExecutionException 
	{
		CompletableFuture<CopyOnWriteArrayList<ResourceCatalogRatingsCache>> future = CompletableFuture.supplyAsync(() -> {			
		CopyOnWriteArrayList<ResourceCatalogRatingsCache> cresCatList = resourceCatalogRatingsRepo.findResourceCatalogRatings(resCatSeqNo);
		return cresCatList;
		},asyncExecutor);
		return future.get();
	}
}