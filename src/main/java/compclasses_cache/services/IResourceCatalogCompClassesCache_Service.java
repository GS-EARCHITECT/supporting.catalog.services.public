package compclasses_cache.services;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;
import compclasses_cache.model.master.ResourceCatalogCompClassesCache;

public interface IResourceCatalogCompClassesCache_Service
{
	abstract public CopyOnWriteArrayList<ResourceCatalogCompClassesCache> getAllResourceCatalogCompClasses(Long resCatSeqNo) throws InterruptedException, ExecutionException;
}