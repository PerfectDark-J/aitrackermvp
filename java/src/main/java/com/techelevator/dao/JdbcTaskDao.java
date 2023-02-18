package com.techelevator.dao;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import com.techelevator.model.Task;

@Component
public class JdbcTaskDao implements TaskDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcTaskDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Task> getAllTasks() {
        List<Task> tasks = new ArrayList<>();
        String sql = "SELECT * FROM task";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while (results.next()) {
            Task task = mapRowToTask(results);
            tasks.add(task);
        }



        return tasks;
    }

    @Override
    public Task getTaskById(int id) {
        String sql = "SELECT * FROM tasks WHERE task_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
        if (results.next()) {
            return mapRowToTask(results);
        } else {
            return null;
        }
    }

    public Integer getTaskByTitle(String title) {
        String sql = "SELECT task.taskid " +
                "FROM task " +
                "INNER JOIN log ON task.userid = log.userid " +
                "WHERE log.content = ? AND task.tasktitle = ?";

        List<Integer> taskIds = jdbcTemplate.queryForList(sql, Integer.class, title, title);
        if (taskIds.isEmpty()) {
            return null;
        } else {
            return taskIds.get(0);
        }
    }




    //    public List<Task> getTaskByProjectId(int projectId) {
//        String sql = "SELECT * FROM task WHERE projectid = ?";
//        List<Task> taskList = new ArrayList<>();
//        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, projectId);
//        while (results.next()) {
//            Task task = mapRowToTask(results);
//            taskList.add(task);
//        }
//        return taskList;
//    }
    public Task getAllTasksByUserId(int userid) {
        String sql = "SELECT * FROM task WHERE userid = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userid);
        if (results.next()) {
            return mapRowToTask(results);
        } else {
            return null;
        }
    }

//    public List<Task> getAllTasksByProjectId(int projectId) {
//        String sql = "SELECT * FROM task WHERE projectid = ?";
//        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, projectId);
//        List<Task> tasks = new ArrayList<>();
//        while (results.next()) {
//            Task task = mapRowToTask(results);
//            tasks.add(task);
//        }
//        return tasks;
//    }

    public List<Task> getTasksForUserByProjectId(int userId, int projectId) {
        String sql = "SELECT task.* FROM task JOIN project ON task.projectid = project.projectid WHERE project.userid = ? AND project.projectid = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId, projectId);
        List<Task> tasks = new ArrayList<>();
        while (results.next()) {
            Task task = mapRowToTask(results);
            tasks.add(task);
        }
        return tasks;
    }

    public void addTask(Task task) {
        String sql = "INSERT INTO task (tasktitle, taskdescription, taskiscompleted, taskcompletiondate, userid, taskisrecurring, comment) VALUES (?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, task.getTasktitle(), task.getTaskdescription(), task.isTaskisCompleted(), task.getTaskcompletiondate(), task.getUserid(), task.isTaskisrecurring(), task.getComment() );
    }

//    public Task getAllTasksByTaskId(int userId, int projectId, int taskId) {
//        String sql = "SELECT * FROM tasks WHERE userid = ? AND projectid = ? AND taskid = ?";
//        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId, projectId, taskId);
//        if (results.next()) {
//            return mapRowToTask(results);
//        } else {
//            return null;
//        }
//    }


    @Override
    public void updateTask(Task task) {
        System.out.println(task.getId());
        System.out.println(task.getTasktitle());
        System.out.println(task.getTaskdescription());
        System.out.println(task.getUserid());
        System.out.println(task.isTaskisCompleted());
        System.out.println(task.isTaskisrecurring());
        //System.out.println(task.getTasks);

        String sql = "UPDATE task SET tasktitle = ?, taskdescription = ?, taskiscompleted = ?, taskcompletiondate = ?, userid = ?, comment = ?, taskisrecurring = ? WHERE taskid = ?";
        jdbcTemplate.update(sql, task.getTasktitle(), task.getTaskdescription(), task.isTaskisCompleted(), task.getTaskcompletiondate(), task.getUserid(), task.getComment(), task.isTaskisrecurring(), task.getId());
    }

    @Override
    public void deleteTask(int taskId) {
        String sql = "DELETE FROM task WHERE taskid = ?";
        jdbcTemplate.update(sql, taskId);
    }

    public void completeTask(int id) {
        String sql = "UPDATE task SET taskiscompleted = true, taskcompletiondate = current_timestamp, taskdescription = 'Task' WHERE taskid = ?";
        jdbcTemplate.update(sql, id);
    }

    public boolean isTaskCompleted(int taskId) {
        boolean isCompleted = false;

        // SQL query to check if the task is completed
        String sql = "SELECT taskiscompleted FROM task WHERE taskid = " + taskId;

        // Use JdbcTemplate to execute the query and retrieve the result
        isCompleted = jdbcTemplate.queryForObject(sql, Boolean.class);

        return isCompleted;
    }



    private Task mapRowToTask(SqlRowSet result) {
        Task task = new Task();
        task.setId(result.getInt("taskid"));
        task.setTasktitle(result.getString("tasktitle"));
        task.setTaskdescription(result.getString("taskdescription"));
        task.setTaskisCompleted(result.getBoolean("taskiscompleted"));
        task.setTaskcompletiondate(result.getString("taskcompletiondate"));
        task.setUserid(result.getInt("userid"));
        task.setComment(result.getString("comment"));

        return task;
    }
}

