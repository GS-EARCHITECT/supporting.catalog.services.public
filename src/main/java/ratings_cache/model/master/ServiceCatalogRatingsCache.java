package ratings_cache.model.master;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the SERVICE_CATALOG_RATINGS database table.
 * 
 */
@Entity
@Table(name = "SERVICE_CATALOG_RATINGS")
public class ServiceCatalogRatingsCache implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ServiceCatalogRatingsCachePK id;

	public ServiceCatalogRatingsCache() {
	}

	public ServiceCatalogRatingsCachePK getId() {
		return this.id;
	}

	public void setId(ServiceCatalogRatingsCachePK id) {
		this.id = id;
	}

	public ServiceCatalogRatingsCache(ServiceCatalogRatingsCachePK id) {
		super();
		this.id = id;
	}

}