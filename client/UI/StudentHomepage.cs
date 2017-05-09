using System.Collections.Generic;
using System.Linq;
using TMCS_Client.Controllers;
using TMCS_Client.CustomUIElements.Labels;
using TMCS_Client.CustomUIElements.ListViews;
using TMCS_Client.CustomUIElements.ViewCells;
using TMCS_Client.DTOs;
using Xamarin.Forms;
using System;

namespace TMCS_Client.UI {
    public class StudentHomepage : ContentPage {

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

        public StudentHomepage() {
            IsPullToRefreshEnabled = true;
            matchesList.RefreshCommand = new Command(async () => {
                IsRefreshing = true;
                    
                await setupMatchedList();

                IsRefreshing = false;
            });

            setupMatchedList();
            menu = new AbsoluteLayout();

            pageContent.Children.Add(new SubSectionTitleLabel("You have been matched with the following jobs:"));

            matchesListContainer.Content = matchesList;
            matchesListContainer.Margin = new Thickness(22, 0);

            pageContent.Children.Add(matchesListContainer);

            bottomItems.Children.Add(new Label() {
                Text = "Select a position you may be interested in"
            },
            new Rectangle(0, 0, 1, 1), AbsoluteLayoutFlags.All);
#if __IOS__
            ToolbarItem btnAddJobPosting;
            ToolbarItems.Add(btnAddJobPosting = new ToolbarItem()
            {
                Icon = "TMCS_Client.iOS.Resources.add.png",
            });
#endif
#if __ANDROID__
            Button btnAddJobPosting;
            btnAddJobPosting = new Button()
            {
                Image = "add_job_posting.png",
                BackgroundColor = Color.Transparent,
                BorderColor = Color.Transparent,
                BorderWidth = 0.0,
            };
            menu.Children.Add(btnAddJobPosting,
                                    new Rectangle(0.95,0.95,80.0,80.0),
                                    AbsoluteLayoutFlags.PositionProportional);
#endif
            btnAddJobPosting.Clicked += (object sender, EventArgs e) =>
                Navigation.PushAsync(new SkillsEditing(app.CurrentStudent));

            Content = pageContent;
        

            pageContent.Children.Add(bottomItems);
            pageContent.Children.Add(menu);

            Content = pageContent;
            
            RefreshCommand += setupMatchedList();
        }

        private void onItemTapped(object sender, ItemTappedEventArgs e) {
            var selectedMatch = e.Item;

            bottomItems.Children.Clear();
            bottomItems.Children.Add(new Button() {
                Text = "Not Interested",
                BackgroundColor = Constants.Forms.Colors.FAILURE,
                HorizontalOptions = LayoutOptions.CenterAndExpand
            },
            new Rectangle(0, 0, 0.5, 1), AbsoluteLayoutFlags.All);

            bottomItems.Children.Add(new Button() {
                Text = "Interested",
                BackgroundColor = Constants.Forms.Colors.SUCCESS,
                HorizontalOptions = LayoutOptions.CenterAndExpand
            },
            new Rectangle(1, 0, 0.5, 1), AbsoluteLayoutFlags.All);
        }

        /// <summary>
        /// Grabs the Match objects for the current Student, and sets those as this view's items
        /// </summary>
        private void setupMatchedList() {
            var student = app.CurrentStudent;
            matches = studentController.getMatchesForStudent(student);
            matchesList.ItemTemplate = new DataTemplate(typeof(MatchCell));

            var postings = matches.Where(match => match.matchStrength > 0.1)
                                  .OrderByDescending(match => match.matchStrength)
                                  .Select(match => new CellData() {
                                      PositionTitle = match.job.positionTitle,
                                      CompanyName = match.job.recruiter.company.companyName,
                                      Location = match.job.location,
                                      Website = match.job.recruiter.company.website,
                                      Match = match
                                  });

            matchesList.ItemsSource = postings;
            matchesList.RowHeight = 110;

            matchesList.ItemTapped += onItemTapped;
        }

        class CellData {
            public string PositionTitle { get; set; }
            public string CompanyName { get; set; }
            public string Location { get; set; }
            public string Website { get; set; }
            public Match Match { get; set; }
        }

        class MatchCell : ViewCell {
            public MatchCell() {
                StackLayout cellWrapper = new StackLayout();
                StackLayout layout = new StackLayout();

                Label title = new Label() {
                    FontSize = 20
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
    }
}
