using System;
using System.Collections.Generic;
using System.Text;
using TMCS_Client.Controllers;
using TMCS_Client.CustomUIElements.ListViews;
using TMCS_Client.CustomUIElements.ViewCells;
using TMCS_Client.DTOs;
using Xamarin.Forms;

namespace TMCS_Client.UI
{
    public class RecruiterInterviewPhase : ContentPage
	{
		private JobPosting activeJobPosting;

        private AbsoluteLayout pageContent;
		private Label lblInterviewPhaseList;
		private FormListView<Match, InterviewPhaseListCell> interviewPhaseList;

        public RecruiterInterviewPhase(JobPosting activeJobPosting)
        {
			this.activeJobPosting = activeJobPosting;
			Title = "Interview Phase";

            pageContent = new AbsoluteLayout();

			lblInterviewPhaseList = new Label()
			{
				Text = "Interview Phase Students",
				FontSize = 24.0,
				HorizontalTextAlignment = TextAlignment.Center,
			};
            pageContent.Children.Add(lblInterviewPhaseList,
                                    new Rectangle(0.5,0.0,0.9,0.06),
                                    AbsoluteLayoutFlags.All);

            interviewPhaseList = new FormListView<Match, InterviewPhaseListCell>(Match.EmptyMatch);
            interviewPhaseList.ItemSelected += (object sender, SelectedItemChangedEventArgs e) => {
                Navigation.PushModalAsync(new RecruiterInterviewModal(interviewPhaseList.SelectedItem as Match));
            };
            pageContent.Children.Add(interviewPhaseList,
                                    new Rectangle(0.5,1.0,1.0,0.94),
                                    AbsoluteLayoutFlags.All);

            Content = pageContent;
        }

		protected override void OnAppearing()
		{
			interviewPhaseList.updateItems(
				MatchController.getMatchController().getInterviewPhaseMatches
				(activeJobPosting));
			base.OnAppearing();
		}
    }
}