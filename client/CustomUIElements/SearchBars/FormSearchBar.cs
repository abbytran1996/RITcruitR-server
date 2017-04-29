using System;
using System.Collections.Generic;
using Xamarin.Forms;
using TMCS_Client.CustomUIElements.ListViews;

namespace TMCS_Client.CustomUIElements.SearchBars
{
    public class FormSearchBar<T> : SearchBar
    {
        private List<T> potentialResults;
        private FormSearchResultsListView<T> searchResultsList;

        public FormSearchBar(String placeholder, List<T> potentialResults, FormSearchResultsListView<T> searchResultsList)
        {
            this.potentialResults = potentialResults;
            this.searchResultsList = searchResultsList;
            Placeholder = placeholder;
            BackgroundColor = Color.White;
            base.SearchButtonPressed += (object sender, EventArgs e) => base.Focus();
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
