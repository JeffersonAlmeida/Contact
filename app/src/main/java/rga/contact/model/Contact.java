package rga.contact.model;

import java.io.Serializable;

public class Contact implements Serializable {

    private Long id;
    private String name;
    private String email;
    private String born;
    private String bio;
    private String photo;

    public static Contact newNull(){
        return new NullContact();
    }

    protected Contact(){} // Needed by Null Object Design Pattern

    private Contact ( Builder builder ){
        this.id = builder.id;
        this.name = builder.name;
        this.email = builder.email;
        this.born = builder.born;
        this.bio = builder.bio;
        this.photo = builder.photoPath;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBorn() {
        return born;
    }

    public void setBorn(String born) {
        this.born = born;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getPhotoPath() {
        return photo;
    }

    public void setPhotoPath(String photo) {
        this.photo = photo;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public boolean hasImage() {
        return getPhotoPath().contains("/") ? true : false;
    }

    public static class Builder {

        private Long id;
        private String name;
        private String email;
        private String born;
        private String bio;
        private String photoPath;

        public Builder id (Long id){ this.id=id; return this; }
        public Builder name (String name){ this.name = name; return this; }
        public Builder email (String email) { this.email = email; return this; }
        public Builder born (String born) { this.born = born; return this; }
        public Builder bio (String bio){ this.bio = bio; return this; }
        public Builder photoPath(String photoPath){ this.photoPath = photoPath; return this; }

        public Contact build(){ return new Contact(this); }
    }
}
