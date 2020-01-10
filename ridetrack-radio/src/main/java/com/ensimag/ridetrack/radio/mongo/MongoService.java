package com.ensimag.ridetrack.radio.mongo;

import org.bson.Document;
import org.springframework.stereotype.Service;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;

@Service
public class MongoService {
	
	private MongoCollection<Document> rawPacketsCollection;
	
	public MongoService() {
		MongoClient client = new MongoClient();
		rawPacketsCollection = client.getDatabase("ridetrack").getCollection("raw_packets");
	}
	
	public void savePacket(String topic, byte[] payload) {
		String string = new String(payload);
		Document document = Document.parse(string);
		document.append("topic", topic);
		rawPacketsCollection.insertOne(document);
	}
	
}
