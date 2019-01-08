package smile.khaled.mohamed.rehab.service.responses.both.signin;

import com.google.gson.annotations.SerializedName;

public class Data{

	@SerializedName("img")
	private String img;

	@SerializedName("address")
	private String address;

	@SerializedName("gender")
	private String gender;

	@SerializedName("mobile")
	private String mobile;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("token")
	private String token;

	@SerializedName("password")
	private String password;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("name")
	private String name;

	@SerializedName("id")
	private String id;

	@SerializedName("lang")
	private String lang;

	@SerializedName("email")
	private String email;

	@SerializedName("username")
	private String username;

	@SerializedName("validate")
	private String validate;

	@SerializedName("status")
	private String status;

	public void setImg(String img){
		this.img = img;
	}

	public String getImg(){
		return img;
	}

	public void setAddress(String address){
		this.address = address;
	}

	public String getAddress(){
		return address;
	}

	public void setGender(String gender){
		this.gender = gender;
	}

	public String getGender(){
		return gender;
	}

	public void setMobile(String mobile){
		this.mobile = mobile;
	}

	public String getMobile(){
		return mobile;
	}

	public void setCreatedAt(String createdAt){
		this.createdAt = createdAt;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public void setToken(String token){
		this.token = token;
	}

	public String getToken(){
		return token;
	}

	public void setPassword(String password){
		this.password = password;
	}

	public String getPassword(){
		return password;
	}

	public void setUpdatedAt(String updatedAt){
		this.updatedAt = updatedAt;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setLang(String lang){
		this.lang = lang;
	}

	public String getLang(){
		return lang;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return email;
	}

	public void setUsername(String username){
		this.username = username;
	}

	public String getUsername(){
		return username;
	}

	public void setValidate(String validate){
		this.validate = validate;
	}

	public String getValidate(){
		return validate;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}

	@Override
 	public String toString(){
		return 
			"{" +
			"img = '" + img + '\'' + 
			",address = '" + address + '\'' + 
			",gender = '" + gender + '\'' + 
			",mobile = '" + mobile + '\'' + 
			",created_at = '" + createdAt + '\'' + 
			",token = '" + token + '\'' + 
			",password = '" + password + '\'' + 
			",updated_at = '" + updatedAt + '\'' + 
			",name = '" + name + '\'' + 
			",id = '" + id + '\'' + 
			",lang = '" + lang + '\'' + 
			",email = '" + email + '\'' + 
			",username = '" + username + '\'' + 
			",validate = '" + validate + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}