using System;
using Xamarin.Forms;
using TMCS_Client.DTOs;
using TMCS_Client.CustomUIElements.Labels;
using System.Collections.ObjectModel;
using TMCS_Client.CustomUIElements.ListViews;
using TMCS_Client.Controllers;
using TMCS_Client.CustomUIElements.ViewCells;
using System.Collections.Generic;

namespace TMCS_Client.UI
{
    public class PostingDetails : ContentPage
    {
        private JobPosting activeJobPosting;

        private AbsoluteLayout pageContent;

        private Button problemStatementSection;

        private Button presentationSection;

        private Button interviewSection;

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
                Navigation.PushAsync(new RecruiterProblemResponses(activeJobPosting));
            };
            

            pageContent.Children.Add(problemStatementSection,
                                    new Rectangle(1.0,0.0,1.0,0.2),
                                    AbsoluteLayoutFlags.All);

            presentationSection = new Button() {
                Text = "Presentation Phase",
                BackgroundColor = Color.AliceBlue
            };
            presentationSection.Clicked += (object sender, EventArgs e) => Navigation.PushAsync(new RecruiterPresentationResponses(activeJobPosting));

            pageContent.Children.Add(presentationSection,
                new Rectangle(1, 0.25, 1, 0.2),
                AbsoluteLayoutFlags.All);

            interviewSection = new Button()
            {
                Text = "Interview Phase",
                BackgroundColor = Color.AliceBlue
            };
            interviewSection.Clicked += (object sender, EventArgs e) => Navigation.PushAsync(new RecruiterInterviewPhase(activeJobPosting));
            pageContent.Children.Add(interviewSection,
                                    new Rectangle(1.0, 0.5, 1, 0.2),
                                    AbsoluteLayoutFlags.All);

            Content = pageContent;
        }

        protected override void OnAppearing() {
            var jobPostingsController = JobPostingController.getJobPostingController();
            var matchController = MatchController.getMatchController();

            var numProblemPhaseMatches = jobPostingsController.getProbPhasePosts(activeJobPosting);
            long numUnviewedProblemPhaseMatches = matchController.getUnviewedProbPhaseMatches(activeJobPosting);
            problemStatementSection.Text = String.Format("Problem Phase - {0}", numProblemPhaseMatches) + 
                (numUnviewedProblemPhaseMatches > 0 ? String.Format(" ({0} new)",numUnviewedProblemPhaseMatches) : "");

            var numPresentationPhaseMatches = MatchController.getMatchController().getPresentationPhaseMatchesCount(activeJobPosting);
			var numUnviewedPresentationPhaseMatches = matchController.getUnviewedPresentationPhaseMatchesCount(activeJobPosting);
			presentationSection.Text = String.Format("Presentation Phase - {0}", numPresentationPhaseMatches) +
				(numUnviewedPresentationPhaseMatches > 0 ? String.Format(" ({0} new)", numUnviewedPresentationPhaseMatches) : "");

			var numInterviewPhaseMatches = MatchController.getMatchController().getInterviewPhaseMatchesCount(activeJobPosting);
			var numUnviewedInterviewPhaseMatches = matchController.getUnviewedInterviewPhaseMatchesCount(activeJobPosting);
			interviewSection.Text = String.Format("Interview Phase - {0}", numInterviewPhaseMatches) +
				(numUnviewedInterviewPhaseMatches > 0 ? String.Format(" ({0} new)", numUnviewedInterviewPhaseMatches) : "");

            base.OnAppearing();
        }
    }
}
