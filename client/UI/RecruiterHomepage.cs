using System;
using Xamarin.Forms;
using TMCS_Client.DTOs;
using TMCS_Client.CustomUIElements.Labels;

namespace TMCS_Client.UI
{
    public class RecruiterHomepage : ContentPage
    {
        private Recruiter loggedInRecruiter;

        private AbsoluteLayout pageContent;

        private PageTitleLabel lblTitle;
        private ListView jobPostings;

        public RecruiterHomepage(Recruiter recruiter)
        {
            loggedInRecruiter = recruiter;
            this.Title = "Job Postings";

            pageContent = new AbsoluteLayout();

            pageContent.Children.Add(lblTitle = new PageTitleLabel("Job Postings"),
                                    new Rectangle(0,0,1.0,Constants.Forms.Sizes.ROW_HEIGHT),
                                     AbsoluteLayoutFlags.PositionProportional | 
                                     AbsoluteLayoutFlags.WidthProportional);



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
