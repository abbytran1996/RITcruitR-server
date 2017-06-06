using System;
using Xamarin.Forms;

namespace TMCS_Client.CustomUIElements.ViewCells
{
    public class JobPostingListCell : ViewCell
	{
        //TODO Refector to be more like ProblemResponseListCell
		public static readonly BindableProperty JobPostingIDProperty =
			BindableProperty.Create("jobPostingID", typeof(long), typeof(JobPostingListCell), -1L);

		public long jobPostingID
		{
			get { return (long)GetValue(JobPostingIDProperty); }
			set { SetValue(JobPostingIDProperty, value); }
		}

        public JobPostingListCell()
        {
            AbsoluteLayout cellLayout = new AbsoluteLayout()
            {
                HeightRequest = Constants.Forms.Sizes.ROW_HEIGHT,
                BackgroundColor = Color.White,
            };

            Label jobPostingPositionTitle = new Label()
            {
                VerticalTextAlignment = TextAlignment.End,
                HorizontalTextAlignment = TextAlignment.Start,
                FontSize = 20.0,
            };

            Label jobPostingLocation = new Label()
            {
                VerticalTextAlignment = TextAlignment.Start,
                HorizontalTextAlignment = TextAlignment.Start,
                FontSize = 14.0,
            };

            Label jobPostingNewApplicants = new Label()
            {
                VerticalTextAlignment = TextAlignment.Center,
                HorizontalTextAlignment = TextAlignment.End,
                FontSize = 18.0,
                TextColor = Color.Red,
                Text = "400 new",//Change to binding later
            };

            cellLayout.Children.Add(jobPostingPositionTitle,
                                   new Rectangle(16.0, 0.0, 0.7, 0.5),
                                    AbsoluteLayoutFlags.SizeProportional |
                                   AbsoluteLayoutFlags.YProportional);
            cellLayout.Children.Add(jobPostingLocation,
                                    new Rectangle(16.0,1.0,0.7,0.5),
								   AbsoluteLayoutFlags.SizeProportional |
								   AbsoluteLayoutFlags.YProportional);
            cellLayout.Children.Add(jobPostingNewApplicants,
                                   new Rectangle(0.9,0.0,0.2,1.0),
                                   AbsoluteLayoutFlags.All);

            jobPostingPositionTitle.SetBinding(Label.TextProperty, "positionTitle");
            jobPostingLocation.SetBinding(Label.TextProperty, "location");

            this.SetBinding(JobPostingListCell.JobPostingIDProperty,"id");

            View = cellLayout;
        }

        protected override void OnBindingContextChanged()
        {
            base.OnBindingContextChanged();
            if (jobPostingID == -1)
            {
                this.View = new AbsoluteLayout()
                {
                    HeightRequest = Constants.Forms.Sizes.ROW_HEIGHT,
                };
                ((AbsoluteLayout)this.View).Children.Add(new Label()
                {
                    Text = "No job postings yet.",
                    VerticalTextAlignment = TextAlignment.Center,
                    HorizontalTextAlignment = TextAlignment.Center,
                    FontSize = 22.0,
                }, new Rectangle(0.0, 0.0, 1.0, 1.0),
                                                         AbsoluteLayoutFlags.All);
            }
        }
    }
}
