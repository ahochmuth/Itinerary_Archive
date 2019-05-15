import requests
from types import SimpleNamespace

expectedVersion = 4


def client_error(http_code):
    return 400 <= http_code <= 499


def server_error(http_code):
    return 500 <= http_code <= 599


def no_error(http_code):
    return http_code == 200


class Req(object):
    baseURL = ''

    def __init__(self, assertObj, endpoint):
        self.assertObj = assertObj
        self.endpoint = endpoint
        self.endpoint_JSON = endpoint
        self.hasRun = False
        self.version = expectedVersion

    def fuzzVersionAndType(self):
        self.version = 42
        self.endpoint_JSON = "banana"

    def runWithoutBody(self):
        assert not self.hasRun

        self.bodySent = False
        self.request_body = None
        r = requests.get(self.getUrl(), verify='./cs314.pem')
        self._processResponse(r)

    def run(self, body):
        assert not self.hasRun
        self.request_body = body
        self.request_body['requestVersion'] = self.version
        self.request_body['requestType'] = self.endpoint_JSON

        self.bodySent = True
        r = requests.post(self.getUrl(),
                          json=self.request_body,
                          verify='./cs314.pem')
        self._processResponse(r)

    def getUrl(self):
        return Req.baseURL + '/api/' + self.endpoint

    def _processResponse(self, r):
        self.http_code = r.status_code
        self.out_dict = r.json()
        self.out = SimpleNamespace(**self.out_dict)
        self.hasRun = True
        if self.successful():
            self._successAssertions()
        else:
            self._failureAssertions()

    def _successAssertions(self):
        # check that version is correct
        assert expectedVersion == self.out.requestVersion, \
            "Expected version %s, got %s" % \
            (expectedVersion, self.out.requestVersion)
        # check that the type is set to the endpoint
        self.assertObj.assertEqual(self.out.requestType, self.endpoint)
        # check that this is a 2xx code
        self.assertObj.assertTrue(no_error(self.http_code))

    def successful(self):
        assert self.hasRun
        return not (client_error(self.http_code) or
                    server_error(self.http_code))

    def _failureAssertions(self):
        if self.bodySent:
            # on error, body should not be changed
            self._assertBodyUnchanged()

    def statusCode(self):
        """Returns HTTP status code"""
        assert self.hasRun
        return self.http_code

    def _assertBodyUnchanged(self):
        self.assertObj.assertEqual(self.request_body, self.out_dict)
