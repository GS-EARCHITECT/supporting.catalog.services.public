package compclasses_cache.model.repo;

import java.util.concurrent.CopyOnWriteArrayList;
import compclasses_cache.model.master.ResourceCatalogCompClassesCache;

public interface IResourceCatalogCompClassesCache_Repo 
{
public CopyOnWriteArrayList<ResourceCatalogCompClassesCache> findResourceCatalogCompClasses(Long resCatSeqNo);	
}
