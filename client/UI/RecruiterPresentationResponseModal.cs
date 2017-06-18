using System;
using System.Collections.Generic;
using System.Text;
using TMCS_Client.Controllers;
using TMCS_Client.CustomUIElements.Buttons;
using TMCS_Client.DTOs;
using Xamarin.Forms;

namespace TMCS_Client.UI {
    class RecruiterPresentationResponseModal : ContentPage {
        private Match match;

        public RecruiterPresentationResponseModal(Match match) {
            this.match = match;
            Title = "Presentation Response Review";

            var pageContent = new AbsoluteLayout();

            var studentPresentation = new WebView() {
                Source = match.studentPresentationLink,
            };

            pageContent.Children.Add(studentPresentation,
                new Rectangle(0.05, 0.05, 0.9, 0.250625),
                AbsoluteLayoutFlags.All);

            var buttons = new AbsoluteLayout();
            var declineButton = new DeclineButton();
            declineButton.Clicked += (object sender, EventArgs e) => {
                match.applicationStatus = Match.ApplicationStatus.REJECTED;
                updateMatch();
            };
            buttons.Children.Add(declineButton,
                new Rectangle(0.0, Constants.Forms.Sizes.ROW_HEIGHT * 1 / 6, 0.3, Constants.Forms.Sizes.ROW_HEIGHT * 5.0 / 6.0),
                AbsoluteLayoutFlags.XProportional | AbsoluteLayoutFlags.WidthProportional);

            var acceptButton = new AcceptButton();
            acceptButton.Clicked += (object sender, EventArgs e) => {
                match.currentPhase = Match.CurrentPhase.INTERVIEW;
                updateMatch();
            };
            buttons.Children.Add(acceptButton,
                new Rectangle(1.0, Constants.Forms.Sizes.ROW_HEIGHT * 1 / 6, 0.3, Constants.Forms.Sizes.ROW_HEIGHT * 5 / 6),
                AbsoluteLayoutFlags.XProportional | AbsoluteLayoutFlags.WidthProportional);

            pageContent.Children.Add(buttons,
                 new Rectangle(0.5, Constants.Forms.Sizes.ROW_HEIGHT * 25 / 6, 0.9, Constants.Forms.Sizes.ROW_HEIGHT * 5 / 6),
                 AbsoluteLayoutFlags.XProportional | AbsoluteLayoutFlags.WidthProportional);

            Content = pageContent;
        }

        void updateMatch() {
            MatchController.getMatchController().updateMatch(match);
            Navigation.PopModalAsync();
        }
    }
}
