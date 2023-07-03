package pricerange_cache.model.repo;

import java.util.concurrent.CopyOnWriteArrayList;
import pricerange_cache.model.master.ResourceCatalogPriceRangeCache;

public interface IResourceCatalogPriceRangeCache_Repo 
{
public CopyOnWriteArrayList<ResourceCatalogPriceRangeCache> findResourceCatalogPriceRanges(Long resCatSeqNo);	
}
