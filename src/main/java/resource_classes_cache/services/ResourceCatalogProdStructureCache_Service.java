package resource_classes_cache.services;

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
import resource_classes_cache.model.master.ResourceCatalogProdStructureCache;
import resource_classes_cache.model.repo.IResourceCatalogProdStructureCache_Repo;
import resource_classes_cache.model.repo.ResourceCatalogProdStructureCache_Repo;

@Service("resourceCatalogProdStructureCacheServ")
@Scope(value = "prototype", proxyMode = ScopedProxyMode.TARGET_CLASS)
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
public class ResourceCatalogProdStructureCache_Service implements I_ResourceCatalogProdStructureCache_Service {
 
	private static final Logger logger = LoggerFactory.getLogger(ResourceCatalogProdStructureCache_Service.class);

	@Autowired
	private IResourceCatalogProdStructureCache_Repo resourceCatalogProdStructureRepo;
	
	@Autowired
	private Executor asyncExecutor;
	// abstract public ArrayList<Long> getAllResourcesForProdStructures();
	
	@Override
	@Cacheable(value = "prodStructureCache",key = "new org.springframework.cache.interceptor.SimpleKey(#resCatSeqNo)")
	public CopyOnWriteArrayList<Long> getAllResourceCatalogProdStructures(Long resCatSeqNo)
			throws InterruptedException, ExecutionException 
	{

		CompletableFuture<CopyOnWriteArrayList<Long>> future = CompletableFuture.supplyAsync(() -> 
		{			
		CopyOnWriteArrayList<Long> cresCatList = resourceCatalogProdStructureRepo.findResourceCatalogProdStructures(resCatSeqNo);
		return cresCatList;
		},asyncExecutor);
		
		CopyOnWriteArrayList<Long>  ss = future.get();		
		return ss;
	}
}