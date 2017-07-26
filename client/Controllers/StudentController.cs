﻿using RestSharp;
using System;
using System.Collections.Generic;
using System.Net;
using System.Text;
using TMCS_Client.DTOs;
using TMCS_Client.ServerComms;

namespace TMCS_Client.Controllers
{
    public class StudentController : ServerCommsBase
    {
        private static StudentController studentController = null;

        private StudentController() { }

        public static StudentController getStudentController()
        {
            if(studentController == null)
            {
                studentController = new StudentController();
            }
            return studentController;
        }

        public Student getStudent(String email)
        {
            var request = new RestRequest(Constants.Students.GET_STUDENT_BY_EMAIL_RESOURCE, Method.GET);
            request.AddUrlSegment("email", email);
            request.RequestFormat = DataFormat.Json;

            var response = client.Execute<Student>(request);
            ensureStatusCode(response, HttpStatusCode.OK);

            return response.Data;
        }

        public Student addStudent(NewStudent newStudent)
        {
            var request = new RestRequest(Constants.Students.ADD_STUDENT_RESOURCE, Method.POST);

            request.RequestFormat = DataFormat.Json;
            request.AddBody(newStudent);

            var response = client.Execute<Student>(request);
            ensureStatusCode(response, HttpStatusCode.Created);
            return response.Data;
        }

        public Student updateSkillsForStudent(Student student)
        {
            var request = new RestRequest(Constants.Students.ADD_SKILLS_RESOURCE, Method.POST);
            request.AddUrlSegment("id", student.id.ToString());
            request.RequestFormat = DataFormat.Json;
            request.AddBody(student.skills);

            var response = client.Execute<Student>(request);
            ensureStatusCode(response, HttpStatusCode.OK);

            return response.Data;
        }

        public void uploadResume(long id, Resume resume)
        {
            var request = new RestRequest(Constants.Students.UPLOAD_RESUME, Method.PUT);
            request.AddUrlSegment("id", id.ToString());
            request.RequestFormat = DataFormat.Json;
            request.AddBody(resume);

            var response = client.Execute(request);
            ensureStatusCode(response, HttpStatusCode.OK);
        }

        public void updateStudent(Student student)
        {
            var request = new RestRequest(Constants.Students.UPDATE_STUDENT, Method.PUT);
            request.AddUrlSegment("id", student.id.ToString());
            request.RequestFormat = DataFormat.Json;
            request.AddBody(student);

            var response = client.Execute(request);
            ensureStatusCode(response, HttpStatusCode.OK);
        }

        public byte[] getResumeforStudent(long id)
        {
            var request = new RestRequest(Constants.Students.DOWNLOAD_RESUME, Method.GET);
            request.AddUrlSegment("id", id.ToString());
            request.RequestFormat = DataFormat.Json;

            var response = client.DownloadData(request);

            return response;

        }
    }
}
