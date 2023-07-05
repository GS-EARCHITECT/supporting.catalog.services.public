package compclasses_cache.services;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;
import compclasses_cache.model.master.ServiceCatalogCompClassesCache;

public interface IServiceCatalogCompClassesCache_Service
{
	abstract public CopyOnWriteArrayList<ServiceCatalogCompClassesCache> getAllServiceCatalogCompClasses(Long srvCatSeqNo) throws InterruptedException, ExecutionException;
}