package location_classes_cache.services;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;
import location_classes_cache.model.master.ServiceCatalogLocaStructureCache;

public interface I_ServiceCatalogLocaStructureCache_Service
{
abstract public CopyOnWriteArrayList<ServiceCatalogLocaStructureCache> getAllServiceCatalogLocaStructures(Long resCatSeqNo) throws InterruptedException, ExecutionException;
}