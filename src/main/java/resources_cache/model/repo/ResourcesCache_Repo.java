package resources_cache.model.repo;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.concurrent.CompletableFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;
import resources_cache.model.dto.ResourceCatalogProdStructure_DTO;
import resources_cache.model.master.ResourceCacheMaster;
import resources_cache.model.dto.ResourceClassDetail_DTO;
import resources_cache.model.dto.ResourceCatalogLocStructure_DTO;
import resources_cache.model.dto.ResourceLocationMaster_DTO;
import resources_cache.model.dto.PlaceClassDetail_DTO;;

@Repository("resourcesCacheRepo")
public class ResourcesCache_Repo implements IResourcesCache_Repo {
	// private static final Logger logger =
	// LoggerFactory.getLogger(ResourceCatalogReadMasterRepo.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Async
	public CompletableFuture<ArrayList<ResourceCacheMaster>> findAllResourcesForConditions(Integer parmLength,
			ArrayList<Long> cList, ArrayList<Long> rList, String catalog) {
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		String qryString = null;
		boolean cflag = false;
		boolean rflag = false;
		boolean caflag = false;

		if (parmLength == 1) {
			if (cList != null) {
				qryString = "select * from resource_class_details where resource_class_seq_no IN (:cList)";
				mapSqlParameterSource.addValue("cList", cList);
			}

			if (rList != null) {
				qryString = "select * from resource_catalog_master where resource_catalog_seq_no IN (:rList)";
				mapSqlParameterSource.addValue("rList", rList);
			}
			if (catalog != null) {
				qryString = "select * from resource_catalog_master where upper(trim(catalog)) = upper(trim(:catalog))";
				mapSqlParameterSource.addValue("catalog", catalog);
			}
		}

		if (parmLength > 1) {
			if (cList != null) {
				qryString = "select * from resource_catalog_master where company_seq_no IN (:cList)";
				mapSqlParameterSource.addValue("cList", cList);
				cflag = true;
			} else if (rList != null) {
				qryString = "select * from resource_catalog_master where resource_catalog_seq_no IN (:rList)";
				mapSqlParameterSource.addValue("rList", rList);
				rflag = true;
			} else if (catalog != null) {
				qryString = "select * from resource_catalog_master where upper(trim(catalog)) = upper(trim(:catalog))";
				mapSqlParameterSource.addValue("catalog", catalog);
				caflag = true;
			}

			for (int i = 1; i < parmLength; i++) {
				if (cList != null && !cflag) {
					qryString = qryString + " or company_seq_no IN (:cList)";
					mapSqlParameterSource.addValue("cList", cList);
					cflag = true;
				} else if (rList != null && !rflag) {
					qryString = qryString + " or resource_catalog_seq_no IN (:rList)";
					mapSqlParameterSource.addValue("rList", rList);
					rflag = true;
				} else if (catalog != null && !caflag) {
					qryString = qryString + " or upper(trim(catalog)) = upper(trim(:catalog))";
					mapSqlParameterSource.addValue("catalog", catalog);
					caflag = true;
				}

			}

		}

		ArrayList<ResourceCacheMaster> lCatalogReadMasters = (ArrayList<ResourceCacheMaster>) namedParameterJdbcTemplate
				.query(qryString, mapSqlParameterSource,
						(rs, rowNum) -> new ResourceCacheMaster(rs.getLong("resource_seq_no")));

		return CompletableFuture.completedFuture(lCatalogReadMasters);
	}

	// noDTO basis - get resourceclassList from resource_catalog_prodstructure
	@Async
	public CompletableFuture<ArrayList<Long>> findResourceClassesForCatalogNoDto(Long resCatSeqNo) {
		String qryString = "select resource_class_seq_no from resource_catalog_prodstructure where resource_catalog_seq_no = :resCatSeqNo";
		String qryString2 = "select par_resource_class_seq_no from resource_catalog_prodstructure where resource_catalog_seq_no = :resCatSeqNo";
		CompletableFuture<ArrayList<Long>> future = CompletableFuture.supplyAsync(() -> {
			List<Long> lResourceCatalogProdStructure_DTOs = jdbcTemplate.queryForList(qryString, Long.class,
					resCatSeqNo);
			List<Long> lResourceCatalogProdStructure_DTOs2 = jdbcTemplate.queryForList(qryString2, Long.class,
					resCatSeqNo);
			ArrayList<Long> tList = new ArrayList<Long>();

			if (lResourceCatalogProdStructure_DTOs != null && lResourceCatalogProdStructure_DTOs.size() > 0) {
				tList.addAll(lResourceCatalogProdStructure_DTOs);
			}
			if (lResourceCatalogProdStructure_DTOs2 != null && lResourceCatalogProdStructure_DTOs2.size() > 0) {
				tList.addAll(lResourceCatalogProdStructure_DTOs2);
			}
			return tList;
		});

		return future;
	}

	// dto basis - get resourceclassList from resource_catalog_prodstructure
	@Async
	public CompletableFuture<ArrayList<Long>> findResourceClassesForCatalog(Long resCatSeqNo) {
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("resCatSeqNo", resCatSeqNo);
		String qryString = "select * from resource_catalog_prodstructure where resource_catalog_seq_no = :resCatSeqNo";

		CompletableFuture<ArrayList<Long>> future = CompletableFuture.supplyAsync(() -> {
			ArrayList<Long> resourceclassList = null;
			ArrayList<ResourceCatalogProdStructure_DTO> lResourceCatalogProdStructure_DTOs = (ArrayList<ResourceCatalogProdStructure_DTO>) namedParameterJdbcTemplate
					.query(qryString, mapSqlParameterSource,
							(rs, rowNum) -> new ResourceCatalogProdStructure_DTO(rs.getLong("resource_class_seq_no"),
									rs.getLong("par_resource_class_seq_no"), rs.getLong("resource_catalog_seq_no")));

			if (lResourceCatalogProdStructure_DTOs != null && lResourceCatalogProdStructure_DTOs.size() > 0) {
				resourceclassList = new ArrayList<Long>();
				for (int i = 0; i < lResourceCatalogProdStructure_DTOs.size(); i++) {
					if (lResourceCatalogProdStructure_DTOs.get(i).getParResourceClassSeqNo() != null) {
						resourceclassList.add(lResourceCatalogProdStructure_DTOs.get(i).getParResourceClassSeqNo());
					}

					if (lResourceCatalogProdStructure_DTOs.get(i).getResourceClassSeqNo() != null) {
						resourceclassList.add(lResourceCatalogProdStructure_DTOs.get(i).getResourceClassSeqNo());
					}
				}
			}

			return resourceclassList;
		});

		return future;
	}

	// noDTo basis - get resources for resource classes in resourceclassList from
	// resource_class_details
	@Async
	public CompletableFuture<ArrayList<Long>> findResourcesForResourceClassesnoDTO(ArrayList<Long> resClassList) {
		CompletableFuture<ArrayList<Long>> future = CompletableFuture.supplyAsync(() -> {
			List<Long> lResSeqNos = jdbcTemplate.queryForList(
					"select resource_seq_no from resource_class_details where resource_class_seq_no in (:resClassList)",
					Long.class, resClassList);
			return (ArrayList<Long>) lResSeqNos;
		});
		return future;
	}

	// DTO basis - get resources for resource classes in resourceclassList from
	// resource_class_details
	@Async
	public CompletableFuture<ArrayList<Long>> findResourcesForResourceClasses(ArrayList<Long> resClassList) {
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("resClassList",resClassList);
		String qryString = "select * from resource_class_details where resource_class_seq_no in (:resClassList)";
		CompletableFuture<ArrayList<Long>> future = CompletableFuture.supplyAsync(() -> {
			ArrayList<Long> resourceList = null;
			ArrayList<ResourceClassDetail_DTO> lResourceClassDetails_DTOs = (ArrayList<ResourceClassDetail_DTO>) namedParameterJdbcTemplate
					.query(qryString, mapSqlParameterSource,
							(rs, rowNum) -> new ResourceClassDetail_DTO(rs.getLong("resource_class_seq_no"),
									rs.getLong("resource_seq_no"), rs.getLong("party_seq_no")));

			if (lResourceClassDetails_DTOs != null && lResourceClassDetails_DTOs.size() > 0) {
				resourceList = new ArrayList<Long>();

				for (int i = 0; i < lResourceClassDetails_DTOs.size(); i++) {
					resourceList.add(lResourceClassDetails_DTOs.get(i).getResourceSeqNo());
				}
			}
			return resourceList;
		});
		return future;
	}

	// noDTo basis - get locationClassList from resource_catalog_locstructure
	@Async
	public CompletableFuture<ArrayList<Long>> findLocationClassesForCatalognoDTO(Long resCatSeqNo) {
		String qryString = "select place_class_seq_no from resource_catalog_locstructure where resource_catalog_seq_no = :resCatSeqNo";

		CompletableFuture<ArrayList<Long>> future = CompletableFuture.supplyAsync(() -> {
			List<Long> locclassList = jdbcTemplate.queryForList(qryString, Long.class, resCatSeqNo);
			return (ArrayList<Long>) locclassList;
		});
		return future;
	}

	// DTo basis - get locationClassList from resource_catalog_locstructure
	@Async
	public CompletableFuture<ArrayList<Long>> findLocationClassesForCatalog(Long resCatSeqNo) {
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("resCatSeqNo", resCatSeqNo);
		String qryString = "select * from resource_catalog_locstructure where resource_catalog_seq_no = :resCatSeqNo";

		CompletableFuture<ArrayList<Long>> future = CompletableFuture.supplyAsync(() -> {
			ArrayList<Long> locclassList = null;
			ArrayList<ResourceCatalogLocStructure_DTO> lResourceCatalogLocStructure_DTOs = (ArrayList<ResourceCatalogLocStructure_DTO>) namedParameterJdbcTemplate
					.query(qryString, mapSqlParameterSource,
							(rs, rowNum) -> new ResourceCatalogLocStructure_DTO(rs.getLong("place_class_seq_no"),
									rs.getLong("par_place_class_seq_no"), rs.getLong("resource_catalog_seq_no")));

			if (lResourceCatalogLocStructure_DTOs != null && lResourceCatalogLocStructure_DTOs.size() > 0) {
				locclassList = new ArrayList<Long>();
				for (int i = 0; i < lResourceCatalogLocStructure_DTOs.size(); i++) {
					if (lResourceCatalogLocStructure_DTOs.get(i).getParLocationClassSeqNo() != null) {
						locclassList.add(lResourceCatalogLocStructure_DTOs.get(i).getParLocationClassSeqNo());
					}

					if (lResourceCatalogLocStructure_DTOs.get(i).getLocationClassSeqNo() != null) {
						locclassList.add(lResourceCatalogLocStructure_DTOs.get(i).getLocationClassSeqNo());
					}
				}
			}

			return locclassList;
		});

		return future;
	}

	// noDTo basis - get locationsList for locations in locationClassList from
	// place_class_details
	@Async
	public CompletableFuture<ArrayList<Long>> findLocationsForLocationsInLocationClassesnoDTO(
			ArrayList<Long> locClassList) {
		String qryString = "select place_seq_no from place_class_details where place_class_seq_no in (:locClassList)";
		CompletableFuture<ArrayList<Long>> future = CompletableFuture.supplyAsync(() -> {
			List<Long> locList = jdbcTemplate.queryForList(qryString, Long.class, locClassList);
			return (ArrayList<Long>) locList;
		});
		return future;
	}

	// DTO basis - get locationsList for locations in locationClassList from
	// place_class_details
	@Async
	public CompletableFuture<ArrayList<Long>> findLocationsForLocationsInLocationClasses(ArrayList<Long> locClassList) {
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("locClassList",locClassList);
		String qryString = "select * from place_class_details where place_class_seq_no in (:locClassList)";
		CompletableFuture<ArrayList<Long>> future = CompletableFuture.supplyAsync(() -> {
			ArrayList<Long> locList = null;
			ArrayList<PlaceClassDetail_DTO> lpPlaceClassDetail_DTOs = (ArrayList<PlaceClassDetail_DTO>) namedParameterJdbcTemplate
					.query(qryString, mapSqlParameterSource,
							(rs, rowNum) -> new PlaceClassDetail_DTO(rs.getLong("party_seq_no"),
									rs.getLong("place_class_seq_no"), rs.getLong("place_seq_no")));

			if (lpPlaceClassDetail_DTOs != null && lpPlaceClassDetail_DTOs.size() > 0) {
				locList = new ArrayList<Long>();

				for (int i = 0; i < lpPlaceClassDetail_DTOs.size(); i++) {
					locList.add(lpPlaceClassDetail_DTOs.get(i).getPlaceSeqNo());
				}
			}
			return locList;
		});
		return future;
	}

	// noDTO basis - get resources for locationsList from resource_location_master
	@Async
	public CompletableFuture<ArrayList<Long>> findResourcesForLocationsnoDTO(ArrayList<Long> locList) {
		String qryString = "select resource_seq_no from resource_location_master where location_seq_no in (:locList)";
		CompletableFuture<ArrayList<Long>> future = CompletableFuture.supplyAsync(() -> {
			List<Long> resList = jdbcTemplate.queryForList(qryString, Long.class, locList);
			return (ArrayList<Long>) resList;
		});
		return future;
	}

	// DTO basis - get resources for locationsList from resource_location_master
	@Async
	public CompletableFuture<ArrayList<Long>> findResourcesForLocations(ArrayList<Long> locList) {
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("locList",locList);
		String qryString = "select * from resource_location_master where location_seq_no in (:locList)";
		CompletableFuture<ArrayList<Long>> future = CompletableFuture.supplyAsync(() -> {
			ArrayList<Long> resList = null;
			ArrayList<ResourceLocationMaster_DTO> lResourcelLocationMaster_DTOs = (ArrayList<ResourceLocationMaster_DTO>) namedParameterJdbcTemplate
					.query(qryString, mapSqlParameterSource,
							(rs, rowNum) -> new ResourceLocationMaster_DTO(rs.getLong("resource_seq_no"),
									rs.getLong("location_seqNo"), rs.getLong("company_seq_no"), rs.getFloat("qty"),
									rs.getLong("qty_seq_no")));

			if (lResourcelLocationMaster_DTOs != null && lResourcelLocationMaster_DTOs.size() > 0) {
				resList = new ArrayList<Long>();

				for (int i = 0; i < lResourcelLocationMaster_DTOs.size(); i++) {
					resList.add(lResourcelLocationMaster_DTOs.get(i).getResourceSeqNo());
				}
			}
			return resList;
		});
		return future;
	}

	// no DTO basis - get supplierclassList from resource_catalog_compclasses
	@Async
	public CompletableFuture<ArrayList<Long>> findSupplierForResourceCatalog(Long resCatSeqNo) {
		String qryString = "select comp_class_seq_no from resource_catalog_compclasses where resource_catalog_seq_no = :resCatSeqNo";
		CompletableFuture<ArrayList<Long>> future = CompletableFuture.supplyAsync(() -> {
			List<Long> compClassList = jdbcTemplate.queryForList(qryString, Long.class, resCatSeqNo);
			return (ArrayList<Long>) compClassList;
		});
		return future;
	}

	// noDTO - get suppliersList for suppliers in supplierClassList from
	// supplier_class_details
	@Async
	public CompletableFuture<ArrayList<Long>> findSupplierListForSupplierClasses(ArrayList<Long> suppClassList) {
		String qryString = "select supplier_seq_no from supplier_class_details where supplier_class_seq_no in (:suppClassList)";
		CompletableFuture<ArrayList<Long>> future = CompletableFuture.supplyAsync(() -> {
			List<Long> suppList = jdbcTemplate.queryForList(qryString, Long.class, suppClassList);
			return (ArrayList<Long>) suppList;
		});
		return future;
	}

	// noDTO - get resources for suppliersList from SUPPLIER_PRODSERV_details
	@Async
	public CompletableFuture<ArrayList<Long>> findResourcesForSuppliers(ArrayList<Long> suppList) {
		String qryString = "select resource_seq_no from supplier_prodserv_details where supplier_seq_no in (:suppList)";
		CompletableFuture<ArrayList<Long>> future = CompletableFuture.supplyAsync(() -> {
			List<Long> resList = jdbcTemplate.queryForList(qryString, Long.class, suppList);
			return (ArrayList<Long>) resList;
		});
		return future;
	}

	// noDTO - get ratingsList from resource_catalog_ratings
	@Async
	public CompletableFuture<ArrayList<Float>> findRatingsForResourceCatalog(Long resCatSeqNo) {
		String qryString = "select rating from resource_catalog_ratings where res_catalog_seq_no = :resCatSeqNo";
		CompletableFuture<ArrayList<Float>> future = CompletableFuture.supplyAsync(() -> {
			List<Float> rateList = jdbcTemplate.queryForList(qryString, Float.class, resCatSeqNo);
			return (ArrayList<Float>) rateList;
		});
		return future;
	}

	// noDTO - resources for matching prodservseqnos in SUPPLIER_PRODSERV_details &
	// SUPPLIER_PRODSERV_ratings & ratingsList
	@Async
	public CompletableFuture<ArrayList<Long>> findResourcesForRatings(ArrayList<Float> ratingsList) {
		String qryString = "select a.resource_seq_no from supplier_prodserv_details a, supplier_prodserv_ratings b where (a.SUPP_PRODSERV_SEQ_NO = b.SUPP_PRODSERV_SEQ_NO and b.rating in (:ratingsList))";
		CompletableFuture<ArrayList<Long>> future = CompletableFuture.supplyAsync(() -> {
			List<Long> resList = jdbcTemplate.queryForList(qryString, Long.class, ratingsList);
			return (ArrayList<Long>) resList;
		});
		return future;
	}

	// noDTO - get priceRange from resource_catalog_pricerange
	@Async
	public CompletableFuture<Float> findPriceRangeUpForResourceCatalog(Long resCatSeqNo) {
		String qryString = "select priceFr from resource_catalog_pricerange where res_catalog_seq_no = :resCatSeqNo";
		CompletableFuture<Float> future = CompletableFuture.supplyAsync(() -> {
			Float rate = jdbcTemplate.queryForObject(qryString, Float.class, resCatSeqNo);
			return rate;
		});
		return future;
	}

	@Async
	public CompletableFuture<Float> findPriceRangeDownForResourceCatalog(Long resCatSeqNo) {
		String qryString = "select priceTo from resource_catalog_pricerange where res_catalog_seq_no = :resCatSeqNo";
		CompletableFuture<Float> future = CompletableFuture.supplyAsync(() -> {
			Float rate = jdbcTemplate.queryForObject(qryString, Float.class, resCatSeqNo);
			return rate;
		});
		return future;
	}

	// noDTO - get resources for matching prodservseqnos in
	// SUPPLIER_PRODSERV_details & SUPPLIER_PRODSERV_prices & priceRange
	@Async
	public CompletableFuture<ArrayList<Long>> findResourcesForPriceRange(Float lPrice, Float hPrice) {
		String qryString = "select a.resource_seq_no from supplier_prodserv_details a, supplier_prodserv_prices b where (a.SUPP_PRODSERV_SEQ_NO = b.SUPP_PRODSERV_SEQ_NO and b.amount >= :lPrice b.amount <= :hPrice)";
		CompletableFuture<ArrayList<Long>> future = CompletableFuture.supplyAsync(() -> {
			List<Long> resList = jdbcTemplate.queryForList(qryString, Long.class, lPrice, hPrice);
			return (ArrayList<Long>) resList;
		});
		return future;
	}

}
