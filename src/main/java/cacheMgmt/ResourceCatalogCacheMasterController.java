package cacheMgmt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import cacheMgmt.I_CacheService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/catalogCacheManagement")
public class ResourceCatalogCacheMasterController
{

	// private static final Logger logger =
	// LoggerFactory.getLogger(ResourceCatalogMasterController.class);

	@Autowired
	private I_CacheService catalogCache;

	@GetMapping("/clearCache/{cacheName}")
	public ResponseEntity<String> clearCache(@PathVariable("cacheName") String cacheName) {
		catalogCache.clearCache(cacheName);
		return new ResponseEntity<>("Cache cleared", HttpStatus.OK);
	}

}