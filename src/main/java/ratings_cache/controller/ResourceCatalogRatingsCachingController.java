package ratings_cache.controller;

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

import ratings_cache.model.master.ResourceCatalogRatingsCache;
import ratings_cache.services.IResourceCatalogRatingsCache_Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/resourceCatalogRatingsCacheManagement")
public class ResourceCatalogRatingsCachingController 
{

//	private static final Logger logger = LoggerFactory.getLogger(ResourceCatalogMasterController.class);

	@Autowired
	private IResourceCatalogRatingsCache_Service resourceCatalogRatingsCacheServ;

	@GetMapping(value = "/getAllResourceCatalogRatingsFromCache/{resCatSeqNo}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ArrayList<ResourceCatalogRatingsCache>> getAllResourceCatalogRatingssFromCache(@PathVariable Long resCatSeqNo) 
	{
		ArrayList<ResourceCatalogRatingsCache> resourceCatalogDTOs=null;
		try {
			resourceCatalogDTOs = resourceCatalogRatingsCacheServ.getAllResourceCatalogRatings(resCatSeqNo);
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