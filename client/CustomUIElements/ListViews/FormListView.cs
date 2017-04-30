using System;
using Xamarin.Forms;
using TMCS_Client.CustomUIElements.SearchBars;
using System.Collections.ObjectModel;
using TMCS_Client.DTOs;

namespace TMCS_Client.CustomUIElements.ListViews
{
    public class FormListView<T> : ListView
    {
        public ObservableCollection<T> items { get; }

        public FormListView(ObservableCollection<T> initialItems = null) : base(ListViewCachingStrategy.RetainElement)
        {
            if (initialItems == null){
                items = new ObservableCollection<T>();
            }else{
                items = initialItems;
            }
            SeparatorVisibility = SeparatorVisibility.None;
            base.ItemsSource = items;
        }

        public void addItem(T newItem){
            items.Remove((T)((object)(Skill.NullSkill)));
            if (!items.Contains(newItem))
                {
                    items.Add(newItem);
                }
        }
    }

    internal class FormListCell : ViewCell{
        //Implement Later if necessary
    }
}
