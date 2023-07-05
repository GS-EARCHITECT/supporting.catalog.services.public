package location_classes_cache.services;

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
import location_classes_cache.model.master.ServiceCatalogLocaStructureCache;
import location_classes_cache.model.repo.ServiceCatalogLocaStructureCache_Repo;

@Service("serviceCatalogLocaStructureCacheServ")
@Scope(value = "prototype", proxyMode = ScopedProxyMode.TARGET_CLASS)
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
public class ServiceCatalogLocaStructureCache_Service implements I_ServiceCatalogLocaStructureCache_Service {
	// private static final Logger logger =
	// LoggerFactory.getLogger(ServiceCatalogMasterService.class);

	@Autowired
	private ServiceCatalogLocaStructureCache_Repo serviceCatalogLocaStructureRepo;
	
	@Autowired
	private Executor asyncExecutor;

	// abstract public CopyOnWriteArrayList<Long> getAllServicesForLocaStructures();

	@Override	
	@Cacheable(value="locaStructureCache",key = "new org.springframework.cache.interceptor.SimpleKey(#resCatSeqNo)")
	public CopyOnWriteArrayList<ServiceCatalogLocaStructureCache> getAllServiceCatalogLocaStructures(Long resCatSeqNo) throws InterruptedException, ExecutionException 
	{
		CompletableFuture<CopyOnWriteArrayList<ServiceCatalogLocaStructureCache>> future = CompletableFuture.supplyAsync(() ->
		{			
		CopyOnWriteArrayList<ServiceCatalogLocaStructureCache> cresCatList = serviceCatalogLocaStructureRepo.findServiceCatalogLocaStructures(resCatSeqNo);
		return cresCatList;
		},asyncExecutor);

		return future.get();

	}
}