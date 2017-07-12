using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using TMCS_Client.Controllers;
using TMCS_Client.CustomUIElements.Labels;
using TMCS_Client.DTOs;
using Xamarin.Forms;

namespace TMCS_Client.UI {
    /// <summary>
    /// Useful superclass for lists of matches in one of the phases
    /// </summary>
    public abstract class StudentListPage : ContentPage {
        private AbsoluteLayout menu;
        private StackLayout pageContent = new StackLayout();
        private ScrollView matchesListContainer = new ScrollView();
        private ListView matchesList = new ListView();
        private AbsoluteLayout bottomItems = new AbsoluteLayout() {
            HorizontalOptions = LayoutOptions.CenterAndExpand
        };

        private App app = Application.Current as App;
        private StudentController studentController = StudentController.getStudentController();

        private List<Match> matches;

        private Match.CurrentPhase phase;

        public StudentListPage(String sectionLabel, Match.CurrentPhase currentPhase) {
            phase = currentPhase;

            matchesList.IsPullToRefreshEnabled = true;
            matchesList.RefreshCommand = new Command(() => {
                matchesList.IsRefreshing = true;

                setupMatchedList();

                matchesList.IsRefreshing = false;
            });

            setupMatchedList();
            menu = new AbsoluteLayout();

            pageContent.Children.Add(new SubSectionTitleLabel(sectionLabel));

            matchesListContainer.Content = matchesList;
            matchesListContainer.Margin = new Thickness(22, 0);

            pageContent.Children.Add(matchesListContainer);
            
            Content = pageContent;
        }

        protected void setupMatchedList() {
            var student = app.CurrentStudent;
            matches = MatchController.getMatchController().getMatchesForStudent(student);
            matchesList.ItemTemplate = new DataTemplate(typeof(MatchCell));

            var postings = matches.Where(match => match.currentPhase == phase)
                                  .Where(match => match.applicationStatus == Match.ApplicationStatus.NEW || match.applicationStatus == Match.ApplicationStatus.IN_PROGRESS)
                                  .Where(match => match.matchStrength > 0.1)
                                  .OrderByDescending(match => match.timeLastUpdated)
                                  .Select(match => new CellData(match));

            matchesList.ItemsSource = postings;
            matchesList.RowHeight = 130;

            matchesList.ItemTapped += onItemTapped;
        }

        protected abstract void onItemTapped(object sender, ItemTappedEventArgs e);

        protected class CellData {
            public string PositionTitle { get; private set; }
            public string CompanyName { get; private set; }
            public string Location { get; private set; }
            public string Website { get; private set; }
            public Match Match { get; private set; }

            public CellData(Match match) {
                PositionTitle = match.job.positionTitle;
                CompanyName = match.job.recruiter.company.companyName;
                Location = match.job.location;
                Website = match.job.recruiter.company.websiteURL;
                Match = match;
            }
        }

        protected class MatchCell : ViewCell {
            public MatchCell() {
                StackLayout cellWrapper = new StackLayout();
                StackLayout layout = new StackLayout();

                Label title = new Label() {
                    FontSize = 18
                };
                Label company = new Label();
                Label location = new Label();
                Label website = new Label();

                title.SetBinding(Label.TextProperty, new Binding("PositionTitle"));
                company.SetBinding(Label.TextProperty, new Binding("CompanyName"));
                location.SetBinding(Label.TextProperty, new Binding("Location"));
                website.SetBinding(Label.TextProperty, new Binding("Website"));

                layout.Children.Add(title);
                layout.Children.Add(company);
                layout.Children.Add(location);
                layout.Children.Add(website);
                cellWrapper.Children.Add(layout);

                View = cellWrapper;
            }
        }

        protected override void OnAppearing() {
            setupMatchedList();
            base.OnAppearing();
        }
    }
}
