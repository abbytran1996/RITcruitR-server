using System;
using System.Collections.Generic;

namespace TMCS_Client.DTOs
{
    public class User
    {
        public int id { get; set; }
        public string username { get; set; }
        public string password { get; set; }
        public string passwordConfirm { get; set; }
        public List<Role> roles { get; set; }
    }
}