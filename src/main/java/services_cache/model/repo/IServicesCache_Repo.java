package services_cache.model.repo;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;
import services_cache.model.master.ServiceCacheMaster;

public interface IServicesCache_Repo 
{
public CompletableFuture<CopyOnWriteArrayList<ServiceCacheMaster>> findAllServicesForConditions(Integer parmLength,CopyOnWriteArrayList<Long> cList, CopyOnWriteArrayList<Long> rList, String catalog);
public CopyOnWriteArrayList<Long> findServicesForPriceRange(Float lPrice, Float hPrice);
public Float findPriceRangeHighForServiceCatalog(Long srvCatSeqNo);
public Float findPriceRangeLowForServiceCatalog(Long srvCatSeqNo);
public CopyOnWriteArrayList<Long> findServicesForRatings(CopyOnWriteArrayList<Float> ratingsList);
public CopyOnWriteArrayList<Float> findRatingsForServiceCatalog(Long srvCatSeqNo);
public CopyOnWriteArrayList<Long> findServicesForSuppliers(CopyOnWriteArrayList<Long> suppList);
public CopyOnWriteArrayList<Long> findSupplierListForSupplierClasses(CopyOnWriteArrayList<Long> suppClassList);
public CopyOnWriteArrayList<Long> findSuppliersForServiceCatalog(Long srvCatSeqNo);
public CopyOnWriteArrayList<Long> findServicesForLocations(CopyOnWriteArrayList<Long> locList);
public CopyOnWriteArrayList<Long> findServicesForLocationsnoDTO(CopyOnWriteArrayList<Long> locList);
public CopyOnWriteArrayList<Long> findLocationsForLocationsInLocationClasses(CopyOnWriteArrayList<Long> locClassList);
public CopyOnWriteArrayList<Long> findLocationsForLocationsInLocationClassesnoDTO(CopyOnWriteArrayList<Long> locClassList);
public CopyOnWriteArrayList<Long> findLocationClassesForCatalog(Long srvCatSeqNo);
public CopyOnWriteArrayList<Long> findLocationClassesForCatalognoDTO(Long srvCatSeqNo);
public CopyOnWriteArrayList<Long> findServicesForServiceClasses(CopyOnWriteArrayList<Long> srvClassList);
public CopyOnWriteArrayList<Long> findServicesForServiceClassesnoDTO(CopyOnWriteArrayList<Long> srvClassList);
public CopyOnWriteArrayList<Long> findServiceClassesForCatalog(Long srvCatSeqNo);
public CopyOnWriteArrayList<Long> findAllServices();
}
