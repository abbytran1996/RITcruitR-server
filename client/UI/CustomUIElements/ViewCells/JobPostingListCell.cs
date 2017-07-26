using System;
using TMCS_Client.DTOs;
using TMCS_Client.Controllers;
using Xamarin.Forms;

namespace TMCS_Client.CustomUIElements.ViewCells
{
    public class JobPostingListCell : ViewCell
    {
        Label lblJobPostingPositionTitle;
        Label lblJobPostingLocation;
        Label lblJobPostingNewApplicants;

        public JobPostingListCell()
        {
            AbsoluteLayout cellLayout = new AbsoluteLayout()
            {
                HeightRequest = Constants.Forms.Sizes.ROW_HEIGHT,
                BackgroundColor = Color.White,
            };

            lblJobPostingPositionTitle = new Label()
            {
                VerticalTextAlignment = TextAlignment.End,
                HorizontalTextAlignment = TextAlignment.Start,
                FontSize = 20.0,
            };

            lblJobPostingLocation = new Label()
            {
                VerticalTextAlignment = TextAlignment.Start,
                HorizontalTextAlignment = TextAlignment.Start,
                FontSize = 14.0,
            };

            lblJobPostingNewApplicants = new Label()
            {
                VerticalTextAlignment = TextAlignment.Center,
                HorizontalTextAlignment = TextAlignment.End,
                FontSize = 14.0,
                TextColor = Color.Red,
                Text = ""
            };

            cellLayout.Children.Add(lblJobPostingPositionTitle, new Rectangle(16.0, 0.0, 0.7, 0.5),
                                    AbsoluteLayoutFlags.SizeProportional | AbsoluteLayoutFlags.YProportional);
            cellLayout.Children.Add(lblJobPostingLocation, new Rectangle(16.0, 1.0, 0.7, 0.5),
                                   AbsoluteLayoutFlags.SizeProportional | AbsoluteLayoutFlags.YProportional);
            cellLayout.Children.Add(lblJobPostingNewApplicants, new Rectangle(0.9, 0.0, 0.2, 1.0), 
                                    AbsoluteLayoutFlags.All);

            View = cellLayout;
        }

        protected override void OnBindingContextChanged()
        {
            base.OnBindingContextChanged();
            if((BindingContext != null) && (((JobPosting)BindingContext) == JobPosting.NullJobPosting))
            {
                this.View = new AbsoluteLayout()
                {
                    HeightRequest = Constants.Forms.Sizes.ROW_HEIGHT,
                };
                ((AbsoluteLayout)this.View).Children.Add(new Label()
                {
                    Text = "No job postings yet.",
                    VerticalTextAlignment = TextAlignment.Center,
                    HorizontalTextAlignment = TextAlignment.Center,
                    FontSize = 22.0,
                }, new Rectangle(0.0, 0.0, 1.0, 1.0), AbsoluteLayoutFlags.All);
            }
            else if(BindingContext != null)
            {
                lblJobPostingLocation.Text = ((JobPosting)BindingContext).location;
                lblJobPostingPositionTitle.Text = ((JobPosting)BindingContext).positionTitle;
                lblJobPostingNewApplicants.Text =
                    MatchController.getMatchController().getUnviewedProbPhaseMatches(((JobPosting)BindingContext)) +
                    MatchController.getMatchController().getUnviewedPresentationPhaseMatchesCount(
                                                  ((JobPosting)BindingContext)) +
                    MatchController.getMatchController().getUnviewedInterviewPhaseMatchesCount(
                                                  ((JobPosting)BindingContext)) > 0 ? "NEW" : "";
            }
        }
    }
}
