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
            ToolbarItem editCompanyButton;
            ToolbarItems.Add(btnAddJobPosting = new ToolbarItem()
            {
                Icon = "TMCS_Client.iOS.Resources.add.png",
            });

            editCompanyButton = new ToolbarItem() {
                Text = "Edit company"
            };

            ToolbarItems.Add(editCompanyButton);
#endif
#if __ANDROID__
            Button btnAddJobPosting;
            btnAddJobPosting = new Button()
            {
                Image = "add_job_posting.png",
                BackgroundColor = Color.Transparent,
                BorderColor = Color.Transparent,
                BorderWidth = 0.0,
                BorderRadius = 0,
            };
            pageContent.Children.Add(btnAddJobPosting,
                                    new Rectangle(0.95,0.95,80.0,80.0),
                                    AbsoluteLayoutFlags.PositionProportional);

            Button editCompanyButton = new Button() {
                Text = "Edit Company"
            };
            pageContent.Children.Add(editCompanyButton,
                                    new Rectangle(0.5, 0.95, 160, 80),
                                    AbsoluteLayoutFlags.PositionProportional);
#endif
			btnAddJobPosting.Clicked += (object sender, EventArgs e) =>
				Navigation.PushAsync(new JobPostingCreation(loggedInRecruiter));

            editCompanyButton.Clicked += editCompanyButton_clicked;

            Content = pageContent;
        }

        private void editCompanyButton_clicked(object sender, EventArgs e) {
            Navigation.PushAsync(new RecruiterCompanyEditPage(loggedInRecruiter.company));
        }

        protected override void OnAppearing()
        {
            jobPostingsList.updateItems(
                JobPostingController.getJobPostingController().
                getJobPostingsByRecruiter(this.loggedInRecruiter));
			base.OnAppearing();
        }
    }
}
