package com.imanmobile.sms.config;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import org.mongodb.morphia.AdvancedDatastore;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.UnknownHostException;

/**
 * Created by jome on 2014/02/28.
 */

@Configuration
public class MongoConfig {

    @Bean(name="mongo")
    Mongo mongoClient(){
        MongoClient client=null;
        try {
            client = new MongoClient("localhost");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return client;
    }

    @Bean
    Datastore morphia(){
        Morphia morphia = new Morphia();
        morphia.mapPackage("com.imanmobile.sms.core.domain");

        Datastore ds = morphia.createDatastore(mongoClient(), "smsmobile");
        AdvancedDatastore ads = (AdvancedDatastore) ds;
        ads.ensureIndexes();
//        ds.ensureIndex(User.class, "username", "username", true, true);
//        ads.ensureIndex(User.class, "email", "email", true, true);
//        ads.ensureIndex(User.class, "cellnumber", "cellnumber", true, true);
//        ads.ensureIndex(Group.class, "name_userid", "name, user_id", true, true);


        return ads;
    }
}
