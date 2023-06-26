package ratings_cache.model.master;

import java.io.Serializable;

public class ResourceCatalogRatingsCache implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6966261065943170001L;
	private Float rating;
	private Long resourceCatalogSeqNo;

	public Float getRating() {
		return rating;
	}

	public void setRating(Float rating) {
		this.rating = rating;
	}

	public Long getResourceCatalogSeqNo() {
		return resourceCatalogSeqNo;
	}

	public void setResourceCatalogSeqNo(Long resourceCatalogSeqNo) {
		this.resourceCatalogSeqNo = resourceCatalogSeqNo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((rating == null) ? 0 : rating.hashCode());
		result = prime * result + ((resourceCatalogSeqNo == null) ? 0 : resourceCatalogSeqNo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ResourceCatalogRatingsCache other = (ResourceCatalogRatingsCache) obj;
		if (rating == null) {
			if (other.rating != null)
				return false;
		} else if (!rating.equals(other.rating))
			return false;
		if (resourceCatalogSeqNo == null) {
			if (other.resourceCatalogSeqNo != null)
				return false;
		} else if (!resourceCatalogSeqNo.equals(other.resourceCatalogSeqNo))
			return false;
		return true;
	}

	public ResourceCatalogRatingsCache(Float rating, Long resourceCatalogSeqNo) {
		super();
		this.rating = rating;
		this.resourceCatalogSeqNo = resourceCatalogSeqNo;
	}

	public ResourceCatalogRatingsCache() {
		super();
	}

}