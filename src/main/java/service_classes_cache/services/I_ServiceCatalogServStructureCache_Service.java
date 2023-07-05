package service_classes_cache.services;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;
import service_classes_cache.model.master.ServiceCatalogServStructureCache;

public interface I_ServiceCatalogServStructureCache_Service
{
abstract public CopyOnWriteArrayList<ServiceCatalogServStructureCache> getAllServiceCatalogServStructusrv(Long srvCatSeqNo) throws InterruptedException, ExecutionException;
}