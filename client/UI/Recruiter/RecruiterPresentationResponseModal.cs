﻿using System;
using System.Collections.Generic;
using System.Text;
using TMCS_Client.Controllers;
using TMCS_Client.CustomUIElements.Buttons;
using TMCS_Client.DTOs;
using Xamarin.Forms;
using TMCS_Client.CustomUIElements.Labels;


namespace TMCS_Client.UI {
    public class RecruiterPresentationResponseModal : ContentPage {
        private Match match;

		private ScrollView pageContent;
		private AbsoluteLayout presentationPage;

		private Label lblStudentpresentation;

		//Submit Response
		private Button btnSubmit;

		//Not Interested
		private Button btnNotInterested;


		public RecruiterPresentationResponseModal(Match match) {
            this.match = match;
            Title = "Presentation Response Review";

            pageContent = new ScrollView()
            {
                Orientation = ScrollOrientation.Vertical,
            };

			presentationPage = new AbsoluteLayout()
			{
				HeightRequest = (Constants.Forms.Sizes.ROW_HEIGHT * 7.0),
			};

			AbsoluteLayout studentPresentation = new AbsoluteLayout()
			{
			};

			string link = match.studentPresentationLink.ToString();
			link = link.Replace("|", "/");
			var studentPres = new WebView()
			{
				Source = link,
			};
            presentationPage.Children.Add(lblStudentpresentation =
										new FormFieldLabel("Student's Presentation:"),
										new Rectangle(0.5, 0, 0.9, 0.25),
										AbsoluteLayoutFlags.All);

            presentationPage.Children.Add(studentPres,
									 new Rectangle(0, 0, 1.0, 6 * Constants.Forms.Sizes.ROW_HEIGHT),
									AbsoluteLayoutFlags.WidthProportional);


			AbsoluteLayout buttons = new AbsoluteLayout()
			{
			};

			var declineButton = new DeclineButton();
			declineButton.Clicked += (object sender, EventArgs e) =>
			{
				match.applicationStatus = Match.ApplicationStatus.REJECTED;
				updateMatch();
			};
            buttons.Children.Add(declineButton,
                                            new Rectangle(0.1, 1.0, 0.4, 0.9),
                                 AbsoluteLayoutFlags.All);


			var acceptButton = new AcceptButton();
			acceptButton.Clicked += (object sender, EventArgs e) =>
			{
				match.currentPhase = Match.CurrentPhase.INTERVIEW;
				updateMatch();
			};

            buttons.Children.Add(acceptButton,
			new Rectangle(0.9, 1.0, 0.4, 0.9),
                                 AbsoluteLayoutFlags.All);

		
			

			presentationPage.Children.Add(buttons,
								new Rectangle(0.5, 8.5 * Constants.Forms.Sizes.ROW_HEIGHT, 1.0, Constants.Forms.Sizes.ROW_HEIGHT),
								AbsoluteLayoutFlags.WidthProportional |
								AbsoluteLayoutFlags.XProportional);

            pageContent.Content = presentationPage;

            Content = pageContent;
        }

        void updateMatch() {
            MatchController.getMatchController().updateMatch(match);
            Navigation.PopModalAsync();
        }
    }
}
