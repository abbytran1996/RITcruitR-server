using RestSharp;
using System;
using System.Net;
using System.Collections.Generic;
using TMCS_Client.DTOs;
using TMCS_Client.ServerComms;

namespace TMCS_Client.Controllers {
    /// <summary>
    /// Handles arbitrary interactions with the server that don't fit in anywhere else
    /// </summary>
    public class ServerController : ServerCommsBase {
        private static ServerController instance = new ServerController();

        public static ServerController getServerController() {
            return instance;
        }

        /// <summary>
        /// Registers a user with the server
        /// </summary>
        /// <param name="user">The user to register</param>
        /// <param name="roleName">The role to register this user to</param>
        public User registerUser(User user, Role.Name roleName) {
            var request = new RestRequest(Constants.Login.NEW_USER_RESOURCE, Method.POST);
            request.RequestFormat = DataFormat.Json;

            request.AddBody(user);
            request.AddParameter("roleName", roleName.ToString(), ParameterType.UrlSegment);

            var response = client.Execute<User>(request);
            ensureStatusCode(response, HttpStatusCode.OK);

            return response.Data;
        }

        /// <summary>
        /// Logs the user in to the server
        /// </summary>
        /// <param name="username">The username of the user to log in</param>
        /// <param name="password">The password of the user to log in</param>
        public User login(string username, string password) {
            var request = new RestRequest(Constants.Login.LOGIN_RESOURCE, Method.POST);
            request.RequestFormat = DataFormat.Json;

            Dictionary<string, string> body = new Dictionary<string, string>();
            body["username"] = username;
            body["password"] = password;
            request.AddBody(body);

            var response = client.Execute<User>(request);
            if((response.ErrorException != null) && (response.ErrorException.GetType() == typeof(WebException))) {
                return null;
            }
            else if (response.Data.id == 0)
            {
                return new User()
                {
                    id = -1,
                };
            }
            //ensureStatusCode(response, System.Net.HttpStatusCode.OK);
            
            foreach(var cookie in response.Cookies) {
                if(cookie.Name == "JSESSIONID") {
                    if(client.CookieContainer == null) {
                        client.CookieContainer = new System.Net.CookieContainer();
                    }
                    client.CookieContainer.Add(new System.Net.Cookie(cookie.Name, cookie.Value, cookie.Path, cookie.Domain));
                    break;
                }
            }

            return response.Data;
        }
    }
}
