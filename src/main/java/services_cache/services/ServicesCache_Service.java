package services_cache.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.HashSet;
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
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import compclasses_cache.services.IServiceCatalogCompClassesCache_Service;
import location_classes_cache.model.master.ServiceCatalogLocaStructureCache;
import location_classes_cache.services.I_ServiceCatalogLocaStructureCache_Service;
import pricerange_cache.services.IServiceCatalogPriceRangeCache_Service;
import ratings_cache.services.IServiceCatalogRatingsCache_Service;
import compclasses_cache.model.master.ServiceCatalogCompClassesCache;
import ratings_cache.model.master.ServiceCatalogRatingsCache;
import pricerange_cache.model.master.ServiceCatalogPriceRangeCache;
import service_classes_cache.services.*;
import service_classes_cache.model.master.*;
import services_cache.model.repo.*;

@Service("servicesCacheServ")  
@Scope(value = "prototype", proxyMode = ScopedProxyMode.TARGET_CLASS)
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
public class ServicesCache_Service implements IServicesCache_Service 
{
	private static final Logger logger = LoggerFactory.getLogger(ServicesCache_Service.class);

	@Autowired
	private IServicesCache_Repo servicesCacheRepo;
	
	@Autowired
	private Executor asyncExecutor;
		
	@Autowired
	private	I_ServiceCatalogServStructureCache_Service serviceCatalogServStructureCacheServ;
	
	@Autowired
	private	I_ServiceCatalogLocaStructureCache_Service serviceCatalogLocaStructureCacheServ;
	
	@Autowired
	private IServiceCatalogCompClassesCache_Service serviceCatalogCompClassesCacheServ;
	
	@Autowired
	private IServiceCatalogRatingsCache_Service serviceCatalogRatingsCacheServ;
	
	@Autowired
	private IServiceCatalogPriceRangeCache_Service serviceCatalogPriceRangeCacheServ; 

	@SuppressWarnings("unchecked")
	@Override
	@Cacheable(value="servicesCache",key = "new org.springframework.cache.interceptor.SimpleKey(#srvCatSeqNo)")
	@HystrixCommand(fallbackMethod = "getAllServices")    
	public List<Long> getAllServicesForCatalog(Long srvCatSeqNo) throws InterruptedException, ExecutionException 
	{
		CompletableFuture<List<Long>> futureC = CompletableFuture.supplyAsync(() -> 
		{
		// get serviceclassList from service_catalog_prodstructure
		CopyOnWriteArrayList<Long> srvList = new CopyOnWriteArrayList<Long>();
		List<Long> gg = null;
		CompletableFuture<ArrayList<Long>> srvFuture=null;
		CompletableFuture<ArrayList<Long>> locFuture=null;
		CompletableFuture<ArrayList<Long>> cmpFuture=null;
		CompletableFuture<ArrayList<Long>> rateFuture=null;
		CompletableFuture<ArrayList<Long>> prnFuture=null;
						
		try {
			srvFuture = this.getServicesForServiceClassList(srvCatSeqNo);
			locFuture=this.getServicesForLocationClassList(srvCatSeqNo);
			cmpFuture=getServicesForSuppliers(srvCatSeqNo);
			rateFuture=getServicesForRatings(srvCatSeqNo);
			prnFuture=getServicesForPriceRange(srvCatSeqNo);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ExecutionException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		       
		       CompletableFuture<Void> futureResult = CompletableFuture.allOf(srvFuture,locFuture, cmpFuture, rateFuture, prnFuture);
		       ArrayList<Long> allList = (ArrayList<Long>) Stream.of(srvFuture,locFuture, cmpFuture, rateFuture, prnFuture).map(CompletableFuture::join).flatMap(List::stream).collect(Collectors.toList());
		       
		return allList;
		},asyncExecutor);
			
		ArrayList<Long> cList = (ArrayList<Long>) futureC.get();
		ArrayList<Long> cNewList = new ArrayList<Long>(); 
		
		synchronized (cList) 
		{		
		HashSet<Long> hset = new HashSet<Long>(cList);
		cNewList.addAll(hset);			
		}
		
		return cNewList;
	}
	
	private CompletableFuture<ArrayList<Long>> getServicesForServiceClassList(Long srvCatSeqNo) throws InterruptedException, ExecutionException 
	{
		// get serviceclassList from service_catalog_prodstructure
		CompletableFuture<CopyOnWriteArrayList<Long>> future1 = CompletableFuture.supplyAsync(() -> 
		{
		CopyOnWriteArrayList<Long> srvCatList=new CopyOnWriteArrayList<Long>();
		
		try {
			CopyOnWriteArrayList<ServiceCatalogServStructureCache> lServiceCatalogServStructure_DTOs = serviceCatalogServStructureCacheServ.getAllServiceCatalogServStructusrv(srvCatSeqNo);
		
		if (lServiceCatalogServStructure_DTOs != null && lServiceCatalogServStructure_DTOs.size() > 0) 
		{
			for (int i = 0; i < lServiceCatalogServStructure_DTOs.size(); i++) 
			{
				if (lServiceCatalogServStructure_DTOs.get(i).getId().getParServiceClassSeqNo() != null) {
					srvCatList.add(lServiceCatalogServStructure_DTOs.get(i).getId().getParServiceClassSeqNo());
				}
			}

			for (int i = 0; i < lServiceCatalogServStructure_DTOs.size(); i++) {
				if (lServiceCatalogServStructure_DTOs.get(i).getId().getServiceClassSeqNo() != null) {
					srvCatList.add(lServiceCatalogServStructure_DTOs.get(i).getId().getServiceClassSeqNo());
				}
			}
		}

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		

		return srvCatList;
		},asyncExecutor);
		
		// get services for service classes in serviceclassList from service_class_details
		CompletableFuture<ArrayList<Long>> cc = future1.thenApplyAsync((input) -> 
		{
			CopyOnWriteArrayList<Long> srvList = null;
			ArrayList<Long> srvLst = new ArrayList<Long>();
		    
			if (input!= null) 
            {
		   	    srvList = servicesCacheRepo.findServicesForServiceClasses(input);
            }
		    
			synchronized (srvLst) 
			{
			for (int i = 0; i < srvList.size(); i++) 
			{
			srvLst.add(srvList.get(i));	
			}	
			}
		    
		    return srvLst;
		},asyncExecutor);
		
						
		return cc;
}
	
	
	// get locationClassList from service_catalog_locstructure
	private CompletableFuture<ArrayList<Long>> getServicesForLocationClassList(Long srvCatSeqNo) throws InterruptedException, ExecutionException 
	{	
	CompletableFuture<CopyOnWriteArrayList<Long>> future3 = CompletableFuture.supplyAsync(() -> 
	{
		CopyOnWriteArrayList<Long> srvLocList =null;
		try {
			CopyOnWriteArrayList<ServiceCatalogLocaStructureCache> srvLocComp = serviceCatalogLocaStructureCacheServ.getAllServiceCatalogLocaStructures(srvCatSeqNo);
			srvLocList =new CopyOnWriteArrayList<Long>();;
			
			if (srvLocComp != null && srvLocComp.size() > 0) 
			{
				for (int i = 0; i < srvLocComp.size(); i++) 
				{
					if (srvLocComp.get(i).getId().getParPlaceClassSeqNo() != null) {
						srvLocList.add(srvLocComp.get(i).getId().getParPlaceClassSeqNo());
					}
				}
				for (int i = 0; i < srvLocComp.size(); i++) 
				{
					if (srvLocComp.get(i).getId().getPlaceClassSeqNo() != null) {
						srvLocList.add(srvLocComp.get(i).getId().getPlaceClassSeqNo());
					}
				}
			}	
			
		}
		catch (InterruptedException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return srvLocList;
		},asyncExecutor);
	

	
		// get locationsList for locations in locationClassList from place_class_details
	CompletableFuture<CopyOnWriteArrayList<Long>> future4 = future3.thenApplyAsync((input) -> 
	{
		
		CopyOnWriteArrayList<Long> srvList = null;
	    if (input!= null) 
        {
	   	    srvList = servicesCacheRepo.findLocationsForLocationsInLocationClasses(input);
    }
	    return srvList;
	},asyncExecutor);
					
			// get services for locationsList from service_location_master
	CompletableFuture<ArrayList<Long>> future5 = future4.thenApplyAsync((input) -> 
	{
		CopyOnWriteArrayList<Long> srvList = null;
		ArrayList<Long> srvLst = new ArrayList<Long>(); 
		
		if (input!= null) 
        {
	    	srvList = servicesCacheRepo.findServicesForLocations(input);
        }
	    
		synchronized (srvLst) 
		{
		for (int i = 0; i < srvList.size(); i++) 
		{
		srvLst.add(srvList.get(i));	
		}	
		}
	    return srvLst;
	},asyncExecutor);
	
	return future5;
}
	// get services for suppliers
	private CompletableFuture<ArrayList<Long>> getServicesForSuppliers(Long srvCatSeqNo) throws InterruptedException, ExecutionException 
	{
		// get supplierclassList from service_catalog_compclasses
		CompletableFuture<CopyOnWriteArrayList<Long>> future6 = CompletableFuture.supplyAsync(() -> 
		{
			CopyOnWriteArrayList<ServiceCatalogCompClassesCache> supClassComps=null;
			try {
				supClassComps = serviceCatalogCompClassesCacheServ.getAllServiceCatalogCompClasses(srvCatSeqNo);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			CopyOnWriteArrayList<Long> supClassList =new CopyOnWriteArrayList<Long>();
			
			if (supClassComps != null && supClassComps.size() > 0) 
			{
				for (int i = 0; i < supClassComps.size(); i++) 
				{
					if (supClassComps.get(i).getId().getCompanyClassSeqNo() != null) {
						supClassList.add(supClassComps.get(i).getId().getCompanyClassSeqNo());
					}
				}

				}						
				return supClassList;
			},asyncExecutor);
		
		// get suppliersList for suppliers in supplierClassList from supplier_class_details
		CompletableFuture<CopyOnWriteArrayList<Long>> future7 = future6.thenApplyAsync((input) -> 
		{
			CopyOnWriteArrayList<Long> supComp = null;
            if (input != null) 
            {
            		supComp = servicesCacheRepo.findSupplierListForSupplierClasses(input); 
            }
    	    return supComp;
    	},asyncExecutor);
		
		// get services for suppliersList from SUPPLIER_PRODSERV_details	
		CompletableFuture<ArrayList<Long>> future8 = future7.thenApplyAsync((input) -> 
		{
			CopyOnWriteArrayList<Long> srvList = null;
            ArrayList<Long> srvLst = new ArrayList<Long>(); 
			
            if (input != null) 
            {
            	srvList = servicesCacheRepo.findServicesForSuppliers(input); 
            }
            
    		synchronized (srvLst) 
    		{
    		for (int i = 0; i < srvList.size(); i++) 
    		{
    		srvLst.add(srvList.get(i));	
    		}	
    		}
    	    return srvLst;

    	},asyncExecutor);

		return future8;		
				
	}
	
	// get services for ratings
	private CompletableFuture<ArrayList<Long>> getServicesForRatings(Long srvCatSeqNo) throws InterruptedException, ExecutionException 
	{
		CompletableFuture<CopyOnWriteArrayList<Float>> future9 = CompletableFuture.supplyAsync(() -> 
		{
			CopyOnWriteArrayList<ServiceCatalogRatingsCache> supRateList=null;
			try {
				supRateList = serviceCatalogRatingsCacheServ.getAllServiceCatalogRatings(srvCatSeqNo);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			CopyOnWriteArrayList<Float>  cList = new CopyOnWriteArrayList<Float>(); 
			if(supRateList!=null)
			{	
			for (int i = 0; i < supRateList.size(); i++) 
			{
			cList.add(supRateList.get(i).getId().getRating());	
			}	
			}
			return cList;
		},asyncExecutor);
		
		CompletableFuture<ArrayList<Long>> future10 = future9.thenApplyAsync((input) -> 
		{
			CopyOnWriteArrayList<Long> srvList = null;
			ArrayList<Long> srvLst = new ArrayList<Long>();
			
			if (input != null) 
            {
            srvList= servicesCacheRepo.findServicesForRatings(input); 
            }
			
			synchronized (srvLst) 
    		{
    		for (int i = 0; i < srvList.size(); i++) 
    		{
    		srvLst.add(srvList.get(i));	
    		}	
    		}
    	    return srvLst;    	    
    	},asyncExecutor);

		return future10;
	}
	

	// get services for price range
	private CompletableFuture<ArrayList<Long>> getServicesForPriceRange(Long srvCatSeqNo) throws InterruptedException, ExecutionException 
	{
        CompletableFuture<ServiceCatalogPriceRangeCache> future11 = CompletableFuture.supplyAsync(() -> 
		{
			ServiceCatalogPriceRangeCache supPriceRange=null;
			
			try {
				supPriceRange = serviceCatalogPriceRangeCacheServ.getAllServiceCatalogPriceRange(srvCatSeqNo);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			
			return supPriceRange;
		},asyncExecutor);
		
		CompletableFuture<ArrayList<Long>> future12 = future11.thenApplyAsync((input) -> 
		{
			CopyOnWriteArrayList<Long> srvList = null;
			ArrayList<Long> srvLst = new ArrayList<Long>();
            
			if (input != null) 
            {
			srvList= servicesCacheRepo.findServicesForPriceRange(input.getId().getPriceFr(),input.getId().getPriceTo()); 
            }
			synchronized (srvLst) 
    		{
    		for (int i = 0; i < srvList.size(); i++) 
    		{
    		srvLst.add(srvList.get(i));	
    		}	
    		}
    	    return srvLst;    	    

    	},asyncExecutor);

		return future12;
	}

	@Cacheable("servicesCache")
	public ArrayList<Long> getAllServices() throws InterruptedException, ExecutionException 
	{
        CompletableFuture<ArrayList<Long>> future13 = CompletableFuture.supplyAsync(() -> 
		{
			ArrayList<Long> srvList=null;
			
			CopyOnWriteArrayList<Long>	cpsrvList = servicesCacheRepo.findAllServices();
			if(cpsrvList!=null)
			{
			srvList = new ArrayList<Long>();
			
			for (int i = 0; i < cpsrvList.size(); i++) 
			{
			srvList.add(cpsrvList.get(i));	
			}
			}			
			
			
			return srvList;
		},asyncExecutor);

	return future13.get();
	}
}