
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
    }
}
