package resources_cache.model.dto;

import java.io.Serializable;

public class ResourceCatalogLocStructure_DTO implements Serializable 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4158292859969236085L;
	private Long locationClassSeqNo;
	private Long parLocationClassSeqNo;
	private Long resourceCatalogSeqNo;

	public Long getLocationClassSeqNo() {
		return locationClassSeqNo;
	}

	public void setLocationClassSeqNo(Long locationClassSeqNo) {
		this.locationClassSeqNo = locationClassSeqNo;
	}

	public Long getParLocationClassSeqNo() {
		return parLocationClassSeqNo;
	}

	public void setParLocationClassSeqNo(Long parLocationClassSeqNo) {
		this.parLocationClassSeqNo = parLocationClassSeqNo;
	}

	public Long getResourceCatalogSeqNo() {
		return resourceCatalogSeqNo;
	}

	public void setResourceCatalogSeqNo(Long resourceCatalogSeqNo) {
		this.resourceCatalogSeqNo = resourceCatalogSeqNo;
	}

	public ResourceCatalogLocStructure_DTO(Long locationClassSeqNo, Long parLocationClassSeqNo,
			Long resourceCatalogSeqNo) {
		super();
		this.locationClassSeqNo = locationClassSeqNo;
		this.parLocationClassSeqNo = parLocationClassSeqNo;
		this.resourceCatalogSeqNo = resourceCatalogSeqNo;
	}

	public ResourceCatalogLocStructure_DTO() {
		super();
	}

}