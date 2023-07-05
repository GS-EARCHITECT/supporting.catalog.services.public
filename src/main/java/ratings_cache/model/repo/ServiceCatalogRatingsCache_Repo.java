package ratings_cache.model.repo;

import java.util.concurrent.CopyOnWriteArrayList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ratings_cache.model.master.ServiceCatalogRatingsCache;
import ratings_cache.model.master.ServiceCatalogRatingsCachePK;

@Repository("serviceCatalogRatingsRepo")
public interface ServiceCatalogRatingsCache_Repo extends JpaRepository<ServiceCatalogRatingsCache, ServiceCatalogRatingsCachePK> 
{
	@Query(value = "select * from resource_catalog_ratings a where a.resource_catalog_seq_no = :resCatSeqNo", nativeQuery = true)
	public CopyOnWriteArrayList<ServiceCatalogRatingsCache> findServiceCatalogRatings(Long resCatSeqNo);	
}
