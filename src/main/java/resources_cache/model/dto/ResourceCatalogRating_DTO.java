package resources_cache.model.dto;

import java.io.Serializable;

public class ResourceCatalogRating_DTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1883276621057795703L;
	private Long resourceCatalogSeqNo;
	private Float rating;

	public Long getResourceCatalogSeqNo() {
		return resourceCatalogSeqNo;
	}

	public void setResourceCatalogSeqNo(Long resourceCatalogSeqNo) {
		this.resourceCatalogSeqNo = resourceCatalogSeqNo;
	}

	public Float getRating() {
		return rating;
	}

	public void setRating(Float rating) {
		this.rating = rating;
	}

	public ResourceCatalogRating_DTO(Long resourceCatalogSeqNo, Float rating) {
		super();
		this.resourceCatalogSeqNo = resourceCatalogSeqNo;
		this.rating = rating;
	}

	public ResourceCatalogRating_DTO() {
		super();
	}

}