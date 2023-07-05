package location_classes_cache.controller;

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
import location_classes_cache.model.master.ServiceCatalogLocaStructureCache;
import location_classes_cache.services.I_ServiceCatalogLocaStructureCache_Service;
import reactor.core.publisher.Flux;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/serviceCatalogLocaStructureCacheManagement")
public class ServiceCatalogLocaStructureCachingController 
{

//	private static final Logger logger = LoggerFactory.getLogger(ServiceCatalogMasterController.class);

	@Autowired
	private I_ServiceCatalogLocaStructureCache_Service serviceCatalogLocaStructureServ;

	@GetMapping(value = "/getAllServiceCatalogLocaStructuresFromCache/{resCatSeqNo}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<CopyOnWriteArrayList<ServiceCatalogLocaStructureCache>> getAllServiceCatalogLocaStructuresFromCache(@PathVariable Long resCatSeqNo) 
	{
		CopyOnWriteArrayList<ServiceCatalogLocaStructureCache> serviceCatalogDTOs=null;
		try {
			serviceCatalogDTOs = serviceCatalogLocaStructureServ.getAllServiceCatalogLocaStructures(resCatSeqNo);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ResponseEntity<>(serviceCatalogDTOs, HttpStatus.OK);
	}

	@GetMapping(value = "/getAllServiceCatalogLocaStructuresFromFluxCache/{resCatSeqNo}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Flux<ServiceCatalogLocaStructureCache>> getAllServiceCatalogLocaStructuresFromFluxCache(@PathVariable Long resCatSeqNo) 
	{
		Flux<ServiceCatalogLocaStructureCache> serviceCatalogDTOs2=null;
		serviceCatalogDTOs2 =Flux.create(emitter -> {
			CompletableFuture<CopyOnWriteArrayList<ServiceCatalogLocaStructureCache>> future = CompletableFuture.supplyAsync(() -> 
			{
			CopyOnWriteArrayList<ServiceCatalogLocaStructureCache> serviceCatalogDTOs=null;	
			try {
				serviceCatalogDTOs=serviceCatalogLocaStructureServ.getAllServiceCatalogLocaStructures(resCatSeqNo);
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