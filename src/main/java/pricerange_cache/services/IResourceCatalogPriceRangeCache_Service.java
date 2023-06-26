package pricerange_cache.services;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import pricerange_cache.model.master.ResourceCatalogPriceRangeCache;

public interface IResourceCatalogPriceRangeCache_Service
{
	abstract public ArrayList<ResourceCatalogPriceRangeCache> getAllResourceCatalogPriceRange(Long resCatSeqNo) throws InterruptedException, ExecutionException;
}