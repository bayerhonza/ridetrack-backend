
package com.ensimag.ridetrack.radio;


import com.mongodb.client.MongoClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;

@Configuration
public class MongoConnConfig extends AbstractMongoClientConfiguration {

    protected String getDatabaseName() {
        return "test";
    }

    @Override
    public MongoClient mongoClient() {
        return null;
    }

}
