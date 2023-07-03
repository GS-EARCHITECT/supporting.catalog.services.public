package pricerange_cache.services;

import java.util.concurrent.ExecutionException;
import pricerange_cache.model.master.ResourceCatalogPriceRangeCache;

public interface IResourceCatalogPriceRangeCache_Service
{
abstract public ResourceCatalogPriceRangeCache getAllResourceCatalogPriceRange(Long resCatSeqNo) throws InterruptedException, ExecutionException;
}