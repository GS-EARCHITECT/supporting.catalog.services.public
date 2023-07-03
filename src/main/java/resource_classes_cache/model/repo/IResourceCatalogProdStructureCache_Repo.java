package resource_classes_cache.model.repo;

import java.util.concurrent.CopyOnWriteArrayList;
import resource_classes_cache.model.master.ResourceCatalogProdStructureCache;

public interface IResourceCatalogProdStructureCache_Repo 
{
public CopyOnWriteArrayList<ResourceCatalogProdStructureCache> findResourceCatalogProdStructures(Long resCatSeqNo);	
}
