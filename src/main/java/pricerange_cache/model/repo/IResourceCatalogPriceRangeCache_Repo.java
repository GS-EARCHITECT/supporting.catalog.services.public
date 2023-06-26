package pricerange_cache.model.repo;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

import pricerange_cache.model.master.ResourceCatalogPriceRangeCache;

public interface IResourceCatalogPriceRangeCache_Repo 
{
public CompletableFuture<ArrayList<ResourceCatalogPriceRangeCache>> findResourceCatalogPriceRanges(Long resCatSeqNo);	
}
