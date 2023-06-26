package location_classes_cache.services;

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
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import location_classes_cache.model.master.ResourceCatalogLocaStructureCache;
import location_classes_cache.model.repo.IResourceCatalogLocaStructureCache_Repo;
import location_classes_cache.model.repo.ResourceCatalogLocaStructureCache_Repo;

@Service("resourceCatalogLocaStructureCacheServ")
@Scope(value = "prototype", proxyMode = ScopedProxyMode.TARGET_CLASS)
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
public class ResourceCatalogLocaStructureCache_Service implements I_ResourceCatalogLocaStructureCache_Service {
	// private static final Logger logger =
	// LoggerFactory.getLogger(ResourceCatalogMasterService.class);

	@Autowired
	private IResourceCatalogLocaStructureCache_Repo resourceCatalogLocaStructureRepo;
	
	@Autowired
	private Executor asyncExecutor;

	// abstract public ArrayList<Long> getAllResourcesForLocaStructures();

	@Override	
	@Cacheable(value="locaStructureCache",key = "new org.springframework.cache.interceptor.SimpleKey(#resCatSeqNo)")
	public ArrayList<ResourceCatalogLocaStructureCache> getAllResourceCatalogLocaStructures(Long resCatSeqNo)
			throws InterruptedException, ExecutionException 
	{
		CompletableFuture<ArrayList<ResourceCatalogLocaStructureCache>> future = CompletableFuture.supplyAsync(() ->
		{			
		CompletableFuture<ArrayList<ResourceCatalogLocaStructureCache>> cresCatList = resourceCatalogLocaStructureRepo.findResourceCatalogLocaStructures(resCatSeqNo);
		ArrayList<ResourceCatalogLocaStructureCache> cresCatList2 = null; 
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