package compclasses_cache.services;

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
import compclasses_cache.model.master.ResourceCatalogCompClassesCache;
import compclasses_cache.model.repo.IResourceCatalogCompClassesCache_Repo;

@Service("resourceCatalogCompClassesCacheServ")
@Scope(value = "prototype", proxyMode = ScopedProxyMode.TARGET_CLASS)
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
public class ResourceCatalogCompClassesCache_Service implements IResourceCatalogCompClassesCache_Service {
	// private static final Logger logger =
	// LoggerFactory.getLogger(ResourceCatalogMasterService.class);

	@Autowired
	private IResourceCatalogCompClassesCache_Repo resourceCatalogCompClassesCacheRepo;

	@Autowired
	private Executor asyncExecutor;

	// abstract public ArrayList<Long> getAllResourcesForCompClassess();

	@Override	
	@Cacheable(value="compClassesCache",key = "new org.springframework.cache.interceptor.SimpleKey(#resCatSeqNo)")
	public ArrayList<ResourceCatalogCompClassesCache> getAllResourceCatalogCompClasses(Long resCatSeqNo)
			throws InterruptedException, ExecutionException 
	{

		CompletableFuture<ArrayList<ResourceCatalogCompClassesCache>> future = CompletableFuture.supplyAsync(() -> {			
			CompletableFuture<ArrayList<ResourceCatalogCompClassesCache>> cresCatList = resourceCatalogCompClassesCacheRepo.findResourceCatalogCompClasses(resCatSeqNo);
			ArrayList<ResourceCatalogCompClassesCache> cresCatList2 = null;
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