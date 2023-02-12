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
        String sql = "INSERT INTO task (tasktitle, taskdescription, taskiscompleted, taskcompletiondate, userid) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, task.getTasktitle(), task.getTaskdescription(), task.isTasksscompleted(), task.getTaskcompletiondate(), task.getUserid());
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
        String sql = "UPDATE task SET tasktitle = ?, taskdescription = ?, taskiscompleted = ?, taskcompletiondate = ?, userid = ? WHERE taskid = ?";
        jdbcTemplate.update(sql, task.getTasktitle(), task.getTaskdescription(), task.isTasksscompleted(), task.getTaskcompletiondate(), task.getUserid(), task.getId());
    }

    @Override
    public void deleteTask(int taskId) {
        String sql = "DELETE FROM task WHERE taskid = ?";
        jdbcTemplate.update(sql, taskId);
    }

    private Task mapRowToTask(SqlRowSet result) {
        Task task = new Task();
        task.setId(result.getInt("taskid"));
        task.setTasktitle(result.getString("tasktitle"));
        task.setTaskdescription(result.getString("taskdescription"));
        task.setTasksscompleted(result.getBoolean("taskiscompleted"));
        task.setTaskcompletiondate(result.getString("taskcompletiondate"));
        task.setUserid(result.getInt("userid"));
        return task;
    }
}

