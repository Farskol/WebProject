package com.finalwebproject.pastrtyshop.entity;

public class Client extends Entity {

    private int clientId;
    private String login;
    private String password;
    private String firstName;
    private String secondName;
    private String email;
    private String phoneNumber;
    private ClientRole role;
    private ClientStatus status;
    private ClientDiscount discount;

    public Client(int clientId, String login, String password, String firstName, String secondName, String email, String phoneNumber, ClientRole role, ClientStatus status, ClientDiscount discount) {
        this.clientId = clientId;
        this.login = login;
        this.password = password;
        this.firstName = firstName;
        this.secondName = secondName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.status = status;
        this.discount = discount;
    }

    public Client(int clientId, String login, String password, String firstName, String secondName, String email, String phoneNumber, ClientRole role, ClientStatus status) {
        this.clientId = clientId;
        this.login = login;
        this.password = password;
        this.firstName = firstName;
        this.secondName = secondName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.status = status;
        this.discount = new ClientDiscount();
    }

    public Client(int clientId, String login, String password, String firstName, String secondName, ClientRole role, ClientStatus status) {
        this.clientId = clientId;
        this.login = login;
        this.password = password;
        this.firstName = firstName;
        this.secondName = secondName;
        this.role = role;
        this.status = status;
        this.discount = new ClientDiscount();
    }

    public Client(int clientId, String login, String password, String firstName, String phoneNumber, ClientRole role, ClientStatus status, ClientDiscount discount) {
        this.clientId = clientId;
        this.login = login;
        this.password = password;
        this.firstName = firstName;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.status = status;
        this.discount = discount;
    }

    public Client(int clientId, String login, String password, String firstName, String email, String phoneNumber, ClientRole role, ClientStatus status, ClientDiscount discount) {
        this.clientId = clientId;
        this.login = login;
        this.password = password;
        this.firstName = firstName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.status = status;
        this.discount = discount;
    }

    public Client(int clientId, String login, String password, String secondName, String email, String phoneNumber, ClientRole role, ClientStatus status) {
        this.clientId = clientId;
        this.login = login;
        this.password = password;
        this.secondName = secondName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.status = status;
        this.discount = new ClientDiscount();
    }

    public Client(String login, String password, String firstName, String secondName, String email, String phoneNumber, ClientRole role, ClientStatus status) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
        this.secondName = secondName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.status = status;
    }

    public Client(int clientId) {
        this.clientId = clientId;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public ClientRole getRole() {
        return role;
    }

    public void setRole(ClientRole role) {
        this.role = role;
    }

    public ClientStatus getStatus() {
        return status;
    }

    public void setStatus(ClientStatus status) {
        this.status = status;
    }

    public ClientDiscount getDiscount() {
        return discount;
    }

    public void setDiscount(ClientDiscount discount) {
        this.discount = discount;
    }

    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(o == null || !this.getClass().equals(o.getClass())) return false;

        Client client = (Client) o;

        if(this.clientId != 0 ? this.clientId != client.clientId : client.clientId != 0) return false;
        if (this.login != null ? !this.login.equals(client.login) : client.login != null) return false;
        if (this.password != null ? !this.login.equals(client.password) : client.password != null) return false;
        if (this.firstName != null ? !this.firstName.equals(client.firstName) : client.firstName != null) return false;
        if (this.secondName != null ? !this.secondName.equals(client.secondName) : client.secondName != null) return false;
        if (this.email != null ? !this.email.equals(client.email) : client.email != null) return false;
        if (this.phoneNumber != null ? !this.phoneNumber.equals(client.phoneNumber) : client.phoneNumber != null) return false;
        if (this.role != null ? !this.role.equals(client.role) : client.role != null) return false;
        if (this.status != null ? !this.status.equals(client.status) : client.status != null) return false;
        if (this.discount != null ? !this.discount.equals(client.discount) : client.discount != null) return false;
        return true;
    }

    @Override
    public int hashCode(){
        int result = 12 * (this.clientId != 0 ? this.clientId : 0);
        result = 12 * result + (this.login != null ? this.login.hashCode() : 0);
        result = 12 * result + (this.password != null ? this.password.hashCode() : 0);
        result = 12 * result + (this.firstName != null ? this.firstName.hashCode() : 0);
        result = 12 * result + (this.secondName != null ? this.secondName.hashCode() : 0);
        result = 12 * result + (this.email != null ? this.email.hashCode() : 0);
        result = 12 * result + (this.phoneNumber != null ? this.phoneNumber.hashCode() : 0);
        result = 12 * result + (this.role != null ? this.role.hashCode() : 0);
        result = 12 * result + (this.status != null ? this.status.hashCode() : 0);
        result = 12 * result + (this.discount != null ? this.discount.hashCode() : 0);

        return result;
    }

    @Override
    public String toString(){
        StringBuilder sb  = new StringBuilder("Client {");
        sb.append("Client id: ").append(this.clientId).append(", ");
        sb.append(("Login: ")).append(this.login).append(", ");
        sb.append("Password: ").append("not so fast").append(", ");
        sb.append("First name: ").append(this.firstName).append(", ");
        sb.append("Second name: ").append(this.secondName).append(", ");
        sb.append("Email: ").append(this.email).append(", ");
        sb.append("Phone number: ").append(this.phoneNumber).append(", ");
        sb.append("Role: ").append(this.role).append(", ");
        sb.append("Status: ").append(this.status.toString()).append(", ");
        sb.append("Discount: ").append(this.discount.toString()).append("} ");

        return sb.toString();
    }
}
