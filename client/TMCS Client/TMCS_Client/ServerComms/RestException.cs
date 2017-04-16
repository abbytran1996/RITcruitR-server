using System;
using System.Collections.Generic;
using System.Net;
using System.Text;

namespace TMCS_Client.ServerComms {
    /// <summary>
    /// Exception for things to throw when a REST call doesn't do what you wanted
    /// </summary>
    class RestException : Exception {
        public RestException(HttpStatusCode statusCode) : base("Could not add student, HTTP status " + statusCode) { }
    }
}
