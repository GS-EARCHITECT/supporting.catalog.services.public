package compclasses_cache.model.master;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the SERVICE_CATALOG_COMPCLASSES database table.
 * 
 */
@Entity
@Table(name="SERVICE_CATALOG_COMPCLASSES")
public class ServiceCatalogCompClassesCache implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ServiceCatalogCompClassesCachePK id;

	public ServiceCatalogCompClassesCache() {
	}

	public ServiceCatalogCompClassesCachePK getId() {
		return this.id;
	}

	public void setId(ServiceCatalogCompClassesCachePK id) {
		this.id = id;
	}

	public ServiceCatalogCompClassesCache(ServiceCatalogCompClassesCachePK id) {
		super();
		this.id = id;
	}
	
	

}