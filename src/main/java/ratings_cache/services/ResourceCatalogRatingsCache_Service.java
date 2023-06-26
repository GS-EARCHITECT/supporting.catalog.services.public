package ratings_cache.services;

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

import ratings_cache.model.master.ResourceCatalogRatingsCache;
import ratings_cache.model.repo.IResourceCatalogRatingsCache_Repo;
import ratings_cache.model.repo.ResourceCatalogRatingsCache_Repo;

@Service("resourceCatalogRatingsCacheServ")
@Scope(value = "prototype", proxyMode = ScopedProxyMode.TARGET_CLASS)
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
public class ResourceCatalogRatingsCache_Service implements IResourceCatalogRatingsCache_Service {
	// private static final Logger logger =
	// LoggerFactory.getLogger(ResourceCatalogMasterService.class);

	@Autowired
	private IResourceCatalogRatingsCache_Repo resourceCatalogRatingsRepo;
	
	@Autowired
	private Executor asyncExecutor;
	
	@Override	
	@Cacheable(value="ratingsCache",key = "new org.springframework.cache.interceptor.SimpleKey(#resCatSeqNo)")
	public ArrayList<ResourceCatalogRatingsCache> getAllResourceCatalogRatings(Long resCatSeqNo)
			throws InterruptedException, ExecutionException 
	{

		CompletableFuture<ArrayList<ResourceCatalogRatingsCache>> future = CompletableFuture.supplyAsync(() -> {			
			CompletableFuture<ArrayList<ResourceCatalogRatingsCache>> cresCatList = resourceCatalogRatingsRepo.findResourceCatalogRatingss(resCatSeqNo);
			ArrayList<ResourceCatalogRatingsCache> cresCatList2 = null;
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