using System;
using Xamarin.Forms;
using TMCS_Client.CustomUIElements.SearchBars;
using System.Collections.ObjectModel;
using TMCS_Client.DTOs;
using TMCS_Client.CustomUIElements.Buttons;

namespace TMCS_Client.CustomUIElements.ListViews
{
    public class FormSearchResultsListView<T, CustomSeachResultCell, CustomListCell> : ListView
    {
        public ObservableCollection<T> searchResults { get; }
        public FormListView<T, CustomListCell> selectedItemsView { get; }

        public FormSearchResultsListView(FormListView<T, CustomListCell> selectedItemsView) : base(ListViewCachingStrategy.RetainElement)
        {
            this.selectedItemsView = selectedItemsView;
            IsVisible = false;
            IsEnabled = false;
            searchResults = new ObservableCollection<T>();
            //BackgroundColor = Color.LightGray;
            //SeparatorVisibility = SeparatorVisibility.None;
            SeparatorColor = Color.Gray;
            base.ItemsSource = searchResults;
            base.ItemTemplate = new DataTemplate(typeof(CustomSeachResultCell));
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
}
