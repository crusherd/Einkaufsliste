package htwg.backend;

public class Address {

	private int id = 0;
	private String street = "";
	private String zipCode = "";
	private String city = "";

	public Address(int id, String street, String zipCode, String city) {
		this.id = id;
		this.street = street;
		this.zipCode = zipCode;
		this.city = city;
	}

	public int getId() {
		return id;
	}

	public String getStreet() {
		return street;
	}

	public String getZipCode() {
		return zipCode;
	}

	public String getCity() {
		return city;
	}

	@Override
	public String toString() {
		return (street + " " + zipCode + " " + city);
	}
}
