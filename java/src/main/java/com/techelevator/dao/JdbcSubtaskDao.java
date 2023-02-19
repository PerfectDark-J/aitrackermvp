package com.techelevator.dao;

import java.util.ArrayList;
import java.util.List;

import com.techelevator.model.Subtask;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class JdbcSubtaskDao {

    private JdbcTemplate jdbcTemplate;

    public JdbcSubtaskDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

//    public JdbcSubtaskDao(JdbcTemplate jdbcTemplate) {
//        this.jdbcTemplate = jdbcTemplate;
//    }

    public List<Subtask> getAllSubtasks() {
        String sql = "SELECT * FROM subtask";
        List<Subtask> subtasks = new ArrayList<>();
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while (results.next()) {
            subtasks.add(mapRowToSubtask(results));
        }
        return subtasks;
    }

    public List<Subtask> getAllSubtasksByTaskId(int taskid) {
        String sql = "SELECT * FROM subtask WHERE taskid = ?";
        List<Subtask> subtasks = new ArrayList<>();
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, taskid);
        while (results.next()) {
            subtasks.add(mapRowToSubtask(results));
        }
        return subtasks;
    }


    public void addSubtask(Subtask subtask) {
        System.out.println(subtask.getTaskid());
        System.out.println(subtask.getSubtitle());
        System.out.println(subtask.isIscompleted());

        String sql = "INSERT INTO subtask (taskid, subtitle, iscompleted) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, subtask.getTaskid(), subtask.getSubtitle(), subtask.isIscompleted());
    }

    public void updateSubtask(Subtask subtask) {
        String sql = "UPDATE subtask SET taskid = ?, subtitle = ?, iscompleted = ? WHERE subtaskid = ?";
        jdbcTemplate.update(sql, subtask.getTaskid(), subtask.getSubtitle(), subtask.isIscompleted(), subtask.getSubtaskid());
    }

    public void deleteSubtask(int subtaskid) {
        String sql = "DELETE FROM subtask WHERE subtaskid = ?";
        jdbcTemplate.update(sql, subtaskid);
    }


    private Subtask mapRowToSubtask(SqlRowSet result) {
        Subtask subtask = new Subtask();
        subtask.setSubtaskid(result.getInt("subtaskid"));
        subtask.setTaskid(result.getInt("taskid"));
        subtask.setSubtitle(result.getString("subtitle"));
        subtask.setIscompleted(result.getBoolean("iscompleted"));
        return subtask;
    }

}

