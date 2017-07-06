using TMCS_Client.Controllers;
using TMCS_Client.CustomUIElements.Buttons;
using TMCS_Client.CustomUIElements.Labels;
using TMCS_Client.DTOs;
using Xamarin.Forms;

namespace TMCS_Client.UI {
    public class StudentInterviewPhase : ContentPage {
        private Match match;

        public StudentInterviewPhase(Match match) {
            this.match = match;
            var job = match.job;

            var declineButton = new DeclineButton();
            declineButton.Clicked += onDeclineButtonClicked;

            Content = new StackLayout {
                Children = {
                    new PageTitleLabel(job.positionTitle),
                    new SubSectionTitleLabel("Position Description"),
                    new Label { Text = job.description },
                    new SubSectionTitleLabel("Location"),
                    new Label { Text = job.recruiter.company.location },
                    new SubSectionTitleLabel("Recruiter Contact Information"),
                    new Label { Text = "Email:\t" + job.recruiter.email },
                    new Label { Text = "Phone:\t" + job.recruiter.phoneNumber },
                    declineButton
                }
            };
        }

        private void onDeclineButtonClicked(object sender, System.EventArgs e) {
            match.applicationStatus = Match.ApplicationStatus.REJECTED;
            MatchController.getMatchController().updateMatch(match);
            Navigation.PopAsync(true);
        }
    }
}
