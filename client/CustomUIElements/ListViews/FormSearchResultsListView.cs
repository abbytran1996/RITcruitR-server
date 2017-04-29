using System;
using Xamarin.Forms;
using TMCS_Client.CustomUIElements.SearchBars;
using System.Collections.ObjectModel;
using TMCS_Client.DTOs;

namespace TMCS_Client.CustomUIElements.ListViews
{
    public class FormSearchResultsListView<T> : ListView
    {
        public ObservableCollection<T> searchResults { get; }

        public FormSearchResultsListView() : base(ListViewCachingStrategy.RetainElement)
        {
            IsVisible = false;
            IsEnabled = false;
            searchResults = new ObservableCollection<T>();
            base.ItemsSource = searchResults;
        }

        public void show(){
            IsVisible = true;
            IsEnabled = true;
        }

        public void hide(){
            IsVisible = false;
            IsEnabled = false;
        }
    }
}
