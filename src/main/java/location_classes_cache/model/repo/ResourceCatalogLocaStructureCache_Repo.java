package location_classes_cache.model.repo;

import java.util.List;
import java.util.concurrent.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import location_classes_cache.model.master.ResourceCatalogLocaStructureCache;

@Repository("resourceCatalogLocaStructureRepo")
public class ResourceCatalogLocaStructureCache_Repo implements IResourceCatalogLocaStructureCache_Repo {
	// private static final Logger logger =
	// LoggerFactory.getLogger(ResourceCatalogReadMasterRepo.class);

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Autowired
	private Executor asyncExecutor;

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public CopyOnWriteArrayList<ResourceCatalogLocaStructureCache> findResourceCatalogLocaStructures(Long resCatSeqNo) {

		CompletableFuture<CopyOnWriteArrayList<ResourceCatalogLocaStructureCache>> future = CompletableFuture
				.supplyAsync(() -> {
					MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
					mapSqlParameterSource.addValue("resCatSeqNo", resCatSeqNo);
					String qryString = "select * from resource_catalog_locstructure a where (a.resource_catalog_seq_no = :resCatSeqNo)";
					List<ResourceCatalogLocaStructureCache> resourceCatalogLocaStructures = namedParameterJdbcTemplate
							.query(qryString, mapSqlParameterSource,
									(rs, rowNum) -> new ResourceCatalogLocaStructureCache(
											rs.getLong("place_class_seq_no"), rs.getLong("par_place_class_seq_no"),
											rs.getLong("resource_catalog_seq_no")));

					CopyOnWriteArrayList<ResourceCatalogLocaStructureCache> cList = new CopyOnWriteArrayList<ResourceCatalogLocaStructureCache>();  
					cList.addAll(resourceCatalogLocaStructures);
					return cList;
				}, asyncExecutor);

		CopyOnWriteArrayList<ResourceCatalogLocaStructureCache> cList = null;
		try {
			cList = future.get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cList;
	}

}