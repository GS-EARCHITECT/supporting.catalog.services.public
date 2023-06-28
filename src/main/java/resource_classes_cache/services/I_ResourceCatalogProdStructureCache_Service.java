package resource_classes_cache.services;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;

public interface I_ResourceCatalogProdStructureCache_Service
{
	abstract public CopyOnWriteArrayList<Long> getAllResourceCatalogProdStructures(Long resCatSeqNo) throws InterruptedException, ExecutionException;
}