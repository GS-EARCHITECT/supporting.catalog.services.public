package compclasses_cache.model.repo;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

import compclasses_cache.model.master.ResourceCatalogCompClassesCache;

public interface IResourceCatalogCompClassesCache_Repo 
{
public CompletableFuture<ArrayList<ResourceCatalogCompClassesCache>> findResourceCatalogCompClasses(Long resCatSeqNo);	
}
