using System;
using System.Collections.Generic;
using Xamarin.Forms;
using TMCS_Client.CustomUIElements.ListViews;

namespace TMCS_Client.CustomUIElements.SearchBars
{
    public class FormSearchBar<T, CustomSearchResultCell, CustomListCell> : SearchBar
    {
        private List<T> potentialResults;
        private FormSearchResultsListView<T, CustomSearchResultCell, CustomListCell> searchResultsList;

        public FormSearchBar(String placeholder, List<T> potentialResults, FormSearchResultsListView<T, CustomSearchResultCell, CustomListCell> searchResultsList)
        {
            this.potentialResults = potentialResults;
            this.searchResultsList = searchResultsList;
            Placeholder = placeholder;
            BackgroundColor = Color.White;
            base.SearchButtonPressed += (object sender, EventArgs e) => base.Focus();
            base.Focused += (object sender, FocusEventArgs e) => OnPropertyChanged(SearchBar.TextProperty.PropertyName);
#if __IOS__
            base.Unfocused += (object sender, FocusEventArgs e) => searchResultsList.hide();
#endif
        }

        protected override void OnPropertyChanged(string propertyName = null)
        {
            Boolean match;
            if ((propertyName != null) && (propertyName == SearchBar.TextProperty.PropertyName))
            {
                if (Text == null || Text == "")
                {
                    searchResultsList.hide();
                    searchResultsList.searchResults.Clear();
                }
                else
                {
                    foreach (T potentialResult in potentialResults)
                    {
                        match = potentialResult.ToString().ToLower().Contains(Text.ToLower());
                        if (match && !searchResultsList.searchResults.Contains(potentialResult))
                        {
                            searchResultsList.searchResults.Add(potentialResult);
                        }else if(!match){
                            searchResultsList.searchResults.Remove(potentialResult);
                        }
					}
					searchResultsList.show();
                }
            }
            else
            {
                base.OnPropertyChanged(propertyName);
            }
        }
    }
}
