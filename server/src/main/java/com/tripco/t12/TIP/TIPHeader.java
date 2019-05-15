package com.tripco.t12.TIP;

public abstract class TIPHeader {
  protected Integer requestVersion;
  protected String requestType;

  public abstract void buildResponse() throws ClientSideException;

  @Override
  public abstract String toString();
}
