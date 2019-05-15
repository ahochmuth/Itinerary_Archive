import './enzyme.config.js';
import React from 'react';
import { mount, shallow } from 'enzyme';
import { Alert } from 'reactstrap';
import ErrorBanner from '../src/components/Application/ErrorBanner';

function testErrorBanner() {
  const error = shallow(<ErrorBanner
                            statusText="error1"
                            statusCode="500"
                            message="error2" />);

  let nbAlert = error.find(Alert).length;
  expect(nbAlert).toEqual(1);
}
test('Error Banner test', testErrorBanner);

function testErrorBannerDeep() {
  const error = mount(<ErrorBanner
                            statusText="error1"
                            statusCode="500"
                            message="error2" />);

  let alertText = error.find('div').at(0).instance().innerHTML;

  expect(alertText).toEqual(expect.stringContaining("error1"));
  expect(alertText).toEqual(expect.stringContaining("error2"));
  expect(alertText).toEqual(expect.stringContaining("500"));
}
test('Error Banner test deep', testErrorBannerDeep);
