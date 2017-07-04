using TMCS_Client.CustomUIElements.Buttons;
using TMCS_Client.CustomUIElements.Labels;
using TMCS_Client.DTOs;
using Xamarin.Forms;

namespace TMCS_Client.UI {
    public class StudentInterviewPhase : ContentPage {
        public StudentInterviewPhase(Match match) {
            var job = match.job;

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
                    new DeclineButton()
                }
            };
        }
    }
}
