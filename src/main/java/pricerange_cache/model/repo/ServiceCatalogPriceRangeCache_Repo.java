package pricerange_cache.model.repo;

import java.util.concurrent.CopyOnWriteArrayList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pricerange_cache.model.master.ServiceCatalogPriceRangeCache;
import pricerange_cache.model.master.ServiceCatalogPriceRangeCachePK;

@Repository("serviceCatalogPriceRangeRepo")
public interface ServiceCatalogPriceRangeCache_Repo extends JpaRepository<ServiceCatalogPriceRangeCache, ServiceCatalogPriceRangeCachePK> 
{
@Query(value = "select * from service_catalog_pricerange a where (rownum=1 and a.service_catalog_seq_no = :resCatSeqNo)", nativeQuery = true)
public CopyOnWriteArrayList<ServiceCatalogPriceRangeCache> findServiceCatalogPriceRanges(Long resCatSeqNo);	
}
