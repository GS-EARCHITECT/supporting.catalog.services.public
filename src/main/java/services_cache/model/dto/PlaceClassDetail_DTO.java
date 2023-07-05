package services_cache.model.dto;

import java.io.Serializable;

public class PlaceClassDetail_DTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4555438970757258471L;
	private Long partySeqNo;
	private Long placeClassSeqNo;
	private Long placeSeqNo;

	public Long getPartySeqNo() {
		return partySeqNo;
	}

	public void setPartySeqNo(Long partySeqNo) {
		this.partySeqNo = partySeqNo;
	}

	public Long getPlaceClassSeqNo() {
		return placeClassSeqNo;
	}

	public void setPlaceClassSeqNo(Long placeClassSeqNo) {
		this.placeClassSeqNo = placeClassSeqNo;
	}

	public Long getPlaceSeqNo() {
		return placeSeqNo;
	}

	public void setPlaceSeqNo(Long placeSeqNo) {
		this.placeSeqNo = placeSeqNo;
	}

	public PlaceClassDetail_DTO(Long partySeqNo, Long placeClassSeqNo, Long placeSeqNo) {
		super();
		this.partySeqNo = partySeqNo;
		this.placeClassSeqNo = placeClassSeqNo;
		this.placeSeqNo = placeSeqNo;
	}

	public PlaceClassDetail_DTO() {
		super();
	}

}