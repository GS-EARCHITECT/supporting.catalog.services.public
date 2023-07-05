package ratings_cache.services;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;
import ratings_cache.model.master.ServiceCatalogRatingsCache;

public interface IServiceCatalogRatingsCache_Service
{
abstract public CopyOnWriteArrayList<ServiceCatalogRatingsCache> getAllServiceCatalogRatings(Long srvCatSeqNo) throws InterruptedException, ExecutionException; 
}