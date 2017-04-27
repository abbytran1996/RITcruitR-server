using System;
using System.Collections.Generic;
using System.Text;

namespace TMCS_Client
{
    /// <summary>
    /// Holds all the constants that the TMCS client uses
    /// </summary>
    class Constants
    {
#if __ANDROID__
        public static string SERVER_URL { get { return "http://10.0.2.2:8080"; } }  // 10.0.2.2 is the IP address of the device the emulator is running on
#endif
#if __IOS__
        public static string SERVER_URL { get { return "http://127.0.0.1:8080"; } }
#endif
        public class Students
        {
            public static string ADD_STUDENT_RESOURCE { get { return "/students"; } }
        }
        public class Company
        {
            public static string ADD_COMPANY_RESOURCE { get { return "/company"; } }
        }
        public class Skill
        {
            public static string GET_SKILL_RESOURCE { get { return "/skills"; } }
        }
        public class JobPosting
        {
            public static string ADD_JOB_POSTING_RESOURCE { get { return "/jobposting/create"; } }
            public static string DELETE_JOB_POSTING_RESOURCE { get { return "/jobposting/delete/{id}"; } }
            public static string GET_JOB_POSTING_RESOURCE { get { return "/jobposting/{id}"; } }
        }

        public static string PASSWORD_REGEX { get { return "(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!#$%&~`]).{8}"; } }

        public class Emails{
            public static string STUDENT { get { return "[a-zA-Z0-9]*@[a-zA-Z\\.]*\\.edu"; } }
        }
    }
}
