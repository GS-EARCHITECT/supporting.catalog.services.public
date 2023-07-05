package compclasses_cache.services;

import org.slf4j.Logger;
import java.util.Optional;
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
import compclasses_cache.model.master.ServiceCatalogCompClassesCache;
import compclasses_cache.model.repo.IServiceCatalogCompClassesCache_Repo;

@Service("serviceCatalogCompClassesCacheServ")
@Scope(value = "prototype", proxyMode = ScopedProxyMode.TARGET_CLASS)
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
public class ServiceCatalogCompClassesCache_Service implements IServiceCatalogCompClassesCache_Service {
	// private static final Logger logger =
	// LoggerFactory.getLogger(ServiceCatalogMasterService.class);

	@Autowired
	private IServiceCatalogCompClassesCache_Repo serviceCatalogCompClassesCacheRepo;

	@Autowired
	private Executor asyncExecutor;

	// abstract public ArrayList<Long> getAllServicesForCompClassess();

	@Override	
	@Cacheable(value="compClassesCache",key = "new org.springframework.cache.interceptor.SimpleKey(#srvCatSeqNo)")
	public CopyOnWriteArrayList<ServiceCatalogCompClassesCache> getAllServiceCatalogCompClasses(Long srvCatSeqNo)
			throws InterruptedException, ExecutionException 
	{
		CompletableFuture<CopyOnWriteArrayList<ServiceCatalogCompClassesCache>> future = CompletableFuture.supplyAsync(() -> 
		{			
			CopyOnWriteArrayList<ServiceCatalogCompClassesCache> csrvCatList = serviceCatalogCompClassesCacheRepo.findServiceCatalogCompClasses(srvCatSeqNo);
			return csrvCatList;
		},asyncExecutor);

		return future.get();
	}
}