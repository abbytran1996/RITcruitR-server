using System;

namespace TMCS_Client.DTOs
{
    public class Role
    {
        public int id { get; set; }
        public string name { get; set; }

        public enum Name
        {
            Student,
            Recruiter,
            Admin
        }
    }
}