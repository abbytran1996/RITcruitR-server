using RestSharp;
using System;
using System.Net;
using System.Collections.Generic;
using TMCS_Client.DTOs;
using TMCS_Client.ServerComms;

namespace TMCS_Client.Controllers
{
    public class ServerController : ServerCommsBase
    {
        private static ServerController instance = new ServerController();

        public static ServerController getServerController()
        {
            return instance;
        }

        public User login(string username, string password)
        {
            var request = new RestRequest(Constants.Login.LOGIN_RESOURCE, Method.POST);
            request.RequestFormat = DataFormat.Json;

            Dictionary<string, string> body = new Dictionary<string, string>();
            body["username"] = username;
            body["password"] = password;
            request.AddBody(body);

            var response = client.Execute<User>(request);
            if((response.ErrorException != null) &&
               (response.ErrorException.GetType() == typeof(System.Net.WebException)))
            {
                return null;
            }
            else if(response.Data.id == 0)
            {
                return new User()
                {
                    id = -1,
                };
            }

            foreach(var cookie in response.Cookies)
            {
                if(cookie.Name == "JSESSIONID")
                {
                    if(client.CookieContainer == null)
                    {
                        client.CookieContainer = new System.Net.CookieContainer();
                    }
                    client.CookieContainer.Add(new System.Net.Cookie(cookie.Name, cookie.Value, cookie.Path, cookie.Domain));
                    break;
                }
            }

            return response.Data;
        }

        public Boolean logOut()
        {
            client.CookieContainer = new CookieContainer();
            return true;
        }
    }
}
