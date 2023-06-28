package resources_cache.controller;

import java.util.ArrayList;
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
import resources_cache.services.IResourcesCache_Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/resourcesCacheManagement")
public class ResourceCatalogMasterController {

	private static final Logger logger = LoggerFactory.getLogger(ResourceCatalogMasterController.class);

	@Autowired
	private IResourcesCache_Service resourceCacheServ;

	@GetMapping(value = "/getAllResourcesFromCache/{resCatSeqNo}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<CopyOnWriteArrayList<Long>> getAllResourcesFromCache(@PathVariable Long resCatSeqNo) 
	{
	//	DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		//Date date = new Date();
	//	logger.info("hit at condition async : " + System.currentTimeMillis());
		CopyOnWriteArrayList<Long> resourceCatalogDTOs=null;
		try {
			resourceCatalogDTOs = resourceCacheServ.getAllResourcesForCatalog(resCatSeqNo);
			logger.info("in controller: "+resourceCatalogDTOs.size());
			
	        for (int i = 0; i < resourceCatalogDTOs.size(); i++) 
	        {
	        logger.info("result :"+resourceCatalogDTOs.get(i));
			}
			
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