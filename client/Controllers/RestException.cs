using System;
using System.Collections.Generic;
using System.Net;
using System.Text;

namespace TMCS_Client.ServerComms
{
    class RestException : Exception
    {
        public RestException(HttpStatusCode statusCode) : base("Request failed, HTTP status " + statusCode) { }
    }
}
