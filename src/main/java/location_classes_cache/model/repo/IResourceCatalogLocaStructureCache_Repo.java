package location_classes_cache.model.repo;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import location_classes_cache.model.master.ResourceCatalogLocaStructureCache;

public interface IResourceCatalogLocaStructureCache_Repo 
{
public CompletableFuture<ArrayList<ResourceCatalogLocaStructureCache>> findResourceCatalogLocaStructures(Long resCatSeqNo);	
}
