#!/usr/bin/env python3
import sys
import unittest
import argparse
from test_helper import Req


class TIPTest(unittest.TestCase):
    def expectKeys(self, r, expectedKeys):
        self.assertTrue(r.successful())
        for key in expectedKeys:
            self.assertTrue(key in r.out_dict.keys(),
                            "%s not in response" % key)

    def expectKeysNotPresent(self, r, notPresent):
        self.assertTrue(r.successful())
        for key in notPresent:
            self.assertTrue(key in r.out_dict.keys(),
                            "%s present in response, shouldn't be" % key)


class Config(TIPTest):
    def test_simple(self):
        r = Req(self, 'config')
        r.runWithoutBody()
        expectedKeys = ['requestType', 'requestVersion', 'serverName',
                        'placeAttributes', 'optimizations', 'filters']
        self.expectKeys(r, expectedKeys)
        self.assertEqual(r.out.optimizations, ['none', 'short', 'shorter'])
        self.assertEqual(type(r.out.serverName), str)
        self.assertEqual(len(r.out.placeAttributes), 9)


def define_place(lat, lon):
    return {'latitude': str(lat), 'longitude': str(lon)}


class Distance(TIPTest):
    def test_simple(self):
        r = Req(self, 'distance')
        r.run({
            'origin': define_place(1, 1),
            'destination': define_place(50, 50),
            'earthRadius': 3958.8,
        })
        self.assertTrue(r.successful())
        expectedKeys = ['requestType', 'requestVersion', 'origin',
                        'destination', 'distance', 'earthRadius']
        self.expectKeys(r, expectedKeys)


class Find(TIPTest):
    def test_simple(self):
        r = Req(self, 'find')
        r.run({
            'match': 'kjljlkj',
            'limit': 0
        })
        self.assertTrue(r.successful())
        expectedKeys = ["requestType", "requestVersion", "match",
                        "limit", "found", "places"]
        self.expectKeys(r, expectedKeys)

    def test_limit_optional(self):
        r = Req(self, 'find')
        r.run({
            'match': 'kjljlkj'
            # no limit field
        })
        self.assertTrue(r.successful())
        self.assertTrue("limit" not in r.out_dict.keys())


if __name__ == '__main__':
    # # code from https://stackoverflow.com/a/8660290/530160
    parser = argparse.ArgumentParser()
    parser.add_argument('--url', default='http://localhost:8088')
    parser.add_argument('unittest_args', nargs='*')

    args = parser.parse_args()
    assert args.url[-1] != '/'
    Req.baseURL = args.url
    print("Using URL %s" % Req.baseURL)

    # call unittest with remaining args
    newArgs = [sys.argv[0]] + args.unittest_args
    unittest.main(argv=newArgs)
