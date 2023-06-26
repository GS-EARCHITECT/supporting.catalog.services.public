package resource_classes_cache.model.repo;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import resource_classes_cache.model.master.ResourceCatalogProdStructureCache;

public interface IResourceCatalogProdStructureCache_Repo 
{
public CompletableFuture<ArrayList<ResourceCatalogProdStructureCache>> findResourceCatalogProdStructures(Long resCatSeqNo);	
}
