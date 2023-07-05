package location_classes_cache.model.repo;

import java.util.concurrent.CopyOnWriteArrayList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import location_classes_cache.model.master.ServiceCatalogLocaStructureCache;
import location_classes_cache.model.master.ServiceCatalogLocaStructureCachePK;

@Repository("serviceCatalogLocaStructureRepo")
public interface ServiceCatalogLocaStructureCache_Repo  extends JpaRepository<ServiceCatalogLocaStructureCache, ServiceCatalogLocaStructureCachePK> 
{
	@Query(value = "select * from service_catalog_locstructure a where (a.resource_catalog_seq_no = :resCatSeqNo)", nativeQuery = true)
	CopyOnWriteArrayList<ServiceCatalogLocaStructureCache> findServiceCatalogLocaStructures(@Param("resCatSeqNo") Long resCatSeqNo);
	
}
