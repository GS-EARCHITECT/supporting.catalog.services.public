package pricerange_cache.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pricerange_cache.model.master.ServiceCatalogPriceRangeCache;
import pricerange_cache.model.repo.ServiceCatalogPriceRangeCache_Repo;

@Service("serviceCatalogPriceRangeCacheServ")
@Scope(value = "prototype", proxyMode = ScopedProxyMode.TARGET_CLASS)
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
public class ServiceCatalogPriceRangeCache_Service implements IServiceCatalogPriceRangeCache_Service {
	 private static final Logger logger = LoggerFactory.getLogger(ServiceCatalogPriceRangeCache_Service.class);

	@Autowired
	private ServiceCatalogPriceRangeCache_Repo serviceCatalogPriceRangeRepo;

	@Autowired
	private Executor asyncExecutor;
	
	// abstract public ArrayList<Long> getAllServicesForPriceRanges();
	
	@Override	
	@Cacheable(value="priceRangeCache",key = "new org.springframework.cache.interceptor.SimpleKey(#srvCatSeqNo)")
	public ServiceCatalogPriceRangeCache getAllServiceCatalogPriceRange(Long srvCatSeqNo)
			throws InterruptedException, ExecutionException 
	{

		CompletableFuture<ServiceCatalogPriceRangeCache> future = CompletableFuture.supplyAsync(() -> {			
		CopyOnWriteArrayList<ServiceCatalogPriceRangeCache> csrvCatList = serviceCatalogPriceRangeRepo.findServiceCatalogPriceRanges(srvCatSeqNo);
		ServiceCatalogPriceRangeCache csrvCat = null;
		if(csrvCatList!=null)
		{
			csrvCat =csrvCatList.get(0);
		}
		
			return csrvCat;
		},asyncExecutor);

		return future.get();

	}
}