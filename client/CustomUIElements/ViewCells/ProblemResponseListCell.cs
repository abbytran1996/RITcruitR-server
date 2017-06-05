using System;
using Xamarin.Forms;
using TMCS_Client.DTOs;

namespace TMCS_Client.CustomUIElements.ViewCells
{
    public class ProblemResponseListCell : ViewCell
	{
		public static readonly BindableProperty MatchIDProperty =
			BindableProperty.Create("matchID", typeof(long), typeof(ProblemResponseListCell), -1L);

		public static readonly BindableProperty ProblemResponseProperty =
			BindableProperty.Create("problemResponse", typeof(String), typeof(ProblemResponseListCell), "");

		public static readonly BindableProperty ProblemResponseReferenceProperty =
			BindableProperty.Create("problemResponseReference", typeof(long), typeof(ProblemResponseListCell), -1L);

		public static readonly BindableProperty ProblemResponseTimeSubmittedProperty =
			BindableProperty.Create("problemResponseTimeSubmitted", typeof(DateTime), typeof(ProblemResponseListCell), DateTime.Now);

		public long matchID
		{
			get { return (long)GetValue(MatchIDProperty); }
			set { SetValue(MatchIDProperty, value); }
		}

		public String problemResponse
		{
			get { return (String)GetValue(ProblemResponseProperty); }
			set { SetValue(ProblemResponseProperty, value); }
		}

		public long problemResponseReference
		{
			get { return (long)GetValue(ProblemResponseReferenceProperty); }
			set { SetValue(ProblemResponseReferenceProperty, value); }
		}

		public DateTime problemResponseTimeSubmitted
		{
			get { return (DateTime)GetValue(ProblemResponseTimeSubmittedProperty); }
			set { SetValue(ProblemResponseTimeSubmittedProperty, value); }
		}

        private Label lblProblemResponse;
        private Label lblProblemResponseReference;
        private Label lblProblemResponseTimeSubmitted;

        public ProblemResponseListCell()
        {
            AbsoluteLayout cellLayout = new AbsoluteLayout()
            {
                HeightRequest = Constants.Forms.Sizes.ROW_HEIGHT,
                BackgroundColor = Color.White,
            };

            lblProblemResponse = new Label()
            {
                VerticalTextAlignment = TextAlignment.Start,
                HorizontalTextAlignment = TextAlignment.Start,
                FontSize = 14.0,
            };

            lblProblemResponseReference = new Label()
            {
                VerticalTextAlignment = TextAlignment.Center,
                HorizontalTextAlignment = TextAlignment.Start,
                FontSize = 12.0,
            };

            lblProblemResponseTimeSubmitted = new Label()
            {
                VerticalTextAlignment = TextAlignment.Center,
                HorizontalTextAlignment = TextAlignment.End,
                FontSize = 12.0,
                TextColor = Color.Gray
            };

            cellLayout.Children.Add(lblProblemResponse,
                                   new Rectangle(0.5, 0.0, 0.95, 0.8),
                                    AbsoluteLayoutFlags.All);
            cellLayout.Children.Add(lblProblemResponseReference,
                                    new Rectangle(0.5, 1.0, 0.95, 0.2),
								   AbsoluteLayoutFlags.All);
            cellLayout.Children.Add(lblProblemResponseTimeSubmitted,
                                   new Rectangle(0.5, 1.0, 0.95, 0.2),
                                   AbsoluteLayoutFlags.All);

			
			this.SetBinding(ProblemResponseListCell.MatchIDProperty, "id");
            //this.SetBinding(ProblemResponseListCell.ProblemResponseProperty, "studentProblemResponse"); //TODO check the variable name in the string
            //this.SetBinding(ProblemResponseListCell.ProblemResponseReferenceProperty, ""); //TODO check the variable name in the string
			//this.SetBinding(ProblemResponseListCell.ProblemResponseTimeSubmittedProperty, "timeLastUpdated"); //TODO check the variable name in the string

            View = cellLayout;
        }

        protected override void OnBindingContextChanged()
        {
            base.OnBindingContextChanged();
            if (matchID == -1)
            {
                this.View = new AbsoluteLayout()
                {
                    HeightRequest = Constants.Forms.Sizes.ROW_HEIGHT,
                };
                ((AbsoluteLayout)this.View).Children.Add(new Label()
                {
                    Text = "No problem responses submitted yet",
                    VerticalTextAlignment = TextAlignment.Center,
                    HorizontalTextAlignment = TextAlignment.Center,
                    FontSize = 22.0,
                }, new Rectangle(0.0, 0.0, 1.0, 1.0),
                                                         AbsoluteLayoutFlags.All);
            }else{
                //TODO Truncate/convert these values
                lblProblemResponse.Text = problemResponse;
                lblProblemResponseReference.Text = "Reference #" + problemResponseReference.ToString();
                lblProblemResponseTimeSubmitted.Text = problemResponseTimeSubmitted.ToString();
            }
        }
    }
}
