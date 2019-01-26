package smile.khaled.mohamed.rehab.service.responses.both.cities;

import com.google.gson.annotations.SerializedName;

public class DataItem{

	@SerializedName("city_name")
	private String cityName;

	@SerializedName("id")
	private String id;

	@SerializedName("nationality_name")
	private String nationalityName;

	public void setCityName(String cityName){
		this.cityName = cityName;
	}

	public String getCityName(){
		return cityName;
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

	@Override
 	public String toString(){
		return 
			"DataItem{" + 
			"city_name = '" + cityName + '\'' + 
			",id = '" + id + '\'' + 
			",nationality_name = '" + nationalityName + '\'' + 
			"}";
		}
}