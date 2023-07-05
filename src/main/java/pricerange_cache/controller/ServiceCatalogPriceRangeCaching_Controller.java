package pricerange_cache.controller;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pricerange_cache.model.master.ServiceCatalogPriceRangeCache;
import pricerange_cache.services.IServiceCatalogPriceRangeCache_Service;
import reactor.core.publisher.Mono;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/serviceCatalogPriceRangeCacheManagement")
public class ServiceCatalogPriceRangeCaching_Controller {

	private static final Logger logger = LoggerFactory.getLogger(ServiceCatalogPriceRangeCaching_Controller.class);

	@Autowired
	private IServiceCatalogPriceRangeCache_Service serviceCatalogPriceRangeCacheServ;

	@GetMapping(value = "/getServiceCatalogPriceRangesFromCache/{srvCatSeqNo}", produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ServiceCatalogPriceRangeCache> getAllServiceCatalogPriceRangesFromCache(
			@PathVariable Long srvCatSeqNo) {
		ServiceCatalogPriceRangeCache serviceCatalogDTOs = null;
		try {
			serviceCatalogDTOs = serviceCatalogPriceRangeCacheServ.getAllServiceCatalogPriceRange(srvCatSeqNo);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ResponseEntity<>(serviceCatalogDTOs, HttpStatus.OK);
	}

	@GetMapping(value = "/getServiceCatalogPriceRangesFromMonoCache/{srvCatSeqNo}", produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Mono<ServiceCatalogPriceRangeCache>> getAllServiceCatalogPriceRangesFromMonoCache(@PathVariable Long srvCatSeqNo) {
		CompletableFuture<ServiceCatalogPriceRangeCache> future = CompletableFuture.supplyAsync(() -> {
			ServiceCatalogPriceRangeCache serviceCatalogDTO2 = null;
			try {
				serviceCatalogDTO2 = serviceCatalogPriceRangeCacheServ.getAllServiceCatalogPriceRange(srvCatSeqNo);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return serviceCatalogDTO2;
		});
		Mono<ServiceCatalogPriceRangeCache> monoFromFuture = Mono.fromFuture(future);
		return new ResponseEntity<>(monoFromFuture, HttpStatus.OK);
	}

}