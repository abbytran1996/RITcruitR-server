using System;
using Xamarin.Forms;
using TMCS_Client.DTOs;

namespace TMCS_Client.UI
{
    public class RecruiterHomepage : ContentPage
    {
        private Recruiter loggedInRecruiter;

        public RecruiterHomepage(Recruiter recruiter)
        {
            loggedInRecruiter = recruiter;
            this.Title = "Job Postings";
#if __IOS__
            ToolbarItem btnAddJobPosting;
            ToolbarItems.Add(btnAddJobPosting = new ToolbarItem()
            {
                Icon = "TMCS_Client.iOS.Resources.add.png",
            });
#endif
#if __ANDROID__

#endif
			btnAddJobPosting.Clicked += (object sender, EventArgs e) =>
				Navigation.PushAsync(new JobPostingCreation(loggedInRecruiter));
        }
    }
}
