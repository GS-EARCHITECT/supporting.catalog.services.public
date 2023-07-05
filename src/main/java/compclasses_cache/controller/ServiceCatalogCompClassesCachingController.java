package compclasses_cache.controller;

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
import compclasses_cache.model.master.ServiceCatalogCompClassesCache;
import compclasses_cache.services.IServiceCatalogCompClassesCache_Service;
import reactor.core.publisher.Flux;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/serviceCatalogCompClassesCacheManagement")
public class ServiceCatalogCompClassesCachingController 
{

//	private static final Logger logger = LoggerFactory.getLogger(ServiceCatalogMasterController.class);

	@Autowired
	private IServiceCatalogCompClassesCache_Service serviceCatalogCompClassesCacheServ;

	@GetMapping(value = "/getAllServiceCatalogCompClassesFromFluxCache/{resCatSeqNo}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Flux<ServiceCatalogCompClassesCache>> getAllServiceCatalogCompClassesFromCache(@PathVariable Long resCatSeqNo) 
	{
		Flux<ServiceCatalogCompClassesCache> serviceCatalogDTOs2=null;
			serviceCatalogDTOs2 =Flux.create(emitter -> {
				CompletableFuture<CopyOnWriteArrayList<ServiceCatalogCompClassesCache>> future = CompletableFuture.supplyAsync(() -> 
				{
				CopyOnWriteArrayList<ServiceCatalogCompClassesCache> serviceCatalogDTOs=null;	
				try {
					serviceCatalogDTOs=serviceCatalogCompClassesCacheServ.getAllServiceCatalogCompClasses(resCatSeqNo);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return serviceCatalogDTOs;
				});
				future.whenComplete((resCatDtoList2, exception) -> {
					if (exception == null) {
						resCatDtoList2.forEach(emitter::next);
						emitter.complete();
					} else {
						emitter.complete();
					}
				});
			}); 
					
		return new ResponseEntity<>(serviceCatalogDTOs2, HttpStatus.OK);
		}
	

	
	
}