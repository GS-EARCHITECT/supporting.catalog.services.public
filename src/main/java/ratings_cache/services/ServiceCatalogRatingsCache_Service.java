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
import ratings_cache.model.master.ServiceCatalogRatingsCache;
import ratings_cache.model.repo.ServiceCatalogRatingsCache_Repo;

@Service("serviceCatalogRatingsCacheServ")
@Scope(value = "prototype", proxyMode = ScopedProxyMode.TARGET_CLASS)
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
public class ServiceCatalogRatingsCache_Service implements IServiceCatalogRatingsCache_Service 
{
	// private static final Logger logger =
	// LoggerFactory.getLogger(ServiceCatalogMasterService.class);

	@Autowired
	private ServiceCatalogRatingsCache_Repo serviceCatalogRatingsRepo;
	
	@Autowired
	private Executor asyncExecutor;
	
	@Override	
	@Cacheable(value="ratingsCache",key = "new org.springframework.cache.interceptor.SimpleKey(#srvCatSeqNo)")
	public CopyOnWriteArrayList<ServiceCatalogRatingsCache> getAllServiceCatalogRatings(Long srvCatSeqNo)
			throws InterruptedException, ExecutionException 
	{
		CompletableFuture<CopyOnWriteArrayList<ServiceCatalogRatingsCache>> future = CompletableFuture.supplyAsync(() -> {			
		CopyOnWriteArrayList<ServiceCatalogRatingsCache> csrvCatList = serviceCatalogRatingsRepo.findServiceCatalogRatings(srvCatSeqNo);
		return csrvCatList;
		},asyncExecutor);
		return future.get();
	}

}