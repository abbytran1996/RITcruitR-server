using System;
using Xamarin.Forms;
using TMCS_Client.DTOs;
using TMCS_Client.CustomUIElements.Labels;
using System.Collections.ObjectModel;

namespace TMCS_Client.UI
{
    public class RecruiterHomepage : ContentPage
    {
        private Recruiter loggedInRecruiter;

        private AbsoluteLayout pageContent;

        private ListView jobPostingsList;
        private ObservableCollection<JobPosting> jobPostings;

        public RecruiterHomepage(Recruiter recruiter)
        {
            loggedInRecruiter = recruiter;
            this.Title = "Job Postings";

            pageContent = new AbsoluteLayout();

            jobPostings = new ObservableCollection<JobPosting>();
            jobPostingsList = new ListView(ListViewCachingStrategy.RetainElement)
            {
                SeparatorVisibility = SeparatorVisibility.None,
            };

            pageContent.Children.Add(jobPostingsList,
                                    new Rectangle(0.0, 0.0, 1.0, 1.0),
                                     AbsoluteLayoutFlags.All);


#if __IOS__
            ToolbarItem btnAddJobPosting;
            ToolbarItems.Add(btnAddJobPosting = new ToolbarItem()
            {
                Icon = "TMCS_Client.iOS.Resources.add.png",
            });
#endif
#if __ANDROID__
            Button btnAddJobPosting;
            btnAddJobPosting = new Button()
            {
                Image = "add_job_posting.png",
                BackgroundColor = Color.Transparent,
                BorderColor = Color.Transparent,
                BorderWidth = 0.0,
            };
            pageContent.Children.Add(btnAddJobPosting,
                                    new Rectangle(0.95,0.95,80.0,80.0),
                                    AbsoluteLayoutFlags.PositionProportional);
#endif
			btnAddJobPosting.Clicked += (object sender, EventArgs e) =>
				Navigation.PushAsync(new JobPostingCreation(loggedInRecruiter));

            Content = pageContent;
        }
    }
}
