using System;
using System.Collections.ObjectModel;
using Xamarin.Forms;
using System.IO;

namespace TMCS_Client.UI
{
    public class FilePickerAndroid : ContentPage
    {
        private ListView directoryListingView;
        private ObservableCollection<String> directoryListing;

        private AbsoluteLayout pageContent;

        public delegate void PickedFileHandler(object sender, PickedFileEventArgs e);
        public event PickedFileHandler PickedFileEvent;

        public FilePickerAndroid()
        {
            Title = "Resume Upload";

            pageContent = new AbsoluteLayout();

            //Diretory Listing
            directoryListing = new ObservableCollection<String>();

            directoryListingView = new ListView(ListViewCachingStrategy.RetainElement);
            directoryListingView.ItemsSource = directoryListing;
            directoryListingView.SeparatorVisibility = SeparatorVisibility.None;
            directoryListingView.Header = "";
            directoryListingView.ItemTapped += (object sender, ItemTappedEventArgs e) => {
                if(((string)e.Item).EndsWith(".pdf",StringComparison.CurrentCultureIgnoreCase))
                {
                    PickedFileEvent(this, new PickedFileEventArgs(directoryListingView.Header + ((string)e.Item)));
				}
				else
				{
					refreshDirectory(e.Item.ToString());
                }
            };
            refreshDirectory("/");

            pageContent.Children.Add(directoryListingView,
									new Rectangle(0.0, 0.0, 1.0, 1.0),
									AbsoluteLayoutFlags.All);

            Content = pageContent;
        }

        private void refreshDirectory(string newDir){
            if(directoryListing != null){
                if (newDir == "..")
                {
                    newDir =
                        ((string)directoryListingView.Header).Substring(0,
                        ((string)directoryListingView.Header).LastIndexOf('/',
                        ((string)directoryListingView.Header).Length - 2) + 1);
                }
                else
                {
                    newDir = directoryListingView.Header + newDir;
                }

                try{
                    Directory.GetDirectories(newDir);
                }catch(UnauthorizedAccessException uae){
                    DisplayAlert("Unauthorized Access Error","Access to " + newDir + " denied.","Return");
                    directoryListingView.SelectedItem = null;
                    return;
                }
                directoryListingView.Header = newDir;
                directoryListing.Clear();
                if (newDir != "/")
                {
                    directoryListing.Add("..");
                }
                foreach (string dir in Directory.GetDirectories(newDir))
				{
                    directoryListing.Add(dir.Remove(0,newDir.Length) + "/");
				}
				foreach (string file in Directory.GetFiles(newDir))
				{
					if (file.EndsWith(".pdf", StringComparison.CurrentCultureIgnoreCase))
					{
						directoryListing.Add(file.Remove(0, newDir.Length));
					}
				}
            }
        }
    }

    public class PickedFileEventArgs : EventArgs
    {
        public string selectedFilePath { get; set; }

        public PickedFileEventArgs(string selectedFilePath){
            this.selectedFilePath = selectedFilePath;
        }
    }
}
