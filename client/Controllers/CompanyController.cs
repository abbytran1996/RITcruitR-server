using RestSharp;
using System;
using System.Collections.Generic;
using System.Net;
using System.Text;
using TMCS_Client.DTOs;
using TMCS_Client.ServerComms;
using TMCS_Client.UI;
using Xamarin.Forms;

namespace TMCS_Client.Controllers
{
    public class CompanyController : ServerCommsBase
    {

        private static CompanyController companyController = null;

        private CompanyController() { }

        public static CompanyController getCompanyController()
        {
            if(companyController == null)
            {
                companyController = new CompanyController();
            }

            return companyController;
        }

        public void addCompany(NewCompany company)
        {
            var request = new RestRequest(Constants.Company.ADD_COMPANY_RESOURCE, Method.POST);
            request.RequestFormat = DataFormat.Json;
            request.AddBody(company);

            var response = client.Execute(request);
            ensureStatusCode(response, HttpStatusCode.OK);
        }

        public Company getCompanyById(long id)
        {
            string url = Constants.Company.GET_COMPANY_BY_ID_RESOURCE;
            url.Replace("id", id.ToString());
            var request = new RestRequest(url, Method.GET);

            var response = client.Execute<Company>(request);

            return response.Data;
        }

        public Company getCompanyByEmailSuffix(string emailSuffix)
        {
            string url = Constants.Company.GET_COMPANY_BY_SUFFIX_RESOURCE;
            url = url.Replace("emailSuffix", emailSuffix);
            var request = new RestRequest(url, Method.GET);
            var response = client.Execute<Company>(request);

            return response.Data;
        }

        public Company getCompanyByName(string companyName)
        {
            string url = Constants.Company.GET_COMPANY_BY_NAME;
            url = url.Replace("companyName", companyName);
            var request = new RestRequest(url, Method.GET);
            var response = client.Execute<Company>(request);

            return response.Data;
        }

        internal void updateCompany(Company company)
        {
            Console.WriteLine("Updating company");

            string url = Constants.Company.UPDATE_COMPANY_RESORUCE;
            url = url.Replace("{id}", company.id.ToString());

            var request = new RestRequest(url, Method.PUT);
            request.AddJsonBody(company);

            var response = client.Execute<Company>(request);

            ensureStatusCode(response, HttpStatusCode.OK);
        }
    }
}
