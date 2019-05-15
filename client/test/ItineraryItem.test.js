import './enzyme.config.js';
import React from 'react';
import { mount, shallow } from 'enzyme';
import ItineraryItem from '../src/components/Application/Itinerary/ItineraryItem.jsx';
import { DragDropContext, Droppable } from 'react-beautiful-dnd';

const place = {
    id: "jaylizz",
    name: "Twisted Pine Brewing Company",
    municipality: "Boulder",
    latitude: "40.0206",
    longitude: "-105.2510",
    altitude: "5283",
}

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


function testRenderItineraryItem() {
  let c = shallow (
    <DragDropContext
      onDragEnd={(e)=>null} >
      <Droppable droppableId="id">
        {provided => (
          <div className="container"
            ref={provided.innerRef}
            {...provided.droppableProps} >
            <table>
              <tbody>
                <ItineraryItem
                  place={place}
                  key={0}
                  index={0}
                  attributesToShow={attributesToShow} />
              </tbody>
            </table>
          </div>
        )}
      </Droppable>
    </DragDropContext>
  );
}
test("Testing rendering ItineraryItem", testRenderItineraryItem);
