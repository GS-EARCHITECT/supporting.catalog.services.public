package location_classes_cache.model.repo;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;
import location_classes_cache.model.master.ResourceCatalogLocaStructureCache;

public interface IResourceCatalogLocaStructureCache_Repo 
{
public CopyOnWriteArrayList<ResourceCatalogLocaStructureCache> findResourceCatalogLocaStructures(Long resCatSeqNo);	
}
