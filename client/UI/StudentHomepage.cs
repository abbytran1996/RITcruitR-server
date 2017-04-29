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
        private StudentController studentController;

        public StudentHomepage() {
            loadMatches();

            Content = pageContent;

            studentController = StudentController.getStudentController();
        }

        /// <summary>
        /// Grabs the Match objects for the current Student, and sets those as this view's items
        /// </summary>
        private void loadMatches() {
            var student = app.CurrentStudent;
            var matches = studentController.getMatchesForStudent(student);
            matchesList.ItemsSource = matches;
        }
    }
}
