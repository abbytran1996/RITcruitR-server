using System;
using RestSharp;
using System.Collections.Generic;
using System.Text;
using TMCS_Client.DTOs;
using TMCS_Client.UI;

namespace TMCS_Client.ServerComms
{
	/// <summary>
	/// Allows someone to interact with the server's Company API
	/// </summary>
	class CompanyComms
	{
        RestClient client = (App.Current as App).Server;
		/// <summary>
		/// Adds the company to the server
		/// </summary>
		/// <param name="company"></param>
		/// <returns>The status of the response</returns>
		/// <exception cref="RestException">Throws a RestException when the server doesn't return a success</exception>
		public void addCompany(NewCompany company){
			var request = new RestRequest(Constants.Company.ADD_COMPANY_RESOURCE, Method.POST);
			request.RequestFormat = DataFormat.Json;
			request.AddBody(company);

			var response = client.Execute(request);
			if (response.StatusCode != System.Net.HttpStatusCode.Created)
			{
				if (response.ErrorException != null)
				{
					throw response.ErrorException;
				}
				throw new RestException(response.StatusCode);
			}
		}
        ///<summary>
		/// gets the company by their ID
		/// </summary>
		/// <param name="id"></param>
		/// <returns>The status of the response</returns>
		public Company getCompanyById(long id)
		{
            string url = Constants.Company.GET_COMPANY_RESOURCE;
			url.Replace("{id}", id.ToString());
			var request = new RestRequest(url, Method.GET);

            var response = client.Execute<Company>(request);

			return response.Data;
		}

		///<summary>
		/// gets the company by their emailSuffix
		/// </summary>
		/// <param name="emailSuffix"></param>
		/// <returns>The status of the response</returns>
		public Company getCompanyByEmailSuffix(string emailSuffix)
		{
            string url = Constants.CompanyEmailSuffix.COMPANYEMAILSUFFIX;
            url.Replace("email_suffix", emailSuffix);
			var request = new RestRequest(url, Method.GET);
			var response = client.Execute<Company>(request);

			return response.Data;
		}
	}
}

