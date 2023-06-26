package resource_classes_cache.services;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import resource_classes_cache.model.master.ResourceCatalogProdStructureCache;

public interface I_ResourceCatalogProdStructureCache_Service
{
	abstract public ArrayList<ResourceCatalogProdStructureCache> getAllResourceCatalogProdStructures(Long resCatSeqNo) throws InterruptedException, ExecutionException;
}