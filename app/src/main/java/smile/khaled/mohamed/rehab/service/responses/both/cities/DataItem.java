package smile.khaled.mohamed.rehab.service.responses.both.cities;

import com.google.gson.annotations.SerializedName;

public class DataItem{

	@SerializedName("name_fr")
	private String nameFr;

	@SerializedName("name_ar")
	private String nameAr;

	@SerializedName("id")
	private String id;

	@SerializedName("nationality_name")
	private String nationalityName;

	@SerializedName("country_id")
	private String countryId;

	@SerializedName("name_en")
	private String nameEn;

	public void setNameFr(String nameFr){
		this.nameFr = nameFr;
	}

	public String getNameFr(){
		return nameFr;
	}

	public void setNameAr(String nameAr){
		this.nameAr = nameAr;
	}

	public String getNameAr(){
		return nameAr;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setNationalityName(String nationalityName){
		this.nationalityName = nationalityName;
	}

	public String getNationalityName(){
		return nationalityName;
	}

	public void setCountryId(String countryId){
		this.countryId = countryId;
	}

	public String getCountryId(){
		return countryId;
	}

	public void setNameEn(String nameEn){
		this.nameEn = nameEn;
	}

	public String getNameEn(){
		return nameEn;
	}

	@Override
 	public String toString(){
		return 
			"DataItem{" + 
			"name_fr = '" + nameFr + '\'' + 
			",name_ar = '" + nameAr + '\'' + 
			",id = '" + id + '\'' + 
			",nationality_name = '" + nationalityName + '\'' + 
			",country_id = '" + countryId + '\'' + 
			",name_en = '" + nameEn + '\'' + 
			"}";
		}
}