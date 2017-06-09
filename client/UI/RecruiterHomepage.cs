using System;
using Xamarin.Forms;
using TMCS_Client.DTOs;
using TMCS_Client.CustomUIElements.Labels;
using System.Collections.ObjectModel;
using TMCS_Client.CustomUIElements.ListViews;
using TMCS_Client.Controllers;
using TMCS_Client.CustomUIElements.ViewCells;

namespace TMCS_Client.UI
{
    public class RecruiterHomepage : ContentPage
    {
        private Recruiter loggedInRecruiter;

        private AbsoluteLayout pageContent;

        private FormListView<JobPosting, JobPostingListCell> jobPostingsList;

        public RecruiterHomepage(Recruiter recruiter)
        {
            loggedInRecruiter = recruiter;
            this.Title = "Job Postings";

            pageContent = new AbsoluteLayout();

            jobPostingsList = new FormListView<JobPosting, JobPostingListCell>(
                JobPosting.NullJobPosting);

            jobPostingsList.ItemSelected += (object sender, SelectedItemChangedEventArgs e) => {
                Navigation.PushAsync(new PostingDetails((JobPosting)jobPostingsList.SelectedItem));
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

        protected override void OnAppearing()
        {
            jobPostingsList.clearItems();
            foreach(JobPosting jobPosting in JobPostingController.getJobPostingController().
                    getJobPostingsByRecruiter(this.loggedInRecruiter)){
                jobPostingsList.addItem(jobPosting);
            }
            base.OnAppearing();
        }
    }
}
