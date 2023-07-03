package ratings_cache.services;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;
import ratings_cache.model.master.ResourceCatalogRatingsCache;

public interface IResourceCatalogRatingsCache_Service
{
abstract public CopyOnWriteArrayList<ResourceCatalogRatingsCache> getAllResourceCatalogRatings(Long resCatSeqNo) throws InterruptedException, ExecutionException; 
}