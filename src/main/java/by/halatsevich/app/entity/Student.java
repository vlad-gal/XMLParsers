package by.halatsevich.app.entity;

public class Student {
    private String login;
    private String name;
    private String faculty;
    private long telephone;
    private Address address = new Address();

    public Student() {
    }

    public Student(String login, String name, String faculty, long telephone, Address address) {
        this.login = login;
        this.name = name;
        this.faculty = faculty;
        this.telephone = telephone;
        this.address = address;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public long getTelephone() {
        return telephone;
    }

    public void setTelephone(long telephone) {
        this.telephone = telephone;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Student{");
        sb.append("login='").append(login).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", faculty='").append(faculty).append('\'');
        sb.append(", telephone=").append(telephone);
        sb.append(", address=").append(address);
        sb.append('}');
        return sb.toString();
    }

    public class Address {
        private String country;
        private String city;
        private String street;

        public Address() {
        }

        public Address(String country, String city, String street) {
            this.country = country;
            this.city = city;
            this.street = street;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getStreet() {
            return street;
        }

        public void setStreet(String street) {
            this.street = street;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("Address{");
            sb.append("country='").append(country).append('\'');
            sb.append(", city='").append(city).append('\'');
            sb.append(", street='").append(street).append('\'');
            sb.append('}');
            return sb.toString();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Address address = (Address) o;

            if (!country.equals(address.country)) return false;
            if (!city.equals(address.city)) return false;
            return street.equals(address.street);
        }

        @Override
        public int hashCode() {
            int result = country.hashCode();
            result = 31 * result + city.hashCode();
            result = 31 * result + street.hashCode();
            return result;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Student student = (Student) o;

        if (telephone != student.telephone) return false;
        if (!login.equals(student.login)) return false;
        if (!name.equals(student.name)) return false;
        if (!faculty.equals(student.faculty)) return false;
        return address.equals(student.address);
    }

    @Override
    public int hashCode() {
        int result = login.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + faculty.hashCode();
        result = 31 * result + (int) (telephone ^ (telephone >>> 32));
        result = 31 * result + address.hashCode();
        return result;
    }
}
