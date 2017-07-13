using System;
using System.Collections.Generic;
using System.Text;
using TMCS_Client.Controllers;
using TMCS_Client.CustomUIElements.Labels;
using TMCS_Client.CustomUIElements.ListViews;
using TMCS_Client.DTOs;
using Xamarin.Forms;

namespace TMCS_Client.UI {
    class RecruiterPresentationResponses : ContentPage {
        private JobPosting activeJobPosting;
        private StackLayout outputHolder = new StackLayout();

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

            pageContent.Children.Add(outputHolder,
                new Rectangle(0.0, 1.0, 1.0, 0.925),
                AbsoluteLayoutFlags.All);

            Content = pageContent;
        }

        protected override void OnAppearing() {
            base.OnAppearing();
            outputHolder.Children.Clear();
            var matches = MatchController.getMatchController().getMatchesInPresentationPhase(activeJobPosting);
            if(matches.Count == 0) {
                outputHolder.Children.Add(new SubSectionTitleLabel("No pending presentation responses"));
            } else {
                var list = new FormListView<Match, PresentationPhaseResponseCell>(Match.EmptyMatch);
                list.updateItems(matches);
                list.ItemSelected += (object sender, SelectedItemChangedEventArgs e) => {
                    Navigation.PushModalAsync(new RecruiterPresentationResponseModal(list.SelectedItem as Match));
                };

                outputHolder.Children.Add(list);
            }
        }

        class PresentationPhaseResponseCell : ViewCell {
            public static readonly BindableProperty MatchIDProperty =
                BindableProperty.Create("matchID", typeof(long), typeof(PresentationPhaseResponseCell), -1L);

            private Label tag;
            private Label timeSubmitted;
            private Label url;

            public PresentationPhaseResponseCell() {
                var cellLayout = new AbsoluteLayout() {
                    HeightRequest = Constants.Forms.Sizes.ROW_HEIGHT,
                    BackgroundColor = Color.White
                };

                url = new Label() {
                    VerticalTextAlignment = TextAlignment.Start,
                    HorizontalTextAlignment = TextAlignment.Start,
                    FontSize = 14.0
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

                url.SetBinding(Label.TextProperty, new Binding("studentPresentationLink"));
                tag.SetBinding(Label.TextProperty, new Binding("tag"));
                timeSubmitted.SetBinding(Label.TextProperty, new Binding("timeLastUpdated"));

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
        }
    }
}
