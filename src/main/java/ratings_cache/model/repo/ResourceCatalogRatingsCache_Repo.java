package ratings_cache.model.repo;

import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ratings_cache.model.master.ResourceCatalogRatingsCache;

@Repository("resourceCatalogRatingsRepo")
public class ResourceCatalogRatingsCache_Repo implements IResourceCatalogRatingsCache_Repo {
	// private static final Logger logger =
	// LoggerFactory.getLogger(ResourceCatalogReadMasterRepo.class);

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Autowired
	private Executor asyncExecutor;

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public CopyOnWriteArrayList<ResourceCatalogRatingsCache> findResourceCatalogRatings(Long resCatSeqNo) {

		CompletableFuture<CopyOnWriteArrayList<ResourceCatalogRatingsCache>> future = CompletableFuture
				.supplyAsync(() -> {
					CopyOnWriteArrayList<ResourceCatalogRatingsCache> cList = null;
					MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
					mapSqlParameterSource.addValue("resCatSeqNo", resCatSeqNo);
					String qryString = "select * from resource_catalog_ratings a where a.resource_catalog_seq_no = :resCatSeqNo";

					ArrayList<ResourceCatalogRatingsCache> resourceCatalogRatings = (ArrayList<ResourceCatalogRatingsCache>) namedParameterJdbcTemplate
							.query(qryString, mapSqlParameterSource,
									(rs, rowNum) -> new ResourceCatalogRatingsCache(rs.getFloat("rating"),
											rs.getLong("resource_catalog_seq_no")));

					cList = new CopyOnWriteArrayList<ResourceCatalogRatingsCache>();
					if (resourceCatalogRatings != null) {
						for (int i = 0; i < resourceCatalogRatings.size(); i++) {
							cList.add(resourceCatalogRatings.get(i));
						}
					}
					return cList;
				},asyncExecutor);

		CopyOnWriteArrayList<ResourceCatalogRatingsCache> cList = null;

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
