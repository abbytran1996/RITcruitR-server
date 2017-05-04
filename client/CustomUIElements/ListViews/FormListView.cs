using System;
using Xamarin.Forms;
using TMCS_Client.CustomUIElements.SearchBars;
using System.Collections.ObjectModel;
using TMCS_Client.DTOs;

namespace TMCS_Client.CustomUIElements.ListViews
{
    public class FormListView<T, CustomDisplayCell> : ListView
    {
        public ObservableCollection<T> items { get; }

        private T emptyListItem;

        public FormListView(T emptyListItem,ObservableCollection<T> initialItems = null) : base(ListViewCachingStrategy.RetainElement)
        {
            if (initialItems == null){
                items = new ObservableCollection<T>();
                items.Add(emptyListItem);
            }else{
                items = initialItems;
            }
            this.emptyListItem = emptyListItem;
            SeparatorVisibility = SeparatorVisibility.None;
            base.ItemsSource = items;
            base.ItemTemplate = new DataTemplate(typeof(CustomDisplayCell));
            base.HasUnevenRows = true;
        }

        public void addItem(T newItem){
            items.Remove(emptyListItem);
            if (!items.Contains(newItem))
                {
                    items.Add(newItem);
                }
        }

        public void removeItem(T toRemove){
            items.Remove(toRemove);
            if(items.Count == 0){
                items.Add(emptyListItem); 
            }
        }
    }
}
