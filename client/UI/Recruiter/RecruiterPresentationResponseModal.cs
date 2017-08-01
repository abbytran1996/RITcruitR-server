using System;
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
		private AcceptButton acceptButton;

		//Not Interested
		private DeclineButton declineButton;


		public RecruiterPresentationResponseModal(Match match) {
            this.match = match;

            string link = match.studentPresentationLink.ToString();
            link = link.Replace("|", "/");
            var studentPres = new WebView()
            {
                Source = link,
            };
            this.Title = "Presentation Response Review";

            pageContent = new ScrollView()
            {
                Orientation = ScrollOrientation.Vertical,
            };

			presentationPage = new AbsoluteLayout()
			{
				HeightRequest = (Constants.Forms.Sizes.ROW_HEIGHT * 7.0),
			};

            AbsoluteLayout studentPresentation = new AbsoluteLayout() { };

			
			
			

            studentPresentation.Children.Add(lblStudentpresentation = new FormFieldLabel("Student's Presentation:"),
										  new Rectangle(0.5, 0.5, 0.9, 0),
										  AbsoluteLayoutFlags.All);

            studentPresentation.Children.Add(studentPres, 
                                          new Rectangle(0, 1, 1.0, 6 * Constants.Forms.Sizes.ROW_HEIGHT), 
                                          AbsoluteLayoutFlags.WidthProportional);

            presentationPage.Children.Add(studentPresentation,
                                          new Rectangle(0, 1, 1.0, 6 * Constants.Forms.Sizes.ROW_HEIGHT),
                                          AbsoluteLayoutFlags.WidthProportional);

            AbsoluteLayout buttons = new AbsoluteLayout() { };

			declineButton = new DeclineButton();
			declineButton.Clicked += (object sender, EventArgs e) =>
			{
				match.applicationStatus = Match.ApplicationStatus.REJECTED;
				updateMatch();
			};
            buttons.Children.Add(declineButton, new Rectangle(0.1, 1.0, 0.4, 0.9), AbsoluteLayoutFlags.All);

			acceptButton = new AcceptButton();
			acceptButton.Clicked += (object sender, EventArgs e) =>
			{
				match.currentPhase = Match.CurrentPhase.INTERVIEW;
                match.applicationStatus = Match.ApplicationStatus.ACCEPTED;
				updateMatch();
			};
            buttons.Children.Add(acceptButton, new Rectangle(0.9, 1.0, 0.4, 0.9), AbsoluteLayoutFlags.All);

			presentationPage.Children.Add(buttons, 
                                          new Rectangle(0.5, 8.5 * Constants.Forms.Sizes.ROW_HEIGHT, 
                                                        1.0, Constants.Forms.Sizes.ROW_HEIGHT), 
                                          AbsoluteLayoutFlags.WidthProportional | 
                                            AbsoluteLayoutFlags.XProportional);

            pageContent.Content = presentationPage;

            Content = pageContent;
        }

        void updateMatch() {
            MatchController.getMatchController().updateMatch(match);
            Navigation.PopAsync();
        }
    }
}
