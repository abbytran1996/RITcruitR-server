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
    public class PostingDetails : ContentPage
    {
        private JobPosting activeJobPosting;

        private AbsoluteLayout pageContent;

        private Button problemStatementSection;
        private Label probStatementlbl;

        private AbsoluteLayout presentationSection;
        private AbsoluteLayout interviewSection;


        public PostingDetails(JobPosting jobPosting)
        {
            activeJobPosting = jobPosting;
            this.Title = "Details for " + activeJobPosting.positionTitle;

            pageContent = new AbsoluteLayout();

            problemStatementSection = new Button()
            {
                Text = "Problem Phase",
            };
            problemStatementSection.BackgroundColor = Color.AliceBlue;
            problemStatementSection.Clicked += (object sender, EventArgs e) => {
                Navigation.PushAsync(new ProblemResponses(activeJobPosting));
            };
            

            pageContent.Children.Add(problemStatementSection,
                                    new Rectangle(1.0,0.0,1.0,0.2),
                                    AbsoluteLayoutFlags.All);


            Content = pageContent;
        }

        protected override void OnAppearing()
        {
            problemStatementSection.Text = "Problem Phase - " + JobPostingController.getJobPostingController().getProbPhasePosts(activeJobPosting).ToString();
            
            base.OnAppearing();
        }
    }
}
