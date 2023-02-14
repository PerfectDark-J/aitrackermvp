package com.techelevator.dao;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import com.techelevator.model.Report;

@Component
public class JdbcReportDao implements ReportDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcReportDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    //Temp method

    public void createReport(Report report) {
        String sql = "INSERT INTO log (userid, content, type, date, description, exercise, reps, weight, minutes) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, report.getuserid(), report.getContent(), report.getType(),
                report.getDate(), report.getDescription(), report.getExercise(), report.getReps(),
                report.getWeight(), report.getMinutes());
    }






   /* @Override
    public void createReport(Report report) {
        String sqlInsertReport = "INSERT INTO reports (project_id, title, description, user_id) VALUES (?,?,?,?)";
        jdbcTemplate.update(sqlInsertReport, report.getProjectId(), report.getTitle(), report.getDescription(), report.getUserId());
    }*/

//    @Override
//    public Report getReportById(int reportId) {
//        String sql = "SELECT * FROM worklog WHERE logid = ?";
//        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, reportId);
//        if(results.next()) {
//            return mapRowToWorkLog(results);
//        } else {
//            return null;
//        }
//    }

//    @Override
//    public List<Report> getReportsByProjectId(int projectId) {
//        List<Report> reports = new ArrayList<>();
//        String sqlGetReportsByProjectId = "SELECT * FROM worklog WHERE projectid = ?";
//        SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetReportsByProjectId, projectId);
//        while (results.next()) {
//            Report report = mapRowToWorkLog(results);
//            reports.add(report);
//        }
//        return reports;
//    }

    public List<Report> getAllReportsByUser(int userId) {
        String sql = "SELECT * FROM log WHERE userid = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);

        List<Report> workLogs = new ArrayList<>();
        while (results.next()) {
            workLogs.add(mapRowToLog(results));
        }

        return workLogs;
    }

    public List<Report> getAllUserWorkouts(int userId) {
        String sql = "SELECT * FROM log WHERE userid = ? AND type = 'Workout'";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);

        List<Report> workLogs = new ArrayList<>();
        while (results.next()) {
            workLogs.add(mapRowToLog(results));
        }

        return workLogs;
    }

    public List<Report> getLogs(String timeframe, String type, String description, String exercise) {
        List<Object> params = new ArrayList<>();
        String sql = "SELECT * FROM log WHERE 1=1 ";

        if (!timeframe.isEmpty()) {
            if (timeframe.equalsIgnoreCase("today")) {
                sql += "AND date_trunc('day', date) = date_trunc('day', CURRENT_TIMESTAMP) ";
            } else if (timeframe.matches("\\d+")) {
                sql += "AND date >= (CURRENT_TIMESTAMP - interval '" + timeframe + " days') ";
            } else if (timeframe.equalsIgnoreCase("365")) {
                sql += "AND date >= (CURRENT_TIMESTAMP - interval '1 year') ";
            }
        }

        if (!type.isEmpty()) {
            sql += "AND type = ? ";
            params.add(type);
        }

        if (!description.isEmpty()) {
            sql += "AND description = ? ";
            params.add(description);
        }

        if (!exercise.isEmpty()) {
            sql += "AND exercise = ? ";
            params.add(exercise);
        }

        List<Report> reports = new ArrayList<>();
        SqlRowSet rows = jdbcTemplate.queryForRowSet(sql, params.toArray());
        while (rows.next()) {
            Report report = new Report();
            report.setId(rows.getInt("id"));
            report.setuserid(rows.getInt("userid"));
            report.setContent(rows.getString("content"));
            report.setType(rows.getString("type"));
            report.setDate(rows.getTimestamp("date"));
            report.setDescription(rows.getString("description"));
            report.setExercise(rows.getString("exercise"));
            report.setReps(rows.getInt("reps"));
            report.setWeight(rows.getInt("weight"));
            report.setMinutes(rows.getInt("minutes"));
            reports.add(report);
        }

        return reports;
    }



//    public List<Report> getAllReportsForUserByProjectId(int userId, int projectId) {
//        List<Report> reports = new ArrayList<>();
//        String sql = "SELECT * FROM worklog WHERE userid = ? AND projectid = ?";
//        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId, projectId);
//        while (results.next()) {
//            reports.add(mapRowToWorkLog(results));
//        }
//        return reports;
//    }




//    @Override
//    public List<Report> getAllReports() {
//        String sql = "SELECT w.*, p.projecttitle FROM worklog w JOIN project p ON w.projectid = p.projectid";
//        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
//        List<Report> reportList = new ArrayList<>();
//        while (results.next()) {
//            Report report = mapRowToWorkLog(results);
//            report.setProjecttitle(results.getString("projecttitle"));
//            reportList.add(report);
//        }
//        return reportList;
//    }


//    public void updateReport(int reportId, Report report) {
//        String sql = "UPDATE worklog SET clockin = ?, clockout = ?, projectid = ? WHERE logid = ?";
//        jdbcTemplate.update(sql, report.getClockin(), report.getClockout(), report.getProjectid(), reportId);
//    }
//
//
//    public void deleteReport(int reportId) {
//        String sql = "DELETE FROM worklog WHERE logid = ?";
//        jdbcTemplate.update(sql, reportId);
//    }


    private Report mapRowToLog(SqlRowSet results) {
        Report report = new Report();
        //report.setId(results.getInt("id"));
        report.setContent(results.getString("content"));
        report.setType(results.getString("type"));
        report.setDate(results.getTimestamp("date"));
        report.setuserid(results.getInt("userid"));
        report.setDescription(results.getString("description"));
        report.setExercise(results.getString("exercise"));
        report.setReps(results.getInt("reps"));
        report.setWeight(results.getInt("weight"));
        report.setMinutes(results.getInt("minutes"));
        return report;
    }



}
