package resources_cache.model.dto;

import java.io.Serializable;

public class ResourceCatalogProdStructure_DTO implements Serializable 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4158292859969236085L;
	private Long resourceClassSeqNo;
	private Long parResourceClassSeqNo;
	private Long resourceCatalogSeqNo;

	public Long getResourceClassSeqNo() {
		return resourceClassSeqNo;
	}

	public void setResourceClassSeqNo(Long resourceClassSeqNo) {
		this.resourceClassSeqNo = resourceClassSeqNo;
	}

	public Long getParResourceClassSeqNo() {
		return parResourceClassSeqNo;
	}

	public void setParResourceClassSeqNo(Long parResourceClassSeqNo) {
		this.parResourceClassSeqNo = parResourceClassSeqNo;
	}

	public Long getResourceCatalogSeqNo() {
		return resourceCatalogSeqNo;
	}

	public void setResourceCatalogSeqNo(Long resourceCatalogSeqNo) {
		this.resourceCatalogSeqNo = resourceCatalogSeqNo;
	}

	public ResourceCatalogProdStructure_DTO(Long resourceClassSeqNo, Long parResourceClassSeqNo,
			Long resourceCatalogSeqNo) {
		super();
		this.resourceClassSeqNo = resourceClassSeqNo;
		this.parResourceClassSeqNo = parResourceClassSeqNo;
		this.resourceCatalogSeqNo = resourceCatalogSeqNo;
	}

	public ResourceCatalogProdStructure_DTO() {
		super();
	}

}