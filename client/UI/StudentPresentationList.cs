using System.Collections.Generic;
using System.Linq;
using TMCS_Client.Controllers;
using TMCS_Client.CustomUIElements.Labels;
using TMCS_Client.CustomUIElements.ListViews;
using TMCS_Client.CustomUIElements.ViewCells;
using TMCS_Client.DTOs;
using Xamarin.Forms;
using System;

namespace TMCS_Client.UI
{
	public class StudentPresentationList : ContentPage
	{

		private AbsoluteLayout menu;
		private StackLayout pageContent = new StackLayout();
		private ScrollView matchesListContainer = new ScrollView();
		private ListView matchesList = new ListView();
		private AbsoluteLayout bottomItems = new AbsoluteLayout()
		{
			HorizontalOptions = LayoutOptions.CenterAndExpand
		};

		private App app = Application.Current as App;
		private StudentController studentController = StudentController.getStudentController();

		private List<Match> matches;

		public StudentPresentationList()
		{

			matchesList.IsPullToRefreshEnabled = true;
			matchesList.RefreshCommand = new Command(() =>
			{
				matchesList.IsRefreshing = true;

				setupMatchedList();

				matchesList.IsRefreshing = false;
			});

			setupMatchedList();
			menu = new AbsoluteLayout();

			pageContent.Children.Add(new SubSectionTitleLabel("You are in the presentation phase with the following jobs:"));

			matchesListContainer.Content = matchesList;
			matchesListContainer.Margin = new Thickness(22, 0);

			pageContent.Children.Add(matchesListContainer);


			Content = pageContent;

		}

		private void setupMatchedList()
		{
			var student = app.CurrentStudent;
			matches = studentController.getMatchesForStudent(student);
			matchesList.ItemTemplate = new DataTemplate(typeof(MatchCell));

            var postings = matches.Where(match => match.currentPhase == Match.CurrentPhase.PRESENTATION_WAITING_FOR_STUDENT)
								  .OrderByDescending(match => match.timeLastUpdated)
								  .Select(match => new CellData()
								  {
									  PositionTitle = match.job.positionTitle,
									  CompanyName = match.job.recruiter.company.companyName,
									  Location = match.job.location,
									  Website = match.job.recruiter.company.websiteURL,
									  Match = match
								  });

			matchesList.ItemsSource = postings;
			matchesList.RowHeight = 110;

			matchesList.ItemTapped += onItemTapped;
		}

		private void onItemTapped(object sender, ItemTappedEventArgs e)
		{
			var selectedMatch = ((CellData)e.Item).Match;

            Navigation.PushAsync(new StudentPresentationPhase(selectedMatch));

		}

		class CellData
		{
			public string PositionTitle { get; set; }
			public string CompanyName { get; set; }
			public string Location { get; set; }
			public string Website { get; set; }
			public Match Match { get; set; }
		}

		class MatchCell : ViewCell
		{
			public MatchCell()
			{
				StackLayout cellWrapper = new StackLayout();
				StackLayout layout = new StackLayout();

				Label title = new Label()
				{
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
