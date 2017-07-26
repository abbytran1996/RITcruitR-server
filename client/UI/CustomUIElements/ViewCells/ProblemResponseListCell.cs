using System;
using Xamarin.Forms;
using TMCS_Client.DTOs;

namespace TMCS_Client.CustomUIElements.ViewCells
{
    public class ProblemResponseListCell : ViewCell
	{
        private Label lblProblemResponse;
        private Label lblTag;
        private Label lblProblemResponseTimeSubmitted;

        public ProblemResponseListCell()
        {
            AbsoluteLayout cellLayout = new AbsoluteLayout()
            {
                HeightRequest = Constants.Forms.Sizes.ROW_HEIGHT * 1.5,
                BackgroundColor = Color.FromHex("eaeff2"),
            };

            lblProblemResponse = new Label()
            {
                VerticalTextAlignment = TextAlignment.Start,
                HorizontalTextAlignment = TextAlignment.Start,
                LineBreakMode = LineBreakMode.WordWrap,
                FontSize = 20.0,
            };

            lblTag = new Label()
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
                                   new Rectangle(0.5, 0.0, 0.95, 0.75),
                                    AbsoluteLayoutFlags.All);
            cellLayout.Children.Add(lblTag,
                                    new Rectangle(0.05, 1.0, 0.475, 0.25),
								   AbsoluteLayoutFlags.All);
            cellLayout.Children.Add(lblProblemResponseTimeSubmitted,
                                   new Rectangle(0.95, 1.0, 0.475, 0.25),
                                   AbsoluteLayoutFlags.All);

            View = cellLayout;
        }

        protected override void OnBindingContextChanged()
        {
            base.OnBindingContextChanged();
            if ((BindingContext != null) && (((Match)BindingContext) == Match.EmptyMatch))
            {
                this.View = new AbsoluteLayout()
                {
                    HeightRequest = Constants.Forms.Sizes.ROW_HEIGHT,
                };
                ((AbsoluteLayout)this.View).Children.Add(new Label()
                {
                    Text = "No pending problem responses",
                    VerticalTextAlignment = TextAlignment.Center,
                    HorizontalTextAlignment = TextAlignment.Center,
                    FontSize = 22.0,
                }, new Rectangle(0.0, 0.0, 1.0, 1.0),
                                                         AbsoluteLayoutFlags.All);
            }else if(BindingContext != null){
                //TODO Truncate/convert these values
                lblProblemResponse.Text = ((Match)BindingContext).studentProblemResponse;
                lblTag.Text = "Tag: " + (((Match)BindingContext).tag == null?"":((Match)BindingContext).tag);
                lblProblemResponseTimeSubmitted.Text = ((Match)BindingContext).timeLastUpdated.ToString();
            }
        }
    }
}
