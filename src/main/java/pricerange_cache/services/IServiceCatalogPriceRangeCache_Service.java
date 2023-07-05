package pricerange_cache.services;

import java.util.concurrent.ExecutionException;
import pricerange_cache.model.master.ServiceCatalogPriceRangeCache;

public interface IServiceCatalogPriceRangeCache_Service
{
abstract public ServiceCatalogPriceRangeCache getAllServiceCatalogPriceRange(Long srvCatSeqNo) throws InterruptedException, ExecutionException;
}