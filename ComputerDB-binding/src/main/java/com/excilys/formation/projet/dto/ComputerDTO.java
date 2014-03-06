package com.excilys.formation.projet.dto;

import org.hibernate.validator.constraints.NotEmpty;

public class ComputerDTO {
	private long id;
	
	@NotEmpty
	private String name;
	
	@DateFormat
	private String introduced;
	
	@DateFormat
	private String discontinued;
	
	private long company;
	
	private String companyName;
	
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	public ComputerDTO(){}
	
	public long getId() {return id;}
	public void setId(long id) {this.id = id;}
	
	public String getName() {return name;}
	public void setName(String name) {this.name = name;}
	
	public String getIntroduced() {return introduced;}
	public void setIntroduced(String introduced) {this.introduced = introduced;}
	
	public String getDiscontinued() {	return discontinued;}
	public void setDiscontinued(String discontinued) {this.discontinued = discontinued;}
	
	public long getCompany() {return company;}
	public void setCompany(long company) {this.company = company;}
	
	//***********BUILDER***********
	public static class Builder {
		private ComputerDTO computerDTO;
		
		public Builder() {
			computerDTO = new ComputerDTO();
		}
		
		//builder methods for setting property
		public Builder id(long id) {
			computerDTO.setId(id);
			return this;
		}
		public Builder name(String name) {
			computerDTO.setName(name);
			return this;
		}
		public Builder introduced(String introduced) {
			computerDTO.setIntroduced(introduced);
			return this;
		}
		public Builder discontinued(String discontinued){
			computerDTO.setDiscontinued(discontinued);
			return this;
		}
		public Builder companyId(long companyId){
			computerDTO.setCompany(companyId);
			return this;
		}
	
		//return fully build object
		public ComputerDTO build() {
			return this.computerDTO;
		}
	}
}