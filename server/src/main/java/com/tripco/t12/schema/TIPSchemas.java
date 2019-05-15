package com.tripco.t12.schema;

import com.tripco.t12.TIP.TIPConfig;
import com.tripco.t12.TIP.TIPDistance;
import com.tripco.t12.TIP.TIPFind;
import com.tripco.t12.TIP.TIPItinerary;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;

import org.everit.json.schema.Schema;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class is responsible for loading schema files from disk and
 * choosing the appropriate schema for validating a request.
 */
public final class TIPSchemas {

    /**
     * Prevent construction of this class by having private constructor.
     */
    private TIPSchemas() {

    }

    private static Schema configSchema;
    private static Schema distanceSchema;
    private static Schema findSchema;
    private static Schema itinerarySchema;

    private static final Logger LOGGER = LoggerFactory.getLogger(TIPSchemas.class);

    static {
        loadSchemas();
    }

    private static void loadSchemas() {
        // See server/src/main/resources to view and edit these files
        configSchema = parseSchemaForClass("config.schema.json");
        distanceSchema = parseSchemaForClass("distance.schema.json");
        findSchema = parseSchemaForClass("find.schema.json");
        itinerarySchema = parseSchemaForClass("itinerary.schema.json");
    }

    private static Schema parseSchemaForClass(final String filename) {
        return SchemaLoader.load(new JSONObject(loadSchema(filename)));
    }

    /**
     * Load schema file from resources directory.
     * @param name name of file in server/src/main/resources.
     * @return JSONTokener containing file contents
     */
    private static JSONTokener loadSchema(final String name) {
        // Get resource relative to top-level of jar.
        // If not in jar, looks for any resource not in a package.
        InputStream in = TIPSchemas.class.getResourceAsStream("/" + name);

        // Open file for reading
        return new JSONTokener(in);
    }

    /**
     * Given a type inherited from TIPHeader, return a Schema object that can
     * be used to validate JSON for that TIP type.
     * @param tipType The Type of the expected TIP object.
     * @return Schema that can be used to validate JSON.
     */
    public static Schema loadSchemaForClass(final Type tipType) {
        if (tipType == TIPConfig.class) {
            return configSchema;
        } else if (tipType == TIPDistance.class) {
            return distanceSchema;
        } else if (tipType == TIPItinerary.class) {
            return itinerarySchema;
        } else if (tipType == TIPFind.class) {
            return findSchema;
        } else {
            throw new RuntimeException("Unknown TIP object type");
        }
    }

    /**
     * Validate String as JSON, according to the schema for the TIP object type.
     * @param tipType Expected TIP object type.
     * @param json JSON from client.
     * @return true if json validates, false if JSON does not parse, or JSON is invalid.
     */
    public static boolean validateJsonAgainstSchema(final Type tipType, final String json) {
        Schema schema = loadSchemaForClass(tipType);
        try {
            JSONObject jsonObj = new JSONObject(json);
            schema.validate(jsonObj);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}
