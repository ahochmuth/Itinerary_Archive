import './enzyme.config.js';
import React from 'react';
import {mount, shallow} from 'enzyme';
import CustomAddDialog from '../src/components/Application/Itinerary/CustomAddDialog.jsx';
import {Button} from 'reactstrap';
import {findButton} from './test-utils.js';

function testAddLocationButton() {
    const addPlace = jest.fn();
    let cad = mount(<CustomAddDialog addPlace={addPlace} />);

    cad.setState({
        isOpen: true,
        name: 'Sesame Street',
        coordinateString: '12,34',
    });
    cad.update();

    const addLocation = findButton(cad, "Add Location");
    addLocation.prop('onClick')();
    const newPlace = {
        name: 'Sesame Street',
        id: 'Sesame Street',
        latitude: '12',
        longitude: '34',
    }
    expect(addPlace).toHaveBeenCalledWith(newPlace)
}
test("Test add location button", testAddLocationButton);


function testValidateCoordinates() {
    let cad = shallow(<CustomAddDialog addPlace={null} />);
    cad.setState({
        name: "place",
        coordinateString: '1,-1',
    });
    const actual = cad.instance().isPlaceReadyToAdd();
    expect(actual).toEqual(true);
}

test("Test isPlaceReadyToAdd", testValidateCoordinates);

