package location_classes_cache.services;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import location_classes_cache.model.master.ResourceCatalogLocaStructureCache;

public interface I_ResourceCatalogLocaStructureCache_Service
{
	abstract public ArrayList<ResourceCatalogLocaStructureCache> getAllResourceCatalogLocaStructures(Long resCatSeqNo) throws InterruptedException, ExecutionException;
}