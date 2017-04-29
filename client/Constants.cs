using System;
using System.Collections.Generic;
using System.Text;
using Xamarin.Forms;

namespace TMCS_Client
{
    /// <summary>
    /// Holds all the constants that the TMCS client uses
    /// </summary>
    public static class Constants
    {
#if __ANDROID__
        public static string SERVER_URL { get { return "http://10.0.2.2:8080"; } }  // 10.0.2.2 is the IP address of the device the emulator is running on
#endif
#if __IOS__
        public static string SERVER_URL { get { return "http://127.0.0.1:8080"; } }
#endif
        public static class Login {
            public static string LOGIN_RESOURCE {  get { return "/users/login"; } }
        }
        public static class Students
        {
            public static string ADD_STUDENT_RESOURCE { get { return "/students"; } }
            public static string GET_MATCHES_RESORUCE { get { return "/students/{0}/matches"; } }
        }
        public static class Recruiters
        {
            public static string ADD_RECRUITER_RESOURCE { get { return "/recruiters"; } }
        }
        public static class Company
        {
            public static string ADD_COMPANY_RESOURCE { get { return "/company"; } }
        }
        public static class Skill
        {
            public static string GET_SKILL_RESOURCE { get { return "/skills"; } }
        }
        public static class JobPosting
        {
            public static string ADD_JOB_POSTING_RESOURCE { get { return "/jobposting/create"; } }
            public static string DELETE_JOB_POSTING_RESOURCE { get { return "/jobposting/delete/{id}"; } }
            public static string GET_JOB_POSTING_RESOURCE { get { return "/jobposting/{id}"; } }
        }

        public static string PASSWORD_REGEX { get { return "(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!#$%&~`]).{8}"; } }

        public static class Emails
        {
            public static string STUDENT { get { return "[a-zA-Z0-9]*@[a-zA-Z\\.]*\\.edu"; } }
            public static string RECRUITER { get { return "[a-zA-Z0-9]*@[a-zA-Z\\.]*\\.*"; } }
        }

        public static class CompanyEmailSuffix
        {
            public static string COMPANYEMAILSUFFIX { get { return "/company/emailSuffix"; } }
        } 

        public static class Forms
        {
            public static class Colors
            {
                public static Color SUCCESS { get { return new Color(0.75, 1.0, 0.75); } }
                public static Color FAILURE { get { return new Color(1.0, 0.75, 0.75); } }
            }

            public static class Sizes
            {
#if __IOS__
                public static double ROW_HEIGHT{ get { return 60.0; } }
#endif
#if __ANDROID__
                public static double ROW_HEIGHT{ get { return 80.0; } }
#endif
			}

            public static class LoginStatusMessage{
                public static Entry EMPTY = new Entry()
                {
                    IsEnabled = false,
                    IsVisible = false,
                };

                public static Entry REGISTRATION_COMPLETE = new Entry()
                {
                    IsEnabled = false,
                    IsVisible = true,
                    Text = "Registration sucessfully completed.",
                    BackgroundColor = Colors.SUCCESS,
                    HorizontalTextAlignment = TextAlignment.Center,
                    FontSize = 14.0,
                };

                public static Entry USERNAME_OR_PASSWORD_INVALID = new Entry()
                {
                    IsEnabled = false,
                    IsVisible = true,
                    Text = "Username and/or password was incorrect.",
                    BackgroundColor = Colors.FAILURE,
                    HorizontalTextAlignment = TextAlignment.Center,
                    FontSize = 14.0,
				};
            }
        }
    }
}
