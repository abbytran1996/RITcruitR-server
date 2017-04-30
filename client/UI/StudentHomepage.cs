using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using TMCS_Client.Controllers;
using TMCS_Client.DTOs;
using Xamarin.Forms;

namespace TMCS_Client.UI {
    public class StudentHomepage : ContentPage {
        private AbsoluteLayout menu;
        private ScrollView pageContent = new ScrollView();
        private ListView matchesList = new ListView();

        private App app = Application.Current as App;
        private StudentController studentController = StudentController.getStudentController();

        public StudentHomepage() {
            loadMatches();

            Content = pageContent;
        }

        /// <summary>
        /// Grabs the Match objects for the current Student, and sets those as this view's items
        /// </summary>
        private void loadMatches() {
            var student = app.CurrentStudent;
            var matches = studentController.getMatchesForStudent(student);
            matchesList.ItemTemplate = new DataTemplate(typeof(MatchCell));

            var postings = matches.Where(match => match.matchStrength > 0.1)
                                  .Select(match => match.jobPosting);
            matchesList.ItemsSource = postings;
        }

        class MatchCell : ViewCell {
            public MatchCell() {
                StackLayout layout = new StackLayout();

                Label title = new Label();
                Label company = new Label();
                Label location = new Label();

                title.SetBinding(Label.TextProperty, "positionTitle");
                company.SetBinding(Label.TextProperty, "company.companyName");
                location.SetBinding(Label.TextProperty, "location");

                layout.Children.Add(title);
                layout.Children.Add(company);
                layout.Children.Add(location);

                View = layout;
            }
        }
    }
}
