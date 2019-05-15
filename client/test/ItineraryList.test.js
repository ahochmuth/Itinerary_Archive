import './enzyme.config.js';
import React from 'react';
import {mount} from 'enzyme';
import {DragDropContext} from 'react-beautiful-dnd'
import ItineraryList from '../src/components/Application/Itinerary/ItineraryList.jsx';
import ItineraryItem from '../src/components/Application/Itinerary/ItineraryItem.jsx';

const places = [
    {"id":"dnvr", "name":"Denver",       "latitude": "39.7392",   "longitude": "-104.9903"},
    {"id":"bldr", "name":"Boulder",      "latitude": "40.01499",  "longitude": "-105.27055"},
    {"id":"foco", "name":"Fort Collins", "latitude": "40.585258", "longitude": "-105.084419"},
];

const distances = [29, 58, 65];

const attributesToShow = {
  internalNames: [
    "id",
    "name",
    "latitude",
    "longitude",
    "distance",
    "cumulativeDistance",
  ],
  displayNames: [
    "ID",
    "Name",
    "Latitude",
    "Longitude",
    "Distance",
    "Cumulative Distance",
  ],
};

const emptyMarkersChecked = {};

function testCumulativeDistances() {
    const distances = [1, 2, 3];
    const actualCD = ItineraryList.calculateCumulativeDistances(distances);
    const expectedCD = [1, 3, 6];
    expect(actualCD).toEqual(expectedCD);
}
test("Testing ItineraryList cumulative distance", testCumulativeDistances);


function testRender() {
    const onDragEnd = jest.fn();
    const list = mount(
        <DragDropContext
            onDragEnd={onDragEnd}>
            <ItineraryList
                places={places}
                distances={distances}
                attributesToShow={attributesToShow}
                markersChecked={emptyMarkersChecked} />
        </DragDropContext>
    );
    const actualItemCount = list.find(ItineraryItem).length;
    const expectedItemCount = 3;
    expect(actualItemCount).toEqual(expectedItemCount);
    expect(onDragEnd).not.toHaveBeenCalled();
}
test("Testing ItineraryList render", testRender);
