using System.Linq;
using TMCS_Client.Controllers;
using Xamarin.Forms;

namespace TMCS_Client.UI {
    public class StudentHomepage : ContentPage {
        private AbsoluteLayout menu;
        private ScrollView pageContent = new ScrollView();
        private ListView matchesList = new ListView();

        private App app = Application.Current as App;
        private StudentController studentController = StudentController.getStudentController();

        public StudentHomepage() {
            this.Title = "Matches";

            loadMatches();

            pageContent.Content = matchesList;

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
                                  .Select(match => new CellData() {
                                      PositionTitle = match.job.positionTitle,
                                      CompanyName = match.job.recruiter.company.companyName,
                                      Location = match.job.location
                                  });

            matchesList.ItemsSource = postings;
            matchesList.RowHeight = 80;
        }

        class CellData {
            public string PositionTitle { get; set; }
            public string CompanyName { get; set; }
            public string Location { get; set; }
        }

        class MatchCell : ViewCell {
            public MatchCell() {
                StackLayout cellWrapper = new StackLayout();
                StackLayout layout = new StackLayout();

                Label title = new Label();
                Label company = new Label();
                Label location = new Label();

                //title.Text = "Position Title";
                //company.Text = "Company";
                //location.Text = "Location";

                title.SetBinding(Label.TextProperty, new Binding("PositionTitle"));
                company.SetBinding(Label.TextProperty, new Binding("CompanyName"));
                location.SetBinding(Label.TextProperty, new Binding("Location"));

                layout.Children.Add(title);
                layout.Children.Add(company);
                layout.Children.Add(location);
                cellWrapper.Children.Add(layout);

                View = cellWrapper;
            }
        }
    }
}
