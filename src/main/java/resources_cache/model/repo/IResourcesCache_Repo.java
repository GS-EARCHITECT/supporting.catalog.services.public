package resources_cache.model.repo;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

import resources_cache.model.master.ResourceCacheMaster;

public interface IResourcesCache_Repo 
{
public CompletableFuture<ArrayList<ResourceCacheMaster>> findAllResourcesForConditions(Integer parmLength,ArrayList<Long> cList, ArrayList<Long> rList, String catalog);
public CompletableFuture<ArrayList<Long>> findResourcesForPriceRange(Float lPrice, Float hPrice);
public CompletableFuture<Float> findPriceRangeDownForResourceCatalog(Long resCatSeqNo);
public CompletableFuture<Float> findPriceRangeUpForResourceCatalog(Long resCatSeqNo);
public CompletableFuture<ArrayList<Long>> findResourcesForRatings(ArrayList<Float> ratingsList);
public CompletableFuture<ArrayList<Float>> findRatingsForResourceCatalog(Long resCatSeqNo);
public CompletableFuture<ArrayList<Long>> findResourcesForSuppliers(ArrayList<Long> suppList);
public CompletableFuture<ArrayList<Long>> findSupplierListForSupplierClasses(ArrayList<Long> suppClassList);
public CompletableFuture<ArrayList<Long>> findSupplierForResourceCatalog(Long resCatSeqNo);
public CompletableFuture<ArrayList<Long>> findResourcesForLocations(ArrayList<Long> locList);
public CompletableFuture<ArrayList<Long>> findResourcesForLocationsnoDTO(ArrayList<Long> locList);
public CompletableFuture<ArrayList<Long>> findLocationsForLocationsInLocationClasses(ArrayList<Long> locClassList);
public CompletableFuture<ArrayList<Long>> findLocationsForLocationsInLocationClassesnoDTO(ArrayList<Long> locClassList);
public CompletableFuture<ArrayList<Long>> findLocationClassesForCatalog(Long resCatSeqNo);
public CompletableFuture<ArrayList<Long>> findLocationClassesForCatalognoDTO(Long resCatSeqNo);
public CompletableFuture<ArrayList<Long>> findResourcesForResourceClasses(ArrayList<Long> resClassList);
public CompletableFuture<ArrayList<Long>> findResourcesForResourceClassesnoDTO(ArrayList<Long> resClassList);
public CompletableFuture<ArrayList<Long>> findResourceClassesForCatalog(Long resCatSeqNo);
public CompletableFuture<ArrayList<Long>> findResourceClassesForCatalogNoDto(Long resCatSeqNo);
}
