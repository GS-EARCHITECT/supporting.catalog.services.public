package services_cache.services;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public interface IServicesCache_Service
{
public List<Long> getAllServicesForCatalog(Long srvCatSeqNo) throws InterruptedException, ExecutionException;    
public ArrayList<Long> getAllServices() throws InterruptedException, ExecutionException;
}