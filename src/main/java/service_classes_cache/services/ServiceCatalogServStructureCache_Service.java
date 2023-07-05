package service_classes_cache.services;

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
import service_classes_cache.model.master.ServiceCatalogServStructureCache;
import service_classes_cache.model.repo.ServiceCatalogServStructureCache_Repo;

@Service("serviceCatalogServStructureCacheServ")
@Scope(value = "prototype", proxyMode = ScopedProxyMode.TARGET_CLASS)
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
public class ServiceCatalogServStructureCache_Service implements I_ServiceCatalogServStructureCache_Service {
 
	private static final Logger logger = LoggerFactory.getLogger(ServiceCatalogServStructureCache_Service.class);

	@Autowired
	private ServiceCatalogServStructureCache_Repo serviceCatalogServStructureRepo;
	
	@Autowired
	private Executor asyncExecutor;
	// abstract public ArrayList<Long> getAllServicesForServStructusrv();
	
	@Override
	@Cacheable(value = "prodStructureCache",key = "new org.springframework.cache.interceptor.SimpleKey(#srvCatSeqNo)")
	public CopyOnWriteArrayList<ServiceCatalogServStructureCache> getAllServiceCatalogServStructusrv(Long srvCatSeqNo) throws InterruptedException, ExecutionException 
	{
		CompletableFuture<CopyOnWriteArrayList<ServiceCatalogServStructureCache>> future = CompletableFuture.supplyAsync(() -> {			
		CopyOnWriteArrayList<ServiceCatalogServStructureCache> csrvCatList = serviceCatalogServStructureRepo.findServiceCatalogServStructures(srvCatSeqNo);
		return csrvCatList;
		},asyncExecutor);
		return future.get();
	}

}