using System;
using TMCS_Client.CustomUIElements.Labels;
using TMCS_Client.DTOs;
using TMCS_Client.Controllers;
using System.Linq;
using TMCS_Client.UI;

using Xamarin.Forms;

namespace TMCS_Client.UI
{
    public class StudentProblemPhase : ContentPage
    {
        //Whole Page
        private ScrollView pageContent;
        private AbsoluteLayout problemPage;

        //Problem Statement
        private Label lblPostingProblem;
        private Editor lblProblemStatement;

        //Response
        private FormFieldLabel lblStudentResponse;
        private Editor txtStudentResponse;

        //Submit Response
        private Button btnSubmit;

        //Not Interested
        private Button btnNotInterested;

        private StudentController studentController = StudentController.getStudentController();

        private Match selectedMatch;

        public StudentProblemPhase(Match selectedMatch)
        {
            this.selectedMatch = selectedMatch;

            var problem = selectedMatch.job.problemStatement;

            this.Title = "Problem Phase";

            //Whole page
            pageContent = new ScrollView()
            {
                Orientation = ScrollOrientation.Vertical,
            };

            problemPage = new AbsoluteLayout()
            {
                HeightRequest = (Constants.Forms.Sizes.ROW_HEIGHT * 4.0),
            };

            AbsoluteLayout postingProblem = new AbsoluteLayout() { };

            postingProblem.Children.Add(lblPostingProblem =
                                        new FormFieldLabel("Recruiter's Problem:"),
                                        new Rectangle(0.5, 0, 0.9, 0.25),
                                        AbsoluteLayoutFlags.All);
            postingProblem.Children.Add(lblProblemStatement =
                                        new Editor
                                        {
                                            IsEnabled = false,
                                            Text = problem,
                                            FontSize = 16
                                        },
#if __IOS__
                                        new Rectangle(0.5, .25, 0.9, 0.85), AbsoluteLayoutFlags.All);
#elif __ANDROID__
                                        new Rectangle(0.5, .25, 0.9, 0.45), AbsoluteLayoutFlags.All);
#endif

            problemPage.Children.Add(postingProblem,
                                     new Rectangle(0, 0, 1.0, 4 * Constants.Forms.Sizes.ROW_HEIGHT),
                                     AbsoluteLayoutFlags.WidthProportional);

            AbsoluteLayout responseEntry = new AbsoluteLayout() { };

            responseEntry.Children.Add(lblStudentResponse = new FormFieldLabel("Your Response:"),
                                       new Rectangle(0.5, 0, 0.9, 0.2), AbsoluteLayoutFlags.All);

            responseEntry.Children.Add(txtStudentResponse =
                                      new Editor
                                      {
                                          Keyboard = Keyboard.Text,
                                          FontSize = 12,
                                          BackgroundColor = Color.GhostWhite,
                                      },
                                      new Rectangle(0.5, 1.0, 0.9, 0.85),
                                      AbsoluteLayoutFlags.All);

            txtStudentResponse.TextChanged += (object sender, TextChangedEventArgs e) => checkResponse();

            problemPage.Children.Add(responseEntry,
                                     new Rectangle(0, 4 * Constants.Forms.Sizes.ROW_HEIGHT,
                                                   1.0, 4 * Constants.Forms.Sizes.ROW_HEIGHT),
                                     AbsoluteLayoutFlags.WidthProportional);

            AbsoluteLayout buttons = new AbsoluteLayout() { };

            buttons.Children.Add(btnSubmit =
            new Button()
            {
                Text = "Submit",
                FontSize = 28,
                TextColor = Color.White,
                BackgroundColor = Color.MediumSeaGreen,
                IsEnabled = false,
            },
                new Rectangle(0.9, 1.0, 0.4, 0.9),
                AbsoluteLayoutFlags.All
            );
            btnSubmit.Clicked += (object sender, EventArgs e) =>
            {
                txtStudentResponse.Text = txtStudentResponse.Text.Replace("\n", "|");
                selectedMatch.currentPhase = Match.CurrentPhase.PROBLEM_WAITING_FOR_RECRUITER;
                selectedMatch.applicationStatus = Match.ApplicationStatus.IN_PROGRESS;
                updateMatch();
                saveResponse(selectedMatch);
                Navigation.PopAsync(true);
            };

            buttons.Children.Add(btnNotInterested =
            new Button()
            {
                Text = "Not Interested",
                FontSize = 22,
                TextColor = Color.White,
                BackgroundColor = Color.Red,
            },
                new Rectangle(0.1, 1.0, 0.4, 0.9),
                AbsoluteLayoutFlags.All
            );
            btnNotInterested.Clicked += (object sender, EventArgs e2) =>
            {
                selectedMatch.applicationStatus = Match.ApplicationStatus.REJECTED;
                selectedMatch.currentPhase = Match.CurrentPhase.NONE;
                updateMatch();
                Navigation.PopAsync(true);
            };

            problemPage.Children.Add(buttons,
                                     new Rectangle(0.5, 8.5 * Constants.Forms.Sizes.ROW_HEIGHT,
                                                   1.0, Constants.Forms.Sizes.ROW_HEIGHT),
                                     AbsoluteLayoutFlags.WidthProportional |
                                        AbsoluteLayoutFlags.XProportional);

            pageContent.Content = problemPage;

            Content = pageContent;
        }

        private void checkResponse()
        {
            if(!string.IsNullOrEmpty(txtStudentResponse.Text))
            {
                btnSubmit.IsEnabled = true;
            }
            else if((txtStudentResponse.Text == null) || (txtStudentResponse.Text == ""))
            {
                btnSubmit.IsEnabled = false;
            }
        }

        private void acceptPosting(Match match, bool accept)
        {
            MatchController.getMatchController().acceptMatch(match, accept);
            match.currentPhase = Match.CurrentPhase.PROBLEM_WAITING_FOR_RECRUITER;
        }

        private void saveResponse(Match match)
        {
            string response = txtStudentResponse.Text;
            var id = match.id;
            MatchController.getMatchController().addStudentResponse(id, response);
        }

        void updateMatch()
        {
            MatchController.getMatchController().updateMatch(selectedMatch);
        }

    }
}

