package rga.contact.model;

class NullContact extends Contact {

    public Long getId(){
        return null;
    }

    @Override
    public String getName() {
        return "";
    }

    @Override
    public String getBio() {
        return "";
    }

    @Override
    public String getBorn() {
        return "";
    }

    @Override
    public String getEmail() {
        return "";
    }

    @Override
    public String getPhotoPath() {
        return "";
    }
}
