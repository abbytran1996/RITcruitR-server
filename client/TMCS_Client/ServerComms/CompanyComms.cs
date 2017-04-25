using System;
using RestSharp;
using System.Collections.Generic;
using System.Text;
using TMCS_Client.DTOs;
                

namespace TMCS_Client.ServerComms
{
	/// <summary>
	/// Allows someone to interact with the server's Company API
	/// </summary>
	class CompanyComms
	{
		RestClient client = new RestClient(Constants.SERVER_URL);
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
	}
}

