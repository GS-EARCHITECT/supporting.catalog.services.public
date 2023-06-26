package ratings_cache.model.repo;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

import ratings_cache.model.master.ResourceCatalogRatingsCache;

public interface IResourceCatalogRatingsCache_Repo 
{
public CompletableFuture<ArrayList<ResourceCatalogRatingsCache>> findResourceCatalogRatingss(Long resCatSeqNo);	
}
