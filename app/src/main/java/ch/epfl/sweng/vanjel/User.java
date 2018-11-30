package ch.epfl.sweng.vanjel;

/**
 * @author Vincent CABRINI
 * @reviewer Luca JOSS
 */
public abstract class User {

    private String email, firstName, lastName, birthday, street, streetNumber, city, country;

    private Gender gender;

    User(String email, String firstName, String lastName, String birthday, String street,
         String streetNumber, String city, String country, Gender gender) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.street = street;
        this.streetNumber = streetNumber;
        this.city = city;
        this.country = country;
        this.gender = gender;
    }

    User() {}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getFullName(){
        //return "Dr. " + this.firstName + " " + this.lastName;
        return this.firstName + " " + this.lastName;
    }

    @Override
    public String toString() {
        return getFirstName() + " " + getLastName();
    }
}
