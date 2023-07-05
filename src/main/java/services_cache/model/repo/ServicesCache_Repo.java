package services_cache.model.repo;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import services_cache.model.dto.PlaceClassDetail_DTO;
import services_cache.model.dto.ServiceCatalogLocStructure_DTO;
import services_cache.model.dto.ServiceCatalogServStructure_DTO;
import services_cache.model.dto.ServiceClassDetail_DTO;
import services_cache.model.dto.ServiceLocationMaster_DTO;
import services_cache.model.master.ServiceCacheMaster;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;;

@Repository("servicesCacheRepo")
public class ServicesCache_Repo implements IServicesCache_Repo {
	private static final Logger logger = LoggerFactory.getLogger(ServicesCache_Repo.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private Executor asyncExecutor;

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	public CompletableFuture<CopyOnWriteArrayList<ServiceCacheMaster>> findAllServicesForConditions(
			Integer parmLength, CopyOnWriteArrayList<Long> cList, CopyOnWriteArrayList<Long> rList, String catalog) {
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		String qryString = null;
		boolean cflag = false;
		boolean rflag = false;
		boolean caflag = false;

		if (parmLength == 1) {
			if (cList != null) {
				qryString = "select * from service_class_details where service_class_seq_no IN (:cList)";
				mapSqlParameterSource.addValue("cList", cList);
			}

			if (rList != null) {
				qryString = "select * from service_catalog_master where service_catalog_seq_no IN (:rList)";
				mapSqlParameterSource.addValue("rList", rList);
			}
			if (catalog != null) {
				qryString = "select * from service_catalog_master where upper(trim(catalog)) = upper(trim(:catalog))";
				mapSqlParameterSource.addValue("catalog", catalog);
			}
		}

		if (parmLength > 1) {
			if (cList != null) {
				qryString = "select * from service_catalog_master where company_seq_no IN (:cList)";
				mapSqlParameterSource.addValue("cList", cList);
				cflag = true;
			} else if (rList != null) {
				qryString = "select * from service_catalog_master where service_catalog_seq_no IN (:rList)";
				mapSqlParameterSource.addValue("rList", rList);
				rflag = true;
			} else if (catalog != null) {
				qryString = "select * from service_catalog_master where upper(trim(catalog)) = upper(trim(:catalog))";
				mapSqlParameterSource.addValue("catalog", catalog);
				caflag = true;
			}

			for (int i = 1; i < parmLength; i++) {
				if (cList != null && !cflag) {
					qryString = qryString + " or company_seq_no IN (:cList)";
					mapSqlParameterSource.addValue("cList", cList);
					cflag = true;
				} else if (rList != null && !rflag) {
					qryString = qryString + " or service_catalog_seq_no IN (:rList)";
					mapSqlParameterSource.addValue("rList", rList);
					rflag = true;
				} else if (catalog != null && !caflag) {
					qryString = qryString + " or upper(trim(catalog)) = upper(trim(:catalog))";
					mapSqlParameterSource.addValue("catalog", catalog);
					caflag = true;
				}

			}

		}

		CopyOnWriteArrayList<ServiceCacheMaster> lCatalogReadMasters = (CopyOnWriteArrayList<ServiceCacheMaster>) namedParameterJdbcTemplate
				.query(qryString, mapSqlParameterSource,
						(rs, rowNum) -> new ServiceCacheMaster(rs.getLong("service_seq_no")));

		return CompletableFuture.completedFuture(lCatalogReadMasters);
	}

	// dto basis - get serviceclassList from service_catalog_prodstructure
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public CopyOnWriteArrayList<Long> findServiceClassesForCatalog(Long srvCatSeqNo) {
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("srvCatSeqNo", srvCatSeqNo);
		String qryString = "select * from service_catalog_prodstructure where service_catalog_seq_no = :srvCatSeqNo";
		CopyOnWriteArrayList<Long> cList = new CopyOnWriteArrayList<Long>();

		CompletableFuture<CopyOnWriteArrayList<Long>> future = CompletableFuture.supplyAsync(() -> {
			List<ServiceCatalogServStructure_DTO> lServiceCatalogServStructure_DTOs = namedParameterJdbcTemplate
					.query(qryString, mapSqlParameterSource,
							(rs, rowNum) -> new ServiceCatalogServStructure_DTO(rs.getLong("service_class_seq_no"),
									rs.getLong("par_service_class_seq_no"), rs.getLong("service_catalog_seq_no")));

			if (lServiceCatalogServStructure_DTOs != null && lServiceCatalogServStructure_DTOs.size() > 0) {
				for (int i = 0; i < lServiceCatalogServStructure_DTOs.size(); i++) {
					if (lServiceCatalogServStructure_DTOs.get(i).getParServiceClassSeqNo() != null) {
						cList.add(lServiceCatalogServStructure_DTOs.get(i).getParServiceClassSeqNo());
					}
				}

				for (int i = 0; i < lServiceCatalogServStructure_DTOs.size(); i++) {
					if (lServiceCatalogServStructure_DTOs.get(i).getServiceClassSeqNo() != null) {
						cList.add(lServiceCatalogServStructure_DTOs.get(i).getParServiceClassSeqNo());
					}
				}
			}

			return cList;
		}, asyncExecutor);

		CopyOnWriteArrayList<Long> serviceclassList2 = null;
		;
		try {
			serviceclassList2 = future.get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return serviceclassList2;
	}

	// noDTo basis - get services for service classes in serviceclassList from
	// service_class_details
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public CopyOnWriteArrayList<Long> findServicesForServiceClassesnoDTO(CopyOnWriteArrayList<Long> srvClassList) {
		CompletableFuture<CopyOnWriteArrayList<Long>> futurexx = CompletableFuture.supplyAsync(() -> {
			List<Long> lResSeqNos = jdbcTemplate.queryForList(
					"select service_seq_no from service_class_details where service_class_seq_no in (:srvClassList)",
					Long.class, srvClassList);
			CopyOnWriteArrayList<Long> cList = new CopyOnWriteArrayList<Long>();
			cList.addAll(lResSeqNos);
			return cList;
		});

		CopyOnWriteArrayList<Long> cAList = null;
		try {
			cAList = futurexx.get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cAList;
	}

	// DTO basis - get services for service classes in serviceclassList from
	// service_class_details
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public CopyOnWriteArrayList<Long> findServicesForServiceClasses(CopyOnWriteArrayList<Long> srvClassList) {
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("srvClassList", srvClassList);
		String qryString = "select * from service_class_details where service_class_seq_no in (:srvClassList)";
		CompletableFuture<CopyOnWriteArrayList<Long>> future = CompletableFuture.supplyAsync(() -> {
			CopyOnWriteArrayList<Long> serviceList = null;
			List<ServiceClassDetail_DTO> lServiceClassDetails_DTOs = namedParameterJdbcTemplate.query(qryString,
					mapSqlParameterSource,
					(rs, rowNum) -> new ServiceClassDetail_DTO(rs.getLong("service_class_seq_no"),
							rs.getLong("service_seq_no"), rs.getLong("party_seq_no")));

			if (lServiceClassDetails_DTOs != null && lServiceClassDetails_DTOs.size() > 0) {
				serviceList = new CopyOnWriteArrayList<Long>();

				for (int i = 0; i < lServiceClassDetails_DTOs.size(); i++) {
					serviceList.add(lServiceClassDetails_DTOs.get(i).getServiceSeqNo());
				}
			}
			return serviceList;
		}, asyncExecutor);

		CopyOnWriteArrayList<Long> serviceList2 = null;
		try {
			serviceList2 = future.get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return serviceList2;
	}

	// noDTo basis - get locationClassList from service_catalog_locstructure
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public CopyOnWriteArrayList<Long> findLocationClassesForCatalognoDTO(Long srvCatSeqNo) {
		String qryString = "select place_class_seq_no from service_catalog_locstructure where service_catalog_seq_no = :srvCatSeqNo";

		CompletableFuture<CopyOnWriteArrayList<Long>> future = CompletableFuture.supplyAsync(() -> {
			List<Long> locclassList = jdbcTemplate.queryForList(qryString, Long.class, srvCatSeqNo);
			CopyOnWriteArrayList<Long> cList = new CopyOnWriteArrayList<Long>();
			cList.addAll(locclassList);
			return cList;
		});

		CopyOnWriteArrayList<Long> cArrayList = null;

		try {
			cArrayList = future.get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return cArrayList;
	}

	// DTo basis - get locationClassList from service_catalog_locstructure
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public CopyOnWriteArrayList<Long> findLocationClassesForCatalog(Long srvCatSeqNo) {
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("srvCatSeqNo", srvCatSeqNo);
		String qryString = "select * from service_catalog_locstructure where service_catalog_seq_no = :srvCatSeqNo";
		CompletableFuture<CopyOnWriteArrayList<Long>> future = CompletableFuture.supplyAsync(() -> {
			CopyOnWriteArrayList<Long> locclassList = null;
			List<ServiceCatalogLocStructure_DTO> lServiceCatalogLocStructure_DTOs = namedParameterJdbcTemplate.query(
					qryString, mapSqlParameterSource,
					(rs, rowNum) -> new ServiceCatalogLocStructure_DTO(rs.getLong("place_class_seq_no"),
							rs.getLong("par_place_class_seq_no"), rs.getLong("service_catalog_seq_no")));

			if (lServiceCatalogLocStructure_DTOs != null && lServiceCatalogLocStructure_DTOs.size() > 0) {
				locclassList = new CopyOnWriteArrayList<Long>();
				for (int i = 0; i < lServiceCatalogLocStructure_DTOs.size(); i++) {
					if (lServiceCatalogLocStructure_DTOs.get(i).getParLocationClassSeqNo() != null) {
						locclassList.add(lServiceCatalogLocStructure_DTOs.get(i).getParLocationClassSeqNo());
					}

					if (lServiceCatalogLocStructure_DTOs.get(i).getLocationClassSeqNo() != null) {
						locclassList.add(lServiceCatalogLocStructure_DTOs.get(i).getLocationClassSeqNo());
					}
				}
			}

			return locclassList;
		});

		CopyOnWriteArrayList<Long> cList = null;
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

	// noDTo basis - get locationsList for locations in locationClassList from
	// place_class_details
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public CopyOnWriteArrayList<Long> findLocationsForLocationsInLocationClassesnoDTO(
			CopyOnWriteArrayList<Long> locClassList) {
		String qryString = "select place_seq_no from place_class_details where place_class_seq_no in (:locClassList)";
		CompletableFuture<CopyOnWriteArrayList<Long>> future = CompletableFuture.supplyAsync(() -> {
			List<Long> locList = jdbcTemplate.queryForList(qryString, Long.class, locClassList);
			CopyOnWriteArrayList<Long> cList = new CopyOnWriteArrayList<Long>();
			cList.addAll(locList);
			return cList;
		});

		CopyOnWriteArrayList<Long> cArrayList = null;

		try {
			cArrayList = future.get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return cArrayList;
	}

	// DTO basis - get locationsList for locations in locationClassList from
	// place_class_details
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public CopyOnWriteArrayList<Long> findLocationsForLocationsInLocationClasses(
			CopyOnWriteArrayList<Long> locClassList) {
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("locClassList", locClassList);
		String qryString = "select * from place_class_details where place_class_seq_no in (:locClassList)";
		CompletableFuture<CopyOnWriteArrayList<Long>> future = CompletableFuture.supplyAsync(() -> {
			CopyOnWriteArrayList<Long> locList = null;
			List<PlaceClassDetail_DTO> lpPlaceClassDetail_DTOs = namedParameterJdbcTemplate.query(qryString,
					mapSqlParameterSource, (rs, rowNum) -> new PlaceClassDetail_DTO(rs.getLong("party_seq_no"),
							rs.getLong("place_class_seq_no"), rs.getLong("place_seq_no")));

			if (lpPlaceClassDetail_DTOs != null && lpPlaceClassDetail_DTOs.size() > 0) {
				locList = new CopyOnWriteArrayList<Long>();

				for (int i = 0; i < lpPlaceClassDetail_DTOs.size(); i++) {
					locList.add(lpPlaceClassDetail_DTOs.get(i).getPlaceSeqNo());
				}
			}
			return locList;
		});

		CopyOnWriteArrayList<Long> cArrayList = null;

		try {
			cArrayList = future.get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return cArrayList;

	}

	// noDTO basis - get services for locationsList from service_location_master
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public CopyOnWriteArrayList<Long> findServicesForLocationsnoDTO(CopyOnWriteArrayList<Long> locList) {
		String qryString = "select service_seq_no from service_location_master where location_seq_no in (:locList)";
		CompletableFuture<CopyOnWriteArrayList<Long>> future = CompletableFuture.supplyAsync(() -> {
			List<Long> srvList = jdbcTemplate.queryForList(qryString, Long.class, locList);
			CopyOnWriteArrayList<Long> cList = new CopyOnWriteArrayList<Long>();
			cList.addAll(srvList);
			return cList;
		});
		CopyOnWriteArrayList<Long> cArrayList = null;

		try {
			cArrayList = future.get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return cArrayList;
	}

	// DTO basis - get services for locationsList from service_location_master
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public CopyOnWriteArrayList<Long> findServicesForLocations(CopyOnWriteArrayList<Long> locList) {
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("locList", locList);
		String qryString = "select * from service_location_master where location_seq_no in (:locList)";
		CompletableFuture<CopyOnWriteArrayList<Long>> future = (CompletableFuture<CopyOnWriteArrayList<Long>>) CompletableFuture
				.supplyAsync(() -> {
					CopyOnWriteArrayList<Long> srvList = null;
					List<ServiceLocationMaster_DTO> lServicelLocationMaster_DTOs = namedParameterJdbcTemplate.query(
							qryString, mapSqlParameterSource,
							(rs, rowNum) -> new ServiceLocationMaster_DTO(rs.getLong("service_seq_no"),
									rs.getLong("location_seq_no"), rs.getLong("company_seq_no"), rs.getFloat("qoh"),
									rs.getLong("qty_seq_no")));

					if (lServicelLocationMaster_DTOs != null && lServicelLocationMaster_DTOs.size() > 0) {
						srvList = new CopyOnWriteArrayList<Long>();

						for (int i = 0; i < lServicelLocationMaster_DTOs.size(); i++) {
							srvList.add(lServicelLocationMaster_DTOs.get(i).getServiceSeqNo());
						}
					}
					return (CopyOnWriteArrayList<Long>) srvList;
				}, asyncExecutor);

		CopyOnWriteArrayList<Long> cArrayList = null;

		try {
			cArrayList = future.get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return cArrayList;
	}

	// no DTO basis - get supplierclassList from service_catalog_compclasses
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public CopyOnWriteArrayList<Long> findSuppliersForServiceCatalog(Long srvCatSeqNo) {
		String qryString = "select company_class_seq_no from service_catalog_compclasses where service_catalog_seq_no = :srvCatSeqNo";
		CompletableFuture<CopyOnWriteArrayList<Long>> future = CompletableFuture.supplyAsync(() -> {
			List<Long> compClassList = jdbcTemplate.queryForList(qryString, Long.class, srvCatSeqNo);
			CopyOnWriteArrayList<Long> cList = new CopyOnWriteArrayList<Long>();
			cList.addAll(compClassList);
			return cList;
		}, asyncExecutor);

		CopyOnWriteArrayList<Long> compClassList2 = null;
		try {
			compClassList2 = future.get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return compClassList2;
	}

	// noDTO - get suppliersList for suppliers in supplierClassList from
	// supplier_class_details
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public CopyOnWriteArrayList<Long> findSupplierListForSupplierClasses(CopyOnWriteArrayList<Long> suppClassList) {
		String qryString = "select supplier_seq_no from supplier_class_details where supplier_class_seq_no in (:suppClassList)";
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("suppClassList", suppClassList);
		CompletableFuture<CopyOnWriteArrayList<Long>> futurejj = CompletableFuture.supplyAsync(() -> {
			List<Long> suppList = namedParameterJdbcTemplate.queryForList(qryString, mapSqlParameterSource, Long.class);
			CopyOnWriteArrayList<Long> cList = new CopyOnWriteArrayList<Long>();
			cList.addAll(suppList);
			return cList;
		});
		CopyOnWriteArrayList<Long> sList = null;

		try {
			sList = futurejj.get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sList;
	}

	// noDTO - get services for suppliersList from SUPPLIER_PRODSERV_details
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public CopyOnWriteArrayList<Long> findServicesForSuppliers(CopyOnWriteArrayList<Long> suppList) {
		String qryString = "select service_seq_no from supplier_prodserv_details where supplier_seq_no in (:suppList)";
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("suppList", suppList);
		CompletableFuture<CopyOnWriteArrayList<Long>> future = CompletableFuture.supplyAsync(() -> {
			List<Long> srvList = namedParameterJdbcTemplate.queryForList(qryString, mapSqlParameterSource, Long.class);
			CopyOnWriteArrayList<Long> cList = new CopyOnWriteArrayList<Long>();
			cList.addAll(srvList);
			return cList;
		});

		CopyOnWriteArrayList<Long> rList = null;
		try {
			rList = future.get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rList;
	}

	// noDTO - get ratingsList from service_catalog_ratings
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public CopyOnWriteArrayList<Float> findRatingsForServiceCatalog(Long srvCatSeqNo) {
		String qryString = "select rating from service_catalog_ratings where service_catalog_seq_no = :srvCatSeqNo";
		CopyOnWriteArrayList<Float> rateList2 = null;
		CompletableFuture<CopyOnWriteArrayList<Float>> future = CompletableFuture.supplyAsync(() -> {
			List<Float> rateList = jdbcTemplate.queryForList(qryString, Float.class, srvCatSeqNo);
			CopyOnWriteArrayList<Float> cList = new CopyOnWriteArrayList<Float>();
			cList.addAll(rateList);
			return cList;
		}, asyncExecutor);

		try {
			rateList2 = future.get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return rateList2;
	}

	// noDTO - services for matching prodservseqnos in SUPPLIER_PRODSERV_details &
	// SUPPLIER_PRODSERV_ratings & ratingsList
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public CopyOnWriteArrayList<Long> findServicesForRatings(CopyOnWriteArrayList<Float> ratingsList) {
		String qryString = "select a.service_seq_no from supplier_prodserv_details a, supplier_prodserv_ratings b where (a.SUPP_PRODSERV_SEQ_NO = b.SUPP_PRODSERV_SEQ_NO and b.rating in (:ratingsList))";
		CopyOnWriteArrayList<Long> srvList2 = null;
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("ratingsList", ratingsList);
		CompletableFuture<CopyOnWriteArrayList<Long>> future = CompletableFuture.supplyAsync(() -> {
			List<Long> srvList = namedParameterJdbcTemplate.queryForList(qryString, mapSqlParameterSource, Long.class);
			CopyOnWriteArrayList<Long> cList = new CopyOnWriteArrayList<Long>();
			cList.addAll(srvList);
			return cList;
		}, asyncExecutor);

		try {
			srvList2 = future.get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return srvList2;
	}

	// noDTO - get priceRange from service_catalog_pricerange
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public Float findPriceRangeLowForServiceCatalog(Long srvCatSeqNo) {
		String qryString = "select price_fr from service_catalog_pricerange where rownum=1 and service_catalog_seq_no = :srvCatSeqNo";
		Float lowPrice = null;
		CompletableFuture<Float> future = CompletableFuture.supplyAsync(() -> {
			Float rate = jdbcTemplate.queryForObject(qryString, Float.class, srvCatSeqNo);
			return rate;
		}, asyncExecutor);

		try {
			lowPrice = future.get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lowPrice;
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public Float findPriceRangeHighForServiceCatalog(Long srvCatSeqNo) {
		String qryString = "select price_to from service_catalog_pricerange where rownum=1 and service_catalog_seq_no = :srvCatSeqNo";
		Float hiPrice = null;
		CompletableFuture<Float> future = CompletableFuture.supplyAsync(() -> {
			Float rate = jdbcTemplate.queryForObject(qryString, Float.class, srvCatSeqNo);
			return rate;
		}, asyncExecutor);

		try {
			hiPrice = future.get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return hiPrice;
	}

	// noDTO - get services for matching prodservseqnos in
	// SUPPLIER_PRODSERV_details & SUPPLIER_PRODSERV_prices & priceRange
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public CopyOnWriteArrayList<Long> findServicesForPriceRange(Float lPrice, Float hPrice) {
		String qryString = "select a.service_seq_no from supplier_prodserv_details a, supplier_prodserv_prices b where a.SUPP_PRODSERV_SEQ_NO = b.SUPP_PRODSERV_SEQ_NO and b.amount >= :lPrice and b.amount <= :hPrice";
		CopyOnWriteArrayList<Long> srvList2 = null;

		CompletableFuture<CopyOnWriteArrayList<Long>> future = CompletableFuture.supplyAsync(() -> {
			List<Long> srvList = jdbcTemplate.queryForList(qryString, Long.class, lPrice, hPrice);
			CopyOnWriteArrayList<Long> cList = new CopyOnWriteArrayList<Long>();
			cList.addAll(srvList);
			return cList;
		});

		try {
			srvList2 = future.get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return srvList2;
	}

	// Fallback - get all services
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public CopyOnWriteArrayList<Long> findAllServices() 
	{
		CompletableFuture<CopyOnWriteArrayList<Long>> futurexx = CompletableFuture.supplyAsync(() -> {
			List<Long> lResSeqNos = jdbcTemplate.queryForList("select service_seq_no from service_master",
					Long.class);
			CopyOnWriteArrayList<Long> cList = new CopyOnWriteArrayList<Long>();
			cList.addAll(lResSeqNos);
			return cList;
		});

		CopyOnWriteArrayList<Long> cAList = null;
		try {
			cAList = futurexx.get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cAList;
	}

}
