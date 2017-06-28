using System;
using TMCS_Client.DTOs;
using TMCS_Client.CustomUIElements.Labels;
using TMCS_Client.Controllers;
using System.Collections.Generic;
using Xamarin.Forms;
using TMCS_Client.CustomUIElements.Buttons;

namespace TMCS_Client.UI
{
    public class RecruiterProblemResponseModal : ContentPage
    {
        private Match activeMatch;

        private AbsoluteLayout pageContent;

        private AbsoluteLayout problemResponseSection;
        private Label lblProblemResponseHeader;
        private Label lblProblemResponse;
        private FormFieldLabel lblTag;
        private Picker pickTag;
        private Label lblTimeSubmitted;

        private AbsoluteLayout buttons;
        private Button btnDecline;
        private Button btnSave;
        private Button btnAccept;

        public RecruiterProblemResponseModal(Match activeMatch)
        {
            this.activeMatch = activeMatch;

            this.Title = "Problem Respone Review";

            pageContent = new AbsoluteLayout();
            problemResponseSection = new AbsoluteLayout();

            lblProblemResponseHeader = new Label()
            {
                Text = "Problem Response Review",
                FontSize = 24.0,
                HorizontalTextAlignment = TextAlignment.Center,
            };
            problemResponseSection.Children.Add(lblProblemResponseHeader,
                                                new Rectangle(0.5,0,0.9,Constants.Forms.Sizes.ROW_HEIGHT*2/3),
                                                AbsoluteLayoutFlags.XProportional | AbsoluteLayoutFlags.WidthProportional);

            lblProblemResponse = new Label()
            {
                Text = activeMatch.studentProblemResponse,
                FontSize = 19.0,

            };
            problemResponseSection.Children.Add(lblProblemResponse,
                                    new Rectangle(0.5,Constants.Forms.Sizes.ROW_HEIGHT*2/3,
                                                  0.9,Constants.Forms.Sizes.ROW_HEIGHT*2),
                                                  AbsoluteLayoutFlags.XProportional | AbsoluteLayoutFlags.WidthProportional);

            lblTimeSubmitted = new Label()
            {
                Text = activeMatch.timeLastUpdated.ToString(),
                FontSize = 16.0,
                HorizontalTextAlignment = TextAlignment.End,
                TextColor = Color.Gray,
            };
            problemResponseSection.Children.Add(lblTimeSubmitted, 
                                               new Rectangle(0.5,Constants.Forms.Sizes.ROW_HEIGHT*8/3,
                                                            0.9,Constants.Forms.Sizes.ROW_HEIGHT/3),
                                               AbsoluteLayoutFlags.XProportional | AbsoluteLayoutFlags.WidthProportional);

            lblTag = new FormFieldLabel("Tag");
            problemResponseSection.Children.Add(lblTag,
                                               new Rectangle(0.5,Constants.Forms.Sizes.ROW_HEIGHT * 3,
                                                             0.9,Constants.Forms.Sizes.ROW_HEIGHT / 2),
                                                AbsoluteLayoutFlags.XProportional | AbsoluteLayoutFlags.WidthProportional);

            pickTag = new Picker()
            {
                ItemsSource = new List<String>(new String[] { "Great", "Good", "Alright", "Bad" }),
                SelectedItem = activeMatch.tag,
            };
            pickTag.PropertyChanged += (object sender, System.ComponentModel.PropertyChangedEventArgs e) => {
                if (e.PropertyName == Picker.SelectedItemProperty.PropertyName){
                    activeMatch.tag = pickTag.SelectedItem.ToString();
                }
            };
            problemResponseSection.Children.Add(pickTag,
                                               new Rectangle(0.5,Constants.Forms.Sizes.ROW_HEIGHT * 3.5,
                                                             0.9,Constants.Forms.Sizes.ROW_HEIGHT / 2),
                                               AbsoluteLayoutFlags.XProportional |
                                               AbsoluteLayoutFlags.WidthProportional);

            buttons = new AbsoluteLayout();

            btnDecline = new DeclineButton();
            btnDecline.Clicked += (object sender, EventArgs e) => {
                activeMatch.applicationStatus = Match.ApplicationStatus.REJECTED;
                buttonPushed();
            };
            buttons.Children.Add(btnDecline,
                                               new Rectangle(0.0, Constants.Forms.Sizes.ROW_HEIGHT * 1/6,
                                                            0.3, Constants.Forms.Sizes.ROW_HEIGHT*5/6),
                                               AbsoluteLayoutFlags.XProportional | AbsoluteLayoutFlags.WidthProportional);

			btnSave = new Button()
			{
				Text = "Save",
				BackgroundColor = Color.Blue,
				TextColor = Color.White,
				FontSize = 18.0,
				FontAttributes = FontAttributes.Bold,
			};
			btnSave.Clicked += (object sender, EventArgs e) =>
			{
				buttonPushed();
			};
			buttons.Children.Add(btnSave,
											   new Rectangle(0.5, Constants.Forms.Sizes.ROW_HEIGHT * 1 / 6,
															0.3, Constants.Forms.Sizes.ROW_HEIGHT * 5 / 6),
											   AbsoluteLayoutFlags.XProportional | AbsoluteLayoutFlags.WidthProportional);

            btnAccept = new AcceptButton();
			btnAccept.Clicked += (object sender, EventArgs e) =>
			{
                activeMatch.currentPhase = Match.CurrentPhase.PRESENTATION_WAITING_FOR_STUDENT;
				buttonPushed();
			};
			buttons.Children.Add(btnAccept,
											   new Rectangle(1.0, Constants.Forms.Sizes.ROW_HEIGHT * 1 / 6,
															0.3, Constants.Forms.Sizes.ROW_HEIGHT * 5 / 6),
											   AbsoluteLayoutFlags.XProportional | AbsoluteLayoutFlags.WidthProportional);
            
            problemResponseSection.Children.Add(buttons,
											   new Rectangle(0.5, Constants.Forms.Sizes.ROW_HEIGHT * 25 / 6,
															0.9, Constants.Forms.Sizes.ROW_HEIGHT * 5 / 6),
                                               AbsoluteLayoutFlags.XProportional | AbsoluteLayoutFlags.WidthProportional);

            pageContent.Children.Add(problemResponseSection,
                                    new Rectangle(0.0,0.1,1.0,Constants.Forms.Sizes.ROW_HEIGHT * 5.0),
                                    AbsoluteLayoutFlags.XProportional | AbsoluteLayoutFlags.YProportional |
                                    AbsoluteLayoutFlags.WidthProportional);

            this.Content = pageContent;
        }

        private void buttonPushed(){
            MatchController.getMatchController().updateMatch(activeMatch);
            Navigation.PopModalAsync();
        }
    }
}
