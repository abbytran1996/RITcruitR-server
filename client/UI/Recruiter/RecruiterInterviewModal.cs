using System;
using System.Collections.Generic;
using System.Text;
using TMCS_Client.Controllers;
using TMCS_Client.CustomUIElements.Buttons;
using TMCS_Client.CustomUIElements.Labels;
using TMCS_Client.DTOs;
using Xamarin.Forms;

namespace TMCS_Client.UI {
    class RecruiterInterviewModal : ContentPage {
        private Match match;
        private DTOs.Student student;

        public RecruiterInterviewModal(Match match) {
            this.match = match;
            this.student = match.student;

            Title = "Interview";
            DeclineButton dbutton = new DeclineButton();
            dbutton.Clicked += (object sender, EventArgs e2) =>
            {
                this.match.applicationStatus = Match.ApplicationStatus.REJECTED;
                this.match.currentPhase = Match.CurrentPhase.NONE;
                updateMatch();
            }; ;

            Content = new StackLayout
            {
                Children = {
                    new PageTitleLabel(student.firstName + " " + student.lastName),
                    new SubSectionTitleLabel("School"),
                    new Label { Text = student.school },
                    new SubSectionTitleLabel("Graduation Date"),
                    new Label { Text = student.graduationDate.ToShortDateString() },
                    /*TODO resume section
                    new SubSectionTitleLabel("Resume"),
                    new Button {Text = "Download Resume", Command = downloadAndOpenResume}*/
                    new SubSectionTitleLabel("Student Contact Information"),
                    new Label { Text = "Email:\t" + student.email },
                    new Label { Text = "Phone:\t" + student.phoneNumber },
                    dbutton
                }
            };
        }

        void updateMatch() {
            MatchController.getMatchController().updateMatch(match);
            Navigation.PopModalAsync();
        }
    }
}
