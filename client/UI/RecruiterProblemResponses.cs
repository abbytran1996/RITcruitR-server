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
    public class RecruiterProblemResponses : ContentPage
    {
        private JobPosting activeJobPosting;

        private AbsoluteLayout pageContent;

        private AbsoluteLayout promptSection;
        private Label lblPromptField;
        private Label lblPrompt;

        private AbsoluteLayout problemResponseSection;
        private Label lblResponseList;
        private FormListView<Match, ProblemResponseListCell> problemResponseList;

        public RecruiterProblemResponses(JobPosting jobPosting)
        {
            activeJobPosting = jobPosting;
            this.Title = "Problem Responses";

            pageContent = new AbsoluteLayout();

            promptSection = new AbsoluteLayout();

            lblPromptField = new Label(){
                Text = "Prompt",
                FontSize = 24.0,
                HorizontalTextAlignment = TextAlignment.Center,
            };
            promptSection.Children.Add(lblPromptField,
                                      new Rectangle(0.5,0.0,0.9,0.3),
                                      AbsoluteLayoutFlags.All);

            lblPrompt = new Label()
            {
                Text = jobPosting.problemStatement,
                FontSize = 14.0,
            };
            promptSection.Children.Add(lblPrompt,
                                      new Rectangle(0.5,1.0,0.9,0.7),
                                      AbsoluteLayoutFlags.All);

            pageContent.Children.Add(promptSection,
                                    new Rectangle(1.0,0.0,1.0,0.2),
                                    AbsoluteLayoutFlags.All);


			problemResponseSection = new AbsoluteLayout();

            lblResponseList = new Label(){
				Text = "Student Reponses",
				FontSize = 24.0,
				HorizontalTextAlignment = TextAlignment.Center,
            };
            problemResponseSection.Children.Add(lblResponseList,
                                               new Rectangle(0.5,0.0,0.9,0.075),
                                               AbsoluteLayoutFlags.All);

            problemResponseList = new FormListView<Match, ProblemResponseListCell>(
                Match.NullMatch
            );
            problemResponseList.ItemSelected += (object sender, SelectedItemChangedEventArgs e) => {
                Navigation.PushModalAsync(new RecruiterProblemResponseModal((Match)problemResponseList.SelectedItem));
            };
            problemResponseSection.Children.Add(problemResponseList,
                                              new Rectangle(0.0, 1.0, 1.0, 0.925),
                                              AbsoluteLayoutFlags.All);


            pageContent.Children.Add(problemResponseSection,
                                    new Rectangle(0.0, 1.0, 1.0, 0.8),
                                     AbsoluteLayoutFlags.All);

            Content = pageContent;
        }

        protected override void OnAppearing()
        {
            problemResponseList.updateItems(
                MatchController.getMatchController().
                getMatchesWithProblemResponsePending(activeJobPosting.id));
            base.OnAppearing();
        }
    }
}
