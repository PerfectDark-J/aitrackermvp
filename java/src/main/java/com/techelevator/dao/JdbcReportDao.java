package com.techelevator.dao;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
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
        String sql = "INSERT INTO log (title, userid, content, type, date, description, exercise, reps, weight, minutes, earnedpoints, bounty, taskid) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, report.getTitle(), report.getuserid(), report.getContent(), report.getType(),
                report.getDate(), report.getDescription(), report.getExercise(), report.getReps(),
                report.getWeight(), report.getMinutes(), report.getEarnedpoints(), report.getBounty(), report.getTaskid());
    }



    public int getMostRecentTaskId() {
        String sql = "SELECT MAX(taskid) FROM task WHERE taskiscompleted = false";
        return jdbcTemplate.queryForObject(sql, Integer.class);
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

    public int getPoints(int timeframe) {
        String sql = "SELECT SUM(earnedpoints) FROM log WHERE date >= ? AND date <= ?";

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -timeframe);
        Timestamp startDate = new Timestamp(cal.getTimeInMillis());

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, startDate, new Timestamp(System.currentTimeMillis()));

        if (results.next()) {
            return results.getInt(1);
        } else {
            return 0;
        }
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

    public Report getReportByTaskId(int taskid) {
        String sql = "SELECT * FROM log WHERE taskid = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, taskid);
        if (results.next()) {
            return mapRowToLog(results);
        } else {
            return null;
        }
    }

    public List<Report> getLogs(String timeframe, String type, String description, String exercise) {
        System.out.println("Timeframe: " + timeframe);
        System.out.println("Type: " + type);
        System.out.println("Desc: " + description);
        System.out.println("Exercise: " + exercise);


        List<Object> params = new ArrayList<>();
        String sql = "SELECT * FROM log ";

        if (!timeframe.isEmpty() || !type.isEmpty() || !description.isEmpty() || !exercise.isEmpty()) {
            sql += "WHERE 1=1 ";
        }

        if (!timeframe.isEmpty() && !timeframe.equalsIgnoreCase("empty")) {
            if (timeframe.equalsIgnoreCase("today")) {
                sql += "AND date_trunc('day', date) = date_trunc('day', CURRENT_DATE) ";
            } else if (timeframe.matches("\\d+")) {
                sql += "AND date >= (CURRENT_DATE - interval '" + timeframe + " days') ";
            } else if (timeframe.equalsIgnoreCase("365")) {
                sql += "AND date >= (CURRENT_DATE - interval '1 year') ";
            }
        }

        if (!type.isEmpty() && !type.equalsIgnoreCase("empty")) {
            sql += "AND type = ? ";
            params.add(type);
        }

        if (!description.isEmpty() && !description.equalsIgnoreCase("empty")) {
            sql += "AND description = ? ";
            params.add(description);
        }

        if (!exercise.isEmpty() && !exercise.equalsIgnoreCase("empty")) {
            sql += "AND exercise = ? ";
            params.add(exercise);
        }

        System.out.println(sql);

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
            report.setTitle(rows.getString("title"));
            report.setTaskid(rows.getInt("taskid"));
            report.setEarnedpoints(rows.getInt("earnedpoints"));
            report.setBounty(rows.getInt("bounty"));
            reports.add(report);
        }

//        if (reports.isEmpty()) {
//            sql = "SELECT * FROM log";
//            rows = jdbcTemplate.queryForRowSet(sql);
//            while (rows.next()) {
//                Report report = new Report();
//                report.setId(rows.getInt("id"));
//                report.setuserid(rows.getInt("userid"));
//                report.setContent(rows.getString("content"));
//                report.setType(rows.getString("type"));
//                report.setDate(rows.getTimestamp("date"));
//                report.setDescription(rows.getString("description"));
//                report.setExercise(rows.getString("exercise"));
//                report.setReps(rows.getInt("reps"));
//                report.setWeight(rows.getInt("weight"));
//                report.setMinutes(rows.getInt("minutes"));
//                reports.add(report);
//            }
//        }

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

    public void updateLog(Report log) {
        System.out.println("id: " + log.getId());
        System.out.println("bounty: " + log.getBounty());
        System.out.println("earned: " + log.getEarnedpoints());

        String sql = "UPDATE log SET content = ?, type = ?, date = ?, userid = ?, description = ?, exercise = ?, reps = ?, weight = ?, minutes = ?, title = ?, taskid = ?, earnedpoints = ?, bounty = ? WHERE id = ?";
        jdbcTemplate.update(sql, log.getContent(), log.getType(), log.getDate(), log.getuserid(), log.getDescription(), log.getExercise(), log.getReps(), log.getWeight(), log.getMinutes(), log.getTitle(), log.getTaskid(), log.getEarnedpoints(), log.getBounty(), log.getId());
    }




    //
    public void deleteReport(int logId) {
        String sql = "DELETE FROM log WHERE id = ?";
        jdbcTemplate.update(sql, logId);
    }


    private Report mapRowToLog(SqlRowSet results) {
        Report report = new Report();
        report.setId(results.getInt("id"));
        report.setContent(results.getString("content"));
        report.setType(results.getString("type"));
        report.setDate(results.getTimestamp("date"));
        report.setuserid(results.getInt("userid"));
        report.setDescription(results.getString("description"));
        report.setExercise(results.getString("exercise"));
        report.setReps(results.getInt("reps"));
        report.setWeight(results.getInt("weight"));
        report.setMinutes(results.getInt("minutes"));
        report.setTitle(results.getString("title"));
        report.setTaskid(results.getInt("taskid"));
        report.setEarnedpoints(results.getInt("earnedpoints"));
        report.setBounty(results.getInt("bounty"));
        return report;
    }



}
