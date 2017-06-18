using System;
using System.Collections.Generic;
using System.Text;
using TMCS_Client.CustomUIElements.ListViews;
using TMCS_Client.DTOs;
using Xamarin.Forms;

namespace TMCS_Client.UI {
    class RecruiterPresentationResponses : ContentPage {
        private JobPosting activeJobPosting;
        private FormListView<Match, PresentationPhaseResponseCell> presentationResponsesList;

        public RecruiterPresentationResponses(JobPosting activeJobPosting) {
            this.activeJobPosting = activeJobPosting;
            Title = "Presentation Responses";

            var pageContent = new AbsoluteLayout();
            var responseListLabel = new Label() {
                Text = "Student Responses",
                FontSize = 24.0,
                HorizontalTextAlignment = TextAlignment.Center
            };
            pageContent.Children.Add(responseListLabel);

            presentationResponsesList = new FormListView<Match, PresentationPhaseResponseCell>(
                Match.NullMatch
                );
            presentationResponsesList.ItemSelected += (object sender, SelectedItemChangedEventArgs e) => {
                // Navigation.PushModalAsync(new RecruiterPresentationResponseModal((Match)presentationResponsesList.SelectedItem));
            };
            pageContent.Children.Add(presentationResponsesList,
                new Rectangle(0.0, 1.0, 1.0, 0.925),
                AbsoluteLayoutFlags.All);

            Content = pageContent;
        }

        class PresentationPhaseResponseCell : ViewCell {
            public static readonly BindableProperty MatchIDProperty =
                BindableProperty.Create("matchID", typeof(long), typeof(PresentationPhaseResponseCell), -1L);

            private Label tag;
            private Label timeSubmitted;

            public PresentationPhaseResponseCell() {
                var cellLayout = new AbsoluteLayout() {
                    HeightRequest = Constants.Forms.Sizes.ROW_HEIGHT,
                    BackgroundColor = Color.White
                };

                tag = new Label() {
                    VerticalTextAlignment = TextAlignment.Start,
                    HorizontalTextAlignment = TextAlignment.Start,
                    FontSize = 12.0,
                };

                timeSubmitted = new Label() {
                    VerticalTextAlignment = TextAlignment.Start,
                    HorizontalTextAlignment = TextAlignment.Start,
                    FontSize = 12.0,
                    TextColor = Color.Gray
                };

                tag.SetBinding(Label.TextProperty, new Binding("tag"));
                timeSubmitted.SetBinding(Label.TextProperty, new Binding("timeLastUpdated"));

                cellLayout.Children.Add(tag,
                    new Rectangle(0.05, 1.0, 0.475, 0.25),
                    AbsoluteLayoutFlags.All);

                cellLayout.Children.Add(timeSubmitted,
                    new Rectangle(0.95, 1.0, 0.475, 0.25),
                    AbsoluteLayoutFlags.All);

                View = cellLayout;
            }

            /*protected override void OnBindingContextChanged() {
                base.OnBindingContextChanged();
                if(matchID == -1) {
                    this.View = new AbsoluteLayout() {
                        HeightRequest = Constants.Forms.Sizes.ROW_HEIGHT,
                    };
                    ((AbsoluteLayout)this.View).Children.Add(new Label() {
                        Text = "No pending presentation responses",
                        VerticalTextAlignment = TextAlignment.Center,
                        HorizontalTextAlignment = TextAlignment.Center,
                        FontSize = 22.0,
                    }, new Rectangle(0.0, 0.0, 1.0, 1.0),
                                                             AbsoluteLayoutFlags.All);
                } else {
                    //TODO Truncate/convert these values
                    tag.Text = "Tag: " + tag;
                    timeSubmitted.Text = problemResponseTimeSubmitted.ToString();
                }
            }*/
        }
    }
}
