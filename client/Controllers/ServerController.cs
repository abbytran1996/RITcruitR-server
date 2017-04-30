using RestSharp;
using System.Collections.Generic;
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
        /// Logs the user in to the server
        /// </summary>
        /// <param name="username">The username of the user to log in</param>
        /// <param name="password">The password of the user to log in</param>
        public void login(string username, string password) {
            var request = new RestRequest(Constants.Login.LOGIN_RESOURCE, Method.POST);
            request.RequestFormat = DataFormat.Json;

            Dictionary<string, string> body = new Dictionary<string, string>();
            body["username"] = username;
            body["password"] = password;
            request.AddBody(body);

            var response = client.Execute(request);
            ensureStatusCode(response, System.Net.HttpStatusCode.OK);
            
            foreach(var cookie in response.Cookies) {
                if(cookie.Name == "JSESSIONID") {
                    if(client.CookieContainer == null) {
                        client.CookieContainer = new System.Net.CookieContainer();
                    }
                    client.CookieContainer.Add(new System.Net.Cookie(cookie.Name, cookie.Value, cookie.Path, cookie.Domain));
                    break;
                }
            }
        }
    }
}
