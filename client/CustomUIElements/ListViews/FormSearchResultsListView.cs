using System;
using Xamarin.Forms;
using TMCS_Client.CustomUIElements.SearchBars;
using System.Collections.ObjectModel;
using TMCS_Client.DTOs;
using TMCS_Client.CustomUIElements.Buttons;

namespace TMCS_Client.CustomUIElements.ListViews
{
    public class FormSearchResultsListView<T> : ListView
    {
        public ObservableCollection<T> searchResults { get; }
        public FormListView<T> selectedItemsView { get; }

        public FormSearchResultsListView(FormListView<T> selectedItemsView) : base(ListViewCachingStrategy.RetainElement)
        {
            this.selectedItemsView = selectedItemsView;
            IsVisible = false;
            IsEnabled = false;
            searchResults = new ObservableCollection<T>();
            SeparatorColor = Color.Gray;
            base.ItemsSource = searchResults;
            base.ItemTemplate = new DataTemplate(typeof(SearchResultCell));
            //Need an improvement to this
            base.ItemSelected += (object sender, SelectedItemChangedEventArgs e) => SelectedItem = null;
        }

        public void show()
        {
            IsVisible = true;
            IsEnabled = true;
        }

        public void hide()
        {
            IsVisible = false;
            IsEnabled = false;
        }
    }

    internal class SearchResultCell : ViewCell
    {
        public SearchResultCell() : base()
        {
            AbsoluteLayout cellLayout = new AbsoluteLayout()
            {
                HeightRequest = Constants.Forms.Sizes.ROW_HEIGHT,
                BackgroundColor = Color.White,
            };

            Label searchResultLabel = new Label()
            {
                BackgroundColor = Color.White,
                VerticalTextAlignment = TextAlignment.Center,
            };

            //Button addSearchResult = new Button();
            AddSkillButton addSearchResult = new AddSkillButton()
            {
            };

            addSearchResult.SetBinding(AddSkillButton.SkillIDProperty, "id");
            addSearchResult.SetBinding(AddSkillButton.NameProperty, "name");

            searchResultLabel.SetBinding(Label.TextProperty, "name");

            cellLayout.Children.Add(searchResultLabel,
                                   new Rectangle(0.5, 0.0, 0.9, 1.0),
                                   AbsoluteLayoutFlags.All);

            cellLayout.Children.Add(addSearchResult,new Rectangle(1.0,0.5,Constants.Forms.Sizes.ROW_HEIGHT * (2.0 / 3.0),
                           Constants.Forms.Sizes.ROW_HEIGHT * (2.0 / 3.0)),
                                   AbsoluteLayoutFlags.PositionProportional);
            
            View = cellLayout;
        }
    }
}
