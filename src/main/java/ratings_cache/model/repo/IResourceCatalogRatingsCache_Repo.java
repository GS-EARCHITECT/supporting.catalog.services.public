package ratings_cache.model.repo;

import java.util.concurrent.CopyOnWriteArrayList;
import ratings_cache.model.master.ResourceCatalogRatingsCache;

public interface IResourceCatalogRatingsCache_Repo 
{
public CopyOnWriteArrayList<ResourceCatalogRatingsCache> findResourceCatalogRatings(Long resCatSeqNo);	
}
