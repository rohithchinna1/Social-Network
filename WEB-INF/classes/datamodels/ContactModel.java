package datamodels;

import beans.Contact;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;

public class ContactModel{

	DBCollection contacts;

	public static final String userid = "userid";
	public static final String phone = "phone";
	public static final String twitter = "twitter";
	public static final String facebook = "facebook";
	public static final String linkedin = "linkedin";
	public static final String address = "address";
	public static final String city = "city";
	public static final String state = "state";
	public static final String country = "country";
	public static final String zipcode = "zipcode";

	public ContactModel(){
		Model model = Model.createInstance();
		contacts = model.getCollection("Contact");
	}

	public void update(Contact contact) {
		BasicDBObject fields = new BasicDBObject();
		fields.put(userid, contact.getUserid());

		DBCursor cursor = contacts.find(fields);

		BasicDBObject ContactObj = new BasicDBObject()
				.append(phone, contact.getPhone())
				.append(twitter, contact.getTwitter())
				.append(facebook, contact.getFacebook())
				.append(linkedin, contact.getLinkedin())
				.append(address, contact.getAddress())
				.append(city, contact.getCity())
				.append(state, contact.getState())
				.append(country, contact.getCountry())
				.append(zipcode, contact.getZipcode());
		
		if (cursor.hasNext()) {
			BasicDBObject setfields = new BasicDBObject();
			setfields.append("$set", ContactObj);					
			contacts.update(fields, setfields);
		} else {
			ContactObj.put(userid,contact.getUserid());
			contacts.insert(ContactObj);
		}
	}
	
	public Contact getContact(String UserID){
		BasicDBObject fields = new BasicDBObject();
		fields.put(userid, UserID);

		Contact contact = new Contact();
		DBCursor cursor = contacts.find(fields);
		if (cursor.hasNext()) {
			BasicDBObject bobj = (BasicDBObject) cursor.next();
			contact.setUserid(bobj.getString(userid));
			contact.setPhone(bobj.getString(phone));
			contact.setTwitter(bobj.getString(twitter));
			contact.setFacebook(bobj.getString(facebook));
			contact.setLinkedin(bobj.getString(linkedin));
			contact.setAddress(bobj.getString(address));
			contact.setCity(bobj.getString(city));
			contact.setState(bobj.getString(state));
			contact.setCountry(bobj.getString(country));
			contact.setZipcode(bobj.getString(zipcode));;
			return contact;
		}
		return contact;
	}
}
