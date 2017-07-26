using RestSharp;
using System;
using System.Collections.Generic;
using System.Net;
using System.Text;
using TMCS_Client.UI;

namespace TMCS_Client.ServerComms
{
    public class ServerCommsBase
    {
        protected RestClient client = (App.Current as App).Server;

        protected void ensureStatusCode(IRestResponse response, HttpStatusCode code)
        {
            if(response.StatusCode != code)
            {
                if(response.ErrorException != null)
                {
                    throw response.ErrorException;
                }
                throw new RestException(response.StatusCode);
            }
        }
    }
}
