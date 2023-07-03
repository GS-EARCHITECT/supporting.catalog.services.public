package resources_cache.model.repo;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;
import resources_cache.model.master.ResourceCacheMaster;

public interface IResourcesCache_Repo 
{
public CompletableFuture<CopyOnWriteArrayList<ResourceCacheMaster>> findAllResourcesForConditions(Integer parmLength,CopyOnWriteArrayList<Long> cList, CopyOnWriteArrayList<Long> rList, String catalog);
public CopyOnWriteArrayList<Long> findResourcesForPriceRange(Float lPrice, Float hPrice);
public Float findPriceRangeHighForResourceCatalog(Long resCatSeqNo);
public Float findPriceRangeLowForResourceCatalog(Long resCatSeqNo);
public CopyOnWriteArrayList<Long> findResourcesForRatings(CopyOnWriteArrayList<Float> ratingsList);
public CopyOnWriteArrayList<Float> findRatingsForResourceCatalog(Long resCatSeqNo);
public CopyOnWriteArrayList<Long> findResourcesForSuppliers(CopyOnWriteArrayList<Long> suppList);
public CopyOnWriteArrayList<Long> findSupplierListForSupplierClasses(CopyOnWriteArrayList<Long> suppClassList);
public CopyOnWriteArrayList<Long> findSuppliersForResourceCatalog(Long resCatSeqNo);
public CopyOnWriteArrayList<Long> findResourcesForLocations(CopyOnWriteArrayList<Long> locList);
public CopyOnWriteArrayList<Long> findResourcesForLocationsnoDTO(CopyOnWriteArrayList<Long> locList);
public CopyOnWriteArrayList<Long> findLocationsForLocationsInLocationClasses(CopyOnWriteArrayList<Long> locClassList);
public CopyOnWriteArrayList<Long> findLocationsForLocationsInLocationClassesnoDTO(CopyOnWriteArrayList<Long> locClassList);
public CopyOnWriteArrayList<Long> findLocationClassesForCatalog(Long resCatSeqNo);
public CopyOnWriteArrayList<Long> findLocationClassesForCatalognoDTO(Long resCatSeqNo);
public CopyOnWriteArrayList<Long> findResourcesForResourceClasses(CopyOnWriteArrayList<Long> resClassList);
public CopyOnWriteArrayList<Long> findResourcesForResourceClassesnoDTO(CopyOnWriteArrayList<Long> resClassList);
public CopyOnWriteArrayList<Long> findResourceClassesForCatalog(Long resCatSeqNo);
public CopyOnWriteArrayList<Long> findAllResources();
}
