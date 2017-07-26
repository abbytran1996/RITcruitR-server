using Xamarin.Forms;

namespace TMCS_Client
{
    public static class Constants
    {
#if __ANDROID__
        public static string SERVER_URL { get { return "http://10.0.2.2:8080"; } }  // 10.0.2.2 is the IP address of the device the emulator is running on
#endif
#if __IOS__
        public static string SERVER_URL { get { return "http://127.0.0.1:8080"; } }
#endif
        public static class Login {
            public static string LOGIN_RESOURCE {  get { return "/user/login"; } }
        }
        public static class Students
        {
            public static string ADD_STUDENT_RESOURCE { get { return "/students"; } }
            public static string ADD_SKILLS_RESOURCE { get { return "/students/{id}/skills"; } }
            public static string GET_STUDENT_BY_EMAIL_RESOURCE { get { return "/students/byEmail/{email}"; } }
			public static string UPLOAD_RESUME { get { return "/students/{id}/uploadResume"; } }
			public static string UPDATE_STUDENT { get { return "/students/{id}/update"; } }
            public static string DOWNLOAD_RESUME { get { return "/students/{id}/resume";  } }

        }
        public static class Recruiters
        {
            public static string ADD_RECRUITER_RESOURCE { get { return "/recruiters"; } }
            public static string GET_RECRUITER_BY_EMAIL_RESOURCE { get { return "/recruiters/byEmail/{email}"; } }
            public static string GET_RECRUITER_BY_USER_RESOURCE { get { return "/recruiters/byUser/{user}"; } }
            public static string UPDATE_RECRUITER_RESOURCE { get { return "/recruiters/{id}"; } }

		}
        public static class Company
        {
            public static string ADD_COMPANY_RESOURCE { get { return "/company"; } }
			public static string GET_COMPANY_BY_ID_RESOURCE { get { return "/company/{id}"; } }
            public static string GET_COMPANY_BY_SUFFIX_RESOURCE { get { return "/company/email_suffix/emailSuffix"; } }
            public static string GET_COMPANY_BY_NAME{ get { return "/company/company_name/companyName"; } }
            public static string UPDATE_COMPANY_RESORUCE {  get { return "/company/{id}"; } }
        }
        public static class Skill
        {
            public static string GET_SKILL_RESOURCE { get { return "/skills"; } }
        }
        public static class JobPosting
        {
            public static string ADD_JOB_POSTING_RESOURCE { get { return "/jobposting/create"; } }
            public static string DELETE_JOB_POSTING_RESOURCE { get { return "/jobposting/delete/{id}"; } }
            public static string GET_JOB_POSTING_RESOURCE { get { return "/jobposting/{0}"; } }
            public static string GET_JOB_POSTING_BY_RECRUITER_RESOURCE { get { return "/jobposting/recruiter/{id}"; } }
        }
        public static class Matches
        {
			public static string ACCEPT_JOB_POSTING { get { return "/matches/{id}/accept"; } }
			public static string GET_MATCHES_RESORUCE { get { return "/matches/studentMatches/{id}/"; } }
			public static string GET_PROBLEM_PHASE_MATCHES { get { return "/matches/posting/{id}/probphase"; } }
			public static string GET_UNVIEWED_PROBLEM_PHASE_MATCHES { get { return "/matches/posting/{id}/probphase/unviewed"; } }
            public static string GET_MATCHES_WITH_PROBLEM_RESPONSE_PENDING { get { return "/matches/{jobPostingID}/problemResponsePending"; } }
            public static string ADD_RESPONSE_RESOURCE { get { return "/matches/{id}/submitresponse/{response}"; } }
            public static string ADD_LINK_RESOURCE { get { return "/matches/{id}/submitlink/{link}"; } }
            public static string UPDATE_MATCH { get { return "/matches/{id}/update"; } }
            public static string GET_PRESENTATION_PHASE_MATCHES { get { return "/matches/{id}/presentationResponsePending"; } }
            public static string GET_INTERVIEW_PHASE_MATCHES_COUNT { get { return "/matches/{jobPostingID}/interviewPhaseMatches/Count"; } }
			public static string GET_UNVIEWED_INTERVIEW_PHASE_MATCHES_COUNT { get { return "/matches/{jobPostingID}/interviewPhaseMatches/unviewed/Count"; } }
			public static string GET_PRESENTATION_PHASE_MATCHES_COUNT { get { return "/matches/{jobPostingID}/presentationPhaseMatches/Count"; } }
			public static string GET_UNVIEWED_PRESENTATION_PHASE_MATCHES_COUNT { get { return "/matches/{jobPostingID}/presentationPhaseMatches/unviewed/Count"; } }
            public static string GET_INTERVIEW_PHASE_MATCHES { get { return "/matches/{jobPostingID}/interviewPhaseMatches"; } }
        }

        public static string PASSWORD_REGEX { get { return "(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!#$%&~`]).{8}"; } }

        public static class Emails
        {
            public static string STUDENT { get { return "[a-zA-Z0-9]*@[a-zA-Z\\.]*\\.edu"; } }
            public static string RECRUITER { get { return "[a-zA-Z0-9]*@[a-zA-Z\\.]*\\.*"; } }
        }


        public static class Forms
        {
            public static class Colors
            {
                public static Color SUCCESS { get { return new Color(0.75, 1.0, 0.75); } }
                public static Color FAILURE { get { return new Color(1.0, 0.75, 0.75); } }
                public static Color WARNING{ get { return new Color(1.0, 1.0, 0.4); } }
                public static Color EDITOR_BACKGROUND { get { return new Color(0.9,0.95,1.0); } }
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

            public static class RECOMMENDED_SKILLS_WEIGHT{
				public static double MIN { get { return 0.05; } }
				public static double MAX { get { return 0.5; } }
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

				public static Entry SERVER_CONNECTION_FAILURE = new Entry()
				{
					IsEnabled = false,
					IsVisible = true,
					Text = "Failed to connect, check connection.",
					BackgroundColor = Colors.WARNING,
					HorizontalTextAlignment = TextAlignment.Center,
					FontSize = 14.0,
				};
            }
        }
    }
}
