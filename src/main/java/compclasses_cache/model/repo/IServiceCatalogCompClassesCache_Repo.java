package compclasses_cache.model.repo;

import java.util.concurrent.CopyOnWriteArrayList;
import compclasses_cache.model.master.ServiceCatalogCompClassesCache;

public interface IServiceCatalogCompClassesCache_Repo 
{
public CopyOnWriteArrayList<ServiceCatalogCompClassesCache> findServiceCatalogCompClasses(Long srvCatSeqNo);	
}
