import axios from 'axios';
const http = axios.create({
    //baseURL: "https://ai-tracker.fly.dev/",
     baseURL: "http://localhost:9000"
  });
  
export default {

    verifyThroughEmail(email){
         return http.post('http://localhost:9000', email)
        return http.post('https://ai-tracker.fly.dev/user', email)
    },

    getUserByEmail(email){
         return http.get(`http://localhost:9000/user?email=${email}`)
        return http.get(`https://ai-tracker.fly.dev/user?email=${email}`)
    },

    //Projects

    //Manager
    getAllProjects() {
        return http.get('/projects');
    },

    //Employee
    getAllProjectsByUserId(userid){
        return http.get(`/projects/${userid}`)
    },

    //Individual Project Page
    getProjectByProjectId(projectid){
        return http.get(`/singleProject/${projectid}`)
    },
    
    addProject(project) {
        return http.post(`/projects`, project);
    },
    
    updateProject(id, project) {
        return http.put(`/editProject/${id}`, project);
    },

    deleteProject(id) {
        return http.delete(`/projects/${id}`);
    },

    //Tasks

    getAllTasks(){
        return http.get('/tasks')
    },
   
    getAllTasksByUserId(userId){
        return http.get(`/tasks/${userId}`)
    },

    getAllTasksByProjectId(projectId){
        return http.get(`/singletask/${projectId}`)
    },

    getAllTasksByTaskId(taskId){
        return http.get(`/task/${taskId}`)
    },
    
    addTask(task){
        return http.post('/task', task)
    },

    updateTask(task){
        return http.put(`/task`, task)
    },

    deleteTask(taskId){
        return http.delete(`task/${taskId}`)
    },

    //Worklogs
    
    // getAllReports(){
    //     return http.get('/worklog');
    // },

    // getAllReportsByUser(userId){
    //     return http.get(`/worklog/user/${userId}`)
    // },

    // getAllReportsForUserByProjectId(userid, projectid){
    //     return http.get(`/worklog/${userid}/${projectid}`)
    // },

    // getReportByReportId(reportId){
    //     return http.get(`/worklog/${reportId}`)
    // },

    createReport(report){
        return http.post('/log', report)
    },

    getLog(query){
        let timeframe = query.timeframe;
        let type = query.type; 
        let description = query.description; 
        let exercise = query.exercise; 
        return http.get('/logs', {params: {timeframe, type, description, exercise}})
    }

    // updateReport(reportId, report){
    //     return http.put(`/worklog/${reportId}`, report)
    // },

    // deleteReport(reportId){
    //     return http.delete(`/worklog/${reportId}`)
    // },
}