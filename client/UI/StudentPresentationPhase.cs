using System;
using TMCS_Client.CustomUIElements.Labels;
using TMCS_Client.DTOs;
using TMCS_Client.Controllers;
using System.Linq;
using TMCS_Client.UI;
using TMCS_Client.CustomUIElements.Entries;
using TMCS_Client.CustomUIElements.ViewCells;
using Xamarin.Forms;

namespace TMCS_Client.UI
{
	public class StudentPresentationPhase : ContentPage
	{
		private JobPosting activeJobPosting;
		private WebView recruiterPresentation;

		//Whole Page
		private ScrollView pageContent;
		private AbsoluteLayout presentationPage;


		//presentation Statement
		private Label lblPostingpresentation;
		//private  lblpresentationStatement;

		//Response
		private FormFieldLabel lblStudentResponse;
		private FormEntry txtStudentURL;

		//Submit Response
		private Button btnSubmit;

		//Not Interested
		private Button btnNotInterested;

		private StudentController studentController = StudentController.getStudentController();


        private Match selectedMatch;

        public StudentPresentationPhase(Match selectedMatch)
		{
            this.selectedMatch = selectedMatch;

            var presentation = selectedMatch.job.url;

            var companyPresentation = new WebView()
            {
                Source = presentation,
			};

			this.Title = "Presentation Phase";

			//Whole page
			pageContent = new ScrollView()
			{
				Orientation = ScrollOrientation.Vertical,
			};

			presentationPage = new AbsoluteLayout()
			{
				HeightRequest = (Constants.Forms.Sizes.ROW_HEIGHT * 6.0),
			};


			AbsoluteLayout postingPresentation = new AbsoluteLayout()
			{
			};


			postingPresentation.Children.Add(lblPostingpresentation =
										new FormFieldLabel("Recruiter's Presentation:"),
										new Rectangle(0.5, 0, 0.9, 0.25),
										AbsoluteLayoutFlags.All);


            presentationPage.Children.Add(companyPresentation,
									 new Rectangle(0, 0, 1.0, 6 * Constants.Forms.Sizes.ROW_HEIGHT),
									AbsoluteLayoutFlags.WidthProportional);

			/*presentationResponsesList = new FormListView<Match, PresentationPhaseResponseCell>(
            Match.NullMatch
            );
            */

			AbsoluteLayout responseEntry = new AbsoluteLayout()
			{
			};

			responseEntry.Children.Add(lblStudentResponse =
										new FormFieldLabel("Your Presentation:"),
										new Rectangle(0.5, 0, 0.9, 0.2),
										AbsoluteLayoutFlags.All);

			responseEntry.Children.Add(txtStudentURL =
									   new FormEntry("Your Presentation's Youtube Link", Keyboard.Text),
									  new Rectangle(0.5, 1.0, 0.9, 0.7),
									  AbsoluteLayoutFlags.All);

			//txtStudentResponse.Completed += (object sender, EventArgs e) => checkResponse();

			txtStudentURL.TextChanged += (object sender, TextChangedEventArgs e) => checkResponse();

			presentationPage.Children.Add(responseEntry,
							 new Rectangle(0, 5.5 * Constants.Forms.Sizes.ROW_HEIGHT, 1.0, 1.5 * Constants.Forms.Sizes.ROW_HEIGHT),
							  AbsoluteLayoutFlags.WidthProportional);

			AbsoluteLayout buttons = new AbsoluteLayout()
			{
			};

			buttons.Children.Add(btnSubmit =
			new Button()
			{
				Text = "Submit",
				FontSize = 28,
				TextColor = Color.White,
				BackgroundColor = Color.MediumSeaGreen,
				IsEnabled = false,

                Command = new Command((object obj) => Navigation.PopAsync(true)),
			},


				new Rectangle(0.9, 1.0, 0.4, 0.9),
				AbsoluteLayoutFlags.All
			);
			btnSubmit.IsEnabled = false;

			buttons.Children.Add(btnNotInterested =
			   new Button()
			   {
				   Text = "Not Interested",
				   FontSize = 22,
				   TextColor = Color.White,
				   BackgroundColor = Color.Red,
				   Command = new Command((object obj) => Navigation.PushAsync(new StudentHomepage())),
			   },
					new Rectangle(0.1, 1.0, 0.4, 0.9),
					AbsoluteLayoutFlags.All
			   );

            btnSubmit.Clicked += (object sender, EventArgs e) => saveResponse(selectedMatch);

			presentationPage.Children.Add(buttons,
								new Rectangle(0.5, 8.5 * Constants.Forms.Sizes.ROW_HEIGHT, 1.0, Constants.Forms.Sizes.ROW_HEIGHT),
								AbsoluteLayoutFlags.WidthProportional |
								AbsoluteLayoutFlags.XProportional);

			pageContent.Content = presentationPage;

			Content = pageContent;


		}

		protected override void OnAppearing()
		{
			base.OnAppearing();
			//presentationResponsesList.updateItems(MatchController.getMatchController().getMatchesInPresentationPhase(activeJobPosting));
		}
		
		private void checkResponse()
		{
			if (!string.IsNullOrEmpty(txtStudentURL.Text))
			{
				btnSubmit.IsEnabled = true;
			}
			else if ((txtStudentURL.Text == null) || (txtStudentURL.Text == ""))
			{
				btnSubmit.IsEnabled = false;
			}

		}


		private void saveResponse(Match match)
		{
			string response = txtStudentURL.Text;
			var id = match.id;
            StudentController.getStudentController().addStudentLink(id, response);
		}

	}

}

