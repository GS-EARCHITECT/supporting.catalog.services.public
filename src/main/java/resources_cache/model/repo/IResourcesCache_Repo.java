package resources_cache.model.repo;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;

import resources_cache.model.master.ResourceCacheMaster;

public interface IResourcesCache_Repo 
{
public CompletableFuture<ArrayList<ResourceCacheMaster>> findAllResourcesForConditions(Integer parmLength,ArrayList<Long> cList, ArrayList<Long> rList, String catalog);
public ArrayList<Long> findResourcesForPriceRange(Float lPrice, Float hPrice);
public Float findPriceRangeHighForResourceCatalog(Long resCatSeqNo);
public Float findPriceRangeLowForResourceCatalog(Long resCatSeqNo);
public ArrayList<Long> findResourcesForRatings(ArrayList<Float> ratingsList);
public ArrayList<Float> findRatingsForResourceCatalog(Long resCatSeqNo);
public CompletableFuture<ArrayList<Long>> findResourcesForSuppliers(ArrayList<Long> suppList);
public CompletableFuture<ArrayList<Long>> findSupplierListForSupplierClasses(ArrayList<Long> suppClassList);
public ArrayList<Long> findSuppliersForResourceCatalog(Long resCatSeqNo);
public CompletableFuture<ArrayList<Long>> findResourcesForLocations(ArrayList<Long> locList);
public CompletableFuture<ArrayList<Long>> findResourcesForLocationsnoDTO(ArrayList<Long> locList);
public CompletableFuture<ArrayList<Long>> findLocationsForLocationsInLocationClasses(ArrayList<Long> locClassList);
public CompletableFuture<ArrayList<Long>> findLocationsForLocationsInLocationClassesnoDTO(ArrayList<Long> locClassList);
public CompletableFuture<ArrayList<Long>> findLocationClassesForCatalog(Long resCatSeqNo);
public CompletableFuture<ArrayList<Long>> findLocationClassesForCatalognoDTO(Long resCatSeqNo);
public CopyOnWriteArrayList<Long> findResourcesForResourceClasses(CopyOnWriteArrayList<Long> resClassList);
public CompletableFuture<ArrayList<Long>> findResourcesForResourceClassesnoDTO(ArrayList<Long> resClassList);
public CopyOnWriteArrayList<Long> findResourceClassesForCatalog(Long resCatSeqNo);
public ArrayList<Long> findResourceClassesForCatalogNoDto(Long resCatSeqNo);
}
