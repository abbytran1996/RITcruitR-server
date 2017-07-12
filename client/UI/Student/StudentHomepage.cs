using TMCS_Client.Controllers;
using TMCS_Client.DTOs;
using Xamarin.Forms;
using System;
using System.Diagnostics;

namespace TMCS_Client.UI {
    public class StudentHomepage : StudentListPage {
        private AbsoluteLayout bottomItems = new AbsoluteLayout() {
            HorizontalOptions = LayoutOptions.CenterAndExpand
        };
        
        private StudentController studentController = StudentController.getStudentController();
        
        public StudentHomepage() : base("You have been matched with the following jobs:", Match.CurrentPhase.NONE) {
            bottomItems.Children.Add(new Label() { Text = "Select a position you may be interested in" },
                new Rectangle(0, 0, 1, 1), AbsoluteLayoutFlags.All);

            var pageContent = Content as StackLayout;
            pageContent.Children.Add(bottomItems);
            Content = pageContent;
        }

        protected override void onItemTapped(object sender, ItemTappedEventArgs e) {
            Debug.WriteLine("Tapped an item");
            var selectedMatch = ((CellData)e.Item).Match;

            bottomItems.Children.Clear();
            Button declineButton = new Button()
            {
                Text = "Not Interested",
                BackgroundColor = Constants.Forms.Colors.FAILURE,
                HorizontalOptions = LayoutOptions.CenterAndExpand
            };
            declineButton.Clicked += (object sender2, EventArgs e2) =>
            {
                acceptPosting(selectedMatch, false);
				bottomItems.Children.Clear();
			};
            bottomItems.Children.Add(declineButton,
            new Rectangle(0, 0, 0.5, 1), AbsoluteLayoutFlags.All);
            Button acceptButton = new Button()
            {
                Text = "Interested",
                BackgroundColor = Constants.Forms.Colors.SUCCESS,
                HorizontalOptions = LayoutOptions.CenterAndExpand,
            };
            acceptButton.Clicked += (object sender2, EventArgs e2) =>
            {
                acceptPosting(selectedMatch, true);
                selectedMatch.currentPhase = Match.CurrentPhase.PROBLEM_WAITING_FOR_STUDENT;
				bottomItems.Children.Clear();
			};
            bottomItems.Children.Add(acceptButton,
            new Rectangle(1, 0, 0.5, 1), AbsoluteLayoutFlags.All);
        }

        private void acceptPosting(Match match, bool accept)
        {
            MatchController.getMatchController().acceptMatch(match, accept);
            setupMatchedList();
        }
    }
}
