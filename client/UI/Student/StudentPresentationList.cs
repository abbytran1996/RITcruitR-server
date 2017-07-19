using TMCS_Client.DTOs;
using Xamarin.Forms;

namespace TMCS_Client.UI {
    public class StudentPresentationList : StudentListPage {
        private bool wasExecuted = false;

        public StudentPresentationList() : base("You are in the presentation phase with the following jobs:", Match.CurrentPhase.PRESENTATION_WAITING_FOR_STUDENT) { }

        protected override void onItemTapped(object sender, ItemTappedEventArgs e) {
            //if(!wasExecuted) {
                var selectedMatch = ((CellData)e.Item).Match;

                Navigation.PushAsync(new StudentPresentationPhase(selectedMatch));
            //    wasExecuted = true;
            //}
        }
    }
}
