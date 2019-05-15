import './enzyme.config.js'
import React from 'react'
import {shallow} from 'enzyme'
import Application from '../src/components/Application/Application'


function testInitialState() {
  mockConfigResponse();

  const app = shallow(<Application/>);

  let actualConfig = app.state().serverConfig;
  let expectedConfig = null;
  expect(actualConfig).toEqual(expectedConfig);

  let actualOptions = app.state().planOptions;
  let expectedOptions = {
    units: {kilometers: 6371, miles: 3959, 'nautical miles': 3440},
    activeUnit: 'miles'
  };

  expect(actualOptions).toEqual(expectedOptions);
}

function mockConfigResponse() {
  fetch.mockResponse(JSON.stringify(
      {
        status: 200,
        statusText: "OK",
        body: {
          "placeAttributes": ["latitude", "longitude", "serverName"],
          "requestType": "config",
          "requestVersion": 4,
          "serverName": "t12"
        },
        type: "basic",
        url: "http://localhost:8088/api/config",
        redirected: false,
        ok: true
      }));
}

test("Testing Application's initial state", testInitialState);

function testUpdateOption() {
  const app = shallow(<Application/>);

  app.instance().updatePlanOption("activeUnit", "miles");

  let actualUnit = app.state().planOptions.activeUnit;
  let expectedUnit = "miles";
  expect(actualUnit).toEqual(expectedUnit);
}

test("Testing Application's updatePlanOption function", testUpdateOption);

function testUpdateOptionKM() {
    const app = shallow(<Application/>);

    app.instance().updatePlanOption("activeUnit", "kilometers");

    let actualUnit = app.state().planOptions.activeUnit;
    let expectedUnit = "kilometers";
    expect(actualUnit).toEqual(expectedUnit);
}

test("Testing Application's updatePlanOption for kilometers", testUpdateOptionKM);

function testUpdateOptionNM() {
    const app = shallow(<Application/>);

    app.instance().updatePlanOption("activeUnit", "nautical miles");

    let actualUnit = app.state().planOptions.activeUnit;
    let expectedUnit = "nautical miles";
    expect(actualUnit).toEqual(expectedUnit);
}

test("Testing Application's updatePlanOption for nautical miles", testUpdateOptionNM);