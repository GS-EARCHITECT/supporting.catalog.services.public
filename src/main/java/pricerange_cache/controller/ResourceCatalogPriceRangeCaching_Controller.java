package pricerange_cache.controller;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ctc.wstx.shaded.msv_core.reader.Controller;
import com.netflix.infix.lang.infix.antlr.EventFilterParser.in_predicate_return;

import pricerange_cache.model.master.ResourceCatalogPriceRangeCache;
import pricerange_cache.services.IResourceCatalogPriceRangeCache_Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/resourceCatalogPriceRangeCacheManagement")
public class ResourceCatalogPriceRangeCaching_Controller 
{

	private static final Logger logger = LoggerFactory.getLogger(ResourceCatalogPriceRangeCaching_Controller.class);

	@Autowired
	private IResourceCatalogPriceRangeCache_Service resourceCatalogPriceRangeCacheServ;

	@GetMapping(value = "/getResourceCatalogPriceRangesFromCache/{resCatSeqNo}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResourceCatalogPriceRangeCache> getAllResourceCatalogPriceRangesFromCache(@PathVariable Long resCatSeqNo) 
	{
		ResourceCatalogPriceRangeCache resourceCatalogDTOs=null;
		try {
			resourceCatalogDTOs = resourceCatalogPriceRangeCacheServ.getAllResourceCatalogPriceRange(resCatSeqNo);
			logger.info("in  Controller :");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ResponseEntity<>(resourceCatalogDTOs, HttpStatus.OK);
	}

}