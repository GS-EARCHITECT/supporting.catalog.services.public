package compclasses_cache.model.repo;

import java.util.concurrent.CopyOnWriteArrayList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import compclasses_cache.model.master.ServiceCatalogCompClassesCache;
import compclasses_cache.model.master.ServiceCatalogCompClassesCachePK;

@Repository("serviceCatalogCompClassesRepo")
public interface ServiceCatalogCompClassesCache_Repo extends JpaRepository<ServiceCatalogCompClassesCache, ServiceCatalogCompClassesCachePK> 
{
	@Query(value = "select * from service_catalog_compclasses a where (a.service_catalog_seq_no = :srvCatSeqNo)", nativeQuery = true)
	CopyOnWriteArrayList<ServiceCatalogCompClassesCache> findServiceCatalogCompClasses(@Param("srvCatSeqNo") Long srvCatSeqNo);
}
