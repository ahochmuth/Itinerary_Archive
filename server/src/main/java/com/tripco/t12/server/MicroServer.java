package com.tripco.t12.server;

import static spark.Spark.secure;

import com.google.gson.Gson;

import com.tripco.t12.TIP.ClientSideException;
import com.tripco.t12.TIP.TIPConfig;
import com.tripco.t12.TIP.TIPDistance;
import com.tripco.t12.TIP.TIPFind;
import com.tripco.t12.TIP.TIPHeader;
import com.tripco.t12.TIP.TIPItinerary;

import com.tripco.t12.schema.TIPSchemas;

import java.lang.reflect.Type;

import org.everit.json.schema.Schema;
import org.everit.json.schema.ValidationException;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import spark.Request;
import spark.Response;
import spark.Spark;


/** A micro server for a single page web application that serves the static files
 * and processes restful API requests.
 */
class MicroServer {

  private static final Logger log = LoggerFactory.getLogger(MicroServer.class);


  MicroServer(int serverPort) {
    configureServer(serverPort);
    serveStaticPages();
    processRestfulAPIrequests();
    log.info("MicroServer running on port: {}", serverPort);
  }


  private void configureServer(int serverPort) {
    Spark.port(serverPort);
    String keystoreFile = System.getenv("KEYSTORE_FILE");
    String keystorePassword = System.getenv("KEYSTORE_PASSWORD");
    if (keystoreFile != null && keystorePassword != null) {
      secure(keystoreFile, keystorePassword, null, null);
      log.info("Keystore file: {}", keystoreFile);
      log.info("Keystore password: {}", keystorePassword);
      log.info("MicroServer using HTTPS.");
    } else {
      log.info("MicroServer using HTTP.");
    }
    log.trace("Server configuration complete");
  }


  private void serveStaticPages() {
    String path = "/public/";
    Spark.staticFileLocation(path);
    Spark.get("/", (req, res) -> { res.redirect("index.html"); return null; });
    log.trace("Static file configuration complete");
  }


  private void processRestfulAPIrequests() {
    Spark.get("/api/config", this::processTIPconfigRequest);
    Spark.post("/api/distance", this::processTIPdistanceRequest);
    Spark.post("/api/itinerary", this::processTIPitineraryRequest);
    Spark.post("/api/find", this::processTIPfindRequest);
    Spark.get("/api/echo", this::echoHTTPrequest);
    log.trace("Restful configuration complete");
  }


  private String processTIPconfigRequest(Request request, Response response) {
    log.info("TIP Config request: {}", HTTPrequestToJson(request));
    response.type("application/json");
    response.header("Access-Control-Allow-Origin", "*");
    response.status(200);
    try {
      Gson jsonConverter = new Gson();
      TIPConfig tipRequest = new TIPConfig();
      tipRequest.buildResponse();
      String responseBody = jsonConverter.toJson(tipRequest);
      if (!TIPSchemas.validateJsonAgainstSchema(TIPConfig.class, responseBody)) {
        log.trace("Invalid Config Response: ", responseBody);
        throw new RuntimeException("Invalid Config Response");
      }
      log.trace("TIP Config response: {}", responseBody);
      return responseBody;
    } catch (Exception e) {
      log.error("Exception: {}", e);
      response.status(500);
      return request.body();
    }
  }


  private String processTIPdistanceRequest(Request request, Response response) {
    return processTIPrequest(TIPDistance.class, request, response);
  }


  private String processTIPitineraryRequest(Request request, Response response) {
    return processTIPrequest(TIPItinerary.class, request, response);
  }


  private String processTIPfindRequest(Request request, Response response) {
    return processTIPrequest(TIPFind.class, request, response);
  }


  private String processTIPrequest(Type tipType, Request request, Response response) {
    log.info("TIP Request: {}", HTTPrequestToJson(request));
    response.type("application/json");
    response.header("Access-Control-Allow-Origin", "*");
    response.status(200);
    try {
      if (!TIPSchemas.validateJsonAgainstSchema(tipType, request.body())) {
        response.status(400);
        log.info("Invalid TIP Request {}", request.body());
        return request.body();
      }
      Gson jsonConverter = new Gson();
      TIPHeader tipRequest = jsonConverter.fromJson(request.body(), tipType);
      tipRequest.buildResponse();

      String responseBody = jsonConverter.toJson(tipRequest);
      log.trace("TIP Response: {}", responseBody);
      return responseBody;
    } catch (Exception e) {
      log.error("Exception: {}", e);
      response.status(500);
      return request.body();
    } catch (ClientSideException e) {
      // We got an exception that was caused by a
      // badly formed client request
      log.error("Client caused exception: ", e);
      response.status(400);
      return request.body();
    }
  }


  private String echoHTTPrequest(Request request, Response response) {
    response.type("application/json");
    response.header("Access-Control-Allow-Origin", "*");
    return HTTPrequestToJson(request);
  }


  private String HTTPrequestToJson(Request request) {
    return "{\n"
        + "\"attributes\":\"" + request.attributes() + "\",\n"
        + "\"body\":\"" + request.body() + "\",\n"
        + "\"contentLength\":\"" + request.contentLength() + "\",\n"
        + "\"contentType\":\"" + request.contentType() + "\",\n"
        + "\"contextPath\":\"" + request.contextPath() + "\",\n"
        + "\"cookies\":\"" + request.cookies() + "\",\n"
        + "\"headers\":\"" + request.headers() + "\",\n"
        + "\"host\":\"" + request.host() + "\",\n"
        + "\"ip\":\"" + request.ip() + "\",\n"
        + "\"params\":\"" + request.params() + "\",\n"
        + "\"pathInfo\":\"" + request.pathInfo() + "\",\n"
        + "\"serverPort\":\"" + request.port() + "\",\n"
        + "\"protocol\":\"" + request.protocol() + "\",\n"
        + "\"queryParams\":\"" + request.queryParams() + "\",\n"
        + "\"requestMethod\":\"" + request.requestMethod() + "\",\n"
        + "\"scheme\":\"" + request.scheme() + "\",\n"
        + "\"servletPath\":\"" + request.servletPath() + "\",\n"
        + "\"session\":\"" + request.session() + "\",\n"
        + "\"uri()\":\"" + request.uri() + "\",\n"
        + "\"url()\":\"" + request.url() + "\",\n"
        + "\"userAgent\":\"" + request.userAgent() + "\"\n"
        + "}";
  }
}
