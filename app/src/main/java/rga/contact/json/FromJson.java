package rga.contact.json;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import rga.contact.model.Contact;

public class FromJson {

    public static List<Contact> toContacts(String json) {
        Gson gson = new Gson();
        Type contactsType = new TypeToken<List<Contact>>(){}.getType();
        return gson.fromJson(json, contactsType);
    }
}

