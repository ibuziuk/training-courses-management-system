package org.exadel.training.service;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.exadel.training.model.*;
import org.exadel.training.utils.ExcelUtil;
import org.exadel.training.utils.StatisticsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class StatisticsService {
    private final int WEEKLY = 1;
    private final int REJECT = 1 << 1;
    private final int IN_WAITING_LIST = 1 << 2;
    private final int VISITED = 1 << 3;
    private final int SUBSCRIBED = 1 << 4;
    private final int AS_TRAINER = 1 << 5;

    @Autowired
    private UserService userService;
    @Autowired
    private TrainingService trainingService;
    @Autowired
    private EmployeeFeedbackService employeeFeedbackService;
    @Autowired
    private AbsenceService absenceService;
    @Autowired
    private AbsenceLessonService absenceLessonService;

    public Workbook getUsersStatisticsInExcel(long userId, long fromDate, long toDate, int bitMask) {
        if (toDate == 0)
            toDate = Long.MAX_VALUE;
        Workbook book = new HSSFWorkbook();
        Sheet sheet = book.createSheet("Statistics");
        Row headerRow = sheet.createRow(0);
        int freeRow = 1;
        int freeCell = 0;

        Map<String, CellStyle> styles = ExcelUtil.createStyles(book);

        List<User> allUsers = null;
        if (userId == 0) {
            allUsers = userService.getAllUsers();
            Collections.sort(allUsers, new Comparator<User>() {
                @Override
                public int compare(User o1, User o2) {
                    return o1.getLastName().compareTo(o2.getLastName());
                }
            });
        } else {
            allUsers = new ArrayList<User>();
            User user = userService.getUserById(userId);
            if (user != null)
                allUsers.add(user);
        }

        int columnCounter = 0;
        int userColumn = -1;
        int statusColumn = -1;
        int titleColumn = -1;
        int dateColumn = -1;
        int trainerColumn = -1;
        int absenceColumn = -1;
        int goofFeedbackColumn = -1;
        int badFeedbackColumn = -1;

        Cell titleCell = headerRow.createCell(freeCell++);
        titleCell.setCellStyle(styles.get("title"));
        titleCell.setCellValue("User");
        userColumn = columnCounter++;

        titleCell = headerRow.createCell(freeCell++);
        titleCell.setCellStyle(styles.get("title"));
        titleCell.setCellValue("Training status");
        statusColumn = columnCounter++;

        titleCell = headerRow.createCell(freeCell++);
        titleCell.setCellStyle(styles.get("title"));
        titleCell.setCellValue("aaaaaaaaaaaaaaaaaaaaaaaaa");
        titleColumn = columnCounter++;
        sheet.autoSizeColumn(titleColumn);
        titleCell.setCellValue("Training title");

        titleCell = headerRow.createCell(freeCell++);
        titleCell.setCellStyle(styles.get("title"));
        titleCell.setCellValue("Training Date");
        dateColumn = columnCounter++;

        titleCell = headerRow.createCell(freeCell++);
        titleCell.setCellStyle(styles.get("title"));
        titleCell.setCellValue("Trainer");
        trainerColumn = columnCounter++;

        titleCell = headerRow.createCell(freeCell++);
        titleCell.setCellStyle(styles.get("title"));
        titleCell.setCellValue("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        absenceColumn = columnCounter++;
        sheet.autoSizeColumn(absenceColumn);
        titleCell.setCellValue("Absence reason");

        titleCell = headerRow.createCell(freeCell++);
        titleCell.setCellStyle(styles.get("title"));
        titleCell.setCellValue("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        goofFeedbackColumn = columnCounter++;
        sheet.autoSizeColumn(goofFeedbackColumn);
        titleCell.setCellValue("Good feedbacks");


        titleCell = headerRow.createCell(freeCell++);
        titleCell.setCellStyle(styles.get("title"));
        titleCell.setCellValue("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        badFeedbackColumn = columnCounter++;
        sheet.autoSizeColumn(badFeedbackColumn);
        titleCell.setCellValue("Bad feedbacks");


        Date date = new Date();
        for (User user : allUsers) {

            List<Training> subscribedTrainigs = new ArrayList<>();
            List<Training> visitedTrainigs = new ArrayList<>();
            List<Training> weeklyTrainigs = new ArrayList<>();
            List<Training> asTrainer = trainingService.getTrainingsByTrainer(userId);
            List<Training> trainings = trainingService.getTrainingsByVisitor(user.getUserId());

            Date curentDate = new Date();
            for (Training training : trainings) {
                if (!training.isRegular()) {
                    if (training.getDate().after(new Timestamp(fromDate)) && training.getDate().before(new Timestamp(toDate))) {
                        if (curentDate.before(training.getDate())) {
                            subscribedTrainigs.add(training);
                        } else {
                            visitedTrainigs.add(training);
                        }
                    }
                } else {
                    weeklyTrainigs.add(training);
                }
            }

            List<Training> waitingListTrainigs = new ArrayList<Training>();
            for (WaitingList waitingList : user.getWaiting()) {
                Training training = waitingList.getTraining();
                if (!training.isRegular()) {
                    if (training.getDate().after(new Timestamp(fromDate)) && training.getDate().before(new Timestamp(toDate))) {
                        waitingListTrainigs.add(training);
                    }
                }
            }

            List<Training> exTrainngs = new ArrayList<Training>();

            for (Training training : userService.getExTrainings(user.getUserId())) {
                if (!training.isRegular()) {
                    if (training.getDate().after(new Timestamp(fromDate)) && training.getDate().before(new Timestamp(toDate))) {
                        exTrainngs.add(training);
                    }
                }
            }

            if ((bitMask & SUBSCRIBED) == SUBSCRIBED) {
                for (Training training : subscribedTrainigs) {
                    Row row = sheet.createRow(freeRow++);
                    setTrainingRow(row, styles.get("infoStyle"), training, user);
                    Cell cell = row.createCell(1);
                    cell.setCellStyle(styles.get("infoStyle"));
                    cell.setCellValue("Subscribed");
                }
            }

            if ((bitMask & VISITED) == VISITED) {
                for (Training training : visitedTrainigs) {
                    Row row = sheet.createRow(freeRow++);
                    setTrainingRow(row, styles.get("infoStyle"), training, user);
                    Cell cell = row.createCell(1);
                    cell.setCellStyle(styles.get("infoStyle"));
                    cell.setCellValue("Visited");
                }
            }

            if ((bitMask & IN_WAITING_LIST) == IN_WAITING_LIST) {
                for (Training training : waitingListTrainigs) {
                    Row row = sheet.createRow(freeRow++);
                    setTrainingRow(row, styles.get("infoStyle"), training, user);
                    Cell cell = row.createCell(1);
                    cell.setCellStyle(styles.get("infoStyle"));
                    cell.setCellValue("In waiting list");
                }
            }

            if ((bitMask & REJECT) == REJECT) {
                for (Training training : exTrainngs) {
                    Row row = sheet.createRow(freeRow++);
                    setTrainingRow(row, styles.get("infoStyle"), training, user);
                    Cell cell = row.createCell(1);
                    cell.setCellStyle(styles.get("infoStyle"));
                    cell.setCellValue("Refused");
                }
            }

            if ((bitMask & WEEKLY) == WEEKLY) {
                for (Training training : weeklyTrainigs) {
                    Row row = sheet.createRow(freeRow++);
                    setTrainingRow(row, styles.get("infoStyle"), training, user);
                    Cell cell = row.createCell(1);
                    cell.setCellStyle(styles.get("infoStyle"));
                    cell.setCellValue("Weekly");
                }
            }

            if ((bitMask & AS_TRAINER) == AS_TRAINER) {

                for (Training training : asTrainer) {
                    Row row = sheet.createRow(freeRow++);
                    CellStyle cellStyle = styles.get("infoStyle");
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
                    int freeCellTrainer = 0;
                    Cell cell = row.createCell(freeCellTrainer++);
                    cell.setCellStyle(cellStyle);
                    cell.setCellValue(user.getLastName() + "\n" + user.getFirstName());

                    cell = row.createCell(freeCellTrainer++);
                    cell.setCellStyle(styles.get("infoStyle"));
                    cell.setCellValue("As trainer");

                    cell = row.createCell(freeCellTrainer++);
                    cell.setCellStyle(cellStyle);
                    cell.setCellValue(training.getTitle());

                    if (!training.isRegular()) {
                        cell = row.createCell(freeCellTrainer++);
                        cell.setCellStyle(cellStyle);
                        cell.setCellValue(dateFormat.format(training.getDate()));
                    } else {
                        cell = row.createCell(freeCellTrainer++);
                        cell.setCellStyle(cellStyle);
                        cell.setCellValue("from: " + dateFormat.format(training.getStart()) + "\n" + "to: " + dateFormat.format(training.getEnd()));
                    }
                    freeCellTrainer += 2;
                    Cell goodFeedbackCell = row.createCell(freeCellTrainer++);
                    Cell badFeedbackCell = row.createCell(freeCellTrainer++);
                    badFeedbackCell.setCellStyle(cellStyle);
                    goodFeedbackCell.setCellStyle(cellStyle);
                    Set<TrainingFeedback> feedbacks = training.getTrainingFeedbacks();
                    for (TrainingFeedback feedback : feedbacks) {
                        if (feedback.getUser().getUserId() == user.getUserId()) {
                            if (feedback.getStarCount() > 4) {
                                goodFeedbackCell.setCellValue(goodFeedbackCell.getStringCellValue() + "\n " + dateFormat.format(feedback.getDate()) + ": " + feedback.getText());
                            } else {
                                badFeedbackCell.setCellValue(badFeedbackCell.getStringCellValue() + "\n " + dateFormat.format(feedback.getDate()) + ": " + feedback.getText());
                            }
                        }
                    }
                }
            }
        }

        if (userColumn != -1)
            sheet.autoSizeColumn(userColumn);
        if (trainerColumn != -1)
            sheet.autoSizeColumn(trainerColumn);
        if (dateColumn != -1)
            sheet.autoSizeColumn(dateColumn);

        return book;
    }

    public List<Training> getVisitedTrainings(long userId, long fromDate, long toDate) {
        if (toDate == 0)
            toDate = Long.MAX_VALUE;
        List<Training> trainings = trainingService.getTrainingsByVisitor(userId);
        List<Training> visitedTrainigs = new ArrayList<>();
        Date curentDate = new Date();

        for (Training training : trainings) {
            if (!training.isRegular()) {
                if (training.getDate().after(new Timestamp(fromDate)) && training.getDate().before(new Timestamp(toDate))) {
                    if (curentDate.after(training.getDate())) {
                        visitedTrainigs.add(training);
                    }
                }
            }
        }

        return visitedTrainigs;
    }

    public List<Training> getSubscribedTrainings(long userId, long fromDate, long toDate) {
        if (toDate == 0)
            toDate = Long.MAX_VALUE;
        List<Training> trainings = trainingService.getTrainingsByVisitor(userId);
        List<Training> subscribedTrainigs = new ArrayList<>();
        Date curentDate = new Date();

        for (Training training : trainings) {
            if (!training.isRegular()) {
                if (training.getDate().after(new Timestamp(fromDate)) && training.getDate().before(new Timestamp(toDate))) {
                    if (curentDate.before(training.getDate())) {
                        subscribedTrainigs.add(training);
                    }
                }
            }
        }

        return subscribedTrainigs;
    }

    public List<Training> getExTrainings(long userId, long fromDate, long toDate) {
        if (toDate == 0)
            toDate = Long.MAX_VALUE;
        User user = userService.getUserById(userId);
        List<Training> exTrainings = new ArrayList<>();
        for (Training training : userService.getExTrainings(userId)) {
            if (!training.isRegular()) {
                if (training.getDate().after(new Timestamp(fromDate)) && training.getDate().before(new Timestamp(toDate))) {
                    exTrainings.add(training);
                }
            }
        }

        return exTrainings;
    }

    public List<Training> getWaitingList(long userId, long fromDate, long toDate) {
        if (toDate == 0)
            toDate = Long.MAX_VALUE;
        User user = userService.getUserById(userId);
        List<Training> waitingListTrainigs = new ArrayList<Training>();
        for (WaitingList waitingList : user.getWaiting()) {
            Training training = waitingList.getTraining();
            if (!training.isRegular()) {
                if (training.getDate().after(new Timestamp(fromDate)) && training.getDate().before(new Timestamp(toDate))) {
                    waitingListTrainigs.add(training);
                }
            }
        }

        return waitingListTrainigs;
    }

    public List<Object> getEmployeFeedbacks(long userId) {
        return StatisticsUtil.customFeedbacjJson(employeeFeedbackService.getFeedbacksByUser(userId));
    }

    public List<Training> getTrainingsAsTrainer(long userId, long fromDate, long toDate) {
        if (toDate == 0)
            toDate = Long.MAX_VALUE;
        List<Training> trainingsAsTrainer = new ArrayList<>();
        List<Training> trainings = trainingService.getTrainingsByTrainer(userId);
        for (Training training : trainings) {
            if (!training.isRegular()) {
                if (training.getDate().after(new Timestamp(fromDate)) && training.getDate().before(new Timestamp(toDate))) {
                    trainingsAsTrainer.add(training);
                }
            }
            else {
                Training weeklyTraining = new Training();
                weeklyTraining.setLanguage(training.getLanguage());
                weeklyTraining.setLocation(training.getLocation());
                weeklyTraining.setTrainer(training.getTrainer());
                weeklyTraining.setDate(new Timestamp(training.getStart().getTime()));
                weeklyTraining.setTitle(training.getTitle());
                weeklyTraining.setTime(training.getTime());
                trainingsAsTrainer.add(weeklyTraining);
            }
        }

        return trainingsAsTrainer;
    }

    public List<Training> getWeeklyTrainings(long userId) {
        List<Training> weeklyTrainings = new ArrayList<>();
        List<Training> trainings = trainingService.getTrainingsByVisitor(userId);
        for (Training training : trainings) {
            if (training.isRegular()) {
                weeklyTrainings.add(training);
            }
        }

        return weeklyTrainings;
    }

    public List<Absence> getAbsenceList(long userId, long fromDate, long toDate) {
        if (toDate == 0)
            toDate = Long.MAX_VALUE;
        User user = userService.getUserById(userId);
        List<Absence> absences = new ArrayList<Absence>();
        absences.addAll(absenceService.getAbsencesByUser(userId));
        List<Absence> filtredAbsence = new ArrayList<Absence>();
        for (Absence absence : absences) {
            Training training = absence.getTraining();
            if (training.getStart().after(new Timestamp(fromDate)) && training.getStart().before(new Timestamp(toDate))) {
                filtredAbsence.add(absence);
            }
        }
        return filtredAbsence;
    }

    public List<AbsenceLesson> getAbsenceLessonsList(long userId, long fromDate, long toDate) {
        if (toDate == 0)
            toDate = Long.MAX_VALUE;
        User user = userService.getUserById(userId);
        List<AbsenceLesson> absences = new ArrayList<AbsenceLesson>();
        absences.addAll(absenceLessonService.getAbsencesByUser(userId));
        List<AbsenceLesson> filtredAbsence = new ArrayList<AbsenceLesson>();
        for (AbsenceLesson absence : absences) {
            RegularLesson lesson = absence.getLesson();
            if (lesson.getDate().after(new Timestamp(fromDate)) && lesson.getDate().before(new Timestamp(toDate))) {
                filtredAbsence.add(absence);
            }
        }
        return filtredAbsence;
    }

    private void setTrainingRow(Row row, CellStyle cellStyle, Training training, User user) {
        SimpleDateFormat feedbackDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        int freeCell = 0;
        Cell cell = row.createCell(freeCell++);
        cell.setCellStyle(cellStyle);
        cell.setCellValue(user.getLastName() + "\n" + user.getFirstName());
        freeCell++;

        cell = row.createCell(freeCell++);
        cell.setCellStyle(cellStyle);
        cell.setCellValue(training.getTitle());

        if (!training.isRegular()) {
            cell = row.createCell(freeCell++);
            cell.setCellStyle(cellStyle);
            cell.setCellValue(dateFormat.format(training.getDate()));
        } else {
            cell = row.createCell(freeCell++);
            cell.setCellStyle(cellStyle);
            cell.setCellValue("from: " + dateFormat.format(training.getStart()) + "\n" + "to: " + dateFormat.format(training.getEnd()));
        }

        cell = row.createCell(freeCell++);
        cell.setCellStyle(cellStyle);
        cell.setCellValue(training.getTrainer().getFirstName() + "\n" + training.getTrainer().getLastName());

        if (!training.isRegular()) {
            cell = row.createCell(freeCell++);
            cell.setCellStyle(cellStyle);
            Absence absence = absenceService.getAbsenceByUserAndTraining(user.getUserId(), training.getTrainingId());
            if (absence != null)
                cell.setCellValue(absence.getReasonText());
        } else {
            cell = row.createCell(freeCell++);
            cell.setCellStyle(cellStyle);
            List<AbsenceLesson> absences = absenceLessonService.getAbsencesByUserAndTraining(user.getUserId(), training.getTrainingId());
            for (AbsenceLesson absence : absences) {
                cell.setCellValue(cell.getStringCellValue() + dateFormat.format(absence.getLesson().getDate()) + " :\n");
                cell.setCellValue(cell.getStringCellValue() + absence.getReasonText() + "\n");
            }
        }

        Cell goodFeedbackCell = row.createCell(freeCell++);
        Cell badFeedbackCell = row.createCell(freeCell++);
        badFeedbackCell.setCellStyle(cellStyle);
        goodFeedbackCell.setCellStyle(cellStyle);
        Set<EmployeeFeedback> feedbacks = training.getTrainerFeedbacks();
        for (EmployeeFeedback feedback : feedbacks) {
            if (feedback.getUser().getUserId() == user.getUserId()) {
                if (feedback.getStarCount() > 4) {
                    goodFeedbackCell.setCellValue(goodFeedbackCell.getStringCellValue() + "\n " + feedbackDateFormat.format(feedback.getDate()) + ": " + feedback.getText());
                } else {
                    badFeedbackCell.setCellValue(badFeedbackCell.getStringCellValue() + "\n " + feedbackDateFormat.format(feedback.getDate()) + ": " + feedback.getText());
                }
            }
        }

    }
}