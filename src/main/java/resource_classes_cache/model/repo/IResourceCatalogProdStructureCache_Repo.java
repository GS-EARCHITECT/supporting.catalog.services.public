package resource_classes_cache.model.repo;

import java.util.concurrent.CopyOnWriteArrayList;

public interface IResourceCatalogProdStructureCache_Repo 
{
public CopyOnWriteArrayList<Long> findResourceCatalogProdStructures(Long resCatSeqNo);	
}
