package location_classes_cache.services;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;
import location_classes_cache.model.master.ResourceCatalogLocaStructureCache;

public interface I_ResourceCatalogLocaStructureCache_Service
{
	abstract public CopyOnWriteArrayList<ResourceCatalogLocaStructureCache> getAllResourceCatalogLocaStructures(Long resCatSeqNo) throws InterruptedException, ExecutionException;
}