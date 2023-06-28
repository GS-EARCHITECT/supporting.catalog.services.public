package resources_cache.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import compclasses_cache.services.IResourceCatalogCompClassesCache_Service;
import location_classes_cache.services.I_ResourceCatalogLocaStructureCache_Service;
import pricerange_cache.services.IResourceCatalogPriceRangeCache_Service;
import ratings_cache.services.IResourceCatalogRatingsCache_Service;
import resource_classes_cache.services.*;
import resources_cache.model.repo.*;

@Service("resourcesCacheServ")  
@Scope(value = "prototype", proxyMode = ScopedProxyMode.TARGET_CLASS)
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
public class ResourcesCache_Service implements IResourcesCache_Service 
{
	private static final Logger logger = LoggerFactory.getLogger(ResourcesCache_Service.class);

	@Autowired
	private IResourcesCache_Repo resourcesCacheRepo;
	
	@Autowired
	private Executor asyncExecutor;
		
	@Autowired
	private	I_ResourceCatalogProdStructureCache_Service resourceCatalogProdStructureCacheServ;
	
	@Autowired
	private	I_ResourceCatalogLocaStructureCache_Service resourceCatalogLocaStructureCacheServ;
	
	@Autowired
	private IResourceCatalogCompClassesCache_Service resourceCatalogCompClassesCacheServ;
	
	@Autowired
	private IResourceCatalogRatingsCache_Service resourceCatalogRatingsCacheServ;
	
	@Autowired
	private IResourceCatalogPriceRangeCache_Service resourceCatalogPriceRangeCacheServ; 

	@Override
	@Cacheable(value="resourcesCache",key = "new org.springframework.cache.interceptor.SimpleKey(#resCatSeqNo)")
	@HystrixCommand(fallbackMethod = "getAllResources")    
	public ArrayList<Long> getAllResourcesForCatalog(Long resCatSeqNo) throws InterruptedException, ExecutionException 
	{
		ArrayList<Long> resListFull=null;
		// get resourceclassList from resource_catalog_prodstructure
		CompletableFuture<ArrayList<Long>> future1 = CompletableFuture.supplyAsync(() -> {
		ArrayList<Long> resList=null;
		try {
			resList = this.getResourceClassList(resCatSeqNo);
			logger.info("list is ? "+resList.size());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
			return resList;
		},asyncExecutor);
		
		resListFull = future1.get();
		return resListFull;
	}
	
	private ArrayList<Long> getResourceClassList(Long resCatSeqNo) throws InterruptedException, ExecutionException 
	{
		// get resourceclassList from resource_catalog_prodstructure
		CompletableFuture<ArrayList<Long>> future1 = CompletableFuture.supplyAsync(() -> 
		{
		ArrayList<Long> resCatList = resourcesCacheRepo.findResourceClassesForCatalog(resCatSeqNo);
		logger.info("res classes size is ? "+resCatList.size());
		return resCatList;
		},asyncExecutor);
		
		// get resources for resource classes in resourceclassList from resource_class_details
		CompletableFuture<ArrayList<Long>> future2 = future1.whenComplete((input, exception) -> 
		{
			logger.info("future 2 input res classes size is ? "+input.size());
            if (exception == null) 
            {
            	    CompletableFuture.supplyAsync(() -> 
            	    {            	    
            	    ArrayList<Long> resList = resourcesCacheRepo.findResourcesForResourceClasses(input); 
            		logger.info("res size is ? "+resList.size());
            		return resList;
            		},asyncExecutor);            	
            	
            } else {
            	logger.info("Resource Classes exception, No Result: " + input);
            }
        });
		
		ArrayList<Long> sList = future2.get();
						
		return sList;
}
	
	
	/*
	@Override
	@Cacheable(value="resourcesCache",key = "new org.springframework.cache.interceptor.SimpleKey(#resCatSeqNo)")
	@HystrixCommand(fallbackMethod = "getAllResources")    
	public ArrayList<Long> getAllResourcesForCatalog(Long resCatSeqNo) throws InterruptedException, ExecutionException 
	{
		// get resourceclassList from resource_catalog_prodstructure
		CompletableFuture<ArrayList<Long>> future1 = CompletableFuture.supplyAsync(() -> {
		CompletableFuture<ArrayList<Long>> resCatComp = resourcesCacheRepo.findResourceClassesForCatalog(resCatSeqNo); 
		ArrayList<Long> resCatList =null;
		try
		{
			resCatList = resCatComp.get();
			} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
			return resCatList;
		},asyncExecutor);
		
		// get resources for resource classes in resourceclassList from resource_class_details
		CompletableFuture<ArrayList<Long>> future2 = future1.whenComplete((input, exception) -> 
		{
            if (exception == null) 
            {
            	    CompletableFuture.supplyAsync(() -> {            	    
            		CompletableFuture<ArrayList<Long>> resCatComp = resourcesCacheRepo.findResourcesForResourceClasses(input); 
            		ArrayList<Long> resCatList =null;
            		try
            		{
            			resCatList = resCatComp.get();            			
            		} catch (InterruptedException e) {
            			// TODO Auto-generated catch block
            			e.printStackTrace();
            		} catch (ExecutionException e) {
            			// TODO Auto-generated catch block
            			e.printStackTrace();
            		}			
            			return resCatList;
            		},asyncExecutor);            	
            	
            } else {
                System.out.println("Resource Classes exception, No Result: " + input);
            }
        });
			
				// get locationClassList from resource_catalog_locstructure
		CompletableFuture<ArrayList<Long>> future3 = CompletableFuture.supplyAsync(() -> {
			CompletableFuture<ArrayList<Long>> resLocComp = resourcesCacheRepo.findLocationClassesForCatalog(resCatSeqNo); 
			ArrayList<Long> resLocList =null;
			try
			{
				resLocList = resLocComp.get();
				logger.info("future 3 list size :"+resLocList.size());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
				return resLocList;
			},asyncExecutor);
		
			// get locationsList for locations in locationClassList from place_class_details
		CompletableFuture<ArrayList<Long>> future4 = future3.whenComplete((input, exception) -> 
		{
            if (exception == null) 
            {
            	    CompletableFuture.supplyAsync(() -> {
            		CompletableFuture<ArrayList<Long>> resLComp = resourcesCacheRepo.findLocationsForLocationsInLocationClasses(input); 
            		ArrayList<Long> reslLList =null;
            		try
            		{
            			reslLList = resLComp.get();            			
            		} catch (InterruptedException e) {
            			// TODO Auto-generated catch block
            			e.printStackTrace();
            		} catch (ExecutionException e) {
            			// TODO Auto-generated catch block
            			e.printStackTrace();
            		}			
            			return reslLList;
            		},asyncExecutor);            	
            	
            } else {
                System.out.println("Locations List exception, No Result: " + input);
            }
        });
				// get resources for locationsList from resource_location_master
		CompletableFuture<ArrayList<Long>> future5 = future4.whenComplete((input, exception) -> 
		{
            if (exception == null) 
            {
            		CompletableFuture.supplyAsync(() -> {
            		CompletableFuture<ArrayList<Long>> resComp = resourcesCacheRepo.findResourcesForLocations(input); 
            		ArrayList<Long> resList =null;
            		try
            		{
            			resList = resComp.get();            		
            		} catch (InterruptedException e) {
            			// TODO Auto-generated catch block
            			e.printStackTrace();
            		} catch (ExecutionException e) {
            			// TODO Auto-generated catch block
            			e.printStackTrace();
            		}			
            			return resList;
            		},asyncExecutor);            	
            	
            } else {
                System.out.println("Resources List exception, No Result: " + input);
            }
        });
				
				// get supplierclassList from resource_catalog_compclasses
		CompletableFuture<ArrayList<Long>> future6 = CompletableFuture.supplyAsync(() -> 
		{
			CompletableFuture<ArrayList<Long>> supClassComp = resourcesCacheRepo.findSuppliersForResourceCatalog(resCatSeqNo); 
			ArrayList<Long> supClassList =null;
			try
			{
				supClassList = supClassComp.get();				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
				return supClassList;
			},asyncExecutor);
		
		// get suppliersList for suppliers in supplierClassList from supplier_class_details
		CompletableFuture<ArrayList<Long>> future7 = future6.whenComplete((input, exception) -> 
		{
            if (exception == null) 
            {
            		CompletableFuture.supplyAsync(() -> {
            		CompletableFuture<ArrayList<Long>> supComp = resourcesCacheRepo.findSupplierListForSupplierClasses(input); 
            		ArrayList<Long> supList =null;
            		try
            		{
            			supList = supComp.get();            		
            		} catch (InterruptedException e) {
            			// TODO Auto-generated catch block
            			e.printStackTrace();
            		} catch (ExecutionException e) {
            			// TODO Auto-generated catch block
            			e.printStackTrace();
            		}			
            			return supList;
            		},asyncExecutor);            	
            	
            } else {
                System.out.println("Supplers List exception, No Result: " + input);
            }
        });
		
				// get resources for suppliersList from SUPPLIER_PRODSERV_details	
		CompletableFuture<ArrayList<Long>> future8 = future7.whenComplete((input, exception) -> 
		{
            if (exception == null) 
            {
            		CompletableFuture.supplyAsync(() -> {
            		CompletableFuture<ArrayList<Long>> resComp = resourcesCacheRepo.findResourcesForSuppliers(input); 
            		ArrayList<Long> resList =null;
            		try
            		{
            			resList = resComp.get();            		
            		} catch (InterruptedException e) {
            			// TODO Auto-generated catch block
            			e.printStackTrace();
            		} catch (ExecutionException e) {
            			// TODO Auto-generated catch block
            			e.printStackTrace();
            		}			
            			return resList;
            		},asyncExecutor);            	
            	
            } else {
                System.out.println("Resource List exception, No Result: " + input);
            }
        });		
		
		// get resources for ratingsList 
		CompletableFuture<ArrayList<Long>> future9 = CompletableFuture.supplyAsync(() -> 
		{
			ArrayList<Float> supRateList = resourcesCacheRepo.findRatingsForResourceCatalog(resCatSeqNo); 
			ArrayList<Long> resList= resourcesCacheRepo.findResourcesForRatings(supRateList);
			return resList;
			},asyncExecutor);
		
			 
		// get resources for for  high and low price range 
		CompletableFuture<ArrayList<Long>> future10 = CompletableFuture.supplyAsync(() -> 
		{
			Float hiPriceList = resourcesCacheRepo.findPriceRangeLowForResourceCatalog(resCatSeqNo);
			Float lowPriceList = resourcesCacheRepo.findPriceRangeHighForResourceCatalog(resCatSeqNo);
			ArrayList<Long> resList= resourcesCacheRepo.findResourcesForPriceRange(lowPriceList, hiPriceList);
			return resList;
			},asyncExecutor);
			
				// get resources for matching prodservseqnos in SUPPLIER_PRODSERV_details & SUPPLIER_PRODSERV_prices & priceRange 
		
			//CompletableFuture<Void> futureResult = CompletableFuture.allOf(future1, future2, future3, future4, future5, future6, future7, future8, future9, future10);
			
			
			 List<CompletableFuture<ArrayList<Long>>> completableFutures = Arrays.asList(future1, future2, future3, future4, future5, future6, future7, future8, future9, future10);

		       CompletableFuture<Void> resultantCf = CompletableFuture.allOf(completableFutures.toArray(new CompletableFuture[completableFutures.size()]));

		        CompletableFuture<Object> allResults = resultantCf.thenApply(t -> completableFutures.stream().map(CompletableFuture::join).collect(Collectors.toList()));
			
		        Thread.sleep(1000);
		        
		        @SuppressWarnings("unchecked")
				ArrayList<Long> gg = (ArrayList<Long>) allResults.get();
		        		        
		        logger.info("in service :");
		        for (int i = 0; i < gg.size(); i++) 
		        {
		        logger.info("result :"+gg.get(i));
				}
		        
				//List<Long> allList = Stream.of(future2, future5, future8, future9, future10).map(CompletableFuture::join).flatMap(List::stream).collect(Collectors.toList());		
				return gg;				
				
}

*/

	public String getAllResources() 
	{
        return "Am Waiting For Data From Host";
	}
}