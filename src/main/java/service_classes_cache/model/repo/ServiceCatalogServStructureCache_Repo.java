package service_classes_cache.model.repo;

import java.util.concurrent.CopyOnWriteArrayList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import service_classes_cache.model.master.ServiceCatalogServStructureCache;
import service_classes_cache.model.master.ServiceCatalogServStructureCachePK;

@Repository("serviceCatalogServStructureRepo")
public interface ServiceCatalogServStructureCache_Repo extends JpaRepository<ServiceCatalogServStructureCache, ServiceCatalogServStructureCachePK> 
{
	@Query(value = "select * from service_catalog_servstructure a where (a.service_catalog_seq_no = :resCatSeqNo)", nativeQuery = true)
	public CopyOnWriteArrayList<ServiceCatalogServStructureCache> findServiceCatalogServStructures(@Param("srvCatSeqNo")  Long resCatSeqNo);	
}
