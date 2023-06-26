package cacheMgmt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

@Service("catalogCache")
public class CacheServiceImpl implements I_CacheService 
{

	@Autowired
	private CacheManager cacheManager;

	@Override
	public void clearCache(String cacheName) {
		cacheManager.getCache(cacheName).clear();
	}
}
