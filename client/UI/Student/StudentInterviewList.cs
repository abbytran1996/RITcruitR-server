
using System.Linq;
using TMCS_Client.Controllers;
using TMCS_Client.DTOs;
using Xamarin.Forms;

namespace TMCS_Client.UI {
    public class StudentInterviewList : StudentListPage {
        private bool wasExecuted = false;

        public StudentInterviewList() : base("You are in the interview phase with the following jobs:", Match.CurrentPhase.INTERVIEW) { }

        protected override void onItemTapped(object sender, ItemTappedEventArgs e) {
            if(!wasExecuted) {
                var selectedMatch = ((CellData)e.Item).Match;

                Navigation.PushAsync(new StudentInterviewPhase(selectedMatch));
                wasExecuted = true;
            }
        }

        protected override void OnAppearing() {
            base.OnAppearing();
            wasExecuted = false;
        }

        protected override void setupMatchedList() {
            var student = app.CurrentStudent;
            matches = MatchController.getMatchController().getMatchesForStudent(student);
            matchesList.ItemTemplate = new DataTemplate(typeof(MatchCell));

            var postings = matches.Where(match => match.currentPhase == phase)
                                  .Where(match => match.applicationStatus == Match.ApplicationStatus.ACCEPTED)
                                  .Where(match => match.matchStrength > 0.1)
                                  .OrderByDescending(match => match.timeLastUpdated)
                                  .Select(match => new CellData(match));

            matchesList.ItemsSource = postings;
            matchesList.RowHeight = 130;

        }
    }
}
