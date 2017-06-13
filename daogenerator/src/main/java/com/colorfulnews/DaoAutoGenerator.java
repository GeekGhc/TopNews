package com.colorfulnews;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class DaoAutoGenerator {
    public static void main(String[] args) throws Exception {
        int version = 1;
        String defaultJavaPackage = "com.kaku.colorfulnews.greendao";

        Schema schema = new Schema(version, defaultJavaPackage);

        createTable(schema);

        new DaoGenerator().generateAll(schema, "./app/src/main/java-gen");
    }

    private static void createTable(Schema schema) {

        Entity entity = schema.addEntity("NewsChannelTable");

        entity.addStringProperty("newsChannelName").notNull().primaryKey().index();

        entity.addStringProperty("newsChannelId").notNull();

        entity.addStringProperty("newsChannelType").notNull();

        entity.addBooleanProperty("newsChannelSelect").notNull();

        entity.addIntProperty("newsChannelIndex").notNull();

        entity.addBooleanProperty("newsChannelFixed");
    }
}