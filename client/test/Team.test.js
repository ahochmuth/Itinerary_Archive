import './enzyme.config.js';
import React from 'react';
import { mount, shallow } from 'enzyme';
import Team from '../src/components/Application/Team/Team';
import TeamPane from '../src/components/Application/Team/TeamPane';

function testTeam() {
  const team = shallow(<Team />);

  let nbPanes = team.find(TeamPane).length;
  expect(nbPanes).toEqual(4);
}
test('Team Test', testTeam);

function testTeamDeep() {
  const team = mount(<Team />);

  let nbPanes = team.find('img').length;
  expect(nbPanes).toEqual(4);
}
test('TeamPane test', testTeamDeep);
