import './enzyme.config.js';
import React from 'react';
import { mount } from 'enzyme';
import Calculator from '../src/components/Application/Calculator/Calculator';


const startProperties = {
  'options': {
    'units': {'miles': 3959, 'kilometers': 6371, 'nautical miles': 3440},
    'activeUnit': 'miles',
  }
};

const startSettings = {
  serverPort: 'black-bottle.cs.colostate.edu:31400'
};

const locationsZeroZero = {
  origin: {coordinates: "0,0", error: false},
  destination: {coordinates: "0,0", error: false},
}

function mockZeroDistRequest() {
  fetch.mockResponse(JSON.stringify({
    "requestType": "distance",
    "requestVersion": 4,
    "origin": {"latitude": "0", "longitude": "0"},
    "destination": {"latitude": "0", "longitude": "0"},
    "distance": 0,
  }));
}

function testCreateInputFields() {
  mockZeroDistRequest();
  const updateDistance = jest.fn();
  const unused = jest.fn();
  const calculator = mount((
      <Calculator
        updateDistance={updateDistance}
        options={startProperties.options}
        settings={startSettings}
        locations={locationsZeroZero}
        distance="0"
        updateLocationOnChange={unused} />));

  let numberOfInputs = calculator.find('Input').length;
  expect(numberOfInputs).toEqual(2);

  let actualInputs = [];
  calculator.find('Input').map((input) => actualInputs.push(input.prop('name')));

  let expectedInputs = [
    'coordinates',
    'coordinates',
  ];

  expect(actualInputs).toEqual(expectedInputs);
  expect(unused).not.toHaveBeenCalled();
}

/* Tests that createForm() correctly renders 4 Input components */
test('Testing the createForm() function in Calculator', testCreateInputFields);

function testInputsOnChange() {
  const unused = jest.fn();
  const updateLocationOnChange = jest.fn()
  const calculator = mount((
    <Calculator
      options={startProperties.options}
      updateDistance={unused}
      settings={startSettings}
      locations={locationsZeroZero}
      updateLocationOnChange={updateLocationOnChange}
      distance="0" />
  ));

  for (let inputIndex = 0; inputIndex < 2; inputIndex++){
    simulateOnChangeEvent(inputIndex, calculator);
  }

  expect(updateLocationOnChange).toHaveBeenCalledWith({
    origin: {coordinates: "42,42", error: false},
    destination: {coordinates: "0,0", error: false},
  });
  expect(updateLocationOnChange).toHaveBeenCalledWith({
    origin: {coordinates: "0,0", error: false},
    destination: {coordinates: "43,43", error: false},
  });
  expect(unused).not.toHaveBeenCalled();
}
test('Testing the onChange event of longitude Input in Calculator', testInputsOnChange);

function simulateOnChangeEvent(inputIndex, reactWrapper) {
  let eventName = 'coordinate';
  let coordinate = inputIndex + 42;
  let coordinateStr = coordinate + ',' + coordinate
  let event = {target: {name: eventName, value: coordinateStr}};
  switch(inputIndex) {
    case 0:
      reactWrapper.find('#originCoordinates').at(0).simulate('change', event);
      break;
    case 1:
      reactWrapper.find('#destinationCoordinates').at(0).simulate('change', event);
      break;
    default:
  }
  reactWrapper.update();
}

/* Loop through the Input indexes and simulate an onChange event with the index
 * as the input. To simulate the change, an event object needs to be created
 * with the name corresponding to its Input 'name' prop. Based on the index,
 * find the corresponding Input by its 'id' prop and simulate the change.
 *
 * Note: using find() with a prop as a selector for Inputs will return 2 objects:
 * 1: The function associated with the Input that is created by React
 * 2: The Input component itself
 *
 * The values in state() should be the ones assigned in the simulations.
 *
 * https://github.com/airbnb/enzyme/blob/master/docs/api/ShallowWrapper/simulate.md
 * https://airbnb.io/enzyme/docs/api/ReactWrapper/props.html
 * https://airbnb.io/enzyme/docs/api/ReactWrapper/find.html
 */

function testShowError() {
  const unused = jest.fn();
  mockZeroDistRequest();
  let calculator = mount((
    <Calculator
      options={startProperties.options}
      settings={startSettings}
      locations={locationsZeroZero}
      distance="0"
      updateDistance={unused}
      updateLocationOnChange={unused} />));
  calculator.instance().showClientSideError("My hovercraft is full of eels");
  calculator.update()
  let actualAlerts = calculator.find('Alert').length;
  expect(actualAlerts).toEqual(1);
  expect(unused).not.toHaveBeenCalled();
}
test('Create client side error', testShowError);

function testBadOriginCoordinates() {
  const updateDistance = jest.fn();
  const unused = jest.fn();
  const locations_error = {
    origin: {coordinates: 'foo', error: false},
    destination: {coordinates: '100, 100', error: false},
  };
  let calculator = mount((
    <Calculator
      options={startProperties.options}
      updateDistance={updateDistance}
      settings={startSettings}
      locations={locations_error}
      distance="0"
      updateLocationOnChange={unused} />));
  calculator.instance().calculateDistance();
  calculator.update();
  let actualAlerts = calculator.find('Alert').length;
  expect(actualAlerts).toEqual(1);

  // We put bad coordinates in, expect to get an error distantance out
  expect(updateDistance).toHaveBeenCalledWith("-");
  expect(unused).not.toHaveBeenCalled();
}
test('Input invalid coordinates', testBadOriginCoordinates);

function testBadDestinationCoordinates() {
  const updateDistance = jest.fn();
  const unused = jest.fn();
  const locations_error = {
    origin: {coordinates: '100, 100', error: false},
    destination: {coordinates: 'bar', error: false},
  };
  let calculator = mount((
    <Calculator
      options={startProperties.options}
      settings={startSettings}
      updateDistance={updateDistance}
      locations={locations_error}
      distance="0"
      updateLocationOnChange={unused} />));
  calculator.instance().calculateDistance();
  calculator.update();
  let actualAlerts = calculator.find('Alert').length;
  expect(actualAlerts).toEqual(1);
  expect(unused).not.toHaveBeenCalled();
}
test('Input invalid destination coordinates', testBadDestinationCoordinates);
