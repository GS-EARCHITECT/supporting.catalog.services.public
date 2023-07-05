package services_cache.model.dto;

import java.io.Serializable;

public class ServiceLocationMaster_DTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8139503852002964204L;
	private Long serviceSeqNo;
	private Long locationSeqNo;
	private Long companySeqNo;
	private Float qty;
	private Long qtySeqNo;

	public Float getQty() {
		return qty;
	}

	public void setQty(Float qty) {
		this.qty = qty;
	}

	public Long getQtySeqNo() {
		return qtySeqNo;
	}

	public void setQtySeqNo(Long qtySeqNo) {
		this.qtySeqNo = qtySeqNo;
	}

	public Long getServiceSeqNo() {
		return serviceSeqNo;
	}

	public void setServiceSeqNo(Long serviceSeqNo) {
		this.serviceSeqNo = serviceSeqNo;
	}

	public Long getLocationSeqNo() {
		return locationSeqNo;
	}

	public void setLocationSeqNo(Long locationSeqNo) {
		this.locationSeqNo = locationSeqNo;
	}

	public Long getCompanySeqNo() {
		return companySeqNo;
	}

	public void setCompanySeqNo(Long companySeqNo) {
		this.companySeqNo = companySeqNo;
	}

	public ServiceLocationMaster_DTO(Long serviceSeqNo, Long locationSeqNo, Long companySeqNo, Float qty,
			Long qtySeqNo) {
		super();
		this.serviceSeqNo = serviceSeqNo;
		this.locationSeqNo = locationSeqNo;
		this.companySeqNo = companySeqNo;
		this.qty = qty;
		this.qtySeqNo = qtySeqNo;
	}

	public ServiceLocationMaster_DTO() {
		super();
	}

}