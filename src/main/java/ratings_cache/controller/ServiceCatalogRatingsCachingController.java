package ratings_cache.controller;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ratings_cache.model.master.ServiceCatalogRatingsCache;
import ratings_cache.services.IServiceCatalogRatingsCache_Service;
import reactor.core.publisher.Flux;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/serviceCatalogRatingsCacheManagement")
public class ServiceCatalogRatingsCachingController 
{

//	private static final Logger logger = LoggerFactory.getLogger(ServiceCatalogMasterController.class);

	@Autowired
	private IServiceCatalogRatingsCache_Service serviceCatalogRatingsCacheServ;

	@GetMapping(value = "/getAllServiceCatalogRatingsFromCache/{srvCatSeqNo}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<CopyOnWriteArrayList<ServiceCatalogRatingsCache>> getAllServiceCatalogRatingssFromCache(@PathVariable Long srvCatSeqNo) 
	{
		CopyOnWriteArrayList<ServiceCatalogRatingsCache> serviceCatalogDTOs=null;
		try {
			serviceCatalogDTOs = serviceCatalogRatingsCacheServ.getAllServiceCatalogRatings(srvCatSeqNo);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ResponseEntity<>(serviceCatalogDTOs, HttpStatus.OK);
	}
	
	@GetMapping(value = "/getAllServiceCatalogRatingsFromFluxCache/{srvCatSeqNo}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Flux<ServiceCatalogRatingsCache>> getAllServiceCatalogRatingsFromFluxCache(@PathVariable Long srvCatSeqNo) 
	{
		Flux<ServiceCatalogRatingsCache> serviceCatalogDTOs2=null;
		serviceCatalogDTOs2 =Flux.create(emitter -> {
			CompletableFuture<CopyOnWriteArrayList<ServiceCatalogRatingsCache>> future = CompletableFuture.supplyAsync(() -> 
			{
			CopyOnWriteArrayList<ServiceCatalogRatingsCache> serviceCatalogDTOs=null;	
			try {
				serviceCatalogDTOs=serviceCatalogRatingsCacheServ.getAllServiceCatalogRatings(srvCatSeqNo);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return serviceCatalogDTOs;
			});
			future.whenComplete((srvCatDtoList2, exception) -> {
				if (exception == null) {
					srvCatDtoList2.forEach(emitter::next);
					emitter.complete();
				} else {
					emitter.complete();
				}
			});
		});
		
		return new ResponseEntity<>(serviceCatalogDTOs2, HttpStatus.OK);
	}


}