using System;
using System.Collections.Generic;
using System.Text;
using TMCS_Client.Controllers;
using TMCS_Client.CustomUIElements.Labels;
using TMCS_Client.CustomUIElements.ListViews;
using TMCS_Client.DTOs;
using Xamarin.Forms;

namespace TMCS_Client.UI
{
    class RecruiterPresentationResponses : ContentPage
    {
        private JobPosting activeJobPosting;
        private FormListView<Match, PresentationPhaseResponseCell> presentationResponsesList;

        public RecruiterPresentationResponses(JobPosting activeJobPosting)
        {
            this.activeJobPosting = activeJobPosting;
            Title = "Presentation Responses";

            var pageContent = new AbsoluteLayout();
            var responseListLabel = new Label()
            {
                Text = "Student Responses",
                FontSize = 24.0,
                HorizontalTextAlignment = TextAlignment.Center
            };
            pageContent.Children.Add(responseListLabel,
                                    new Rectangle(0.5, 0.0, 0.9, 0.075),
                                    AbsoluteLayoutFlags.All);

            presentationResponsesList = new FormListView<Match, PresentationPhaseResponseCell>(
                Match.EmptyMatch
                );
            presentationResponsesList.ItemSelected += (object sender, SelectedItemChangedEventArgs e) =>
            {
                if((Match)presentationResponsesList.SelectedItem != Match.EmptyMatch)
                {
                    Navigation.PushModalAsync(new RecruiterPresentationResponseModal(presentationResponsesList.SelectedItem as Match));
                }
            };
            pageContent.Children.Add(presentationResponsesList,
                new Rectangle(0.5, 1.0, 1.0, 0.925),
                AbsoluteLayoutFlags.All);

            Content = pageContent;
        }

        protected override void OnAppearing()
        {
            base.OnAppearing();
            var matches = MatchController.getMatchController().getMatchesInPresentationPhase(activeJobPosting);
            presentationResponsesList.updateItems(matches);
        }

        class PresentationPhaseResponseCell : ViewCell
        {
            public static readonly BindableProperty MatchIDProperty =
                BindableProperty.Create("matchID", typeof(long), typeof(PresentationPhaseResponseCell), -1L);

            private Label tag;
            private Label timeSubmitted;
            private Label url;

            public PresentationPhaseResponseCell()
            {
                var cellLayout = new AbsoluteLayout()
                {
                    HeightRequest = Constants.Forms.Sizes.ROW_HEIGHT,
                    BackgroundColor = Color.White
                };

                url = new Label()
                {
                    VerticalTextAlignment = TextAlignment.Start,
                    HorizontalTextAlignment = TextAlignment.Start,
                    FontSize = 14.0
                };

                tag = new Label()
                {
                    VerticalTextAlignment = TextAlignment.Start,
                    HorizontalTextAlignment = TextAlignment.Start,
                    FontSize = 12.0,
                };

                timeSubmitted = new Label()
                {
                    VerticalTextAlignment = TextAlignment.Start,
                    HorizontalTextAlignment = TextAlignment.End,
                    FontSize = 12.0,
                    TextColor = Color.Gray
                };

                cellLayout.Children.Add(url,
                    new Rectangle(0.5, 0.0, 0.95, 0.75),
                    AbsoluteLayoutFlags.All);

                cellLayout.Children.Add(tag,
                    new Rectangle(0.05, 1.0, 0.475, 0.25),
                    AbsoluteLayoutFlags.All);

                cellLayout.Children.Add(timeSubmitted,
                    new Rectangle(0.95, 1.0, 0.475, 0.25),
                    AbsoluteLayoutFlags.All);

                View = cellLayout;
            }

            protected override void OnBindingContextChanged()
            {
                base.OnBindingContextChanged();
                if((BindingContext != null) && (((Match)BindingContext) == Match.EmptyMatch))
                {
                    this.View = new AbsoluteLayout()
                    {
                        HeightRequest = Constants.Forms.Sizes.ROW_HEIGHT,
                    };
                    ((AbsoluteLayout)this.View).Children.Add(new Label()
                    {
                        Text = "No pending presentation responses",
                        VerticalTextAlignment = TextAlignment.Center,
                        HorizontalTextAlignment = TextAlignment.Center,
                        FontSize = 22.0,
                    }, new Rectangle(0.0, 0.0, 1.0, 1.0), AbsoluteLayoutFlags.All);
                }
                else if(BindingContext != null)
                {
                    //TODO Truncate/convert these values
                    tag.Text = "Tag: " + (((Match)BindingContext).tag == null ? "" : ((Match)BindingContext).tag);
                    url.Text = /*"URL: " + */(((Match)BindingContext).studentPresentationLink == null ? "" : ((Match)BindingContext).studentPresentationLink.ToString());
                    timeSubmitted.Text = ((Match)BindingContext).timeLastUpdated.ToString();
                }
            }
        }
    }
}
